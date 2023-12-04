package fr.univtours.polytech.reglesgestions;

import fr.univtours.polytech.Planning;
import fr.univtours.polytech.Simulation;
import fr.univtours.polytech.entite.PatientUrgent;
/**
 * Regle de gestion priorisant le patient arriver en premier dans la file d'attente pour la prochaine operation
 */
public class GPPrioritePremierArriveReserveDynamique implements GestionPlanning {
	private Simulation simulation;

	public GPPrioritePremierArriveReserveDynamique(Simulation simulation) {
		this.simulation = simulation;
	}
	public Planning solution(PatientUrgent patientUrgent) {
		return null;
	}
}
