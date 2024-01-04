package fr.univtours.polytech.evenements;

import java.time.LocalTime;

import fr.univtours.polytech.entite.Patient;
import fr.univtours.polytech.ressource.Chirurgien;
import fr.univtours.polytech.ressource.Infirmier;
import fr.univtours.polytech.ressource.Ressource;
import fr.univtours.polytech.ressource.Salle;

public class EvFinPreparation extends Evenement {

	public void deroulement() {
		patient.setEtat(Patient.listeEtats.ATTENTECHIRURGIEN, heureDebut);

		infirmier.setEtat(Ressource.listeEtats.LIBRE, heureDebut);

		salle.setEtat(Salle.listeEtats.ATTENTEOPERATION, heureDebut);

		deroulement.ajouterEvenement(heureDebut,
				new EvInfirmiereDisponible(heureDebut, null, infirmier, null, chirurgien, deroulement));

		Integer i = 0;
		while (i < deroulement.getSimulation().getChirurgiens().size() && chirurgien == null) {
			Chirurgien pott = deroulement.getSimulation().getChirurgiens().get(i);
			if (pott.getEtat() == Ressource.listeEtats.LIBRE) {
				chirurgien = pott;
			}
			i++;
		}

		if (chirurgien != null) {
			deroulement.ajouterEvenement(heureDebut,
					new EvDebutOperation(heureDebut, patient, null, salle, chirurgien, deroulement));
		} else {
			deroulement.getSimulation().getRegles().getRegleGestionChirurgiens().ajoutSalle(salle, patient);
		}
	}

	public EvFinPreparation(LocalTime heureDebut, Patient patient, Infirmier infirmier, Salle salle,
			Chirurgien chirurgien, Deroulement deroulement) {
		super(heureDebut, patient, infirmier, salle, chirurgien, deroulement);
	}

}
