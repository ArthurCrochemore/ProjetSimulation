package fr.univtours.polytech.reglesgestions;

// Import des classes nécessaires
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import fr.univtours.polytech.Planning;
import fr.univtours.polytech.Simulation;
import fr.univtours.polytech.entite.Patient;
import fr.univtours.polytech.entite.PatientRDV;
import fr.univtours.polytech.ressource.Salle;

// Classe implémentant l'interface GestionPlanning
public class GPPrioritePremierArriveReserveStatique implements GestionPlanning {
	private Simulation simulation;

	// Constructeur prenant une simulation en paramètre
	public GPPrioritePremierArriveReserveStatique(Simulation simulation) {
		this.simulation = simulation;
	}

	// Méthode de résolution de la planification
	public Planning solution(Patient patientUrgent) {
		// Initialisation de la nouvelle liste de patients
		List<Patient> nouvListePatient = new ArrayList<>();

		if (patientUrgent != null) {
			// Si un patient urgent est donné, récupérer le planning existant et l'ajouter à
			// la nouvelle liste
			Planning ancienPlanning = simulation.getPlanning();
			nouvListePatient = ancienPlanning.extraiteDonnee();
			nouvListePatient.add(patientUrgent);
			System.out.println("changememnt planning");
		} else {
			// Sinon,cela signifie que c'est la création du premier et planning et donc il faut récupérer les patients prévus pour un rendez-vous et les ajouter à la
			// nouvelle liste
			System.out.println("premier planning");
			List<PatientRDV> patientsRDV = simulation.getPatientsRDV();
			for (Patient p : patientsRDV) {
				if (p != null && !nouvListePatient.contains(p)) {
					nouvListePatient.add(p);
				}
			}
		}

		// Récupération des salles de la simulation
		Collection<List<Salle>> pileSalle = simulation.getSalles().values();

		// Comparateur pour trier les salles en fonction de leur état et type
		Comparator<Salle> etatComparator = Comparator.comparing((Salle salle) -> {
			// Comparaison des états pour trier les salles
			Salle.listeEtats[] orderedStates = { Salle.listeEtats.LIBRE, Salle.listeEtats.LIBERATION,
					Salle.listeEtats.ATTENTELIBERATION, Salle.listeEtats.OPERATION, Salle.listeEtats.ATTENTEOPERATION,
					Salle.listeEtats.PREPARATION, Salle.listeEtats.ATTENTEPREPARATION };
			for (int i = 0; i < orderedStates.length; i++) {
				if (salle.getEtat() == orderedStates[i]) {
					return i;
				}
			}
			return orderedStates.length;
		}).thenComparing((Salle salle) -> {
			// Comparaison des types pour trier les salles
			Salle.typeSalles[] orderedTypes = { Salle.typeSalles.RESERVE, Salle.typeSalles.PEUEQUIPE,
					Salle.typeSalles.SEMIEQUIPE, Salle.typeSalles.TRESEQUIPE };
			for (int i = 0; i < orderedTypes.length; i++) {
				if (salle.getType() == orderedTypes[i]) {
					return i;
				}
			}
			return orderedTypes.length;
		});

		// Tri des salles en utilisant le comparateur d'état et type
		for (List<Salle> salles : pileSalle) {
			Collections.sort(salles, etatComparator);
		}

		// Tri de la liste de patients par heure d'arrivée
		ReglesDeGestion.trierParHeureArrivee(nouvListePatient);

		// Initialisation de la nouvelle carte de planification
		Map<Salle, List<Patient>> nouvelleMapPlanning = new HashMap<>();

		// Association de chaque salle à une liste vide dans la carte de planification
		for (List<Salle> salles : pileSalle) {
			for (Salle salle : salles) {
				nouvelleMapPlanning.put(salle, new ArrayList<>());
			}
		}

		// Attribution des salles aux patients en fonction de leur urgence ou gravité
		for (Patient patient : nouvListePatient) {
			if (patient.estUrgent()) {
				for (List<Salle> salles : pileSalle) {
					for (Iterator<Salle> iterator = salles.iterator(); iterator.hasNext();) {
						Salle salle = iterator.next();
						// Attribution des salles "RESERVE" ou "TRESEQUIPE" aux patients urgents
						if ((salle.getType() == Salle.typeSalles.RESERVE
								|| salle.getType() == Salle.typeSalles.TRESEQUIPE)) {
							nouvelleMapPlanning.get(salle).add(patient);
							iterator.remove();
							salles.add(salle);
							break;
						}
					}
				}
			} else {
				for (List<Salle> salles : pileSalle) {
					for (Iterator<Salle> iterator = salles.iterator(); iterator.hasNext();) {
						Salle salle = iterator.next();
						// Attribution des salles correspondant à la gravité du patient
						if ((salle.getType() == Salle.typeSalles.PEUEQUIPE
								&& patient.getGravite() == PatientRDV.listeGravite.PEUEQUIPE)
								|| (salle.getType() == Salle.typeSalles.SEMIEQUIPE
										&& patient.getGravite() == PatientRDV.listeGravite.SEMIEQUIPE)
								|| (salle.getType() == Salle.typeSalles.TRESEQUIPE
										&& patient.getGravite() == PatientRDV.listeGravite.TRESEQUIPE)) {
							nouvelleMapPlanning.get(salle).add(patient);
							iterator.remove();
							salles.add(salle);
							break;
						}
					}
				}
			}
		}

		// Retourne un nouvel objet Planning basé sur la carte de planification mise à jour
		
		return new Planning(nouvelleMapPlanning);
	}
}
