package fr.univtours.polytech.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import fr.univtours.polytech.Simulation;
import fr.univtours.polytech.entite.Patient;
import fr.univtours.polytech.ressource.Salle;

public class TrouPlanning {
	private LocalTime heureMinDebut;
	private LocalTime heureMaxDebut;
	private int indice;
	private Salle salle;

	// Constructeur pour initialiser les champs
	public TrouPlanning(LocalTime heureMinDebut, LocalTime heureMaxDebut, int indice, Salle salle) {
		this.heureMinDebut = heureMinDebut;
		this.heureMaxDebut = heureMaxDebut;
		this.indice = indice;
		this.salle = salle;
	}

	public LocalTime getHeureMinDebut() {
		return heureMinDebut;
	}

	public void setHeureMinDebut(LocalTime heureMinDebut) {
		this.heureMinDebut = heureMinDebut;
	}

	public LocalTime getHeureMaxDebut() {
		return heureMaxDebut;
	}

	public void setHeureMaxDebut(LocalTime heureMaxDebut) {
		this.heureMaxDebut = heureMaxDebut;
	}

	public int getIndice() {
		return indice;
	}

	public void setIndice(int indice) {
		this.indice = indice;
	}

	public Salle getSalle() {
		return salle;
	}

	public void setSalle(Salle salle) {
		this.salle = salle;
	}

	public List<TrouPlanning> RechercheTrouPlanning(Simulation simulation) {
		// regarder ppour cahque salle tout les couple de patient d'affiler comme (1,2),
		// (2,3)...
		// Si délai entre 2 heure d'arrivée patient > 2(TMP ANSTH2SIE + temps libération
		// + temps préparation + temps d'opération moyenne)
		// Alors new TrouPlanning(heureDebutMin = patient1.heured'arivé + 4 tmp,
		// heureMaxDebut = , indice = indice patient1 +1,salle = patient1.getSale
		// sinon return null
		List<TrouPlanning> listTrouPlanning = new ArrayList<TrouPlanning>();
		Collection<List<Salle>> salles = simulation.getSalles().values();

		for (List<Salle> salleList : salles) {
			for (Salle salle : salleList) {
				List<Patient> patients = simulation.getPlanning().extraiteDonnee();

				for (int i = 0; i < patients.size() - 1;) {
					Patient patient1 = patients.get(i);
					Patient patient2 = patients.get(i + 1);

					LocalTime tempsMoyen = simulation.getConstantes().getTempsMoyen();
					LocalTime heureArrivePatient1 = patient1.getHeureArrive();
					LocalTime heureArrivePatient2 = patient2.getHeureArrive();

					// Calcul du délai entre les deux heures d'arrivée des patients
					LocalTime delaiPatients = heureArrivePatient2.minusHours(heureArrivePatient1.getHour())
							.minusMinutes(heureArrivePatient1.getMinute());

					// Vérification si le délai entre les patients est supérieur au temps total
					// d'attente requis
					if (delaiPatients.isAfter(tempsMoyen.plusMinutes(tempsMoyen.getMinute()))) {
						listTrouPlanning.add(new TrouPlanning(
								heureArrivePatient1.plusHours(tempsMoyen.getHour()).plusMinutes(tempsMoyen.getMinute()),
								heureArrivePatient2.minusHours(tempsMoyen.getHour())
										.plusMinutes(tempsMoyen.getMinute()),
								i + 1, salle));
					}

				}
			}
		}
		return listTrouPlanning;
	}

}
