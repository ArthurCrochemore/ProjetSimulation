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
 * Regle de gestion priorisant la prise en charge des RDV Lorsqu'un patient
 * urgent est déclaré il est affecté aux salles reserve
 */
public class GPPrioriteRDV implements GestionPlanning {
	private Simulation simulation;

	private List<Patient> nouvListePatientRDV;
	private List<Patient> nouvListePatientUrgent;
	List<Salle> pileSalleRDV;
	List<Salle> pileSalleUrgent;

	Constantes constantes;
	LocalTime tempsMoyen;
	LocalTime heureActuelle;

	Map<Salle, List<TrouPlanning>> mapTrousParSalle;
	List<Salle> sallesTresEquipees; // Servira à chercher les trous possibles entre les
									// patientsUrgents pour placer les patientsRDV

	public GPPrioriteRDV(Simulation simulation) {
		this.simulation = simulation;
	}

	/**
	 * Méthode de résolution de la planification
	 * 
	 * @param patientUrgent, le patient urgent qui vient d'etre declare
	 */
	public Planning solution(Patient patientUrgent) {
		// Initialisation de la nouvelle liste de patients
		nouvListePatientRDV = new ArrayList<>();
		nouvListePatientUrgent = new ArrayList<>();
		List<Patient> listePatient;

		if (patientUrgent != null) {
			// Si un patient urgent est donné, récupérer le planning existant et l'ajouter à
			// la nouvelle liste
			Planning ancienPlanning = simulation.getPlanning();

			listePatient = ancienPlanning.extraiteDonnee();

			nouvListePatientUrgent.add(patientUrgent);
			System.out.println("changement de planning");
		} else {
			return new GPPrioritePremierArriveReserveStatique(simulation).solution(null); // On applique la règle de
																							// Gestion la plus simple
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

		// Tri de la liste de patient en fonction du temps d'arrivée
		Collections.sort(nouvListePatientRDV, (p1, p2) -> p1.getHeureArrive().compareTo(p2.getHeureArrive()));
		Collections.sort(nouvListePatientUrgent, (p1, p2) -> p1.getHeureArrive().compareTo(p2.getHeureArrive()));

		// Récupération des salles de la simulation
		Map<Salle.typeSalles, List<Salle>> sallesMap = simulation.getSalles();
		pileSalleUrgent = new ArrayList<Salle>(); // Pile utilisée pour placer les patients Urgents
		pileSalleRDV = new ArrayList<Salle>(); // Pile utilisée pour placer les patients RDV

		sallesTresEquipees = new ArrayList<Salle>();

		// Tri des salles
		Map<Salle, List<Patient>> renvoi = triDesSalles(sallesMap);

		renvoi = placementDesPatientsRDV(renvoi);
		
		mapTrousParSalle = TrouPlanning.RechercheTrouPlanning(sallesTresEquipees, mapTrousParSalle, renvoi, constantes,
				heureActuelle);

		return new Planning(placementDesPatientsUrgent(renvoi), heureActuelle, new ExtractionJSON(simulation));
	}

	/**
	 * Crée la pile des salles qui sera utilisé pour placer les patients
	 * 
	 * @param sallesMap
	 * @return renvoi, le map qui sera utiliser pout initialiser le planning avec
	 *         toutes ses listes initialiser
	 */
	private Map<Salle, List<Patient>> triDesSalles(Map<Salle.typeSalles, List<Salle>> sallesMap) {
		Map<Salle, List<Patient>> renvoi = new HashMap<Salle, List<Patient>>();
		mapTrousParSalle = new HashMap<>(); // Map qui stokera les trous trouvés

		Salle.typeSalles type;
		for (Salle.listeEtats etat : Salle.listeEtats.values()) {
			type = Salle.typeSalles.RESERVE;
			if (sallesMap.containsKey(type)) {
				for (Salle salle : sallesMap.get(type)) {
					if (salle.getEtat() == etat) {
						pileSalleUrgent.add(salle);
						renvoi.put(salle, new ArrayList<Patient>());

						LocalTime tempsMoyenEnFonctionEtat = constantes.getTempsMoyen(etat);

						List<TrouPlanning> listeTrous = new ArrayList<>();
						listeTrous.add(TrouPlanning.CreerPlaningAvecHeureDebutTheoriqueEtHeureLimite(
								heureActuelle.plusMinutes(tempsMoyenEnFonctionEtat.getMinute())
										.plusHours(tempsMoyenEnFonctionEtat.getHour()),
								LocalTime.of(23, 59, 0), 0, salle, tempsMoyen));
						mapTrousParSalle.put(salle, listeTrous);
					}
				}
			}

			type = Salle.typeSalles.PEUEQUIPE;
			if (sallesMap.containsKey(type)) {
				for (Salle salle : sallesMap.get(type)) {
					if (salle.getEtat() == etat) {
						pileSalleRDV.add(salle);
						renvoi.put(salle, new ArrayList<Patient>());
					}
				}
			}

			type = Salle.typeSalles.SEMIEQUIPE;
			if (sallesMap.containsKey(type)) {
				for (Salle salle : sallesMap.get(type)) {
					if (salle.getEtat() == etat) {
						pileSalleRDV.add(salle);
						renvoi.put(salle, new ArrayList<Patient>());
					}
				}
			}

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
	 * Méthode qui gère l'affectation des patients Urgents dans les salles
	 * 
	 * @param renvoi
	 * @return renvoi, la map qui permettra de faire le planning
	 */
	private Map<Salle, List<Patient>> placementDesPatientsUrgent(Map<Salle, List<Patient>> renvoi) {
		for (Patient patient : nouvListePatientUrgent) {
			
			LocalTime heureArrivePatient = patient.getHeureArrive();
			Map<Salle, LocalTime> mapPourTrie = new HashMap<>();

			for (Salle salle : mapTrousParSalle.keySet()) {
				while (mapTrousParSalle.get(salle).size() > 1 && mapTrousParSalle.get(salle).get(0)
						.getHeureLimite().isBefore(heureArrivePatient)) {
					mapTrousParSalle.get(salle).remove(0);
				}

				mapPourTrie.put(salle, mapTrousParSalle.get(salle).get(0).getHeureLimite());
			}
			pileSalleUrgent = new ArrayList<>(mapPourTrie.entrySet().stream().sorted(Entry.comparingByValue())
					.collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new))
					.keySet());

			Salle salle = pileSalleUrgent.get(0);

			TrouPlanning trou = mapTrousParSalle.get(salle).get(0);
			renvoi.get(salle).add(trou.getIndice(), patient);

			for (TrouPlanning trouAIncrementer : mapTrousParSalle.get(salle)) {
				trouAIncrementer.incrementerIndice();
			}
			
			pileSalleUrgent.remove(0);
			pileSalleUrgent.add(salle);

			if (mapTrousParSalle.get(salle).get(0).miseAjourTrou(patient.getHeureArrive(), tempsMoyen,
					simulation.getHeureFinSimulation()) == null) {
				mapTrousParSalle.get(salle).remove(0);

			}
		}

		return renvoi;

	}

	/**
	 * Méthode qui gère l'affectation des patients RDV dans les salles
	 * 
	 * @param renvoi
	 * @return renvoi, la map qui permettra de faire le planning
	 */
	private Map<Salle, List<Patient>> placementDesPatientsRDV(Map<Salle, List<Patient>> renvoi) {
		for (Patient patient : nouvListePatientRDV) {
			int indice = 0;

			boolean place = false;
			Salle salle;
			while (!place && indice < pileSalleRDV.size()) {
				salle = pileSalleRDV.get(indice);

				if (patient.getGravite() == PatientRDV.listeGravite.TRESEQUIPE) {
					if (salle.getType() == Salle.typeSalles.TRESEQUIPE) {
						renvoi.get(salle).add(patient);
						place = true;

						pileSalleRDV.remove(indice);
						pileSalleRDV.add(salle);
					}
				} else {
					if (patient.getGravite() == PatientRDV.listeGravite.SEMIEQUIPE) {
						if (salle.getType() == Salle.typeSalles.TRESEQUIPE
								|| salle.getType() == Salle.typeSalles.SEMIEQUIPE) {
							renvoi.get(salle).add(patient);
							place = true;

							pileSalleRDV.remove(indice);
							pileSalleRDV.add(salle);
						}
					} else {
						if (salle.getType() == Salle.typeSalles.TRESEQUIPE
								|| salle.getType() == Salle.typeSalles.SEMIEQUIPE
								|| salle.getType() == Salle.typeSalles.PEUEQUIPE) {

							renvoi.get(salle).add(patient);
							place = true;

							pileSalleRDV.remove(indice);
							pileSalleRDV.add(salle);
						}
					}
				}

				indice++;

			}
		}

		return renvoi;
	}
}