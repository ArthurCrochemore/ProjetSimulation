package fr.univtours.polytech.evenements;

import java.time.LocalTime;
import java.util.List;

import fr.univtours.polytech.entite.Patient;
import fr.univtours.polytech.ressource.Chirurgien;
import fr.univtours.polytech.ressource.Infirmier;
import fr.univtours.polytech.ressource.Salle;

public class EvInfirmiereDisponible extends Evenement {
	
	public void deroulement() {
		
	}
	
	public EvInfirmiereDisponible(LocalTime heureDebut, Patient patient, Infirmier infirmier, Salle salle, Chirurgien chirurgien, Deroulement deroulement) {
		super(heureDebut, patient, infirmier, salle, chirurgien, deroulement);
	}
}
