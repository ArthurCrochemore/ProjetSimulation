package fr.univtours.polytech;

import java.time.LocalTime;

import fr.univtours.polytech.ressource.Salle;

public class Constantes {
	private LocalTime tempsPreparation;
	private LocalTime tempsAnesthesie;
	private LocalTime tempsLiberation;
	private LocalTime tempsMoyen;
	private Integer nbReserve;
	private Integer tempsMoyenOperation;

	private LocalTime tempsMoyenDepuisAttentePreparation;
	private LocalTime tempsMoyenDepuisAttenteOperation;
	private LocalTime tempsMoyenDepuisAttenteLiberation;

	private Double marge;

	/**
	 * Accesseur en lecture du temps de preparation d'une salle
	 * 
	 * @return tempsPreparation
	 */
	public LocalTime getTempsPreparation() {
		return tempsPreparation;
	}

	/**
	 * Accesseur en lecture du temps d'anesthesie d'un patient
	 * 
	 * @return tempsAnesthesie
	 */
	public LocalTime getTempsAnesthesie() {
		return tempsAnesthesie;
	}


	/**
	 * Accesseur en lecture du temps de libération d'une salle
	 *
	 * @return tempsLiberation
	 */
	public LocalTime getTempsLiberation() {
		return tempsLiberation;
	}

	/**
	 * Accesseur en lecture du nombre de salles réservées
	 * 
	 * @return nbReserve
	 */
	public Integer getNbReserve() {
		return nbReserve;
	}

	/**
	 * Accesseur en écriture du nombre de salles réservées
	 * 
	 * @param nbReserve
	 */
	public void setNbReserve(Integer nbReserve) {
		this.nbReserve = nbReserve;
	}

	/**
	 * Construction de confort de Constantes
	 * 
	 * @param tempsPreparation
	 * @param tempsAnesthesie
	 * @param tempsLiberation
	 * @param nbReserve
	 * @param tempsMoyenOperation
	 * @param marge
	 */
	public Constantes(LocalTime tempsPreparation, LocalTime tempsAnesthesie, LocalTime tempsLiberation,
			Integer nbReserve, Integer tempsMoyenOperation, Integer marge) {
		this.tempsPreparation = tempsPreparation;
		this.tempsAnesthesie = tempsAnesthesie;
		this.tempsLiberation = tempsLiberation;
		this.nbReserve = nbReserve;
		this.tempsMoyenOperation = tempsMoyenOperation;

		this.marge = marge/100.0;
		setTempsMoyen();

		tempsMoyenDepuisAttentePreparation = getHeureMarge(tempsPreparation);
		tempsMoyenDepuisAttentePreparation = tempsMoyen.minusHours(tempsMoyenDepuisAttentePreparation.getHour())
				.minusMinutes(tempsMoyenDepuisAttentePreparation.getMinute());
		tempsMoyenDepuisAttenteOperation = getHeureMarge(0, tempsMoyenOperation);
		tempsMoyenDepuisAttenteOperation = tempsMoyenDepuisAttentePreparation
				.minusHours(tempsMoyenDepuisAttenteOperation.getHour())
				.minusMinutes(tempsMoyenDepuisAttenteOperation.getMinute());
		tempsMoyenDepuisAttenteLiberation = getHeureMarge(tempsLiberation);
		tempsMoyenDepuisAttenteLiberation = tempsMoyenDepuisAttenteOperation
				.minusHours(tempsMoyenDepuisAttenteLiberation.getHour())
				.minusMinutes(tempsMoyenDepuisAttenteLiberation.getMinute());
	}

	/**
	 * Accesseur en lecture du temps moyen d'occupation d'une salle
	 * 
	 * @return tempsMoyen
	 */
	public LocalTime getTempsMoyen() {
		return tempsMoyen;
	}

	/**
	 * Accesseur en lecture du temps moyen d'occupation d'une salle depuis un stade
	 * 
	 * @param etat
	 * @return LocalTime, le temps estimee qu'il reste avant que la salle soit liberee
	 */
	public LocalTime getTempsMoyen(Salle.listeEtats etat) {
		switch (etat) {
		case ATTENTEPREPARATION:
		case PREPARATION:
			return tempsMoyenDepuisAttentePreparation;
		case ATTENTEOPERATION:
		case OPERATION:
			return tempsMoyenDepuisAttenteOperation;
		case ATTENTELIBERATION:
		case LIBERATION:
			return tempsMoyenDepuisAttenteLiberation;
		default:
			return tempsMoyen;
		}
	}

	/**
	 * Méthode qui initialise le temps Moyen en fonction des temps deja stocke
	 */
	public void setTempsMoyen() {
		int nombreHeure = tempsPreparation.getHour() + tempsAnesthesie.getHour() + tempsLiberation.getHour();
		int nombreMinute = tempsPreparation.getMinute() + tempsAnesthesie.getMinute() + tempsMoyenOperation
				+ tempsLiberation.getMinute();

		tempsMoyen = getHeureMarge(nombreHeure, nombreMinute);
	}

	/**
	 * Méthode qui renvoie le temps saisie en paramètre après avoir ajouter la marge
	 * 
	 * @param heure
	 * @param nombreHeure
	 * @param nombreMinute
	 * @return heure, apres modification
	 */
	private LocalTime getHeureMarge(LocalTime heure, Integer nombreHeure, Integer nombreMinute) {
		if (heure != null) {
			nombreHeure = heure.getHour();
			nombreMinute = heure.getMinute();
		}

		double nombreHeureMarge = nombreHeure * marge;
		int nombreHeureMargeEntier = (int) nombreHeureMarge;
		nombreHeure += nombreHeureMargeEntier;

		int nombreMinuteMargeDepuisHeure = (int) (nombreHeureMarge - nombreHeureMarge) * 60;
		double nombreMinuteMarge = nombreMinute * marge;
		int nombreMinuteMargeEntier = nombreMinuteMargeDepuisHeure + ((int) nombreMinuteMarge);
		nombreMinute += nombreMinuteMargeEntier;

		int nbHeureDsMinute = nombreMinute / 60;

		nombreHeure += nbHeureDsMinute;
		nombreMinute = nombreMinute - nbHeureDsMinute * 60;

		return LocalTime.of(nombreHeure, nombreMinute);
	}

	/**
	 * Surcharge de getHeureMarge pour un parametre LocalTime
	 * 
	 * @param heure
	 * @return heure
	 */
	public LocalTime getHeureMarge(LocalTime heure) {
		return getHeureMarge(heure, 0, 0);
	}

	/**
	 * Surcharge de getHeureMarge pour deux parametres entiers
	 * 
	 * @param nombreHeure
	 * @param nombreMinute
	 * @return heure
	 */
	public LocalTime getHeureMarge(Integer nombreHeure, Integer nombreMinute) {
		return getHeureMarge(null, nombreHeure, nombreMinute);
	}
}
