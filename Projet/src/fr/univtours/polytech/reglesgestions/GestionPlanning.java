package fr.univtours.polytech.reglesgestions;

import fr.univtours.polytech.Planning;
import fr.univtours.polytech.entite.PatientUrgent;
/**
 * Interface des regles de gestions pour les plannings.
 */
public interface GestionPlanning {	
	
	public Planning solution(PatientUrgent patienturgent);
}
