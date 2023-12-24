package fr.univtours.polytech.initialisation;

import java.io.IOException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import fr.univtours.polytech.Simulation;
import fr.univtours.polytech.entite.PatientRDV;

public class DonneeInitialisation {
	// regles de gestion appliquees
	private Integer regle1;
	private Integer regle2;
	private Integer regle3;

	// heures de la journee
	private LocalTime heureDebutJournee;
	private LocalTime heureFinJournee;

	// Nb de Ressource
	private Integer nbInfirmiere;
	private Integer nbChirurgien;
	private Integer nbSallesPeuEquipee;
	private Integer nbSallesSemiEquipee;
	private Integer nbSallesTresEquipee;
	private Integer nbSallesReserveesUrgence;

	// Constantes
	private LocalTime tempsPreparation;
	private LocalTime tempsAnesthesie;
	private LocalTime tempsLiberation;
	private Integer moyTempsOperation;
	private Integer marge;

	// Data pour les Patients
	private Integer nbPatientsRDV;
	private Integer nbPatientsUrgent;
	private Map<Integer, LocalTime> mapArrivees;
	private Map<Integer, PatientRDV.listeGravite> mapGravites;
	private Map<Integer, LocalTime> mapTempsOperation;
	private Map<Integer, LocalTime> mapDeclaration;

	public Integer getRegle1() {
		return regle1;
	}

	public void setRegle1(Integer regle1) {
		this.regle1 = regle1;
	}

	public Integer getRegle2() {
		return regle2;
	}

	public void setRegle2(Integer regle2) {
		this.regle2 = regle2;
	}

	public Integer getRegle3() {
		return regle3;
	}

	public void setRegle3(Integer regle3) {
		this.regle3 = regle3;
	}

	public LocalTime getHeureDebutJournee() {
		return heureDebutJournee;
	}

	public void setHeureDebutJournee(LocalTime heureDebutJournee) {
		this.heureDebutJournee = heureDebutJournee;
	}

	public LocalTime getHeureFinJournee() {
		return heureFinJournee;
	}

	public void setHeureFinJournee(LocalTime heureFinJournee) {
		this.heureFinJournee = heureFinJournee;
	}

	public Integer getNbInfirmiere() {
		return nbInfirmiere;
	}

	public void setNbInfirmiere(Integer nbInfirmiere) {
		this.nbInfirmiere = nbInfirmiere;
	}

	public Integer getNbChirurgien() {
		return nbChirurgien;
	}

	public void setNbChirurgien(Integer nbChirurgien) {
		this.nbChirurgien = nbChirurgien;
	}

	public LocalTime getTempsPreparation() {
		return tempsPreparation;
	}

	public void setTempsPreparation(LocalTime tempsPreparation) {
		this.tempsPreparation = tempsPreparation;
	}

	public LocalTime getTempsAnesthesie() {
		return tempsAnesthesie;
	}

	public void setTempsAnesthesie(LocalTime tempsAnesthesie) {
		this.tempsAnesthesie = tempsAnesthesie;
	}

	public LocalTime getTempsLiberation() {
		return tempsLiberation;
	}

	public void setTempsLiberation(LocalTime tempsLiberation) {
		this.tempsLiberation = tempsLiberation;
	}

	public Integer getMoyTempsOperation() {
		return moyTempsOperation;
	}

	public void setMoyTempsOperation(Integer moyTempsOperation) {
		this.moyTempsOperation = moyTempsOperation;
	}

	public Integer getMarge() {
		return marge;
	}

	public void setMarge(Integer marge) {
		this.marge = marge;
	}

	public Integer getNbSallesPeuEquipee() {
		return nbSallesPeuEquipee;
	}

	public void setNbSallesPeuEquipee(Integer nbSallesPeuEquipee) {
		this.nbSallesPeuEquipee = nbSallesPeuEquipee;
	}

	public Integer getNbSallesSemiEquipee() {
		return nbSallesSemiEquipee;
	}

	public void setNbSallesSemiEquipee(Integer nbSallesSemiEquipee) {
		this.nbSallesSemiEquipee = nbSallesSemiEquipee;
	}

	public Integer getNbSallesTresEquipee() {
		return nbSallesTresEquipee;
	}

	public void setNbSallesTresEquipee(Integer nbSallesTresEquipee) {
		this.nbSallesTresEquipee = nbSallesTresEquipee;
	}

	public Integer getNbSallesReserveesUrgence() {
		return nbSallesReserveesUrgence;
	}

	public void setNbSallesReserveesUrgence(Integer nbSallesReserveesUrgence) {
		this.nbSallesReserveesUrgence = nbSallesReserveesUrgence;
	}

	public Integer getNbPatientsRDV() {
		return nbPatientsRDV;
	}

	public void setNbPatientsRDV(Integer nbPatientsRDV) {
		this.nbPatientsRDV = nbPatientsRDV;
	}

	public Integer getNbPatientsUrgent() {
		return nbPatientsUrgent;
	}

	public void setNbPatientsUrgent(Integer nbPatientsUrgent) {
		this.nbPatientsUrgent = nbPatientsUrgent;
	}

	public Map<Integer, LocalTime> getMapArrivees() {
		return mapArrivees;
	}

	public void setMapArrivees(Map<Integer, LocalTime> mapArrivees) {
		this.mapArrivees = mapArrivees;
	}

	public void ajouterPatientMapArrivees(Integer id, LocalTime heureArrivee) {
		this.mapArrivees.put(id, heureArrivee);
	}

	public Map<Integer, PatientRDV.listeGravite> getMapGravites() {
		return mapGravites;
	}

	public void setMapGravites(Map<Integer, PatientRDV.listeGravite> mapGravites) {
		this.mapGravites = mapGravites;
	}

	public void ajouterPatientMapGravites(Integer id, PatientRDV.listeGravite gravite) {
		this.mapGravites.put(id, gravite);
	}

	public Map<Integer, LocalTime> getMapTempsOperation() {
		return mapTempsOperation;
	}

	public void setMapTempsOperation(Map<Integer, LocalTime> mapTempsOperation) {
		this.mapTempsOperation = mapTempsOperation;
	}

	public void ajouterPatientMapTempsOperation(Integer id, LocalTime tempsOperation) {
		this.mapTempsOperation.put(id, tempsOperation);
	}

	public Map<Integer, LocalTime> getMapDeclaration() {
		return mapDeclaration;
	}

	public void setMapDeclaration(Map<Integer, LocalTime> mapDeclaration) {
		this.mapDeclaration = mapDeclaration;
	}

	public void ajouterPatientMapDeclaration(Integer id, LocalTime heureDeclaration) {
		this.mapDeclaration.put(id, heureDeclaration);
	}

	public Simulation creerSimultation() {
		try {
			return new Simulation(this);
		} catch (Exception e) {
			e.printStackTrace();
			
			return null;
		}
	}

	public DonneeInitialisation() {
		mapArrivees = new HashMap<Integer, LocalTime>();
		mapGravites = new HashMap<Integer, PatientRDV.listeGravite>();
		mapTempsOperation = new HashMap<Integer, LocalTime>();
		mapDeclaration = new HashMap<Integer, LocalTime>();
	}
}
