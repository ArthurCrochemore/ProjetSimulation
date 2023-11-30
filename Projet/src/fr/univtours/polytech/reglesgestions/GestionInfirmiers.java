package fr.univtours.polytech.reglesgestions;

import fr.univtours.polytech.ressource.Salle;

/**
 * Interface des regles de gestions pour les infirmiers.
 */
public interface GestionInfirmiers extends Regle{
	public Salle solution();
	
}
