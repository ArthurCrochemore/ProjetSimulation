package fr.univtours.polytech.evenements;

import java.time.LocalTime;

import fr.univtours.polytech.entite.Patient;
import fr.univtours.polytech.ressource.Chirurgien;
import fr.univtours.polytech.ressource.Infirmier;
import fr.univtours.polytech.ressource.Salle;

public abstract class Evenement {
	protected LocalTime heureDebut;
	protected LocalTime heureFin;
	protected Patient patient;
	protected Infirmier infirmier;
	protected Salle salle;
	protected Chirurgien chirurgien;
	protected Deroulement deroulement;

	public abstract void deroulement();

	/**
	 * Constructeur de confort de l'objet evenement
	 * 
	 * @param heureDebut,  heure d'execution de l'evenement
	 * @param patient,     patient concerne par l'evenement, null si aucun n'est
	 *                     concerne
	 * @param infirmier,   infirmier concerne par l'evenement, null si aucun n'est
	 *                     concerne
	 * @param salle,       salle concernee par l'evenement, null si aucun n'est
	 *                     concernee
	 * @param chirurgien,  chirurgien concerne par l'evenement, null si aucun n'est
	 *                     concerne
	 * @param deroulement, objet deroulement qui contient l'evenement
	 */
	public Evenement(LocalTime heureDebut, Patient patient, Infirmier infirmier, Salle salle, Chirurgien chirurgien,
			Deroulement deroulement) {
		this.heureDebut = heureDebut;
		this.heureFin = null;
		this.patient = patient;
		this.infirmier = infirmier;
		this.salle = salle;
		this.chirurgien = chirurgien;
		this.deroulement = deroulement;
	}
}
