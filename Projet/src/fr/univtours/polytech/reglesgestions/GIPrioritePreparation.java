package fr.univtours.polytech.reglesgestions;

import fr.univtours.polytech.ListesAttentes;
import fr.univtours.polytech.Simulation;
import fr.univtours.polytech.ressource.Salle;

/**
 * Regle de gestion priorisant l'affectation des infirmieres lors de leur
 * liberation vers les salle en attente de preparation.
 */
public class GIPrioritePreparation implements GestionInfirmiers {
	private Simulation simulation;

	public GIPrioritePreparation(Simulation simulation) {
		this.simulation = simulation;
	}

	public Salle solution() {

		if (simulation.getListes().getListesAttente().get(ListesAttentes.typeListes.LAIP).get(0) == null
				&& simulation.getListes().getListesAttente().get(ListesAttentes.typeListes.LAIL).get(0) != null) {
			return simulation.getListes().getListesAttente().get(ListesAttentes.typeListes.LAIL).get(0);
		}
		if (simulation.getListes().getListesAttente().get(ListesAttentes.typeListes.LAIP).get(0) != null) {
			return simulation.getListes().getListesAttente().get(ListesAttentes.typeListes.LAIP).get(0);
		}
		return null;
	}
}
