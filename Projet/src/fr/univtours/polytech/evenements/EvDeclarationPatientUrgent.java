package fr.univtours.polytech.evenements;

import java.time.LocalTime;

import fr.univtours.polytech.entite.Patient;
import fr.univtours.polytech.ressource.Chirurgien;
import fr.univtours.polytech.ressource.Infirmier;
import fr.univtours.polytech.ressource.Salle;

public class EvDeclarationPatientUrgent extends Evenement {
	public void deroulement() {
		//System.out.println(deroulement.getHeureSimulation() + " : declaration");

		deroulement.ajouterEvenement(patient.getHeureArrive(),
				new EvArrivePatient(patient.getHeureArrive(), patient, infirmier, salle, chirurgien, deroulement));
	}

	public EvDeclarationPatientUrgent(LocalTime heureDebut, Patient patient, Infirmier infirmier, Salle salle,
			Chirurgien chirurgien, Deroulement deroulement) {
		super(heureDebut, patient, infirmier, salle, chirurgien, deroulement);
	}
}
