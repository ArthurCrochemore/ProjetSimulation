package fr.univtours.polytech.evenements;

import java.time.LocalTime;

import fr.univtours.polytech.entite.Patient;
import fr.univtours.polytech.ressource.Chirurgien;
import fr.univtours.polytech.ressource.Infirmier;
import fr.univtours.polytech.ressource.Salle;
import fr.univtours.polytech.util.Tuple;

public class EvArrivePatient extends Evenement {

	public void deroulement() {
		// System.out.println(deroulement.getHeureSimulation() + " : arrivee");

		patient.setEtat(Patient.listeEtats.ATTENTESALLE);
		patient.getTempsAttente().put(Patient.listeEtats.ATTENTESALLE, new Tuple<LocalTime, LocalTime>(heureDebut));
		// salleAffectee = deroulement.getSimulation().getPlanning().lireSalle(patient);
		this.salle = new Salle(1, Salle.typeSalles.PEUEQUIPE, heureDebut);
		// SI une salle est affectee
		deroulement.ajouterEvenement(heureDebut, new EvArriveDansLaSalle(heureDebut, patient, infirmier,
				salle /* salleAffectee */, chirurgien, deroulement));
		// SINON

	}

	public EvArrivePatient(LocalTime heureDebut, Patient patient, Infirmier infirmier, Salle salle,
			Chirurgien chirurgien, Deroulement deroulement) {
		super(heureDebut, patient, infirmier, salle, chirurgien, deroulement);
	}
}
