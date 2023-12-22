package fr.univtours.polytech.reglesgestions;

import fr.univtours.polytech.ListesAttentes;
import fr.univtours.polytech.Simulation;
import fr.univtours.polytech.entite.Patient;
import fr.univtours.polytech.ressource.Salle;
import fr.univtours.polytech.util.Tuple;

/**
 * Regle de gestion priorisant l'affectation des chirurgiens dans la premiere
 * salle ou se trouve un patient urgent sinon le premier dans la file d'attente
 * des salles en attente d'un chirurgien
 */
public class GCPrioriteUrgent implements GestionChirurgiens {

	private Simulation simulation;

	public GCPrioriteUrgent(Simulation simulation) {
		this.simulation = simulation;
	}

	public Tuple<Salle, Patient> solution() {
		// Verifie si La liste des salle en attente de chirurgien et qui on un patient
		// urgent est vide
		if (simulation.getListes().getListesAttente().get(ListesAttentes.typeListes.LACU).size() > 0) {
			// Renvoie la premiere salle en attente de chrirugien
			Tuple<Salle, Patient> retour = simulation.getListes().getListesAttente().get(ListesAttentes.typeListes.LACU)
					.get(0);
			simulation.getListes().getListesAttente().get(ListesAttentes.typeListes.LACU).remove(0);
			return retour;
		}

		if (simulation.getListes().getListesAttente().get(ListesAttentes.typeListes.LAC).size() > 0) {
			// Renvoie la premiere salle en attente de chrirugien
			Tuple<Salle, Patient> retour = simulation.getListes().getListesAttente().get(ListesAttentes.typeListes.LAC)
					.get(0);
			simulation.getListes().getListesAttente().get(ListesAttentes.typeListes.LAC).remove(0);
			return retour;
		}

		return null;
	}

	public Simulation getSimulation() {
		return simulation;
	}

	public void ajoutSalle(Salle salle, Patient patient) {
		simulation.getListes().getListesAttente().get(ListesAttentes.typeListes.LACU)
				.add(new Tuple<Salle, Patient>(salle, patient));
	}
}
