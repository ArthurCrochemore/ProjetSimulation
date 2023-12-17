package fr.univtours.polytech.evenements;

import java.time.LocalTime;

import fr.univtours.polytech.entite.Patient;
import fr.univtours.polytech.ressource.Chirurgien;
import fr.univtours.polytech.ressource.Infirmier;
import fr.univtours.polytech.ressource.Ressource;
import fr.univtours.polytech.ressource.Salle;

public class EvFinLiberationSalle extends Evenement {

	public void deroulement() {
		System.out.println(deroulement.getHeureSimulation() + " : fin libe ");

		salle.setEtat(Salle.listeEtats.LIBRE, heureDebut);

		infirmier.setEtat(Ressource.listeEtats.LIBRE, heureDebut);

		deroulement.ajouterEvenement(heureDebut,
				new EvInfirmiereDisponible(heureDebut, null, infirmier, null, chirurgien, deroulement));

		patient = deroulement.getSimulation().getPlanning().lireProchainPatient(salle);
		if (patient != null && patient.getEtat() == Patient.listeEtats.ATTENTESALLE) {
			deroulement.ajouterEvenement(heureDebut,
					new EvArriveDansLaSalle(heureDebut, patient, infirmier, salle, chirurgien, deroulement));
		}
	}

	public EvFinLiberationSalle(LocalTime heureDebut, Patient patient, Infirmier infirmier, Salle salle,
			Chirurgien chirurgien, Deroulement deroulement) {
		super(heureDebut, patient, infirmier, salle, chirurgien, deroulement);
	}
}
