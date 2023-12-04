package fr.univtours.polytech;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.univtours.polytech.entite.Patient;
import fr.univtours.polytech.ressource.Salle;

public class Planning {
	private Map<Salle, List<Patient>> planning;
	
	
	/**
	 * Renvoie la salle a laquelle un patient est affecte s'il est le prochain et que la salle est LIBRE, sinon renvoi null
	 * @param patient
	 * @return salle, la salle affecte a patient, null si pas encore disponible pour lui
	 */
	public Salle lireSalle(Patient patient) {
		for(Salle salle : planning.keySet()) {
			if(patient == planning.get(salle).get(0) ) {
				if (salle.getEtat() == Salle.listeEtats.LIBRE)
					return salle;
				return null;
			}
		}
		return null;
	}
	
	/**
	 * Renvoie l'ID 
	 * @param salle
	 * @return
	 */
	public Patient lireProchainPatient(Salle salle) {
		return planning.get(salle).get(0);
	}
	
	public List<Patient> extraiteDonnee() {
		List<Patient> patientParSalle;
		List<Patient> patients = new ArrayList<Patient>();
		for(Salle salle : planning.keySet()) {
			patientParSalle = planning.get(salle);
			for (int indice = 0; indice < patientParSalle.size(); indice ++)
				patients.add(patientParSalle.get(indice));
		}
			
		return patients;
	}
	
	public Planning(Planning autrePlanning) {
        this.planning = new HashMap<>(); // Initialisation d'une nouvelle map pour éviter les références partagées

        // Copie des données de l'autre instance de Planning
        for (Salle salle : autrePlanning.planning.keySet()) {
            List<Patient> patients = autrePlanning.planning.get(salle);
            this.planning.put(salle, new ArrayList<>(patients)); // Copie profonde de la liste des patients
        }
	}
}
