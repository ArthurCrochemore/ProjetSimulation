package fr.univtours.polytech;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import fr.univtours.polytech.entite.Patient;
import fr.univtours.polytech.ressource.Chirurgien;
import fr.univtours.polytech.ressource.Infirmier;
import fr.univtours.polytech.ressource.Ressource;
import fr.univtours.polytech.ressource.Salle;
import fr.univtours.polytech.util.Tuple;

public class ExtractionJSON {
	Simulation simulation;
	
	/**
	 * Methode gerant l'ecriture des donnees de temps stocke dans les entites et ressources de la simulation stocke en attribut
	 * @param adresse, adresse du fichier .json ou les donnees doivent etre ecrite
	 */
	public void extraiteDonnees(String adresse) {
		try {
			PrintWriter writer = new PrintWriter(adresse, "UTF-8");

			/* Recuperation des valeurs */
			int nbPatientsRDV = simulation.getPatientsRDV().size();
			int nbPatientsUrgent = simulation.getPatientsUrgent().size();
			
			Map<Salle.typeSalles, List<Salle>> salles = simulation.getSalles();
			int nbInfirmier = simulation.getPatientsUrgent().size();
			int nbChirurgien = simulation.getChirurgiens().size();
			int nbSallePE = salles.get(Salle.typeSalles.PEUEQUIPE).size();
			int nbSalleSE = salles.get(Salle.typeSalles.SEMIEQUIPE).size();
			int nbSalleTE = salles.get(Salle.typeSalles.TRESEQUIPE).size();
			
			/* Ecriture des donnees de PatientRDV */
			writer.println("{\n\t\"patientRDV\": {\n");
			for (int i = 0; i < nbPatientsRDV - 1; i++) {
				Patient p = simulation.getPatientsRDV().get(i);
				ecrirePatient(p, writer, i);
				writer.println("\t\t,\n");
			}
			
			ecrirePatient(simulation.getPatientsRDV().get(nbPatientsRDV - 1), writer, nbPatientsRDV - 1);
			writer.println("\t},\n");
			
			/* Ecriture des donnees de PatientUrgent */
			writer.println("\t\"patientUrgent\": {\n");
			for (int i = 0; i < nbPatientsUrgent - 1; i++) {
				Patient p = simulation.getPatientsUrgent().get(i);
				ecrirePatient(p, writer, i);
				writer.println("\t\t,\n");
			}
			
			ecrirePatient(simulation.getPatientsUrgent().get(nbPatientsUrgent - 1), writer, nbPatientsUrgent - 1);
			writer.println("\t},\n");
			
			/* Ecriture des donnees de Infirmier */
			writer.println("\t\"infirmier\": {");
			for (int i = 0; i < nbInfirmier; i++) {
				Infirmier infirmier = simulation.getInfirmiers().get(i);
				ecrireRessource(infirmier.getTempsAttente(), writer, i);
				if (i < nbInfirmier - 1)
					writer.println("\t,");
				else 
					writer.println("\t},");
			}
			
			/* Ecriture des donnees de Chirurgien */
			writer.println("\n\t\"chirugien\": {");
			for (int i = 0; i < nbChirurgien; i++) {
				Chirurgien chirurgien = simulation.getChirurgiens().get(i);
				ecrireRessource(chirurgien.getTempsAttente(), writer, i);
				if (i < nbChirurgien - 1)
					writer.println("\t,");
				else 
					writer.println("\t},");
			}
			
			/* Ecriture des donnees de Salle */
			writer.println("\n\t\"salle\": {");

			writer.println("\t\t\"peuEquipe\" : {");
			for (int i = 0; i < nbSallePE; i++) {
				Salle salle = salles.get(Salle.typeSalles.PEUEQUIPE).get(i);
				ecrireSalle(salle.getTempsAttente(), writer, i);
				if (i < nbSallePE - 1)
					writer.println("\t\t,");
				else 
					writer.println("\t\t}");
			}
			writer.println("\t,");
			writer.println("\t\t\"semiEquipe\" : {");
			for (int i = 0; i < nbSalleSE; i++) {
				Salle salle = salles.get(Salle.typeSalles.SEMIEQUIPE).get(i);
				ecrireSalle(salle.getTempsAttente(), writer, i);
				if (i < nbSalleSE - 1)
					writer.println("\t\t,");
				else 
					writer.println("\t\t}");
			}
			writer.println("\t,");
			writer.println("\t\t\"tresEquipe\" : {");
			for (int i = 0; i < nbSalleTE; i++) {
				Salle salle = salles.get(Salle.typeSalles.TRESEQUIPE).get(i);
				ecrireSalle(salle.getTempsAttente(), writer, i);
				if (i < nbSalleTE - 1)
					writer.println("\t\t,");
				else 
					writer.println("\t\t}");
			}
			writer.println("\t}");
			
			writer.println("}");
			writer.close();

		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (UnsupportedEncodingException e) {
			System.out.println(e);
		}
	}

	/**
	 * Surcharge de la methode d'extraction a l'adresse par defaut
	 */
	public void extraiteDonnees() {
		extraiteDonnees("../extraction.json");
	}
	
	/**
	 * Methode gerant l'ecriture pour un patient
	 * @param p, patient
	 * @param writer
	 * @param i, indice du patient
	 */
	private void ecrirePatient(Patient p, PrintWriter writer, int i) {
		Map<Patient.listeEtats, Tuple<LocalTime, LocalTime>> temps = p.getTempsAttente();

		writer.println("\t\t\t\"id\" : " + i + ",");
		if (p.getEtat() == Patient.listeEtats.TERMINE) {
			writer.println("\t\t\t\"ATTENTESALLE\" : [ " + temps.get(Patient.listeEtats.ATTENTESALLE).getPremierElement()
							+ " , " + temps.get(Patient.listeEtats.ATTENTESALLE).getSecondElement() + " ],");
			writer.println("\t\t\t\"ATTENTEPREPARATION\" : [ "
					+ temps.get(Patient.listeEtats.ATTENTEPREPARATION).getPremierElement() + " , "
					+ temps.get(Patient.listeEtats.ATTENTEPREPARATION).getSecondElement() + " ],");
			writer.println("\t\t\t\"ATTENTECHIRURGIEN\" : [ "
					+ temps.get(Patient.listeEtats.ATTENTECHIRURGIEN).getPremierElement() + " , "
					+ temps.get(Patient.listeEtats.ATTENTECHIRURGIEN).getSecondElement() + " ],");
			writer.println("\t\t\t\"ATTENTELIBERATION\" : [ "
					+ temps.get(Patient.listeEtats.ATTENTELIBERATION).getPremierElement() + " , "
					+ temps.get(Patient.listeEtats.ATTENTELIBERATION).getSecondElement() + " ]");
		} else {

			if (p.getEtat() == Patient.listeEtats.ATTENTELIBERATION) {
				writer.println("\t\t\t\"ATTENTESALLE\" : [ "
						+ temps.get(Patient.listeEtats.ATTENTESALLE).getPremierElement() + " , "
						+ temps.get(Patient.listeEtats.ATTENTESALLE).getSecondElement() + " ],");
				writer.println("\t\t\t\"ATTENTEPREPARATION\" : [ "
						+ temps.get(Patient.listeEtats.ATTENTEPREPARATION).getPremierElement() + " , "
						+ temps.get(Patient.listeEtats.ATTENTEPREPARATION).getSecondElement() + " ],");
				writer.println("\t\t\t\"ATTENTECHIRURGIEN\" : [ "
						+ temps.get(Patient.listeEtats.ATTENTECHIRURGIEN).getPremierElement() + " , "
						+ temps.get(Patient.listeEtats.ATTENTECHIRURGIEN).getSecondElement() + " ],");
				writer.println("\t\t\t\"ATTENTELIBERATION\" : [ "
						+ temps.get(Patient.listeEtats.ATTENTELIBERATION).getPremierElement() + " , "
						+ "null ]");

			} else {

				if (p.getEtat() == Patient.listeEtats.ENOPERATION) {
					writer.println("\t\t\t\"ATTENTESALLE\" : [ "
							+ temps.get(Patient.listeEtats.ATTENTESALLE).getPremierElement() + " , "
							+ temps.get(Patient.listeEtats.ATTENTESALLE).getSecondElement() + " ],");
					writer.println("\t\t\t\"ATTENTEPREPARATION\" : [ "
							+ temps.get(Patient.listeEtats.ATTENTEPREPARATION).getPremierElement() + " , "
							+ temps.get(Patient.listeEtats.ATTENTEPREPARATION).getSecondElement() + " ],");
					writer.println("\t\t\t\"ATTENTECHIRURGIEN\" : [ "
							+ temps.get(Patient.listeEtats.ATTENTECHIRURGIEN).getPremierElement() + " , "
							+ temps.get(Patient.listeEtats.ATTENTECHIRURGIEN).getSecondElement() + " ],");
				} else {

					if (p.getEtat() == Patient.listeEtats.ATTENTECHIRURGIEN) {
						writer.println("\t\t\t\"ATTENTESALLE\" : [ "
								+ temps.get(Patient.listeEtats.ATTENTESALLE).getPremierElement() + " , "
								+ temps.get(Patient.listeEtats.ATTENTESALLE).getSecondElement() + " ],");
						writer.println("\t\t\t\"ATTENTEPREPARATION\" : [ "
								+ temps.get(Patient.listeEtats.ATTENTEPREPARATION).getPremierElement() + " , "
								+ temps.get(Patient.listeEtats.ATTENTEPREPARATION).getSecondElement() + " ],");
						writer.println("\t\t\t\"ATTENTECHIRURGIEN\" : [ "
								+ temps.get(Patient.listeEtats.ATTENTECHIRURGIEN).getPremierElement() + " , "
								+ "null ],");

					} else {
						if (p.getEtat() == Patient.listeEtats.ENPREPARATION) {
							writer.println("\t\t\t\"ATTENTESALLE\" : [ "
									+ temps.get(Patient.listeEtats.ATTENTESALLE).getPremierElement() + " , "
									+ temps.get(Patient.listeEtats.ATTENTESALLE).getSecondElement() + " ],");
							writer.println("\t\t\t\"ATTENTEPREPARATION\" : [ "
									+ temps.get(Patient.listeEtats.ATTENTEPREPARATION).getPremierElement()
									+ " , "
									+ temps.get(Patient.listeEtats.ATTENTEPREPARATION).getSecondElement()
									+ " ],");

						} else {
							if (p.getEtat() == Patient.listeEtats.ATTENTEPREPARATION) {
								writer.println("\t\t\t\"ATTENTESALLE\" : [ "
										+ temps.get(Patient.listeEtats.ATTENTESALLE).getPremierElement() + " , "
										+ temps.get(Patient.listeEtats.ATTENTESALLE).getSecondElement()
										+ " ],");
								writer.println("\t\t\t\"ATTENTEPREPARATION\" : [ "
										+ temps.get(Patient.listeEtats.ATTENTEPREPARATION).getPremierElement()
										+ " , " + "null ],");

							} else {
								if (p.getEtat() == Patient.listeEtats.ATTENTESALLE) {
									writer.println("\t\t\t\"ATTENTESALLE\" : [ "
											+ temps.get(Patient.listeEtats.ATTENTESALLE).getPremierElement()
											+ " , " + "null ],");

								} else {
									writer.println("\t\t\t\"ATTENTESALLE\": [" + " ],");
								}
								writer.println("\t\t\t\"ATTENTEPREPARATION\": [" + " ],");
							}
						}
						writer.println("\t\t\t\"ATTENTECHIRURGIEN\": [" + " ],");
					}

				}

				writer.println("\t\t\t\"ATTENTELIBERATION\" : [ " + "]");
			}
		}
	}
	
	/**
	 * @param temps, List<Tuple<LocalTime, LocalTime>> stocke dans la ressource associee
	 * @param writer
	 * @param i, indice de la ressource
	 */
	private void ecrireRessource(List<Tuple<LocalTime, LocalTime>> temps, PrintWriter writer, int i) {
		writer.println("\t\t\"id\" : " + i + " ,");
		writer.println("\t\t\"attente\" : [");
		for(int j = 0; j < temps.size() - 1; j ++) {
			writer.println("\t\t\t[" + temps.get(j).getPremierElement() + ", " + temps.get(j).getSecondElement() + "],\n\t],");
		}
		writer.println("\t\t\t[" + temps.get( temps.size() - 1).getPremierElement() + ", " + temps.get( temps.size() - 1).getSecondElement() + "]\n\t\t]");
	}
	
	/**
	 * @param temps, List<Tuple<LocalTime, LocalTime>> stocke dans la salle associee
	 * @param writer
	 * @param i, indice de la salle
	 */
	private void ecrireSalle(List<Tuple<LocalTime, LocalTime>> temps, PrintWriter writer, int i) {
		writer.println("\t\t\t\"id\" : " + i + " ,");
		writer.println("\t\t\t\"libre\" : [");
		for(int j = 0; j < temps.size() - 1; j ++) {
			writer.println("\t\t\t\t[" + temps.get(j).getPremierElement() + ", " + temps.get(j).getSecondElement() + "],\n\t\t],");
		}
		writer.println("\t\t\t\t[" + temps.get( temps.size() - 1).getPremierElement() + ", " + temps.get( temps.size() - 1).getSecondElement() + "]\n\t\t\t]");
	}
	
	public ExtractionJSON(Simulation simulation) {
		this.simulation = simulation;		
	}
}
