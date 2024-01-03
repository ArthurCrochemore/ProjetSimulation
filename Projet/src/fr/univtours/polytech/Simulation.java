package fr.univtours.polytech;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.univtours.polytech.entite.PatientRDV;
import fr.univtours.polytech.entite.PatientUrgent;
import fr.univtours.polytech.evenements.Deroulement;
import fr.univtours.polytech.evenements.EvArrivePatient;
import fr.univtours.polytech.evenements.EvDeclarationPatientUrgent;
import fr.univtours.polytech.initialisation.DonneeInitialisation;
import fr.univtours.polytech.reglesgestions.ReglesDeGestion;
import fr.univtours.polytech.ressource.Chirurgien;
import fr.univtours.polytech.ressource.Infirmier;
import fr.univtours.polytech.ressource.Salle;

public class Simulation {
	private ListesAttentes listes;
	private Deroulement deroulement;
	private List<PatientRDV> patientsRDV;
	private List<PatientUrgent> patientsUrgent;
	private List<Infirmier> infirmiers;
	private List<Chirurgien> chirurgiens;
	private Map<Salle.typeSalles, List<Salle>> salles;
	private Planning planning;
	private ReglesDeGestion regles;
	private Constantes constantes;

	private LocalTime heureDebutSimulation;
	private LocalTime heureFinSimulation;

	/**
	 * Accesseur en lecture de l'objet deroulement
	 * 
	 * @return deroulement
	 */
	public Deroulement getDeroulement() {
		return deroulement;
	}

	/**
	 * Accesseur en écriture de l'objet ListeAttentes
	 * 
	 * @param listes
	 */
	public void setListes(ListesAttentes listes) {
		this.listes = listes;
	}

	/**
	 * Accesseur en lecture de l'objet ListeAttentes
	 * 
	 * @return listes
	 */
	public ListesAttentes getListes() {
		return listes;
	}

	/**
	 * Accesseur en lecture de la liste contenant les patients RDV
	 * 
	 * @return patientsRDV
	 */
	public List<PatientRDV> getPatientsRDV() {
		return patientsRDV;
	}

	/**
	 * Accesseur en lecture de la liste contenant les patients urgents
	 * 
	 * @return patientsUrgent
	 */
	public List<PatientUrgent> getPatientsUrgent() {
		return patientsUrgent;
	}

	/**
	 * Accesseur en lecture de la liste contennant les infirmiers
	 * 
	 * @return infirmiers
	 */
	public List<Infirmier> getInfirmiers() {
		return infirmiers;
	}

	/**
	 * Accesseur en lecture de la liste contenant les chirugiens
	 * 
	 * @return chirurgiens
	 */
	public List<Chirurgien> getChirurgiens() {
		return chirurgiens;
	}

	/**
	 * Accesseur en lecture de la map contenant les salles
	 * 
	 * @return salles
	 */
	public Map<Salle.typeSalles, List<Salle>> getSalles() {
		return salles;
	}

	/**
	 * Accesseur en lecture du planning d'affectation des salles aux patients
	 * 
	 * @return planning
	 */
	public Planning getPlanning() {
		return planning;
	}

	/**
	 * Accesseur en écriture de planning
	 * 
	 * @param planning
	 */
	public void setPlanning(Planning planning) {
		this.planning = planning;
	}

	/**
	 * Accesseur en lecture des regles de gestions appliquées
	 * 
	 * @return regles
	 */
	public ReglesDeGestion getRegles() {
		return regles;
	}

	/**
	 * Accesseur en lecture des constantes définies pour la simulation
	 * 
	 * @return constantes
	 */
	public Constantes getConstantes() {
		return constantes;
	}

	/**
	 * Accesseur en lecture de l'heure de debut de la simulation
	 * 
	 * @return heureDebutSimulation
	 */
	public LocalTime getHeureDebutSimulation() {
		return heureDebutSimulation;
	}

	/**
	 * Accesseur en lecture de l'heure de fin de la simulation
	 * 
	 * @return heureFinSimulation
	 */
	public LocalTime getHeureFinSimulation() {
		return heureFinSimulation;
	}

	/**
	 * Constructeur de Simulation qui gère l'initisialisation de toutes les données
	 * necessaires à la simulation puis qui la lance
	 * 
	 * @param data, objet DonneeInitialisation qui contient toutes les donnees qui
	 *              ont ete lues dans la fichier d'initialisation
	 * @throws Exception
	 */
	public Simulation(DonneeInitialisation data) throws Exception {

		listes = new ListesAttentes();

		/* Creation des regles de gestion appliquees */
		regles = new ReglesDeGestion(this, data.getRegle1(), data.getRegle2(), data.getRegle3());

		/*
		 * Definition des heures de la journee et creation de l'objet Deroulement qui
		 * gere le deroulement des evenements de la simulation
		 */
		heureDebutSimulation = data.getHeureDebutJournee();
		heureFinSimulation = data.getHeureFinJournee();
		deroulement = new Deroulement(this, heureDebutSimulation, data.getHeureFinJournee());

		/* Creations des Ressources */
		this.infirmiers = new ArrayList<Infirmier>();
		this.chirurgiens = new ArrayList<Chirurgien>();
		this.salles = new HashMap<Salle.typeSalles, List<Salle>>();

		Integer nbInfirmiers = data.getNbInfirmiere();
		for (int indice = 0; indice < nbInfirmiers; indice++)
			infirmiers.add(new Infirmier(indice, heureDebutSimulation));

		Integer nbChirurgiens = data.getNbChirurgien();
		for (int indice = 0; indice < nbChirurgiens; indice++)
			chirurgiens.add(new Chirurgien(indice, heureDebutSimulation));

		List<Salle> liste = new ArrayList<Salle>();
		Integer nbPeuEquipe = data.getNbSallesPeuEquipee();
		for (int indice = 0; indice < nbPeuEquipe; indice++)
			liste.add(new Salle(indice, Salle.typeSalles.PEUEQUIPE, heureDebutSimulation));
		salles.put(Salle.typeSalles.PEUEQUIPE, liste);

		liste = new ArrayList<Salle>();
		Integer nbSemiEquipe = data.getNbSallesSemiEquipee();
		for (int indice = 0; indice < nbSemiEquipe; indice++)
			liste.add(new Salle(indice + nbPeuEquipe, Salle.typeSalles.SEMIEQUIPE, heureDebutSimulation));
		salles.put(Salle.typeSalles.SEMIEQUIPE, liste);

		liste = new ArrayList<Salle>();
		Integer nbTresEquipe = data.getNbSallesTresEquipee();
		for (int indice = 0; indice < nbTresEquipe; indice++)
			liste.add(
					new Salle(indice + nbPeuEquipe + nbSemiEquipe, Salle.typeSalles.TRESEQUIPE, heureDebutSimulation));
		Integer nbReserve = data.getNbSallesReserveesUrgence();
		for (int indice = 0; indice < nbReserve; indice++)
			liste.add(new Salle(indice + nbPeuEquipe + nbSemiEquipe + nbTresEquipe, Salle.typeSalles.RESERVE,
					heureDebutSimulation));
		salles.put(Salle.typeSalles.TRESEQUIPE, liste); // Une salle reservee est une salle tres equipee et son statut
														// de reserve peut changer au fil de la simulation

		/* Definition des constantes */
		this.constantes = new Constantes(data.getTempsPreparation(), data.getTempsAnesthesie(),
				data.getTempsLiberation(), nbReserve, data.getMoyTempsOperation(), data.getMarge());

		/*
		 * Creation des patients et des Evenements liés à ces patients (declaration pour
		 * les patients urgents et arrivees pour les patients rdv
		 */
		this.patientsRDV = new ArrayList<PatientRDV>();
		this.patientsUrgent = new ArrayList<PatientUrgent>();

		Integer nbPatientsRDV = data.getNbPatientsRDV();
		Integer nbPatientsUrgent = data.getNbPatientsUrgent();
		Map<Integer, LocalTime> mapArrivees = data.getMapArrivees();
		Map<Integer, PatientRDV.listeGravite> mapGravites = data.getMapGravites();
		Map<Integer, LocalTime> mapTempsOperation = data.getMapTempsOperation();
		Map<Integer, LocalTime> mapDeclaration = data.getMapDeclaration();

		for (int i = 0; i < nbPatientsRDV; i++) {
			LocalTime heureArrivee = mapArrivees.get(i);
			PatientRDV nvPatient = new PatientRDV(i, heureArrivee, mapGravites.get(i), mapTempsOperation.get(i));
			patientsRDV.add(nvPatient);
			deroulement.ajouterEvenement(heureArrivee,
					new EvArrivePatient(heureArrivee, nvPatient, null, null, null, deroulement));
		}

		for (int i = 0; i < nbPatientsUrgent; i++) {
			LocalTime heureDeclaration = mapDeclaration.get(i + nbPatientsRDV);
			PatientUrgent nvPatient = new PatientUrgent(i + nbPatientsRDV, mapArrivees.get(i + nbPatientsRDV),
					mapTempsOperation.get(i + nbPatientsRDV), heureDeclaration);
			patientsUrgent.add(nvPatient);
			deroulement.ajouterEvenement(heureDeclaration,
					new EvDeclarationPatientUrgent(heureDeclaration, nvPatient, null, null, null, deroulement));

		}

		/* Creation du premier Planning */
		this.planning = regles.getRegleGestionPlanning().solution(null);
		
		deroulement.setASuppr(nbPatientsRDV + nbPatientsUrgent);
		deroulement.setASuppr2(0);
	}
}
