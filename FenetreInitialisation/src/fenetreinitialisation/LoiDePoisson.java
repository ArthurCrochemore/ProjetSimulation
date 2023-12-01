package fr.univtours.polytech.util;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;

public class LoiDePoisson {
	
	/**
	 * Genere un entier entre borneMin et borneMax
	 * @param borneMin 
	 * @param borneMax
	 * @param lambda parametre pour la loi de poisson
	 * @return L'entier genere grace a la loi de poisson
	 */
	public static int genererEntier(int borneMin, int borneMax, double lambda) {
        Random random = new Random();
        
        // Générer une valeur aléatoire selon la loi de Poisson
        double resultatPoissonatPoisson = poisson(lambda);

        // Ajuster la valeur pour qu'elle soit dans la plage spécifiée
        int resultatPoisson = (int) (borneMin + (resultatPoissonatPoisson % (borneMax - borneMin + 1)));
        
        return resultatPoisson;
    }

    /**
     * Genere un flottant entre borneMin et borneMax
	 * @param borneMin 
	 * @param borneMax
	 * @param lambda parametre pour la loi de poisson
	 * @return Le flottant genere grace a la loi de poisson
     */
    public static double genererFlottant(double borneMin, double borneMax, double lambda) {
        Random random = new Random();
        double resultatPoissonatPoisson = poisson(lambda);
        double resultatPoisson = borneMin + (resultatPoissonatPoisson * (borneMax - borneMin));
        return resultatPoisson;
    }
    
    /**
     * Genere un horraire de type LocalTime entre borneMin et borneMax
	 * @param borneMin heure au plus tot qui peut etre generer
	 * @param borneMax heure au plus tard qui peut etre generer
	 * @param lambda parametre pour la loi de poisson
	 * @return L'horraire genere grace a la loi de poisson
     */
    public static LocalTime genererHeure(LocalTime borneMin, LocalTime borneMax, double lambda) {
    	int borneMinHour = borneMin.getHour();
    	int borneMaxHour = borneMax.getHour();
        
    	int heures = genererEntier(borneMinHour, borneMaxHour, lambda);
        int minutes;
        
        if (heures == borneMinHour) {
        	if (heures == borneMaxHour) {
        		minutes = genererEntier(borneMin.getMinute(), borneMax.getMinute(), lambda);
        	} else {

        		minutes = genererEntier(borneMin.getMinute(), 59, lambda);
        	}
        } else {
        	if (heures == borneMaxHour) {
        		minutes = genererEntier(0, borneMax.getMinute(), lambda);
        	} else {	
        		minutes = genererEntier(0, 59, lambda);
        	}
        }
        
        return LocalTime.of(heures, minutes);
    }
    
    /**
     * Genere un delai de type LocalTime entre borneMin et borneMax
	 * @param borneMin nb minutes minimale
	 * @param borneMax nb minutes maximale
	 * @param lambda parametre pour la loi de poisson
	 * @return Le delai genere grace a la loi de poisson
     */
    public static LocalTime genererDelai(int borneMin, int borneMax, double lambda) {
    	int minutes = genererEntier(borneMin, borneMax, lambda);
    	int heures = minutes % 60;
    	
    	minutes = minutes - heures*60;
        
        return LocalTime.of(heures, minutes);
    }
    
    /**
     * Fonction loi de poisson
     * @param lambda
     * @return resultat genere avec la loi de poisson
     */
    private static double poisson(double lambda) {
        // Utiliser la formule de la distribution de Poisson pour générer une valeur
        double L = Math.exp(-lambda);
        double p = 1.0;
        int k = 0;

        Random random = new Random();

        do {
            k++;
            p *= random.nextDouble();
        } while (p > L);

        return k - 1;
    }
}