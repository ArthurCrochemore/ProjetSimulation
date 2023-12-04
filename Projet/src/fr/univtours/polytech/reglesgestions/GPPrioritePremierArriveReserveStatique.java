package fr.univtours.polytech.reglesgestions;

import fr.univtours.polytech.Planning;
import fr.univtours.polytech.Simulation;
import fr.univtours.polytech.entite.PatientUrgent;
/**
 * Regle de gestion priorisant le patient arriver en premier dans la file d'attente pour la prochaine operation
 */
public class GPPrioritePremierArriveReserveStatique implements GestionPlanning {
	private Simulation simulation;

	public GPPrioritePremierArriveReserveStatique(Simulation simulation) {
		this.simulation = simulation;
	}
	//creer une structure avec heure de debut heure de fin index et salle
	//creer une focntion qui liste les trous dans le planning superieur ou  egale a c+c+c + temps moyen operation. Liste des structure
	//parcourir les salles tres equipes et stocker la valeur du creneau potentiel faire le min de cette valeur.
	
	public Planning solution(PatientUrgent patientUrgent) {
		//appeler la fonction de detection des trous
		
		
		return null;
	}
}
