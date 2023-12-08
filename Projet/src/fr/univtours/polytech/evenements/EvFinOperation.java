package fr.univtours.polytech.evenements;

import java.time.LocalTime;

import fr.univtours.polytech.ListesAttentes;
import fr.univtours.polytech.entite.Patient;
import fr.univtours.polytech.ressource.Chirurgien;
import fr.univtours.polytech.ressource.Infirmier;
import fr.univtours.polytech.ressource.Ressource;
import fr.univtours.polytech.ressource.Salle;
import fr.univtours.polytech.util.Tuple;

public class EvFinOperation extends Evenement {
	
	public void deroulement() {
		//System.out.println(deroulement.getHeureSimulation() + " : fin ope");		

		patient.setEtat(Patient.listeEtats.ATTENTELIBERATION);
		patient.getTempsAttente().put(Patient.listeEtats.ATTENTELIBERATION, new Tuple<LocalTime, LocalTime>(heureDebut));
		
		chirurgien.setEtat(Ressource.listeEtats.LIBRE, heureDebut);
		
		//////// GERER AFFECTATION //////////
		
		
		Integer i = 0;
		while (i < deroulement.getSimulation().getInfirmiers().size() && infirmier == null) {
			Infirmier pott = deroulement.getSimulation().getInfirmiers().get(i);
			if (pott.getEtat() == Ressource.listeEtats.LIBRE) {
				infirmier = pott;
			}
		}
		
		if (infirmier != null) {
			deroulement.ajouterEvenement(heureDebut, new EvDebutLiberationSalle(heureDebut, patient, infirmier, salle, chirurgien, deroulement));
		} else {
			ListesAttentes liste = deroulement.getSimulation().getListes();
			liste.ajouter(ListesAttentes.typeListes.LAI, salle);
			liste.ajouter(ListesAttentes.typeListes.LAIL, salle);
			
			if (patient.estUrgent()) {
				liste.ajouter(ListesAttentes.typeListes.LAIU, salle);
			}
			salle.setEtat(Salle.listeEtats.ATTENTELIBERATION);
		}
	}
	
	public EvFinOperation(LocalTime heureDebut, Patient patient, Infirmier infirmier, Salle salle, Chirurgien chirurgien, Deroulement deroulement) {
		super(heureDebut, patient, infirmier, salle, chirurgien, deroulement);
	}

}
