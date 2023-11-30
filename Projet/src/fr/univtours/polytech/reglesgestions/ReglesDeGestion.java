package fr.univtours.polytech.reglesgestions;

import java.util.ArrayList;
import java.util.List;

import fr.univtours.polytech.Simulation;

public class ReglesDeGestion  {
	private Simulation simulation;
	private List<Regle> regles;
	private Integer nbRegles;

	public ReglesDeGestion(Simulation simulation, int regle1, int regle2, int regle3)  throws Exception {
		this.simulation = simulation;
		regles = new ArrayList<Regle>();
		nbRegles = 2;

		switch (regle1) {
		case 1:
			regles.add(new GIPrioritePreparation(simulation));
			break;
		case 2:
			regles.add(new GIPrioriteLiberation(simulation));
			break;
		case 3:
			regles.add(new GIPrioritePremierEnAttente(simulation));
			break;
		case 4:
			regles.add(new GIPrioriteUrgence(simulation));
			break;
		default:
			throw new Exception("Entier regle de gestion de type Salle trop grand");
		}

		switch (regle2) {
		case 1:
			regles.add(new GPPrioriteAbsoluUrgenceReserveStatique(simulation));
			break;
		case 2:
			regles.add(new GPPrioriteRDV(simulation));
			break;
		case 3:
			regles.add(new GPPrioritePremierArriveReserveDynamique(simulation));
			break;
		default:
			throw new Exception("Entier regle de gestion de type Infirmier trop grand");
		}
		
		switch (regle3) {
		case 1:
			regles.add(new GCPrioritePremierEnAttente(simulation));
			break;
		case 2:
			regles.add(new GCPrioriteUrgent(simulation));
			break;
		default:
			throw new Exception("Entier regle de gestion des chirurgiens trop grand");
		
		}
	}

	public Simulation getSimulation() {
		return simulation;
	}
}
