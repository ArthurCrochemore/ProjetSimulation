package fr.univtours.polytech.evenements;

import java.time.LocalTime;

import fr.univtours.polytech.entite.Patient;
import fr.univtours.polytech.ressource.Chirurgien;
import fr.univtours.polytech.ressource.Infirmier;
import fr.univtours.polytech.ressource.Ressource;
import fr.univtours.polytech.ressource.Salle;

public class EvDebutOperation extends Evenement {
	
	public void deroulement() {
		//System.out.println(deroulement.getHeureSimulation() + " : debut ope");		
		
		patient.getTempsAttente().get(Patient.listeEtats.ATTENTECHIRURGIEN).setSecondElement(heureDebut);
		patient.setEtat(Patient.listeEtats.ENOPERATION);
				
		chirurgien.setEtat(Ressource.listeEtats.OCCUPE, heureDebut);

		LocalTime tempsOperation = patient.getTempsOperation();
		LocalTime heureDebutEvSuivant = heureDebut.plusHours(tempsOperation.getHour()).plusMinutes(tempsOperation.getMinute());
		deroulement.ajouterEvenement(heureDebutEvSuivant, new EvFinOperation(heureDebutEvSuivant, patient, infirmier, salle, chirurgien, deroulement));
	}
	
	public EvDebutOperation(LocalTime heureDebut, Patient patient, Infirmier infirmier, Salle salle, Chirurgien chirurgien, Deroulement deroulement) {
		super(heureDebut, patient, infirmier, salle, chirurgien, deroulement);
	}

}
