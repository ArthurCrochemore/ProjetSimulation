package fr.univtours.polytech.entite;

import java.time.LocalTime;

public class PatientRDV extends Patient {
	public static enum listeGravite {
		PEUEQUIPE, SEMIEQUIPE, TRESEQUIPE
	};

	private listeGravite gravite;
	
	public boolean estUrgent() {
		return false;
	}

	public PatientRDV.listeGravite getGravite() {
		return gravite;
	}
	
	public PatientRDV(Integer id, LocalTime heure, PatientRDV.listeGravite gravite) {
		super(id, heure, (LocalTime) null);
		
		this.gravite = gravite;
	}
	
	public PatientRDV(Integer id, LocalTime heure, PatientRDV.listeGravite gravite, LocalTime temps) {
		super(id, heure, temps);
		
		this.gravite = gravite;
	}
}
