package fr.univtours.polytech;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fr.univtours.polytech.entite.Patient;
import fr.univtours.polytech.ressource.Salle;

public class Planning {
	private Map<Salle, List<Patient>> planning;

	/**
	 * Renvoie la salle a laquelle un patient est affecte s'il est le prochain et
	 * que la salle est LIBRE, sinon renvoi null
	 * 
	 * @param patient
	 * @return salle, la salle affecte a patient, null si pas encore disponible pour
	 *         lui
	 */
	public Salle lireSalle(Patient patient) {
		for (Salle salle : planning.keySet()) {
			if (planning.get(salle).size() > 0) {
				if (patient.getId() == planning.get(salle).get(0).getId()) {
					if (salle.getEtat() == Salle.listeEtats.LIBRE)
						return salle;
					return null;
				}
			}
		}
		return null;
	}

	/**
	 * Renvoie l'ID
	 * 
	 * @param salle
	 * @return
	 */
	public Patient lireProchainPatient(Salle salle) {
		if (planning.get(salle).size() > 0) {
			return planning.get(salle).get(0);
		}
		return null;
	}

	public List<Patient> extraiteDonnee() {
		List<Patient> patientParSalle;
		List<Patient> patients = new ArrayList<Patient>();
		for (Salle salle : planning.keySet()) {
			patientParSalle = planning.get(salle);
			for (int indice = 0; indice < patientParSalle.size(); indice++)
				patients.add(patientParSalle.get(indice));
		}

		return patients;
	}

	public Planning(Map<Salle, List<Patient>> planning) {
		this.planning = planning;
	}

	public Planning() {

	}
}
