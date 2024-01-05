package fr.univtours.polytech.ressource;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import fr.univtours.polytech.util.Tuple;

public abstract class Ressource {
	private Integer id;
	Integer taille; // Correspond au nombre d'affectation que la Ressource a eu au cours de la
					// Simulation
	private List<Tuple<LocalTime, LocalTime>> tempsAttente; // Stocke les Intervalles de temps où la ressource n'est pas
															// utilisée

	/* Etats par lesquels la ressource passe */
	public static enum listeEtats {
		LIBRE, OCCUPE
	};

	protected listeEtats etat;

	public Integer getId() {
		return id;
	}

	public Integer getTaille() {
		return taille;
	}

	public void incrementerTaille() {
		this.taille++;
	}

	public List<Tuple<LocalTime, LocalTime>> getTempsAttente() {
		return tempsAttente;
	}

	/**
	 * Accesseur en écriture de l'attribut etat. Gère aussi la mise à jour de
	 * l'attente
	 * 
	 * @param etat
	 * @param heure, heure à partir de laquelle la ressource change d'état
	 */
	public void setEtat(listeEtats etat, LocalTime heure) {
		try {
			
			/*
			 * Si la ressource devient occupée, on ferme l'intervalle de temps précedemment
			 * ouvert
			 */
			if (this.etat == Ressource.listeEtats.LIBRE && etat == Ressource.listeEtats.OCCUPE) {
				tempsAttente.get(taille - 1).setSecondElement(heure);
			}
			/* Sinon, on ouvre un nouvel intervalle de temps */
			else {
				if (this.etat == Ressource.listeEtats.OCCUPE && etat == Ressource.listeEtats.LIBRE) {
					tempsAttente.add(new Tuple<LocalTime, LocalTime>(heure));
	
					incrementerTaille();
					;
				} else {
					throw new Exception("Changement d'etat illegal");
				}
			}
			this.etat = etat;
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public Ressource(Integer id, LocalTime heureDebut) {
		this.id = id;
		this.etat = Ressource.listeEtats.LIBRE;

		taille = 1;
		tempsAttente = new ArrayList<Tuple<LocalTime, LocalTime>>();
		tempsAttente.add(new Tuple<LocalTime, LocalTime>(heureDebut));
	}
}
