package fr.univtours.polytech.reglesgestions;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import fr.univtours.polytech.Simulation;
import fr.univtours.polytech.entite.Patient;

public class ReglesDeGestion {
	private Simulation simulation;
	private GestionInfirmiers regleGestionInfirmiers;
	private GestionPlanning regleGestionPlanning;
	private GestionChirurgiens regleGestionChirurgiens;

	public ReglesDeGestion(Simulation simulation, int regle1, int regle2, int regle3) throws Exception {
		this.simulation = simulation;

		switch (regle1) {
		case 1:
			regleGestionInfirmiers = new GIPrioritePreparation(simulation);
			break;
		case 2:
			regleGestionInfirmiers = new GIPrioriteLiberation(simulation);
			break;
		case 3:
			regleGestionInfirmiers = new GIPrioritePremierEnAttente(simulation);
			break;
		case 4:
			regleGestionInfirmiers = new GIPrioriteUrgence(simulation);
			break;
		default:
			throw new Exception("Entier regle de gestion de type Salle incohérent");
		}

		switch (regle2) {
		case 1:
			regleGestionPlanning = new GPPrioritePremierArriveReserveStatique(simulation);
			break;
		case 2:
			regleGestionPlanning = new GPPrioriteRDV(simulation);
			break;
		case 3:
			regleGestionPlanning = new GPPrioriteAbsoluUrgence(simulation);
			break;
		case 4:
			regleGestionPlanning = new GPPrioritePremierArriveReserveDynamique(simulation);
			break;
		default:
			throw new Exception("Entier regle de gestion de type Infirmier incohérent");
		}

		switch (regle3) {
		case 1:
			regleGestionChirurgiens = new GCPrioritePremierEnAttente(simulation);
			break;
		case 2:
			regleGestionChirurgiens = new GCPrioriteUrgent(simulation);
			break;
		default:
			throw new Exception("Entier regle de gestion des chirurgiens incohérent");
		}
	}

	public GestionInfirmiers getRegleGestionInfirmiers() {
		return regleGestionInfirmiers;
	}

	public GestionPlanning getRegleGestionPlanning() {
		return regleGestionPlanning;
	}

	public GestionChirurgiens getRegleGestionChirurgiens() {
		return regleGestionChirurgiens;
	}

	public Simulation getSimulation() {
		return simulation;
	}

	public static void trierParHeureArrivee(List<Patient> listePatients) {
		Collections.sort(listePatients, new Comparator<Patient>() {
			@Override
			public int compare(Patient patient1, Patient patient2) {
				return patient1.getHeureArrive().compareTo(patient2.getHeureArrive());
			}
		});
	}
}
