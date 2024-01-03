package fr.univtours.polytech.evenements;

import java.time.LocalTime;

import fr.univtours.polytech.entite.Patient;
import fr.univtours.polytech.ressource.Chirurgien;
import fr.univtours.polytech.ressource.Infirmier;
import fr.univtours.polytech.ressource.Ressource;
import fr.univtours.polytech.ressource.Salle;
import fr.univtours.polytech.util.Tuple;

public class EvFinOperation extends Evenement {

	public void deroulement() {
		//System.out.println(deroulement.getHeureSimulation() + " : fin ope " + patient.getId());

		patient.setEtat(Patient.listeEtats.ATTENTELIBERATION, heureDebut);

		salle.setEtat(Salle.listeEtats.ATTENTELIBERATION, heureDebut);

		chirurgien.setEtat(Ressource.listeEtats.LIBRE, heureDebut);

		Tuple<Salle, Patient> tuple = deroulement.getSimulation().getRegles().getRegleGestionChirurgiens().solution();

		if (tuple != null) {
			deroulement.ajouterEvenement(heureDebut, new EvDebutOperation(heureDebut, tuple.getSecondElement(),
					infirmier, tuple.getPremierElement(), chirurgien, deroulement));
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
					new EvDebutLiberationSalle(heureDebut, patient, infirmier, salle, null, deroulement));
		} else {
			deroulement.getSimulation().getRegles().getRegleGestionInfirmiers().ajoutSalle(salle, patient);
		}
	}

	public EvFinOperation(LocalTime heureDebut, Patient patient, Infirmier infirmier, Salle salle,
			Chirurgien chirurgien, Deroulement deroulement) {
		super(heureDebut, patient, infirmier, salle, chirurgien, deroulement);
	}

}
