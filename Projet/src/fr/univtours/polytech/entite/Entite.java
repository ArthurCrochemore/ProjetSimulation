package fr.univtours.polytech.entite;

public abstract class Entite {
	private Integer id;
	
	public Integer getId() {
		return id;
	}
	
	public Entite(Integer id) {
		this.id = id;
	}
}
