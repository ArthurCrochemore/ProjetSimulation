package fr.univtours.polytech.reglesgestions;

import fr.univtours.polytech.ListesAttentes;
import fr.univtours.polytech.Simulation;
import fr.univtours.polytech.entite.Patient;
import fr.univtours.polytech.ressource.Salle;
import fr.univtours.polytech.util.Tuple;

/**
 * Regle de gestion priorisant l'affectation des infirmieres lors de leur
 * liberation vers les salle en attente de liberation.
 */
public class GIPrioritePremierEnAttente implements GestionInfirmiers {
	private Simulation simulation;

	public GIPrioritePremierEnAttente(Simulation simulation) {
		this.simulation = simulation;
	}

	public Tuple<Salle, Patient> solution() {
		if (simulation.getListes().getListesAttente().get(ListesAttentes.typeListes.LAI).size() > 0) {
			Tuple<Salle, Patient> retour = simulation.getListes().getListesAttente().get(ListesAttentes.typeListes.LAI)
					.get(0);
			simulation.getListes().getListesAttente().get(ListesAttentes.typeListes.LAI).remove(0);
			return retour;
		}
		return null;
	}

	public void ajoutSalle(Salle salle, Patient patient) {
		simulation.getListes().getListesAttente().get(ListesAttentes.typeListes.LAI)
				.add(new Tuple<Salle, Patient>(salle, patient));
	}
}
