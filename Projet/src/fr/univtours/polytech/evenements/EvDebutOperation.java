package fr.univtours.polytech.evenements;

import java.time.LocalTime;

import fr.univtours.polytech.entite.Patient;
import fr.univtours.polytech.ressource.Chirurgien;
import fr.univtours.polytech.ressource.Infirmier;
import fr.univtours.polytech.ressource.Ressource;
import fr.univtours.polytech.ressource.Salle;

public class EvDebutOperation extends Evenement {

	public void deroulement() {
		patient.setEtat(Patient.listeEtats.ENOPERATION, heureDebut);

		chirurgien.setEtat(Ressource.listeEtats.OCCUPE, heureDebut);

		salle.setEtat(Salle.listeEtats.OPERATION, heureDebut);

		LocalTime tempsOperation = patient.getTempsOperation();
		LocalTime tempsAnesthesie = deroulement.getSimulation().getConstantes().getTempsAnesthesie();
		LocalTime heureDebutEvSuivant = heureDebut.plusHours(tempsOperation.getHour() + tempsAnesthesie.getHour())
				.plusMinutes(tempsOperation.getMinute() + tempsAnesthesie.getMinute());
		deroulement.ajouterEvenement(heureDebutEvSuivant,
				new EvFinOperation(heureDebutEvSuivant, patient, infirmier, salle, chirurgien, deroulement));
	}

	public EvDebutOperation(LocalTime heureDebut, Patient patient, Infirmier infirmier, Salle salle,
			Chirurgien chirurgien, Deroulement deroulement) {
		super(heureDebut, patient, infirmier, salle, chirurgien, deroulement);
	}

}
