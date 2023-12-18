package fr.univtours.polytech.reglesgestions;

import java.util.ArrayList;
import java.util.List;

import fr.univtours.polytech.Planning;
import fr.univtours.polytech.Simulation;
import fr.univtours.polytech.entite.Patient;
import fr.univtours.polytech.entite.PatientRDV;

/**
 * Regle de gestion priorisant la prise en charge des RDV Lorsqu'un patient
 * urgent est déclaré il est affecté aux salles reserve
 */
public class GPPrioriteRDV implements GestionPlanning {
	private Simulation simulation;

	public GPPrioriteRDV(Simulation simulation) {
		this.simulation = simulation;
	}

	/**
	 * parcours les salles reservees et une liste de patient associée à une salle
	 * reserve est vide ,patient urgent y est mis. Sinon il rentre dans la liste en
	 * dernier dans celle avec la liste d'attente la plus courte.COmment on lui met
	 * l'heure.
	 * 
	 */
	// creer une structure avec heure de debut heure de fin index et salle
	// creer une fonction qui liste les trous dans le planning superieur ou egale a
	// c+c+c + temps moyen operation. Liste des structure
	// parcourir les salles tres equipes et stocker la valeur du creneau potentiel
	// faire le min de cette valeur.

	public Planning solution(Patient patientUrgent) {
		// Planning newPlanning = new Planning();
		// List<PatientRDV> ListePatientRDV = simulation.getPatientsRDV();
		// simulation.creerPlanning(ListePatientRDV);
		// Initialisation de la nouvelle liste de patients
		List<Patient> nouvListePatient = new ArrayList<>();

		if (patientUrgent != null) {
			// Si un patient urgent est donné, récupérer le planning existant et l'ajouter à
			// la nouvelle liste
			Planning ancienPlanning = simulation.getPlanning();

			nouvListePatient = ancienPlanning.extraiteDonnee();
			nouvListePatient.add(patientUrgent);

			System.out.println("changememnt planning");
		} else {
			// Sinon,cela signifie que c'est la création du premier et planning et donc il
			// faut récupérer les patients prévus pour un rendez-vous et les ajouter à la
			// nouvelle liste
			System.out.println("premier planning");
			List<PatientRDV> patientsRDV = simulation.getPatientsRDV();

			for (Patient p : patientsRDV) {
				nouvListePatient.add(p);
			}
		}

		// parcours les salles de types reserve si une libre il est affecter sinon on
		// lui affecter un horraire
		return null;
	}
}
