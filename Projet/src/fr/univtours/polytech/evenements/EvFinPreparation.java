package fr.univtours.polytech.evenements;

import java.time.LocalTime;

import fr.univtours.polytech.entite.Patient;
import fr.univtours.polytech.ressource.Chirurgien;
import fr.univtours.polytech.ressource.Infirmier;
import fr.univtours.polytech.ressource.Salle;
import fr.univtours.polytech.util.Tuple;

public class EvFinPreparation extends Evenement {
	
	public void deroulement() {
		//System.out.println(deroulement.getHeureSimulation() + " : fin prepa");
		
		patient.setEtat(Patient.listeEtats.ATTENTECHIRURGIEN);
		patient.getTempsAttente().put(Patient.listeEtats.ATTENTECHIRURGIEN, new Tuple<LocalTime, LocalTime>(heureDebut));
		/*infirmier.setEtat(Ressource.listeEtats.LIBRE);
		infirmier.incrementerTaille();
		infirmier.getTempsAttente().add(new Tuple<LocalTime, LocalTime>(heureDebut));*/		
		//deroulement.ajouterEvenement(heureDebut, new EvInfirmiereDisponible(heureDebut, null, infirmier, null, chirurgien, deroulement));
		
		//SI un chirurgien est libre
		deroulement.ajouterEvenement(heureDebut, new EvDebutOperation(heureDebut, patient, null, salle, chirurgien, deroulement));
		
		//SINON
		/*
		deroulement.getSimulation().getListes().ajouter(ListesAttentes.typeListes.LAC, salle);		
		 */
	}
	
	public EvFinPreparation(LocalTime heureDebut, Patient patient, Infirmier infirmier, Salle salle, Chirurgien chirurgien, Deroulement deroulement) {
		super(heureDebut, patient, infirmier, salle, chirurgien, deroulement);
	}

}
