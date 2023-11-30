package fr.univtours.polytech.ressource;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.univtours.polytech.ressource.Salle.listeEtats;
import fr.univtours.polytech.util.Tuple;

public abstract class Ressource {
	private Integer id;
	Integer taille;
	private List<Tuple<LocalTime, LocalTime>> tempsAttente;
	
	public static enum listeEtats {
		LIBRE, OCCUPE
	};

	protected listeEtats etat;
	private Map<Ressource.listeEtats, List<Tuple<LocalTime, LocalTime>>> donnees;
	
	public Integer getId() {
		return id;
	}

	public Integer getTaille() {
		return taille;
	}
	
	public void incrementerTaille() {
		this.taille ++ ;
	}

	public List<Tuple<LocalTime, LocalTime>> getTempsAttente() {
		return tempsAttente;
	}
	
	public void setEtat(listeEtats etat) {
		this.etat = etat;
	}
	
	public Ressource(Integer id, LocalTime heureDebut) {
		this.id = id;
		this.etat = Ressource.listeEtats.LIBRE;

		taille = 1;
		tempsAttente = new ArrayList<Tuple<LocalTime, LocalTime>>();
		tempsAttente.add(new Tuple<LocalTime, LocalTime>(heureDebut));
	}
}
