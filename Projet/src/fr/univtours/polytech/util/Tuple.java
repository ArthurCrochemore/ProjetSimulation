package fr.univtours.polytech.util;

public class Tuple<A, B> {
	private A premierElement;
	private B secondElement;

	/**
	 * Accesseur en lecture du premier element
	 * 
	 * @return premierElement
	 */
	public A getPremierElement() {
		return premierElement;
	}

	/**
	 * Accesseur en ecriture du premier element
	 * 
	 * @param premierElement
	 */
	public void setPremierElement(A premierElement) {
		this.premierElement = premierElement;
	}

	/**
	 * Accesseur en lecture du second element
	 * 
	 * @return premierElement
	 */
	public B getSecondElement() {
		return secondElement;
	}

	/**
	 * Accesseur en ecriture du second element
	 * 
	 * @param premierElement
	 */
	public void setSecondElement(B secondElement) {
		this.secondElement = secondElement;
	}

	/**
	 * Constructeur de confort à partir de deux elements
	 * 
	 * @param premierElement
	 * @param secondElement
	 */
	public Tuple(A premierElement, B secondElement) {
		this.premierElement = premierElement;
		this.secondElement = secondElement;
	}

	/**
	 * Constructeur de confort à partir d'un elements
	 * 
	 * @param premierElement
	 */
	public Tuple(A premierElement) {
		this.premierElement = premierElement;
		this.secondElement = null;
	}

	/**
	 * Constructeur par default
	 */
	public Tuple() {
		this.premierElement = null;
		this.secondElement = null;
	}

	public String toString() {
		return "{" + premierElement.toString() + ", " + secondElement.toString() + "}";
	}
}
