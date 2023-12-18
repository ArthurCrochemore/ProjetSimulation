package fr.univtours.polytech;

import java.time.LocalTime;

public class Constantes {
	private LocalTime tempsPreparation;
	private LocalTime tempsAnesthesie;
	private LocalTime tempsLiberation;
	private Integer nbReserve;
	private Integer tempsMoyenOperation;
	private LocalTime tempsMoyen;

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

	public Constantes(LocalTime tempsPreparation, LocalTime tempsAnesthesie, LocalTime tempsLiberation, Integer nbReserve, Integer tempsMoyenOperation) {
		this.tempsPreparation = tempsPreparation;
		this.tempsAnesthesie = tempsAnesthesie;
		this.tempsLiberation = tempsLiberation;
		this.nbReserve = nbReserve;
		this.tempsMoyenOperation = tempsMoyenOperation;
		this.setTempsMoyen(tempsPreparation.plusHours(tempsAnesthesie.getHour() + tempsLiberation.getHour()).plusMinutes(tempsAnesthesie.getMinute() + tempsLiberation.getMinute() + tempsMoyenOperation));
	}

	public LocalTime getTempsMoyen() {
		return tempsMoyen;
	}

	public void setTempsMoyen(LocalTime tempsMoyen) {
		this.tempsMoyen = tempsMoyen;
	}

}
