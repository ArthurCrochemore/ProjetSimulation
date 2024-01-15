package fr.univtours.polytech.initialisation;

import java.time.LocalTime;

import fr.univtours.polytech.entite.PatientRDV;

public class LectureDeFichierSimulation extends LectureDeFichier {
	/**
	 * Lit le contenu de la ligne lue comme un entier
	 * 
	 * @return ligneLue convertit en Integer
	 */
	public Integer lireEntier() {
		lireProchaineLigne();
		return Integer.parseInt(getLigneLue());
	}

	/**
	 * Lit le contenu de la ligne lue comme un LocalTime
	 * 
	 * @return ligneLue convertit en LocalTime
	 */
	public LocalTime lireTemps() {
		lireProchaineLigne();
		return LocalTime.parse(getLigneLue());
	}

	/**
	 * Initialise un objet DonneeInitialisation a partir du fichier lu
	 * 
	 * @return DonneeInitialisation
	 */
	public DonneeInitialisation initialiserSimulation() {
		DonneeInitialisation donnees = new DonneeInitialisation();

		donnees.setRegle1(lireEntier());
		donnees.setRegle2(lireEntier());
		donnees.setRegle3(lireEntier());
		donnees.setHeureDebutJournee(lireTemps());
		donnees.setHeureFinJournee(lireTemps());
		donnees.setNbInfirmiere(lireEntier());
		donnees.setNbChirurgien(lireEntier());
		donnees.setTempsPreparation(lireTemps());
		donnees.setTempsAnesthesie(lireTemps());
		donnees.setTempsLiberation(lireTemps());
		donnees.setMoyTempsOperation(lireEntier());
		donnees.setMarge(lireEntier());
		donnees.setNbSallesPeuEquipee(lireEntier());
		donnees.setNbSallesSemiEquipee(lireEntier());
		donnees.setNbSallesTresEquipee(lireEntier());
		donnees.setNbSallesReserveesUrgence(lireEntier());

		Integer nbPatientPeuEquipe = lireEntier();
		Integer nbPatientSemiEquipe = lireEntier();
		Integer nbPatientTresEquipe = lireEntier();
		Integer nbPatientUrgent = lireEntier();

		donnees.setNbPatientsRDV(nbPatientPeuEquipe + nbPatientSemiEquipe + nbPatientTresEquipe);
		donnees.setNbPatientsUrgent(nbPatientUrgent);

		// On stocke les informations des nbPatientPeuEquipe premiers patients
		for (Integer indice = 0; indice < nbPatientPeuEquipe; indice++) {
			donnees.ajouterPatientMapArrivees(indice, lireTemps());
			donnees.ajouterPatientMapGravites(indice, PatientRDV.listeGravite.PEUEQUIPE);
			donnees.ajouterPatientMapTempsOperation(indice, lireTemps());
		}

		// On stocke les informations des nbPatientSemiEquipe patients suivants
		for (Integer indice = nbPatientPeuEquipe; indice < nbPatientSemiEquipe + nbPatientPeuEquipe; indice++) {
			donnees.ajouterPatientMapArrivees(indice, lireTemps());
			donnees.ajouterPatientMapGravites(indice, PatientRDV.listeGravite.SEMIEQUIPE);
			donnees.ajouterPatientMapTempsOperation(indice, lireTemps());
		}

		// On stocke les informations des nbPatientTresEquipe patients suivants
		for (Integer indice = nbPatientSemiEquipe + nbPatientPeuEquipe; indice < nbPatientTresEquipe
				+ nbPatientSemiEquipe + nbPatientPeuEquipe; indice++) {
			donnees.ajouterPatientMapArrivees(indice, lireTemps());
			donnees.ajouterPatientMapGravites(indice, PatientRDV.listeGravite.TRESEQUIPE);
			donnees.ajouterPatientMapTempsOperation(indice, lireTemps());
		}

		// On stocke les informations des nbPatientUrgent derniers patients
		for (Integer indice = nbPatientTresEquipe + nbPatientSemiEquipe + nbPatientPeuEquipe; indice < nbPatientUrgent
				+ nbPatientTresEquipe + nbPatientSemiEquipe + nbPatientPeuEquipe; indice++) {
			donnees.ajouterPatientMapArrivees(indice, lireTemps());
			donnees.ajouterPatientMapTempsOperation(indice, lireTemps());
			donnees.ajouterPatientMapDeclaration(indice, lireTemps());
		}

		return donnees;
	}

	/**
	 * Constructeur de confort a partir de l'adresse du fichier d'initialisation
	 * 
	 * @param adresse
	 */
	public LectureDeFichierSimulation(String adresse) {
		super(adresse);
	}
}
