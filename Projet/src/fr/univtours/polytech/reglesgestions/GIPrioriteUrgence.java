package fr.univtours.polytech.reglesgestions;

import fr.univtours.polytech.ListesAttentes;
import fr.univtours.polytech.Simulation;
import fr.univtours.polytech.ressource.Salle;

/**
 * Regle de gestion priorisant l'affectation des infirmieres lors de leur
 * liberation vers les salles ou il y a des patients urgent en attente.
 */
public class GIPrioriteUrgence implements GestionInfirmiers {
	private Simulation simulation;

	public GIPrioriteUrgence(Simulation simulation) {
		this.simulation = simulation;
	}

	public Salle solution() {
		if (simulation.getListes().getListesAttente().get(ListesAttentes.typeListes.LAIU) == null) {
			// Renvoie la premiere salle en attente d'infirmiere
			return simulation.getListes().getListesAttente().get(ListesAttentes.typeListes.LAC).get(0);
		}
		if (simulation.getListes().getListesAttente().get(ListesAttentes.typeListes.LAIU) != null) {
			return simulation.getListes().getListesAttente().get(ListesAttentes.typeListes.LAIU).get(0);
		}
		return null;
	}
}
