package fr.univtours.polytech.ressource;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import fr.univtours.polytech.util.Tuple;

public class Chirurgien extends Ressource {
	public listeEtats getEtat() {
		return etat;
	}
	
	public Chirurgien(Integer id, LocalTime heureDebut) {
		super(id, heureDebut);
	}
}
