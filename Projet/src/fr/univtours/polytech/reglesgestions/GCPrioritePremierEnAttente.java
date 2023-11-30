package fr.univtours.polytech.reglesgestions;

import fr.univtours.polytech.ListesAttentes;
import fr.univtours.polytech.Simulation;
import fr.univtours.polytech.ressource.Salle;

/**
 * Regle de gestion priorisant l'affectation des chirurgiens dans la premiere
 * salle de LAC
 */
public class GCPrioritePremierEnAttente implements GestionChirurgiens {

	private Simulation simulation;

	public GCPrioritePremierEnAttente(Simulation simulation) {
		this.simulation = simulation;
	}

	public Salle solution() {
		if (simulation.getListes().getListesAttente().get(ListesAttentes.typeListes.LAC).get(0) == null) {
			return null;
		} else {
			// Renvoie la premiere salle en attente de chrirugien
			return simulation.getListes().getListesAttente().get(ListesAttentes.typeListes.LAC).get(0);
		}

	}

	public void ajoutSalle(Salle salle) {
		simulation.getListes().getListesAttente().get(ListesAttentes.typeListes.LAC).add(salle);
	}

	public Simulation getSimulation() {
		return simulation;
	}
}
