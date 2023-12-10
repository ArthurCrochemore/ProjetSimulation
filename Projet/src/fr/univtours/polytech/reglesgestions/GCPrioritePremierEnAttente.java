package fr.univtours.polytech.reglesgestions;

import fr.univtours.polytech.ListesAttentes;
import fr.univtours.polytech.Simulation;
import fr.univtours.polytech.entite.Patient;
import fr.univtours.polytech.ressource.Salle;
import fr.univtours.polytech.util.Tuple;

/**
 * Regle de gestion priorisant l'affectation des chirurgiens dans la premiere
 * salle de LAC
 */
public class GCPrioritePremierEnAttente implements GestionChirurgiens {

	private Simulation simulation;

	public GCPrioritePremierEnAttente(Simulation simulation) {
		this.simulation = simulation;
	}

	public Tuple<Salle, Patient> solution() {
		if (simulation.getListes().getListesAttente().get(ListesAttentes.typeListes.LAC).size() <= 0) {
			return null;
		} else {
			// Renvoie la premiere salle en attente de chrirugien
			Tuple<Salle, Patient> retour =  simulation.getListes().getListesAttente().get(ListesAttentes.typeListes.LAC).get(0);
			simulation.getListes().getListesAttente().get(ListesAttentes.typeListes.LAC).remove(0);
			return retour;
		}

	}

	public void ajoutSalle(Salle salle, Patient patient) {
		simulation.getListes().getListesAttente().get(ListesAttentes.typeListes.LAC).add(new Tuple<Salle, Patient>(salle, patient));
	}

	public Simulation getSimulation() {
		return simulation;
	}
}
