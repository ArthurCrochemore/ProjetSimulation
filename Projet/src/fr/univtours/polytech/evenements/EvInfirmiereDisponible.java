package fr.univtours.polytech.evenements;

import java.time.LocalTime;

import fr.univtours.polytech.entite.Patient;
import fr.univtours.polytech.ressource.Chirurgien;
import fr.univtours.polytech.ressource.Infirmier;
import fr.univtours.polytech.ressource.Salle;
import fr.univtours.polytech.util.Tuple;

public class EvInfirmiereDisponible extends Evenement {

	public void deroulement() {
		//System.out.println(deroulement.getHeureSimulation() + " : affectation infirmiere prepa");
		
		Tuple<Salle, Patient> tuple = deroulement.getSimulation().getRegles().getRegleGestionInfirmiers().solution();
		
		if (tuple != null) {
			if (tuple.getPremierElement().getEtat() == Salle.listeEtats.ATTENTEPREPARATION) {
				deroulement.ajouterEvenement(heureDebut, new EvDebutPreparation(heureDebut, tuple.getSecondElement(), infirmier, tuple.getPremierElement(), chirurgien, deroulement));
			} else {
				deroulement.ajouterEvenement(heureDebut, new EvDebutLiberationSalle(heureDebut, tuple.getSecondElement(), infirmier, tuple.getPremierElement(), chirurgien, deroulement));
			}
		}
	}

	public EvInfirmiereDisponible(LocalTime heureDebut, Patient patient, Infirmier infirmier, Salle salle,
			Chirurgien chirurgien, Deroulement deroulement) {
		super(heureDebut, patient, infirmier, salle, chirurgien, deroulement);
	}
}
