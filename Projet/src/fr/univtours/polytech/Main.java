package fr.univtours.polytech;

import java.util.List;

import fr.univtours.polytech.entite.PatientRDV;
import fr.univtours.polytech.evenements.Deroulement;
import fr.univtours.polytech.initialisation.DonneeInitialisation;
import fr.univtours.polytech.initialisation.LectureDeFichierSimulation;

public class Main {
	public static void main (String[] args) throws Exception {
		LectureDeFichierSimulation lecture = new LectureDeFichierSimulation("../fichier_type.txt");
	
		DonneeInitialisation data = lecture.initialiserSimulation();
		/*
		Map<Integer, LocalTime> m1 = data.getMapArrivees();
		Map<Integer, PatientRDV.listeGravite> m2 = data.getMapGravites();
		Map<Integer, LocalTime> m3 = data.getMapTempsOperation();
		Map<Integer, LocalTime> m4 = data.getMapDeclaration();

		
		for (Integer indice = 0; indice < data.getNbPatientsRDV(); indice++) {
			System.out.println("Patient " + indice + " : " + m1.get(indice) + ", " + m2.get(indice) + ", " + m3.get(indice));
			
		}
		
		System.out.println(("RDV OK"));

		for (Integer indice = data.getNbPatientsRDV(); indice < data.getNbPatientsRDV() + data.getNbPatientsUrgent(); indice++) {
			System.out.println("Patient " + indice + " : " + m1.get(indice) + ", " + m3.get(indice) + ", " + m4.get(indice));
		}
		System.out.println("Urgent OK");
		*/
		
		Simulation s = data.creerSimultation();
		Deroulement d = s.getDeroulement();
		
		d.execution();
		ExtractionJSON e = new ExtractionJSON(s);
		e.extraiteDonnees();
	}
}
