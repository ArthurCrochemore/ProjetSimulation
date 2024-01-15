package fr.univtours.polytech.entite;

public abstract class Entite {
	private Integer id;

	/**
	 * Accesseur en lecture de l'id
	 * 
	 * @return id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Constructeur de confort utilisant l'id
	 * 
	 * @param id
	 */
	public Entite(Integer id) {
		this.id = id;
	}
}
