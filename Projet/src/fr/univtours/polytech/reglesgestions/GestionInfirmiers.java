package fr.univtours.polytech.reglesgestions;

import fr.univtours.polytech.entite.Patient;
import fr.univtours.polytech.ressource.Salle;
import fr.univtours.polytech.util.Tuple;

/**
 * Interface des regles de gestions pour les infirmiers.
 */
public interface GestionInfirmiers {
	public Tuple<Salle, Patient> solution();

	public void ajoutSalle(Salle salle, Patient patient);
}
