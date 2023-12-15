package fr.univtours.polytech.evenements;

import java.time.LocalTime;

import fr.univtours.polytech.entite.Patient;
import fr.univtours.polytech.ressource.Chirurgien;
import fr.univtours.polytech.ressource.Infirmier;
import fr.univtours.polytech.ressource.Ressource;
import fr.univtours.polytech.ressource.Salle;

public class EvDebutLiberationSalle extends Evenement {

	public void deroulement() {
		// System.out.println(deroulement.getHeureSimulation() + " : debut libe");
		System.out.println(deroulement.getHeureSimulation() + " : Patient " + patient.getId() + " sort du modele");

		patient.setEtat(Patient.listeEtats.TERMINE, heureDebut);
		patient.setHeureSortie(heureDebut);
		patient = null;

		salle.setEtat(Salle.listeEtats.LIBERATION, heureDebut);

		infirmier.setEtat(Ressource.listeEtats.OCCUPE, heureDebut);

		LocalTime tempsLiberation = deroulement.getSimulation().getConstantes().getTempsLiberation();
		LocalTime heureDebutEvSuivant = heureDebut.plusHours(tempsLiberation.getHour())
				.plusMinutes(tempsLiberation.getMinute());
		deroulement.ajouterEvenement(heureDebutEvSuivant,
				new EvFinLiberationSalle(heureDebutEvSuivant, patient, infirmier, salle, chirurgien, deroulement));

		deroulement.setASuppr2(deroulement.getASuppr2() + 1);
	}

	public EvDebutLiberationSalle(LocalTime heureDebut, Patient patient, Infirmier infirmier, Salle salle,
			Chirurgien chirurgien, Deroulement deroulement) {
		super(heureDebut, patient, infirmier, salle, chirurgien, deroulement);
	}
}
