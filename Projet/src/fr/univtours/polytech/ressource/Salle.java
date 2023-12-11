package fr.univtours.polytech.ressource;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import fr.univtours.polytech.util.Tuple;

public class Salle extends Ressource {
	
	public static enum listeEtats {
		LIBRE, ATTENTEPREPARATION, PREPARATION, ATTENTEOPERATION, OPERATION, ATTENTELIBERATION, LIBERATION
	};

	private listeEtats etat;

	public static enum typeSalles {
		PEUEQUIPE, SEMIEQUIPE, TRESEQUIPE, RESERVE
	};

	private typeSalles type;

	public listeEtats getEtat() {
		return etat;
	}

	public void setEtat(listeEtats etat, LocalTime heure) {
		this.etat = etat;
		
		if(this.etat == Salle.listeEtats.LIBRE && etat == Salle.listeEtats.ATTENTEPREPARATION) {
			getTempsAttente().get(taille - 1).setSecondElement(heure);
			this.etat = etat;
		} else {
			if(this.etat == Salle.listeEtats.LIBERATION && etat == Salle.listeEtats.LIBRE) {
				getTempsAttente().add(new Tuple<LocalTime, LocalTime>(heure));
				this.etat = etat;
				taille ++;
			}
		}
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
