package fr.univtours.polytech.entite;

import java.time.LocalTime;

public class PatientRDV extends Patient {
	/* Gravité qui représente le type d'équipement minimal de la salle dans laquelle le patient sera opéré */
	public static enum listeGravite {
		PEUEQUIPE, SEMIEQUIPE, TRESEQUIPE
	};

	private listeGravite gravite;

	/* Surcharge de la méthode estUrgent qui renvoit false */
	public boolean estUrgent() {
		return false;
	}

	public PatientRDV.listeGravite getGravite() {
		return gravite;
	}
	
	public PatientRDV(Integer id, LocalTime heure, PatientRDV.listeGravite gravite, LocalTime tempsOperation) {
		super(id, heure, tempsOperation);
		
		this.gravite = gravite;
	}
}
