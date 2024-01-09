package fr.univtours.polytech;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.univtours.polytech.entite.Patient;
import fr.univtours.polytech.ressource.Salle;
import fr.univtours.polytech.util.Tuple;

public class ListesAttentes {
	public enum typeListes {
		LAI, LAIP, LAIL, LAC, LACU, LAIU
	};

	private Map<typeListes, List<Tuple<Salle, Patient>>> listesAttente;

	/**
	 * Accesseur en lecture des listes d'attente
	 * 
	 * @return listesAttente
	 */
	public Map<typeListes, List<Tuple<Salle, Patient>>> getListesAttente() {
		return listesAttente;
	}

	/**
	 * Méthode permettant l'ajout d'un patient à une liste d'attente
	 * 
	 * @param type
	 * @param salle
	 * @param patient
	 */
	public void ajouter(typeListes type, Salle salle, Patient patient) {
		listesAttente.get(type).add(new Tuple<Salle, Patient>(salle, patient));
	}

	/**
	 * Initialisation des listes d'attente
	 */
	public ListesAttentes() {
		listesAttente = new HashMap<typeListes, List<Tuple<Salle, Patient>>>();

		listesAttente.put(typeListes.LAI, new ArrayList<Tuple<Salle, Patient>>());
		listesAttente.put(typeListes.LAIP, new ArrayList<Tuple<Salle, Patient>>());
		listesAttente.put(typeListes.LAIL, new ArrayList<Tuple<Salle, Patient>>());
		listesAttente.put(typeListes.LAC, new ArrayList<Tuple<Salle, Patient>>());
		listesAttente.put(typeListes.LACU, new ArrayList<Tuple<Salle, Patient>>());
		listesAttente.put(typeListes.LAIU, new ArrayList<Tuple<Salle, Patient>>());
	}
}
