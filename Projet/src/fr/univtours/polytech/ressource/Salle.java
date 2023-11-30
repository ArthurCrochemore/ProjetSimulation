package fr.univtours.polytech.ressource;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import fr.univtours.polytech.util.Tuple;

public class Salle extends Ressource {
	
	public static enum listeEtats {
		LIBRE, OCCUPE, ATTENTEPREPARATION, ATTENTELIBERATION
	};

	private listeEtats etat;

	public static enum typeSalles {
		PEUEQUIPE, SEMIEQUIPE, TRESEQUIPE, RESERVE
	};

	private typeSalles type;

	public listeEtats getEtat() {
		return etat;
	}

	public void setEtat(listeEtats etat) {
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
