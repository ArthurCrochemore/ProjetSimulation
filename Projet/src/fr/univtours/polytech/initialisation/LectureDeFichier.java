package fr.univtours.polytech.initialisation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

class LectureDeFichier {
	private String ligneLue;
	private FileReader fichier;
	private BufferedReader buffer;
	private StringBuffer contenu;
	
	/**
	 * Bouge le buffer sur la ligne suivante et actualise l'attribut contenu
	 */
	public void lireProchaineLigne() {
		try {
			ligneLue = buffer.readLine();
			contenu.append(ligneLue);
			contenu.append("\n");
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Ferme le fichier lu
	 */
	public void fermer() {
		try {
			fichier.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
		
	/**
	 * Accesseur en lecture du contenu de la ligne lue
	 * @return ligneLue
	 */
	public String getLigneLue() {
		return ligneLue;
	}

	/**
	 * Constructeur de confort a partir de l'adresse d'un fichier
	 * @param adresse
	 */
	public LectureDeFichier(String adresse) {
		try {
			File file = new File(adresse);
			fichier = new FileReader(file);
			buffer = new BufferedReader(fichier);
			contenu = new StringBuffer();
			ligneLue = null;
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}
