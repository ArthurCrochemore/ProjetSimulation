package fr.univtours.polytech.util;

import java.util.ArrayList;
import java.util.List;

public class RangeFloat {
	private Float max;
	private Float min;
	private List<Float> liste;
	private Integer taille;

	public Integer lireTaille() {
		return taille;
	}

	public Float lire(Integer indice) {
		return liste.get(indice);
	}

	public List<Float> lireListe() {
		return liste;
	}

	public RangeFloat(Float max, Float min, Float ecart) {
		this.min = min;
		this.liste = new ArrayList<Float>();
		this.taille = 0;

		while (min <= max) {
			liste.add(min);
			min += ecart;

			taille++;
		}

		this.max = min - ecart;
	}
}
