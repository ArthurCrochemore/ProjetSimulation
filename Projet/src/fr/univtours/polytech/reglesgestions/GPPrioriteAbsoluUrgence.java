package fr.univtours.polytech.reglesgestions;

import fr.univtours.polytech.Planning;
import fr.univtours.polytech.Simulation;

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

	public Planning solution() {

		return null;
	}
}
