package fr.univtours.polytech;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.univtours.polytech.ressource.Salle;

public class ListesAttentes {
	public enum typeListes {
		LAI, LAIP, LAIL, LAC, LACU, LAIU
	};

	private Map<typeListes, List<Salle>> listesAttente;

	public Map<typeListes, List<Salle>> getListesAttente() {
		return listesAttente;
	}

	public void ajouter(typeListes type, Salle salle) {
		listesAttente.get(type).add(salle);
	}
	
	public ListesAttentes() {
		listesAttente = new HashMap<typeListes, List<Salle>>();
		
		listesAttente.put(typeListes.LAI, new ArrayList<Salle>());
		listesAttente.put(typeListes.LAIP, new ArrayList<Salle>());
		listesAttente.put(typeListes.LAIL, new ArrayList<Salle>());
		listesAttente.put(typeListes.LAC, new ArrayList<Salle>());
		listesAttente.put(typeListes.LACU, new ArrayList<Salle>());
		listesAttente.put(typeListes.LAIU, new ArrayList<Salle>());
	}
}
