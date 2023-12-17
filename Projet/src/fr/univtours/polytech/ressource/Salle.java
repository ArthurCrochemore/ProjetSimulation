package fr.univtours.polytech.ressource;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import fr.univtours.polytech.util.Tuple;

public class Salle extends Ressource {
	/* Etats par lesquels la salle passe detaillées */
	public static enum listeEtats {
		LIBRE, LIBERATION, ATTENTELIBERATION, OPERATION, ATTENTEOPERATION, PREPARATION, ATTENTEPREPARATION
	};

	private listeEtats etat;

	/* Type qui définies la salle */ 
	public static enum typeSalles {
		RESERVE, PEUEQUIPE, SEMIEQUIPE, TRESEQUIPE
	};

	private typeSalles type;

	public listeEtats getEtat() {
		return etat;
	}

	/**
	 * Accesseur en écriture de l'attribut etat. 
	 * Gère aussi la mise à jour de l'attente 
	 * 
	 * @param etat
	 * @param heure, heure à partir de laquelle la salle change d'état
	 */
	public void setEtat(listeEtats etat, LocalTime heure) {
		/* Si la ressource devient occupée, on ferme l'intervalle de temps précedemment ouvert */
		if(this.etat == Salle.listeEtats.LIBRE && etat == Salle.listeEtats.ATTENTEPREPARATION) {
			getTempsAttente().get(taille - 1).setSecondElement(heure);
		} 
		/* Sinon, on ouvre un nouvel intervalle de temps */
		else {
			if(this.etat == Salle.listeEtats.LIBERATION && etat == Salle.listeEtats.LIBRE) {
				getTempsAttente().add(new Tuple<LocalTime, LocalTime>(heure));
				
				incrementerTaille();
			}
		}
		
		this.etat = etat;
	}

	public typeSalles getType() {
		return type;
	}

	public void setType(typeSalles type) {
		this.type = type;
	}
	
	public Salle(Integer id, Salle.typeSalles type, LocalTime heureDebut) {
		super(id, heureDebut);
		
		this.etat = Salle.listeEtats.LIBRE;
		this.type = type;
	}
}
