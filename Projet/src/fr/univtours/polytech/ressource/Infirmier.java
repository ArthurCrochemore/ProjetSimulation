package fr.univtours.polytech.ressource;

import java.time.LocalTime;

public class Infirmier extends Ressource {
	public listeEtats getEtat() {
		return etat;
	}

	public Infirmier(Integer id, LocalTime heureDebut) {
		super(id, heureDebut);
	}
}
