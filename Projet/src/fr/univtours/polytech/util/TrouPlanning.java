package fr.univtours.polytech.util;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.univtours.polytech.entite.Patient;
import fr.univtours.polytech.ressource.Salle;

public class TrouPlanning {
	private LocalTime heureFinPatient1;
	private LocalTime heureLimiteDebutNouveauPatient;
	private int indice;
	private Salle salle;

	public static TrouPlanning CreerPlaningDepuisHeureFinPatient1(LocalTime heureFinPatient1, LocalTime heureArriveePatient2, int indice,
			Salle salle, LocalTime tempsMoyen) {
		try {
			LocalTime heureLimiteDebutNouveauPatient = heureArriveePatient2.minusHours(tempsMoyen.getHour())
					.minusMinutes(tempsMoyen.getMinute());
			if (heureFinPatient1.isBefore(heureLimiteDebutNouveauPatient))
				throw new Exception("Erreur : Le Trou crée est trop court");

			return new TrouPlanning(heureFinPatient1, heureLimiteDebutNouveauPatient, indice, salle);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static TrouPlanning CreerPlaning(LocalTime heureArriveePatient1, LocalTime heureArriveePatient2, int indice,
			Salle salle, LocalTime tempsMoyen) {
		
		LocalTime heureFinPatient1 = heureArriveePatient1.plusHours(tempsMoyen.getHour())
				.plusMinutes(tempsMoyen.getMinute());
		
		return CreerPlaningDepuisHeureFinPatient1(heureFinPatient1, heureArriveePatient2, indice, salle, tempsMoyen);		
	}

	// Constructeur pour initialiser les champs
	private TrouPlanning(LocalTime heureFinPatient1, LocalTime heureLimiteDebutNouveauPatient, int indice,
			Salle salle) {
		this.heureFinPatient1 = heureFinPatient1;
		this.heureLimiteDebutNouveauPatient = heureLimiteDebutNouveauPatient;
		this.indice = indice;
		this.salle = salle;
	}

	public LocalTime getheureFinPatient1() {
		return heureFinPatient1;
	}

	public LocalTime getheureLimiteDebutNouveauPatient() {
		return heureLimiteDebutNouveauPatient;
	}

	public int getIndice() {
		return indice;
	}

	public Salle getSalle() {
		return salle;
	}

	// regarder ppour cahque salle tout les couple de patient d'affiler comme (1,2),
	// (2,3)...
	// Si délai entre 2 heure d'arrivée patient > 2(TMP ANSTH2SIE + temps libération
	// + temps préparation + temps d'opération moyenne)
	// Alors new TrouPlanning(heureDebutMin = patient1.heured'arivé + 4 tmp,
	// heureMaxDebut = , indice = indice patient1 +1,salle = patient1.getSale
	// sinon return null
	public static Map<Salle, List<TrouPlanning>> RechercheTrouPlanning(List<Salle> listeSalleTresEquipe, Map<Salle, List<Patient>> planning, LocalTime tempsMoyen) {
	    Map<Salle, List<TrouPlanning>> MapTrou = new HashMap<Salle, List<TrouPlanning>>();

	    for (Salle salleTE : listeSalleTresEquipe) {
	        List<Patient> listePatientTE = planning.get(salleTE);
	        List<TrouPlanning> listTrouPlanning = new ArrayList<TrouPlanning>();

	        for (int i = 0; i < listePatientTE.size() - 1; i++) {
	            Patient patient1 = listePatientTE.get(i);
	            Patient patient2 = listePatientTE.get(i + 1);

	            TrouPlanning trou = CreerPlaning(patient1.getHeureArrive(), patient2.getHeureArrive(), 1, salleTE, tempsMoyen);
	            if (trou != null) {
	                listTrouPlanning.add(trou);
	            }
	        }
	        MapTrou.put(salleTE, listTrouPlanning);
	    }
	    return MapTrou;
	}
		
}
