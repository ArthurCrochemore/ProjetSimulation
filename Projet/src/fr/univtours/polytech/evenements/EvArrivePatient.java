package fr.univtours.polytech.evenements;

import java.time.LocalTime;

import fr.univtours.polytech.entite.Patient;
import fr.univtours.polytech.ressource.Chirurgien;
import fr.univtours.polytech.ressource.Infirmier;
import fr.univtours.polytech.ressource.Salle;
import fr.univtours.polytech.util.Tuple;

public class EvArrivePatient extends Evenement {

	public void deroulement() {
		System.out.println(deroulement.getHeureSimulation() + " : arrivee patient " +
		patient.getId());

		patient.setEtat(Patient.listeEtats.ATTENTESALLE, heureDebut);
		patient.getTempsAttente().put(Patient.listeEtats.ATTENTESALLE, new Tuple<LocalTime, LocalTime>(heureDebut));
		Salle salleAffectee = deroulement.getSimulation().getPlanning().lireSalle(patient);

		if (salleAffectee != null) {
			deroulement.ajouterEvenement(heureDebut,
					new EvArriveDansLaSalle(heureDebut, patient, infirmier, salleAffectee, chirurgien, deroulement));
		}

	}

	public EvArrivePatient(LocalTime heureDebut, Patient patient, Infirmier infirmier, Salle salle,
			Chirurgien chirurgien, Deroulement deroulement) {
		super(heureDebut, patient, infirmier, salle, chirurgien, deroulement);
	}
}
