package fr.univtours.polytech.evenements;

import java.time.LocalTime;

import fr.univtours.polytech.entite.Patient;
import fr.univtours.polytech.ressource.Chirurgien;
import fr.univtours.polytech.ressource.Infirmier;
import fr.univtours.polytech.ressource.Ressource;
import fr.univtours.polytech.ressource.Salle;
import fr.univtours.polytech.util.Tuple;

public class EvDebutPreparation extends Evenement {
	
	public void deroulement() {
		//System.out.println(deroulement.getHeureSimulation() + " : debut prepa");
		//System.out.println(deroulement.getHeureSimulation() + " : Salle " + salle.getId() + " est affecte au Patient " + patient.getId());
		
		patient.getTempsAttente().get(Patient.listeEtats.ATTENTEPREPARATION).setSecondElement(heureDebut);
		patient.setEtat(Patient.listeEtats.ENPREPARATION);
		
		infirmier.setEtat(Ressource.listeEtats.OCCUPE, heureDebut);
		
		//Salle s = deroulement.getSimulation().getRegles().
		//salle.setEtat(Salle.listeEtats.OCCUPE);
		
		LocalTime tempsPreparation = deroulement.getSimulation().getConstantes().getTempsPreparation();
		LocalTime heureDebutEvSuivant = heureDebut.plusHours(tempsPreparation.getHour()).plusMinutes(tempsPreparation.getMinute());
		deroulement.ajouterEvenement(heureDebutEvSuivant, new EvFinPreparation(heureDebutEvSuivant, patient, infirmier, salle, chirurgien, deroulement));
	}
	
	public EvDebutPreparation(LocalTime heureDebut, Patient patient, Infirmier infirmier, Salle salle, Chirurgien chirurgien, Deroulement deroulement) {
		super(heureDebut, patient, infirmier, salle, chirurgien, deroulement);
	}

}