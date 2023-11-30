package fr.univtours.polytech.reglesgestions;

import fr.univtours.polytech.ListesAttentes;
import fr.univtours.polytech.Simulation;
import fr.univtours.polytech.ressource.Salle;
/**
 * Regle de gestion priorisant l'affectation des infirmieres lors de leur liberation vers les salle en attente de liberation.
 */
public class GIPrioritePremierEnAttente implements GestionInfirmiers {
	private Simulation simulation;

	public GIPrioritePremierEnAttente(Simulation simulation) {
		this.simulation = simulation;
	}
	public Salle solution() {
		if(simulation.getListes().getListesAttente().get(ListesAttentes.typeListes.LAI).get(0) ==null )
		{
			return simulation.getListes().getListesAttente().get(ListesAttentes.typeListes.LAI).get(0);
		}
		return null;
	}
}
