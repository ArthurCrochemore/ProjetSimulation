package fr.univtours.polytech.evenements;

import java.time.LocalTime;

import fr.univtours.polytech.ListesAttentes;
import fr.univtours.polytech.entite.Patient;
import fr.univtours.polytech.ressource.Chirurgien;
import fr.univtours.polytech.ressource.Infirmier;
import fr.univtours.polytech.ressource.Ressource;
import fr.univtours.polytech.ressource.Salle;
import fr.univtours.polytech.util.Tuple;

public class EvArriveDansLaSalle extends Evenement {

	public void deroulement() {
		// System.out.println(deroulement.getHeureSimulation() + " : arrivee dans la
		// salle");

		patient.getTempsAttente().get(Patient.listeEtats.ATTENTESALLE).setSecondElement(heureDebut);
		;

		patient.setEtat(Patient.listeEtats.ATTENTEPREPARATION);
		patient.getTempsAttente().put(Patient.listeEtats.ATTENTEPREPARATION,
				new Tuple<LocalTime, LocalTime>(heureDebut));
		salle.setEtat(Salle.listeEtats.ATTENTEPREPARATION);
		

		Integer i = 0;
		while (i < deroulement.getSimulation().getInfirmiers().size() && infirmier == null) {
			Infirmier pott = deroulement.getSimulation().getInfirmiers().get(i);
			if (pott.getEtat() == Ressource.listeEtats.LIBRE) {
				infirmier = pott;
			}
		}

		if (infirmier != null) {
			deroulement.ajouterEvenement(heureDebut,
					new EvDebutPreparation(heureDebut, patient, infirmier, salle, chirurgien, deroulement));
		} else {
			deroulement.getSimulation().getListes().ajouter(ListesAttentes.typeListes.LAI, salle);
			deroulement.getSimulation().getListes().ajouter(ListesAttentes.typeListes.LAIP, salle);
			salle.setEtat(Salle.listeEtats.ATTENTEPREPARATION);

			if (patient.estUrgent()) {
				deroulement.getSimulation().getListes().ajouter(ListesAttentes.typeListes.LAIU, salle);
				salle.setEtat(Salle.listeEtats.ATTENTEPREPARATION);
			}
		}
	}

	public EvArriveDansLaSalle(LocalTime heureDebut, Patient patient, Infirmier infirmier, Salle salle,
			Chirurgien chirurgien, Deroulement deroulement) {
		super(heureDebut, patient, infirmier, salle, chirurgien, deroulement);
	}

}