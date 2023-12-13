 package fr.univtours.polytech.evenements;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.univtours.polytech.Simulation;
import fr.univtours.polytech.entite.Patient;
import fr.univtours.polytech.entite.PatientRDV;
import fr.univtours.polytech.ressource.Chirurgien;
import fr.univtours.polytech.ressource.Infirmier;
import fr.univtours.polytech.ressource.Salle;
import fr.univtours.polytech.util.Tuple;

public class Deroulement {
	private Simulation simulation;
	private LocalTime heureSimulation;
	private LocalTime heureFinSimulation;
	private Map<LocalTime, List<Evenement>> evenements;
	
	//===========================================
	Integer aSuppr;
	Integer aSuppr2;
	public void setASuppr(Integer i) {
		aSuppr = i;
	}
	public Integer getASuppr() {
		return aSuppr;
	}
	public void setASuppr2(Integer i) {
		aSuppr2 = i;
	}
	public Integer getASuppr2() {
		return aSuppr2;
	}
	//===========================================	
	
	/**
	 * Accesseur en lecture de la simulation
	 * 
	 * @return simulation
	 */
	public Simulation getSimulation() {
		return simulation;
	}

	/**
	 * Accesseur en lecture de l'heure de la simulation
	 * 
	 * @return heureSimulation
	 */
	public LocalTime getHeureSimulation() {
		return heureSimulation;
	}

	/**
	 * Accesseur en lecture de la map des Evenements
	 * 
	 * @return evenements
	 */
	public Map<LocalTime, List<Evenement>> getEvenements() {
		return evenements;
	}

	/**
	 * Accesseur en ecriture de l'heure de la simulation
	 * 
	 * @param heureSimulation
	 */
	public void setHeureSimulation(LocalTime heureSimulation) {
		this.heureSimulation = heureSimulation;
	}

	/**
	 * Permet d'ajouter un évenement à la map des Evenements
	 * 
	 * @param heure,     heure a laquelle l'evenement doit etre execute
	 * @param evenement, evenement qui est ajoute
	 */
	public void ajouterEvenement(LocalTime heure, Evenement evenement) {
		List<Evenement> liste = evenements.get(heure);
		if (liste == null) {
			liste = new ArrayList<Evenement>();
			evenements.put(heure, liste);
		}
		liste.add(evenement);
	}

	/**
	 * Renvoi le prochain evenement a executer
	 * 
	 * @return Evenement
	 */
	public Evenement prochainEvenement() {
		while (!evenements.containsKey(heureSimulation)) {
			heureSimulation = heureSimulation.plusMinutes(1);
			
			if (!heureSimulation.isBefore(heureFinSimulation)) {
				return null;
			}
		}

		return evenements.get(heureSimulation).get(0);
	}

	/**
	 * Execute l'evenement en parametre
	 * 
	 * @param evenement, Evenement a executer
	 */
	public void executerEvenement(Evenement evenement) {
		evenement.deroulement();
		List<Evenement> liste = evenements.get(heureSimulation);
		if (liste.size() == 1) {
			evenements.remove(heureSimulation);
		} else {
			liste.remove(0);
		}
	}

	/**
	 * Methode principale de Deroulement qui gere le deroulement correct des
	 * evenements de la simulation
	 */
	public void execution() {
		Evenement evenement = null;
		while (heureSimulation.isBefore(heureFinSimulation)) {
			evenement = prochainEvenement();

			if (evenement != null) {
				executerEvenement(evenement);
			}
		}
		finExecution();
	}

	/**
	 * Methode qui ferme les tuples de temps en attentes ouverts dans les ressources
	 */
	public void finExecution() {
		for (Infirmier i : simulation.getInfirmiers()) {
			Tuple<LocalTime, LocalTime> tuple = i.getTempsAttente().get(i.getTaille() - 1);
			if (tuple.getSecondElement() == null) {
				tuple.setSecondElement(heureFinSimulation);
			}
		}
		
		for (Chirurgien c : simulation.getChirurgiens()) {
			Tuple<LocalTime, LocalTime> tuple = c.getTempsAttente().get(c.getTaille() - 1);
			if (tuple.getSecondElement() == null) {
				tuple.setSecondElement(heureFinSimulation);
			}
		}
		
		Map<Salle.typeSalles, List<Salle>> salles = simulation.getSalles();
		for (Salle s : salles.get(Salle.typeSalles.PEUEQUIPE)) {
			Tuple<LocalTime, LocalTime> tuple = s.getTempsAttente().get(s.getTaille() - 1);
			if (tuple.getSecondElement() == null) {
				tuple.setSecondElement(heureFinSimulation);
			}
		}
		for (Salle s : salles.get(Salle.typeSalles.SEMIEQUIPE)) {
			Tuple<LocalTime, LocalTime> tuple = s.getTempsAttente().get(s.getTaille() - 1);
			if (tuple.getSecondElement() == null) {
				tuple.setSecondElement(heureFinSimulation);
			}
		}
		for (Salle s : salles.get(Salle.typeSalles.TRESEQUIPE)) {
			Tuple<LocalTime, LocalTime> tuple = s.getTempsAttente().get(s.getTaille() - 1);
			if (tuple.getSecondElement() == null) {
				tuple.setSecondElement(heureFinSimulation);
			}
		}
		for (PatientRDV p : simulation.getPatientsRDV()) {
			Patient.listeEtats e = p.getEtat();
			if(e == Patient.listeEtats.ATTENTESALLE) {
				p.setEtat(Patient.listeEtats.AATTENDUUNESALLE, heureFinSimulation);
			} else {
				if(e == Patient.listeEtats.ATTENTEPREPARATION) {
					p.setEtat(Patient.listeEtats.ENPREPARATION, heureFinSimulation);
				} else {
					if(e == Patient.listeEtats.ATTENTECHIRURGIEN) {
						p.setEtat(Patient.listeEtats.ENOPERATION, heureFinSimulation);
					} else {
						if(e == Patient.listeEtats.ATTENTELIBERATION) {
							p.setEtat(Patient.listeEtats.TERMINE, heureFinSimulation);
						} 
					}
				}
			}
		}
		System.out.println("\n===================================================\n" + aSuppr2 + " patients traites / " + aSuppr + " patients\n Y a t-il un probleme : " + (!(aSuppr == aSuppr2)));
	}

	/**
	 * Constructeur de confort de l'objet Deroulement
	 * 
	 * @param simulation
	 * @param heureSimulation
	 * @param heureFinSimulation
	 */
	public Deroulement(Simulation simulation, LocalTime heureSimulation, LocalTime heureFinSimulation) {
		this.simulation = simulation;
		this.heureSimulation = heureSimulation;
		this.heureFinSimulation = heureFinSimulation;

		evenements = new HashMap<LocalTime, List<Evenement>>();
	}
}
