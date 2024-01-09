package fr.univtours.polytech.entite;

import java.time.LocalTime;

public class PatientUrgent extends Patient {
	private LocalTime heureDeclaration;

	/* Surcharge de la méthode estUrgent qui renvoit true */
	public boolean estUrgent() {
		return true;
	}

	public PatientUrgent(Integer id, LocalTime heure, LocalTime temps, LocalTime heureDeclaration) {
		super(id, heure, temps);
		this.heureDeclaration = heureDeclaration;
	}
}
