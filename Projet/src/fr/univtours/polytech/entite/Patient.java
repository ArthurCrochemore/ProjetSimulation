package fr.univtours.polytech.entite;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import fr.univtours.polytech.util.Tuple;

public class Patient extends Entite {
	private LocalTime heureArrive;
	private LocalTime heureSortie;
	private LocalTime tempsOperation;

	public static enum listeEtats {
		PASARRIVE, ATTENTESALLE, ATTENTEPREPARATION, ENPREPARATION, ATTENTECHIRURGIEN, ENOPERATION, ATTENTELIBERATION, TERMINE
	};

	private listeEtats etat;
	private Map<listeEtats, Tuple<LocalTime, LocalTime>> tempsAttente;

	public LocalTime getHeureArrive() {
		return heureArrive;
	}

	public void setHeureArrive(LocalTime heureArrive) {
		this.heureArrive = heureArrive;
	}

	public LocalTime getHeureSortie() {
		return heureSortie;
	}

	public void setHeureSortie(LocalTime heureSortie) {
		this.heureSortie = heureSortie;
	}
	
	public LocalTime getTempsOperation() {
		return tempsOperation;
	}

	public void setTempsOperation(LocalTime tempsOperation) {
		this.tempsOperation = tempsOperation;
	}

	public listeEtats getEtat() {
		return etat;
	}

	public void setEtat(listeEtats etat) {
		this.etat = etat;
	}

	public Map<listeEtats, Tuple<LocalTime, LocalTime>> getTempsAttente() {
		return tempsAttente;
	}

	public void setTempsAttente(Map<listeEtats, Tuple<LocalTime, LocalTime>> tempsAttente) {
		this.tempsAttente = tempsAttente;
	}

	public Patient(Integer id, LocalTime heure, LocalTime temps) {
		super(id);
		this.heureArrive = heure;
		this.tempsOperation = temps;
		this.heureSortie = null;
		this.etat = Patient.listeEtats.PASARRIVE;
		
		this.tempsAttente = new HashMap<listeEtats, Tuple<LocalTime, LocalTime>>();
	}
}
