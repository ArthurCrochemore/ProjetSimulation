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

	public Integer getNbReserve() {
		return nbReserve;
	}

	public void setNbReserve(Integer nbReserve) {
		this.nbReserve = nbReserve;
	}

	public Constantes(LocalTime tempsPreparation, LocalTime tempsAnesthesie, LocalTime tempsLiberation,
			Integer nbReserve, Integer tempsMoyenOperation) {
		this.tempsPreparation = tempsPreparation;
		this.tempsAnesthesie = tempsAnesthesie;
		this.tempsLiberation = tempsLiberation;
		this.nbReserve = nbReserve;
		this.tempsMoyenOperation = tempsMoyenOperation;

		this.marge = 0.1;
		setTempsMoyen();

		tempsMoyenDepuisAttentePreparation = getHeureMarge(tempsPreparation);
		tempsMoyenDepuisAttentePreparation = tempsMoyen.minusHours(tempsMoyenDepuisAttentePreparation.getHour())
				.minusMinutes(tempsMoyenDepuisAttentePreparation.getMinute());
		tempsMoyenDepuisAttenteOperation = getHeureMarge(0, tempsMoyenOperation);
		tempsMoyenDepuisAttenteOperation = tempsMoyenDepuisAttentePreparation.minusHours(tempsMoyenDepuisAttenteOperation.getHour())
				.minusMinutes(tempsMoyenDepuisAttenteOperation.getMinute());
		tempsMoyenDepuisAttenteLiberation = getHeureMarge(tempsLiberation);
		tempsMoyenDepuisAttenteLiberation = tempsMoyenDepuisAttenteOperation.minusHours(tempsMoyenDepuisAttenteLiberation.getHour())
				.minusMinutes(tempsMoyenDepuisAttenteLiberation.getMinute());
	}

	public LocalTime getTempsMoyen() {
		return tempsMoyen;
	}
	
	public LocalTime getTempsMoyen(Salle.listeEtats etat) {
		switch(etat) {
		case ATTENTEPREPARATION:
		case PREPARATION:
			return tempsMoyenDepuisAttentePreparation;
		case ATTE:
		case PREPARATION:
			return 
		case ATTENTEPREPARATION:
		case PREPARATION:
			return 
		case ATTENTEPREPARATION:
		case PREPARATION:
			return 
		default :
			return tempsMoyen;
		}
	}

	public void setTempsMoyen(LocalTime tempsMoyen) {
		this.tempsMoyen = tempsMoyen;
	}

	public void setTempsMoyen() {
		int nombreHeure = tempsPreparation.getHour() + tempsAnesthesie.getHour() + tempsLiberation.getHour();
		int nombreMinute = tempsPreparation.getMinute() + tempsAnesthesie.getMinute() + tempsMoyenOperation
				+ tempsLiberation.getMinute();

		tempsMoyen = getHeureMarge(nombreHeure, nombreMinute);
	}

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

		int nbHeureDsMinute = nombreMinuteMargeEntier / 60;

		nombreHeure += nbHeureDsMinute;
		nombreMinute -= nbHeureDsMinute * 60;

		return LocalTime.of(nombreHeure, nombreMinute);
	}

	public LocalTime getHeureMarge(LocalTime heure) {
		return getHeureMarge(heure, 0, 0);
	}

	public LocalTime getHeureMarge(Integer nombreHeure, Integer nombreMinute) {
		return getHeureMarge(null, nombreHeure, nombreMinute);
	}
}
