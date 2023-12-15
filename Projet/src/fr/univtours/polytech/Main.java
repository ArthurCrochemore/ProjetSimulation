package fr.univtours.polytech;

import fr.univtours.polytech.evenements.Deroulement;
import fr.univtours.polytech.initialisation.DonneeInitialisation;
import fr.univtours.polytech.initialisation.LectureDeFichierSimulation;

public class Main {
	public static void main(String[] args) throws Exception {
		LectureDeFichierSimulation lecture = new LectureDeFichierSimulation("../fichier_type.txt");

		DonneeInitialisation data = lecture.initialiserSimulation();
		Simulation s = data.creerSimultation();
		Deroulement d = s.getDeroulement();

		d.execution();
		ExtractionJSON e = new ExtractionJSON(s);
		e.extraiteDonnees();
	}
}
