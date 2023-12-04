package fr.univtours.polytech.reglesgestions;

import fr.univtours.polytech.ListesAttentes;
import fr.univtours.polytech.Simulation;
import fr.univtours.polytech.ressource.Salle;

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

	public Salle solution() {
		// Verifie si La liste des salle en attente de chirurgien et qui on un patient
		// urgent est vide
		if (simulation.getListes().getListesAttente().get(ListesAttentes.typeListes.LACU) == null) {
			// Renvoie la premiere salle en attente de chrirugien
			return simulation.getListes().getListesAttente().get(ListesAttentes.typeListes.LAC).get(0);
		}
		
		if (simulation.getListes().getListesAttente().get(ListesAttentes.typeListes.LACU) != null) {
			// Renvoie la premiere salle en attente de chrirugien
			return simulation.getListes().getListesAttente().get(ListesAttentes.typeListes.LACU).get(0);
		}
		return null;

	}
	

	public Simulation getSimulation() {
		return simulation;
	}

	public void ajoutSalle(Salle salle) {
		simulation.getListes().getListesAttente().get(ListesAttentes.typeListes.LACU).add(salle);
	}
}
