package fr.univtours.polytech.evenements;

import java.time.LocalTime;

import fr.univtours.polytech.entite.Patient;
import fr.univtours.polytech.ressource.Chirurgien;
import fr.univtours.polytech.ressource.Infirmier;
import fr.univtours.polytech.ressource.Salle;
import fr.univtours.polytech.util.Tuple;

public class EvArriveDansLaSalle extends Evenement {

	public void deroulement() {
		//System.out.println(deroulement.getHeureSimulation() + " : arrivee dans la salle");


		patient.getTempsAttente().get(Patient.listeEtats.ATTENTESALLE).setSecondElement(heureDebut);;

		patient.setEtat(Patient.listeEtats.ATTENTEPREPARATION);
		patient.getTempsAttente().put(Patient.listeEtats.ATTENTEPREPARATION, new Tuple<LocalTime, LocalTime>(heureDebut));
		// salle.setEtat(Salle.listeEtats.ATTENTEPREPARATION);

		// SI une infirmiere est dispo (regle gestion infirmiere)
		deroulement.ajouterEvenement(heureDebut,
				new EvDebutPreparation(heureDebut, patient, infirmier, salle, chirurgien, deroulement));

		// Sinon
		/*
		 * deroulement.getSimulation().getListes().ajouter(ListesAttentes.typeListes.
		 * LAI, salle);
		 * deroulement.getSimulation().getListes().ajouter(ListesAttentes.typeListes.
		 * LAIP, salle); salle.setEtat(Salle.listeEtats.ATTENTEPREPARATION);
		 * 
		 * SI patient.estUrgent()
		 * deroulement.getSimulation().getListes().ajouter(ListesAttentes.typeListes.
		 * LAIU, salle); salle.setEtat(Salle.listeEtats.ATTENTEPREPARATION);
		 */
	}

	public EvArriveDansLaSalle(LocalTime heureDebut, Patient patient, Infirmier infirmier, Salle salle,
			Chirurgien chirurgien, Deroulement deroulement) {
		super(heureDebut, patient, infirmier, salle, chirurgien, deroulement);
	}

}
