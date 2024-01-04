package fr.univtours.polytech;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fr.univtours.polytech.entite.Patient;
import fr.univtours.polytech.ressource.Salle;

public class Planning {
	public Map<Salle, List<Patient>> planning;

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

	public void sortirPatient(Patient patient, Salle salle) throws IllegalAccessError {
		if (lireProchainPatient(salle).getId() == patient.getId()) {
			planning.get(salle).remove(0);
		} else {
			throw new IllegalAccessError(
					"Le patient " + patient.getId() + " n'est pas le prochain patient de la salle " + salle.getId());
		}
	}

	/**
	 * Renvoie la liste des patients actuellement affecte le planning, methode
	 * utilise lorsqu'un nouveau planning doit etre cree
	 * 
	 * @param heure, heure de la simulation au moment du changement de planning
	 * @param extracteur, objet ExtractionJSON qui effectue la sauvegarde du planning
	 * @return patients, liste des patients de ce planning
	 */
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

	public Planning(Map<Salle, List<Patient>> planning, LocalTime heure, ExtractionJSON extracteur) {
		extracteur.extrairePlanning(planning, heure);
		this.planning = planning;
	}
}
