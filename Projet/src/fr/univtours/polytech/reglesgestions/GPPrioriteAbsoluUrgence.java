package fr.univtours.polytech.reglesgestions;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import fr.univtours.polytech.Constantes;
import fr.univtours.polytech.ExtractionJSON;
import fr.univtours.polytech.Planning;
import fr.univtours.polytech.Simulation;
import fr.univtours.polytech.entite.Patient;
import fr.univtours.polytech.entite.PatientRDV;
import fr.univtours.polytech.ressource.Salle;
import fr.univtours.polytech.util.TrouPlanning;

/**
 * Regle de gestion qui place d'abords les patients urgents puis les patients
 * RDV
 */
public class GPPrioriteAbsoluUrgence implements GestionPlanning {
	private Simulation simulation;

	private List<Patient> nouvListePatientRDV;
	private List<Patient> nouvListePatientUrgent;
	private List<Salle> pileSalleRDV;
	private List<Salle> pileSalleUrgent;

	private Constantes constantes;
	private LocalTime tempsMoyen;
	private LocalTime heureActuelle;

	private Map<Salle, List<TrouPlanning>> mapTrousParSalle;
	private List<Salle> sallesTresEquipees; // Servira à chercher les trous possibles entre les
											// patientsUrgents pour placer les patientsRDV

	public GPPrioriteAbsoluUrgence(Simulation simulation) {
		this.simulation = simulation;
	}

	/**
	 * Méthode de résolution de la planification
	 * 
	 * @param patientUrgent, le patient urgent qui vient d'etre declare
	 */
	public Planning solution(Patient patientUrgent) {
		/* Initialisation des nouvelles listes de patients RDV et urgents */
		nouvListePatientRDV = new ArrayList<>();
		nouvListePatientUrgent = new ArrayList<>();
		List<Patient> listePatient;

		if (patientUrgent != null) {
			/*
			 * Si un patient urgent est donné, récupérer le planning existant et l'ajouter à
			 * la nouvelle liste
			 */
			Planning ancienPlanning = simulation.getPlanning();

			listePatient = ancienPlanning.extraiteDonnee();

			nouvListePatientUrgent.add(patientUrgent);
			System.out.println("changement de planning");
		} else {
			/* Sinon, on applique la regle de gestion la plus simple */
			return new GPPrioritePremierArriveReserveStatique(simulation).solution(null);
		}

		for (Patient p : listePatient) {
			if (p.estUrgent()) {
				nouvListePatientUrgent.add(p);
			} else {
				nouvListePatientRDV.add(p);
			}
		}

		constantes = simulation.getConstantes();
		tempsMoyen = constantes.getTempsMoyen();
		heureActuelle = simulation.getDeroulement().getHeureSimulation();

		/* Tri des listes de patient en fonction du temps d'arrivée */
		Collections.sort(nouvListePatientRDV, (p1, p2) -> p1.getHeureArrive().compareTo(p2.getHeureArrive()));
		Collections.sort(nouvListePatientUrgent, (p1, p2) -> p1.getHeureArrive().compareTo(p2.getHeureArrive()));

		/* Récupération des salles de la simulation */
		Map<Salle.typeSalles, List<Salle>> sallesMap = simulation.getSalles();
		pileSalleUrgent = new ArrayList<Salle>(); // Pile utilisée pour placer les patients Urgents
		pileSalleRDV = new ArrayList<Salle>(); // Pile utilisée pour placer les patients RDV

		sallesTresEquipees = new ArrayList<Salle>();

		/*
		 * Tri des salles, initialisation des piles et de renvoi, qui correspond au
		 * futur attribut planning de l'objet Planning
		 */
		Map<Salle, List<Patient>> renvoi = triDesSalles(sallesMap);

		renvoi = placementDesPatientsUrgent(renvoi);

		mapTrousParSalle = TrouPlanning.RechercheTrouPlanning(sallesTresEquipees, mapTrousParSalle, renvoi, constantes,
				heureActuelle); // On recherche les trous entre les patients urgents déjà place

		renvoi = placementDesPatientsRDV(renvoi);

		return new Planning(renvoi, heureActuelle, new ExtractionJSON(simulation));
	}

	/**
	 * Crée la pile des salles qui sera utilisé pour placer les patients et
	 * intialise renvoi et mapTrousParSalle
	 * 
	 * @param sallesMap
	 * @return renvoi, le map qui sera utiliser pout initialiser le planning avec
	 *         toutes ses listes initialiser
	 */
	private Map<Salle, List<Patient>> triDesSalles(Map<Salle.typeSalles, List<Salle>> sallesMap) {
		Map<Salle, List<Patient>> renvoi = new HashMap<Salle, List<Patient>>();
		mapTrousParSalle = new HashMap<>(); // Map qui stokera les trous trouvés

		/**
		 * Pour toutes les salles reservees on ajoute la salle à la pile pour les
		 * patient urgents, seul eux peuvent être affecté à ces salles
		 */
		Salle.typeSalles type;
		for (Salle.listeEtats etat : Salle.listeEtats.values()) {
			type = Salle.typeSalles.RESERVE;
			if (sallesMap.containsKey(type)) {
				for (Salle salle : sallesMap.get(type)) {
					if (salle.getEtat() == etat) {
						pileSalleUrgent.add(salle);
						renvoi.put(salle, new ArrayList<Patient>());
					}
				}
			}

			/**
			 * Pour toutes les salles PE on ajoute la salle à la pile pour les patient RDV,
			 * seul eux peuvent être affecté à ces salles, et on initialise un "trou final"
			 * dans mapTrousParSalle
			 */
			type = Salle.typeSalles.PEUEQUIPE;
			if (sallesMap.containsKey(type)) {
				for (Salle salle : sallesMap.get(type)) {
					if (salle.getEtat() == etat) {
						pileSalleRDV.add(salle);
						renvoi.put(salle, new ArrayList<Patient>());

						LocalTime tempsMoyenEnFonctionEtat = constantes.getTempsMoyen(etat);

						List<TrouPlanning> listeTrous = new ArrayList<>();
						listeTrous.add(TrouPlanning.CreerPlaningAvecHeureDebutTheoriqueEtHeureLimite(
								heureActuelle.plusMinutes(tempsMoyenEnFonctionEtat.getMinute())
										.plusHours(tempsMoyenEnFonctionEtat.getHour()),
								TrouPlanning.getHeureLimiteTrouFinal(), 0, salle, tempsMoyen));
						mapTrousParSalle.put(salle, listeTrous);
					}
				}
			}

			/**
			 * Pour toutes les salles SE on ajoute la salle à la pile pour les patient RDV,
			 * seul eux peuvent être affecté à ces salles, et on initialise un "trou final"
			 * dans mapTrousParSalle
			 */
			type = Salle.typeSalles.SEMIEQUIPE;
			if (sallesMap.containsKey(type)) {
				for (Salle salle : sallesMap.get(type)) {
					if (salle.getEtat() == etat) {
						pileSalleRDV.add(salle);
						renvoi.put(salle, new ArrayList<Patient>());

						LocalTime tempsMoyenEnFonctionEtat = constantes.getTempsMoyen(etat);

						List<TrouPlanning> listeTrous = new ArrayList<>();
						listeTrous.add(TrouPlanning.CreerPlaningAvecHeureDebutTheoriqueEtHeureLimite(
								heureActuelle.plusMinutes(tempsMoyenEnFonctionEtat.getMinute())
										.plusHours(tempsMoyenEnFonctionEtat.getHour()),
								TrouPlanning.getHeureLimiteTrouFinal(), 0, salle, tempsMoyen));
						mapTrousParSalle.put(salle, listeTrous);
					}
				}
			}

			/**
			 * Pour toutes les salles TE on ajoute la salle aux deux pile et on les ajoute à
			 * sallesTresEquipees qui servira à rechercher les trous après avoir affecté les
			 * patients urgents (à l'aide de RechercheTrouPlanning)
			 */
			type = Salle.typeSalles.TRESEQUIPE;
			if (sallesMap.containsKey(type)) {
				for (Salle salle : sallesMap.get(type)) {
					if (salle.getEtat() == etat) {
						pileSalleUrgent.add(salle);
						pileSalleRDV.add(salle);
						sallesTresEquipees.add(salle);

						renvoi.put(salle, new ArrayList<Patient>());
					}
				}
			}

		}
		return renvoi;
	}

	/**
	 * Méthode qui gère l'affectation des patients urgents dans les salles, ici il
	 * s'agit simplement de les placer les un à la suite des autres en prennant
	 * chaque salle une par une
	 * 
	 * @param renvoi
	 * @return renvoi, la map qui permettra de faire le planning
	 */
	private Map<Salle, List<Patient>> placementDesPatientsUrgent(Map<Salle, List<Patient>> renvoi) {
		for (Patient patient : nouvListePatientUrgent) {

			Salle salle = pileSalleUrgent.get(0);// La 1ere salle est forcement compatible (il n'y a sue des salles TE
													// et Reservees)
			renvoi.get(salle).add(patient);

			/* On passe la salle en bas de la pile */
			pileSalleUrgent.remove(0);
			pileSalleUrgent.add(salle);
		}
		return renvoi;
	}

	/**
	 * Méthode qui gère l'affectation des patients RDV dans les salles, ici il
	 * s'agit de les placer entre les patients urgents déjà placé puis de de les
	 * placer les un à la suite des autres en prennant chaque salle une par une
	 * 
	 * @param renvoi
	 * @return renvoi, la map qui permettra de faire le planning
	 */
	private Map<Salle, List<Patient>> placementDesPatientsRDV(Map<Salle, List<Patient>> renvoi) {
		List<Salle> mapTrousParSalleKeySet = new ArrayList<>(mapTrousParSalle.keySet());

		for (Patient patient : nouvListePatientRDV) {
			LocalTime heureArrivePatient = patient.getHeureArrive();
			Map<Salle, LocalTime> mapPourTrie = new HashMap<>();

			/*
			 * On supprime les trous qui sont devenus obsolètes (les patients que l'on
			 * regarde arrive après leur heureLimite
			 */
			for (Salle salle : mapTrousParSalleKeySet) {
				while (mapTrousParSalle.get(salle).size() > 1 // On garde tout de même le "trou final"
						&& mapTrousParSalle.get(salle).get(0).getHeureLimite().isBefore(heureArrivePatient)) {
					mapTrousParSalle.get(salle).remove(0);
				}

				/*
				 * On sauvegarde l'heureDebutTheorique du trou le plus cohérent comme réference
				 * pour le tri des salles
				 */
				mapPourTrie.put(salle, mapTrousParSalle.get(salle).get(0).getHeureDebutTheorique());
			}
			/* On tri de nouveau la pile de salles (les trous risquent d'avoir changer) */
			pileSalleRDV = new ArrayList<>(mapPourTrie.entrySet().stream().sorted(Entry.comparingByValue())
					.collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new))
					.keySet());

			int indice = 0;

			boolean place = false;

			/* On répète l'opération jusqu'à ce que le patient soit affecté à une salle */
			while (!place && indice < pileSalleRDV.size()) {
				Salle salle = pileSalleRDV.get(indice);

				/* Si la salle est TE et le patient a une gravité TE, c'est bon */
				if (patient.getGravite() == PatientRDV.listeGravite.TRESEQUIPE) {
					if (salle.getType() == Salle.typeSalles.TRESEQUIPE) {

						renvoi = placerDansTrous(salle, patient, renvoi, indice);
						place = true;
					}
				} else {
					/* Sinon, si la salle est TE ou SE et le patient a une gravité SE, c'est bon */
					if (patient.getGravite() == PatientRDV.listeGravite.SEMIEQUIPE) {
						if (salle.getType() == Salle.typeSalles.TRESEQUIPE
								|| salle.getType() == Salle.typeSalles.SEMIEQUIPE) {

							renvoi = placerDansTrous(salle, patient, renvoi, indice);
							place = true;
						}
					} else {
						/*
						 * Sinon, si la salle est TE, SE ou PE et le patient a une gravité PE, c'est bon
						 */
						if (salle.getType() == Salle.typeSalles.TRESEQUIPE
								|| salle.getType() == Salle.typeSalles.SEMIEQUIPE
								|| salle.getType() == Salle.typeSalles.PEUEQUIPE) {

							renvoi = placerDansTrous(salle, patient, renvoi, indice);
							place = true;
						}
					}
				}

				indice++;

			}
		}

		return renvoi;
	}

	/**
	 * Méthode qui évite le code répetitif dans placementDesPatientsRDV. Gère le
	 * placement dans patient dans un trou + met à jour les indices de tout les
	 * autres trous de la salle et met à jour le trou si c'est possible
	 * 
	 * @param salle
	 * @param patient
	 * @param renvoi
	 * @param indice
	 * @return
	 */
	private Map<Salle, List<Patient>> placerDansTrous(Salle salle, Patient patient, Map<Salle, List<Patient>> renvoi,
			int indice) {

		TrouPlanning trou = mapTrousParSalle.get(salle).get(0); // On recupere le trou
		renvoi.get(salle).add(trou.getIndice(), patient); // On bouche le trou avec le patient

		/* Pour chaque trous de la salle choisie, on met à jour leur indice */
		for (TrouPlanning trouAIncrementer : mapTrousParSalle.get(salle)) {
			trouAIncrementer.incrementerIndice();
		}

		/* On met à jour le trou, si il est devenu incohérent, on le supprime */
		if (mapTrousParSalle.get(salle).get(0).miseAjourTrou(patient.getHeureArrive(), tempsMoyen,
				heureActuelle) == null) {
			mapTrousParSalle.get(salle).remove(0);
		}

		return renvoi;
	}
}
