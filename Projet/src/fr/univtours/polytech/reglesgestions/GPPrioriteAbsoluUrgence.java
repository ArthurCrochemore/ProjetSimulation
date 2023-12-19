package fr.univtours.polytech.reglesgestions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.univtours.polytech.Constantes;
import fr.univtours.polytech.Planning;
import fr.univtours.polytech.Simulation;
import fr.univtours.polytech.entite.Patient;
import fr.univtours.polytech.entite.PatientRDV;
import fr.univtours.polytech.ressource.Salle;
import fr.univtours.polytech.util.TrouPlanning;

/**
 * Regle de gestion qui lorsque qu'un patient urgent est declare dans la
 * simulation le planning change en mettant en premier dans la liste d'attente
 * des salles tres equipe le patient urgent
 */
public class GPPrioriteAbsoluUrgence implements GestionPlanning {
	private Simulation simulation;

	public GPPrioriteAbsoluUrgence(Simulation simulation) {
		this.simulation = simulation;
	}

	public Planning solution(Patient patientUrgent) {
		// Initialisation de la nouvelle liste de patients
		List<Patient> nouvListePatientRDV = new ArrayList<>();
		List<Patient> nouvListePatientUrgent = new ArrayList<>();
		List<Patient> listePatient;

		if (patientUrgent != null) {
			// Si un patient urgent est donné, récupérer le planning existant et l'ajouter à
			// la nouvelle liste
			Planning ancienPlanning = simulation.getPlanning();

			listePatient = ancienPlanning.extraiteDonnee();

			nouvListePatientUrgent.add(patientUrgent);
			System.out.println("changememnt planning");
		} else {
			return new GPPrioritePremierArriveReserveStatique(simulation).solution(null); // On applique la règle de Gestion la plus simple
		}

		for (Patient p : listePatient) {
			if (p.estUrgent()) {
				nouvListePatientUrgent.add(p);
			} else {
				nouvListePatientRDV.add(p);
			}
		}

		// Tri de la liste de patient en fcontion du temps d'arrivée
		Collections.sort(nouvListePatientRDV, (p1, p2) -> p1.getHeureArrive().compareTo(p2.getHeureArrive()));
		Collections.sort(nouvListePatientUrgent, (p1, p2) -> p1.getHeureArrive().compareTo(p2.getHeureArrive()));

		// Récupération des salles de la simulation
		Map<Salle.typeSalles, List<Salle>> sallesMap = simulation.getSalles();
		List<Salle> pileSalleUrgent = new ArrayList<Salle>(); // Pile utilisée pour placer les patients Urgents
		List<Salle> pileSalleRDV = new ArrayList<Salle>(); // Pile utilisée pour placer les patients RDV
		
		List<Salle> sallesTresEquipees = new ArrayList<Salle>(); // Servira à chercher les trous possibles entre les patientsUrgents pour placer les patientsRDV
		Map<Salle, List<TrouPlanning>> mapTrousParSalle; // Map qui stokera les trous trouvés
		
		Map<Salle, List<Patient>> renvoi = new HashMap<Salle, List<Patient>>(); // Le Map qui sera stocker dans le planning
		
		Constantes constantes = simulation.getConstantes();
		
		// Tri des salles
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
			
			type = Salle.typeSalles.PEUEQUIPE;
			if (sallesMap.containsKey(type)) {
				for (Salle salle : sallesMap.get(type)) {
					if (salle.getEtat() == etat) {
						pileSalleRDV.add(salle);
						renvoi.put(salle, new ArrayList<Patient>());
						
						List<TrouPlanning> listeTrous = new ArrayList<>();
						listeTrous.add(new TrouPlanning(constantes.get));
						mapTrousParSalle.put(salle, listeTrous);
					}
				}
			}
			
			type = Salle.typeSalles.RESERVE;
			if (sallesMap.containsKey(type)) {
				for (Salle salle : sallesMap.get(type)) {
					if (salle.getEtat() == etat) {
						pileSalleUrgent.add(salle);
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

		for (Patient patient : nouvListePatientRDV) {
//			System.out.println("On place le patient " + patient.getId() + "   - Gravite / urgent : "
//					+ patient.getGravite() + " / " + patient.estUrgent());

			int indice = 0;

			boolean place = false;
			Salle salle;
			while (!place) {
				salle = pileSalle.get(indice);
				// System.out.println("Salle " + salle.getId() + " - Gravite : " +
				// salle.getType());

				if (salle.getType() == Salle.typeSalles.TRESEQUIPE || salle.getType() == Salle.typeSalles.RESERVE) {
					renvoi.get(salle).add(patient);
					place = true;

					pileSalle.remove(indice);
					pileSalle.add(salle);
				}

			}

			indice++;
		}
	}

	return new Planning(renvoi);
}}
