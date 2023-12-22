package fr.univtours.polytech;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.univtours.polytech.entite.Patient;
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

	public void miseAJourPlanning() {

	}

	public Deroulement getDeroulement() {
		return deroulement;
	}

	public void setListes(ListesAttentes listes) {
		this.listes = listes;
	}

	public ListesAttentes getListes() {
		return listes;
	}

	public List<PatientRDV> getPatientsRDV() {
		return patientsRDV;
	}

	public List<PatientUrgent> getPatientsUrgent() {
		return patientsUrgent;
	}

	public List<Infirmier> getInfirmiers() {
		return infirmiers;
	}

	public List<Chirurgien> getChirurgiens() {
		return chirurgiens;
	}

	public Map<Salle.typeSalles, List<Salle>> getSalles() {
		return salles;
	}

	public Planning getPlanning() {
		return planning;
	}

	public void setPlanning(Planning planning) {
		this.planning = planning;
	}

	public ReglesDeGestion getRegles() {
		return regles;
	}

	public Constantes getConstantes() {
		return constantes;
	}
	
	public LocalTime getHeureDebutSimulation() {
		return heureDebutSimulation;
	}

	public LocalTime getHeureFinSimulation() {
		return heureFinSimulation;
	}

	public Simulation(DonneeInitialisation data) throws Exception {
		heureDebutSimulation = data.getHeureDebutJournee();
		heureFinSimulation = data.getHeureFinJournee();
		
		listes = new ListesAttentes();

		// Creation des regles de gestion appliquees
		regles = new ReglesDeGestion(this, data.getRegle1(), data.getRegle2(), data.getRegle3());

		// Definition des heures de la journee
		LocalTime heureDebut = data.getHeureDebutJournee();
		deroulement = new Deroulement(this, heureDebut, data.getHeureFinJournee());

		// Creations des Ressources
		this.infirmiers = new ArrayList<Infirmier>();
		this.chirurgiens = new ArrayList<Chirurgien>();
		this.salles = new HashMap<Salle.typeSalles, List<Salle>>();

		Integer nbInfirmiers = data.getNbInfirmiere();
		for (int indice = 0; indice < nbInfirmiers; indice++)
			infirmiers.add(new Infirmier(indice, heureDebut));

		Integer nbChirurgiens = data.getNbChirurgien();
		for (int indice = 0; indice < nbChirurgiens; indice++)
			chirurgiens.add(new Chirurgien(indice, heureDebut));

		List<Salle> liste = new ArrayList<Salle>();
		Integer nbPeuEquipe = data.getNbSallesPeuEquipee();
		for (int indice = 0; indice < nbPeuEquipe; indice++)
			liste.add(new Salle(indice, Salle.typeSalles.PEUEQUIPE, heureDebut));
		salles.put(Salle.typeSalles.PEUEQUIPE, liste);

		liste = new ArrayList<Salle>();
		Integer nbSemiEquipe = data.getNbSallesSemiEquipee();
		for (int indice = 0; indice < nbSemiEquipe; indice++)
			liste.add(new Salle(indice, Salle.typeSalles.SEMIEQUIPE, heureDebut));
		salles.put(Salle.typeSalles.SEMIEQUIPE, liste);

		liste = new ArrayList<Salle>();
		Integer nbTresEquipe = data.getNbSallesTresEquipee();
		for (int indice = 0; indice < nbTresEquipe; indice++)
			liste.add(new Salle(indice, Salle.typeSalles.TRESEQUIPE, heureDebut));
		Integer nbReserve = data.getNbSallesReserveesUrgence();
		for (int indice = 0; indice < nbReserve; indice++)
			liste.add(new Salle(indice, Salle.typeSalles.RESERVE, heureDebut));
		salles.put(Salle.typeSalles.TRESEQUIPE, liste);

		// Definition des constantes
		this.constantes = new Constantes(data.getTempsPreparation(), data.getTempsAnesthesie(),
				data.getTempsLiberation(), nbReserve, data.getMoyTempsOperation());

		// Creation des patients et des Evenements
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
			PatientUrgent nvPatient = new PatientUrgent(i + nbPatientsRDV, mapArrivees.get(i + nbPatientsRDV), mapTempsOperation.get(i + nbPatientsRDV),
					heureDeclaration);
			patientsUrgent.add(nvPatient);
			deroulement.ajouterEvenement(heureDeclaration,
					new EvDeclarationPatientUrgent(heureDeclaration, nvPatient, null, null, null, deroulement));
			
		}
		
		//Creation du Planning
		this.planning = regles.getRegleGestionPlanning().solution(null);
		
		deroulement.setASuppr(nbPatientsRDV+nbPatientsUrgent);
		deroulement.setASuppr2(0);
	}
}
