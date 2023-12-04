package fr.univtours.polytech.reglesgestions;

import java.util.List;

import fr.univtours.polytech.Planning;
import fr.univtours.polytech.Simulation;
import fr.univtours.polytech.entite.PatientRDV;
import fr.univtours.polytech.entite.PatientUrgent;
/**
 * Regle de gestion priorisant la prise en charge des RDV 
 * Lorsqu'un patient urgent est déclaré il est affecté aux salles reserve
 */
public class GPPrioriteRDV implements GestionPlanning {
	private Simulation simulation;
	

	public GPPrioriteRDV(Simulation simulation) {
		this.simulation = simulation;
	}
	/**
	 * parcours les salles reservees et une liste de patient associée à une salle reserve est vide ,patient urgent y est mis. Sinon il rentre dans la liste en dernier dans celle 
	 * avec la liste d'attente la plus courte.COmment on lui met l'heure.
	 * 
	 */
	public Planning solution(PatientUrgent patientUrgent) {
		//Planning  newPlanning = new Planning();
		//List<PatientRDV> ListePatientRDV = simulation.getPatientsRDV();
		//simulation.creerPlanning(ListePatientRDV);
		
		
				//parcours les salles de types reserve si une libre il est affecter sinon on lui affecter un horraire
		return null;
	}
}
