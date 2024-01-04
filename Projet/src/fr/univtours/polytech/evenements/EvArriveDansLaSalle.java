package fr.univtours.polytech.evenements;

import java.time.LocalTime;

import fr.univtours.polytech.entite.Patient;
import fr.univtours.polytech.ressource.Chirurgien;
import fr.univtours.polytech.ressource.Infirmier;
import fr.univtours.polytech.ressource.Ressource;
import fr.univtours.polytech.ressource.Salle;

public class EvArriveDansLaSalle extends Evenement {

	public void deroulement() {
		System.out.println(deroulement.getHeureSimulation() + " : patient " + patient.getId() + " affecte a la salle "
				+ salle.getId());

		patient.setEtat(Patient.listeEtats.AATTENDUUNESALLE, heureDebut);
		patient.setEtat(Patient.listeEtats.ATTENTEPREPARATION, heureDebut);

		salle.setEtat(Salle.listeEtats.ATTENTEPREPARATION, heureDebut);

		try {
			deroulement.getSimulation().getPlanning().sortirPatient(patient, salle);
		} catch (IllegalAccessError e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}

		Integer i = 0;
		while (i < deroulement.getSimulation().getInfirmiers().size() && infirmier == null) {
			Infirmier pott = deroulement.getSimulation().getInfirmiers().get(i);
			if (pott.getEtat() == Ressource.listeEtats.LIBRE) {
				infirmier = pott;
			}

			i++;
		}

		if (infirmier != null) {
			deroulement.ajouterEvenement(heureDebut,
					new EvDebutPreparation(heureDebut, patient, infirmier, salle, chirurgien, deroulement));
		} else {
			deroulement.getSimulation().getRegles().getRegleGestionInfirmiers().ajoutSalle(salle, patient);
		}
	}

	public EvArriveDansLaSalle(LocalTime heureDebut, Patient patient, Infirmier infirmier, Salle salle,
			Chirurgien chirurgien, Deroulement deroulement) {
		super(heureDebut, patient, infirmier, salle, chirurgien, deroulement);
	}

}
