package fr.univtours.polytech.util;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fr.univtours.polytech.Constantes;
import fr.univtours.polytech.entite.Patient;
import fr.univtours.polytech.ressource.Salle;

/**
 * Cette classe contient des méthode utile pour les règles de gestion
 * PrioriteAbsoluUrgence et PrioriteRDV. Pour ces règles on place d'abords un
 * type de Patient puis le second type donc lorsque l'on place le 2e type on
 * doit regarder s'il y a pas moyen qu'un patient passe entre 2 patients dèjà
 * placé. Le but de cette classe est donc de représenter ces trous
 */
public class TrouPlanning {
	private LocalTime heureDebutTheorique; // Heure théorique à laquelle la salle devient disponible
	private LocalTime heureLimite; // Heure limite à laquelle peut être glissé un patient (heure où la salle
									// devient indisponible - le temps moyen estimé d'occupation d'une salle)
	private int indice; // Indice auquel le Patient doit être introduit dans la liste de Patient pour
						// boucher le trou
	private Salle salle; // Salle où se trouve le trou

	/**
	 * Methode qui permet de trouver ou peuvent etre glisser des patients dans les
	 * listes d'affectation des salles Est utilisé pour les règles de gestion
	 * Priorite Absolu Urgence et Priorite RDV pour, respectivement, placé des
	 * patients RDV entre des patients urgents et placé des patients urgents entre
	 * des patients RDV
	 * 
	 * @param listeSalleTresEquipe, liste des salles pour lesquels on cherche à
	 *                              glisser des patients (seulement des salles TE
	 *                              car c'est les seules salles qui peuvent etre
	 *                              partage entre les patients RDV et les patients
	 *                              urgents
	 * @param trousMap,             le map qui contient les "trous" intervalles dans
	 *                              lesquels peuvent être glissé des patients, qui
	 *                              doit donc etre modifier puis retourne
	 * @param planning,             le placement actuel des patients dans les salles
	 * @param constantes,           objet Constantes permettant d'acceder aux
	 *                              constantes
	 * @param heureActuelle,        heure de la simulation au moment où cette
	 *                              methode est appelée
	 * @return trousMap
	 */
	public static Map<Salle, List<TrouPlanning>> RechercheTrouPlanning(List<Salle> listeSalleTresEquipe,
			Map<Salle, List<TrouPlanning>> trousMap, Map<Salle, List<Patient>> planning, Constantes constantes,
			LocalTime heureActuelle) {

		LocalTime tempsMoyen = constantes.getTempsMoyen();

		/**
		 * On recherche les trous pour chaque salles TE
		 */
		for (Salle salleTE : listeSalleTresEquipe) {
			List<Patient> listePatientTE = planning.get(salleTE); // On récupère la liste des patients affectés à la
																	// salle
			List<TrouPlanning> listeTrouPlanning = new ArrayList<TrouPlanning>(); // On crée la liste qui stockera les
																					// trous

			LocalTime debutTrouFinal = heureActuelle;

			/*
			 * Si des patients sont dèjà placé dans la salle, sinon cele ne sert à rien de
			 * chercher des trous
			 */
			if (listePatientTE.size() > 0) {
				Patient patient1 = null; // Représentera le patient après lequel on cherche à placer un patient
				Patient patient2 = listePatientTE.get(0); // Représentera le patient avant lequel on cherche à placer un
															// patient

				/* 1er trou : avant le 1er patient */
				LocalTime tempsMoyenEnFonctionEtat = constantes.getTempsMoyen(salleTE.getEtat()); // On regarde dans
				// combien de temps la salle risque d'être libéré, dépend de le patient en est
				// dans son opération

				TrouPlanning trou = CreerPlaningAvecHeureDebutTheorique(somme(heureActuelle, tempsMoyenEnFonctionEtat),
						patient2.getHeureArrive(), 0, salleTE, tempsMoyen); // On essayes de crée un trou entre le
																			// moment où la salle va être libére et
																			// l'heure d'arrivé du 1er patient placé

				/*
				 * Si CreerPlaningAvecHeureDebutTheorique n'a pas renvoyé null, et donc que
				 * l'intervalle est suffisant pour créer un trou
				 */
				if (trou != null) {
					listeTrouPlanning.add(trou); // On ajoute le trou au map de trous
				}

				/* On regarde maintenant entre chaque deux patients qui se suivent */
				for (int i = 0; i < listePatientTE.size() - 1; i++) {
					patient1 = patient2;
					patient2 = listePatientTE.get(i + 1);

					trou = CreerPlaning(patient1.getHeureArrive(), patient2.getHeureArrive(), i + 1, salleTE,
							tempsMoyen);

					/*
					 * Si CreerPlaning n'a pas renvoyé null, et donc que l'intervalle est suffisant
					 * pour créer un trou
					 */
					if (trou != null) {
						listeTrouPlanning.add(trou); // On ajoute le trou au map de trous
					}
				}

				/*
				 * On enregistre l'heure théorique à laquelle sort le dernier patient pour créer
				 * le "trou final"
				 */
				debutTrouFinal = somme(listePatientTE.get(listePatientTE.size() - 1).getHeureArrive(), tempsMoyen);
			}

			/*
			 * Enfin, on place le "trou final" c'est à dire entre le dernier patient (s'il y
			 * a des patients) et le plus tard possible (23h59 puisque l'on utilise des
			 * LocalTime)
			 */
			listeTrouPlanning.add(CreerPlaningAvecHeureDebutTheoriqueEtHeureLimite(debutTrouFinal, LocalTime.of(23, 59),
					listePatientTE.size(), salleTE, tempsMoyen));

			trousMap.put(salleTE, listeTrouPlanning); // On met à jour le map de trous
		}

		return trousMap; // On renvoi le map de Trou qui a été modifié
	}

	/**
	 * Méthode utile pour mettre à jour un trou. Elle est appelée lorsque qu'un
	 * patient est placé dans un trou, permet de savoir s'il existe toujours un trou
	 * après ce patient qui vient d'être placé
	 * 
	 * @param heureArrivee, heure d'arrivee du patient qui vient d'être placé
	 * @param tempsMoyen,   temps estimé d'une opération
	 * @param heureFin,     heure de fin de la simulation
	 * @return TrouPlanning, renvoi le trou s'il existe toujours un trou et null
	 *         sinon
	 */
	public TrouPlanning miseAjourTrou(LocalTime heureArrivee, LocalTime tempsMoyen, LocalTime heureFin) {
		LocalTime heureDebutTheorique = somme(heureArrivee, tempsMoyen); // On calcul l'heureDebutTheorique

		/* Si la nouvelle heureDebutTheorique est après l'heureLimite */
		if (heureDebutTheorique.isAfter(heureLimite)) {
			/* Si le trou n'est pas le "trou final" */
			if (heureLimite != LocalTime.of(23, 59)) {
				return null; // Le trou est alors rempli, on renvoit donc null
			}
		}
		/* Sinon, on met à jour l'heureDebutTheorique */
		else {
			this.heureDebutTheorique = heureDebutTheorique;
		}

		return this;
	}

	/**
	 * Méthode qui permet de gérer la création de Planing
	 * 
	 * @param heureDebutTheorique
	 * @param heureLimite
	 * @param indice
	 * @param salle
	 * @param tempsMoyen
	 * @return TrouPlanning, le trou qui vient d'être crée ou null si ce n'est pas
	 *         possible
	 */
	public static TrouPlanning CreerPlaningAvecHeureDebutTheoriqueEtHeureLimite(LocalTime heureDebutTheorique,
			LocalTime heureLimite, int indice, Salle salle, LocalTime tempsMoyen) {
		try {
			/* Si le trou est trop court */
			if (heureDebutTheorique.isAfter(heureLimite))
				throw new IllegalArgumentException("Erreur : Le Trou cree est trop court");

			/* Sinon, on crée le TrouPlanning et on le renvoit */
			return new TrouPlanning(heureDebutTheorique, heureLimite, indice, salle);
		} catch (IllegalArgumentException e) {
			return null; // On renvoit null
		}
	}

	/**
	 * Méthode qui calcule l'heureLimite puis qui gére la création de Planing
	 * 
	 * @param heureDebutTheorique
	 * @param heureArriveePatient2, l'heure à laquelle arrive le patient après le
	 *                              trou
	 * @param indice
	 * @param salle
	 * @param tempsMoyen
	 * @return TrouPlanning, le trou qui vient d'être crée ou null si ce n'est pas
	 *         possible
	 */
	public static TrouPlanning CreerPlaningAvecHeureDebutTheorique(LocalTime heureDebutTheorique,
			LocalTime heureArriveePatient2, int indice, Salle salle, LocalTime tempsMoyen) {
		LocalTime heureLimiteDebutNouveauPatient = soustraction(heureArriveePatient2, tempsMoyen);

		return CreerPlaningAvecHeureDebutTheoriqueEtHeureLimite(heureDebutTheorique, heureLimiteDebutNouveauPatient,
				indice, salle, tempsMoyen);
	}

	/**
	 * Méthode qui calcule l'heureDebutTheorique puis l'heureLimite puis qui gére la
	 * création de Planing
	 * 
	 * @param heureArriveePatient1, l'heure à laquelle arrive le patient avant le
	 *                              trou
	 * @param heureArriveePatient2, l'heure à laquelle arrive le patient après le
	 *                              trou
	 * @param indice
	 * @param salle
	 * @param tempsMoyen
	 * @return TrouPlanning, le trou qui vient d'être crée ou null si ce n'est pas
	 *         possible
	 */
	public static TrouPlanning CreerPlaning(LocalTime heureArriveePatient1, LocalTime heureArriveePatient2, int indice,
			Salle salle, LocalTime tempsMoyen) {

		LocalTime heureDebutTheorique = somme(heureArriveePatient1, tempsMoyen);

		return CreerPlaningAvecHeureDebutTheorique(heureDebutTheorique, heureArriveePatient2, indice, salle,
				tempsMoyen);
	}

	/**
	 * Méthode qui gère l'incrémentation de indice, utilisé lorsque un trou est
	 * bouché, tout les autres trous de la salle doivent alors mettre à jour leur
	 * indice
	 */
	public void incrementerIndice() {
		indice++;
	}

	public LocalTime getHeureDebutTheorique() {
		return heureDebutTheorique;
	}

	public LocalTime getHeureLimite() {
		return heureLimite;
	}

	public int getIndice() {
		return indice;
	}

	public Salle getSalle() {
		return salle;
	}

	private TrouPlanning(LocalTime heureDebutTheorique, LocalTime heureLimite, int indice, Salle salle) {
		this.heureDebutTheorique = heureDebutTheorique;
		this.heureLimite = heureLimite;
		this.indice = indice;
		this.salle = salle;
	}

	/**
	 * Méthode pour faire la somme de deux LocalTime
	 * 
	 * @param heure1
	 * @param heure2
	 * @return heure1 + heure2
	 */
	private static LocalTime somme(LocalTime heure1, LocalTime heure2) {
		LocalTime renvoi = heure1.plusHours(heure2.getHour()).plusMinutes(heure2.getMinute());

		/* Pour éviter les résultats incohérents */
		if (renvoi.isBefore(heure1) || renvoi.isBefore(heure2)) {
			return LocalTime.of(23, 59);
		}

		return renvoi;
	}

	/**
	 * Méthode pour faire la soustraction de deux LocalTime
	 * 
	 * @param heure1
	 * @param heure2
	 * @return heure1 - heure2
	 */
	private static LocalTime soustraction(LocalTime heure1, LocalTime heure2) {
		LocalTime renvoi = heure1.minusHours(heure2.getHour()).minusMinutes(heure2.getMinute());

		/* Pour éviter les résultats incohérents */
		if (heure2.isAfter(heure1)) {
			return LocalTime.of(0, 0);
		}

		return renvoi;
	}
}
