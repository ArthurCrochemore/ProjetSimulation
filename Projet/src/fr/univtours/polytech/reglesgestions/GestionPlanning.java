package fr.univtours.polytech.reglesgestions;

import fr.univtours.polytech.Planning;
/**
 * Interface des regles de gestions pour les plannings.
 */
interface GestionPlanning extends Regle{	
	
	public Planning solution();
}
