package fr.univtours.polytech.reglesgestions;

import fr.univtours.polytech.ListesAttentes;
import fr.univtours.polytech.Simulation;
import fr.univtours.polytech.entite.Patient;
import fr.univtours.polytech.ressource.Salle;
import fr.univtours.polytech.util.Tuple;

/**
 * Regle de gestion priorisant l'affectation des infirmieres lors de leur
 * liberation vers les salle en attente de preparation.
 */
public class GIPrioritePreparation implements GestionInfirmiers {
	private Simulation simulation;

	public GIPrioritePreparation(Simulation simulation) {
		this.simulation = simulation;
	}

	public Tuple<Salle, Patient> solution() {

		if (simulation.getListes().getListesAttente().get(ListesAttentes.typeListes.LAIP).size() > 0) {
			Tuple<Salle, Patient> retour = simulation.getListes().getListesAttente().get(ListesAttentes.typeListes.LAIP)
					.get(0);
			simulation.getListes().getListesAttente().get(ListesAttentes.typeListes.LAIP).remove(0);
			return retour;
		}
		if (simulation.getListes().getListesAttente().get(ListesAttentes.typeListes.LAIL).size() > 0) {
			Tuple<Salle, Patient> retour = simulation.getListes().getListesAttente().get(ListesAttentes.typeListes.LAIL)
					.get(0);
			simulation.getListes().getListesAttente().get(ListesAttentes.typeListes.LAIL).remove(0);
			return retour;
		}

		return null;
	}

	public void ajoutSalle(Salle salle, Patient patient) {
		if (salle.getEtat() == Salle.listeEtats.ATTENTEPREPARATION) {
			simulation.getListes().getListesAttente().get(ListesAttentes.typeListes.LAIP)
					.add(new Tuple<Salle, Patient>(salle, patient));
		} else {
			simulation.getListes().getListesAttente().get(ListesAttentes.typeListes.LAIL)
					.add(new Tuple<Salle, Patient>(salle, patient));
		}
	}
}
