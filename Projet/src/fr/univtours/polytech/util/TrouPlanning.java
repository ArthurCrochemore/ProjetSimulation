package fr.univtours.polytech.util;

import java.time.LocalDateTime;

import fr.univtours.polytech.ressource.Salle;

public class TrouPlanning {
	 private LocalDateTime heureDebut;
	    private LocalDateTime heureFin;
	    private int indice;
	    private Salle salle;

	    // Constructeur pour initialiser les champs
	    public TrouPlanning( LocalDateTime heureDebut, LocalDateTime heureFin, int indice, Salle salle) {
	        this.heureDebut = heureDebut;
	        this.heureFin = heureFin;
	        this.indice = indice;
	        this.salle = salle;
	    }
}
