package fr.univtours.polytech.reglesgestions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.univtours.polytech.Planning;
import fr.univtours.polytech.Simulation;
import fr.univtours.polytech.entite.Patient;
import fr.univtours.polytech.entite.PatientUrgent;
import fr.univtours.polytech.ressource.Salle;

/**
 * Regle de gestion priorisant le patient arriver en premier dans la file
 * d'attente pour la prochaine operation
 */
public class GPPrioritePremierArriveReserveStatique implements GestionPlanning {
	private Simulation simulation;

	public GPPrioritePremierArriveReserveStatique(Simulation simulation) {
		this.simulation = simulation;
	}

	public Planning solution(PatientUrgent patientUrgent) {
		// Recuperation de l'ancien planning

		Planning ancienPlanning = simulation.getPlanning();
		List<Patient> nouvListePatient = ancienPlanning.extraiteDonnee();

		// Mise a jour de la liste de patient avec l'ajout du nouveau patient urgent

		nouvListePatient.add(patientUrgent);
		Collection<List<Salle>> pileSalle = simulation.getSalles().values();

		// Tri de la liste de patient dans l'ordre croissant des heures d'arrivee

		ReglesDeGestion.trierParHeureArrivee(nouvListePatient);

		// Création d'une nouvelle Map pour construire le planning

		Map<Salle, List<Patient>> nouvelleMapPlanning = new HashMap<>();

		for (List<Salle> salles : pileSalle) {
			for (Salle salle : salles) {

				nouvelleMapPlanning.put(salle, new ArrayList<>());
			}

		}

		for (Patient patient : nouvListePatient) {
			// Si patienturgent alors on parcours la pile des salle et des qu'on tombe sur
			// une salle R ou TE on affecte le patient et la salle passe en bas de la pile
			if (patient.estUrgent()) {
				for (List<Salle> salles : pileSalle) {
					for (Salle salle : salles) {
						// Vérifie si la salle est de type R ou TE
						if ((salle.getType().equals(Salle.typeSalles.RESERVE)
								|| salle.getType().equals(Salle.typeSalles.TRESEQUIPE))) {
							// Ajoute le patient à la liste de patients associée à cette salle
							nouvelleMapPlanning.get(salle).add(patient);
							// Arrête la recherche pour ce patient (on le traite une seule fois)
							// Retire la salle de sa position actuelle et l'ajoute à la fin de la liste
			                salles.remove(salle);
			                salles.add(salle);
							break;

						}
					}

				}
			} else {
				for (List<Salle> salles : pileSalle) {
					for (Salle salle : salles) {
						// Vérifie si la salle est de type R ou TE
						if ((salle.getType().equals(patient.getGravite()))) {
							// Ajoute le patient à la liste de patients associée à cette salle
							nouvelleMapPlanning.get(salle).add(patient);
							// Arrête la recherche pour ce patient (on le traite une seule fois)
							// Retire la salle de sa position actuelle et l'ajoute à la fin de la liste
			                salles.remove(salle);
			                salles.add(salle);
							break;

						}
					}
				}
			}

		}
		return new Planning(nouvelleMapPlanning);
	}
}
