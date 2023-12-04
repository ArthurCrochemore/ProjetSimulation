package fr.univtours.polytech.evenements;

import java.time.LocalTime;

import fr.univtours.polytech.entite.Patient;
import fr.univtours.polytech.ressource.Chirurgien;
import fr.univtours.polytech.ressource.Infirmier;
import fr.univtours.polytech.ressource.Salle;
import fr.univtours.polytech.util.Tuple;

public class EvFinOperation extends Evenement {
	
	public void deroulement() {
		//System.out.println(deroulement.getHeureSimulation() + " : fin ope");		

		patient.setEtat(Patient.listeEtats.ATTENTELIBERATION);
		patient.getTempsAttente().put(Patient.listeEtats.ATTENTELIBERATION, new Tuple<LocalTime, LocalTime>(heureDebut));
		/*
		chirurgien.setEtat(Ressource.listeEtats.LIBRE);
		chirurgien.incrementerTaille();
		chirurgien.getTempsAttente().add(new Tuple<LocalTime, LocalTime>(heureDebut));
		
		//////// GERER AFFECTATION //////////
		*/
		
		//SI 1 infirmiere est dispo
		deroulement.ajouterEvenement(heureDebut, new EvDebutLiberationSalle(heureDebut, patient, infirmier, salle, chirurgien, deroulement));
		
		//SINON
		/*
		ListesAttentes liste = deroulement.getSimulation().getListes();
		liste.ajouter(ListesAttentes.typeListes.LAI, salle);
		liste.ajouter(ListesAttentes.typeListes.LAIL, salle);
		salle.setEtat(Salle.listeEtats.ATTENTELIBERATION);
		*/
	}
	
	public EvFinOperation(LocalTime heureDebut, Patient patient, Infirmier infirmier, Salle salle, Chirurgien chirurgien, Deroulement deroulement) {
		super(heureDebut, patient, infirmier, salle, chirurgien, deroulement);
	}

}
