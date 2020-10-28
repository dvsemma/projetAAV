// @author DEVOS Emma, CHAIX Iris
// Version Finale 

package appli;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Appli {
	public static final int nbToMultiply = 10; 
	
	public static void main(String[] args) throws Exception{
		int poids_max = 150; // On est pass� de 15 � 150 pour faciliter les calculs en passant le poids des items en int (on a tout multipli� par 10)
		SacADos sac;
		try {
			sac = new SacADos("C:\\Users\\emmae\\JAVA\\proj\\items.txt" ,poids_max);
		} 
		
		catch (FileNotFoundException e) {
			throw new FileNotFoundException("Le fichier est introuvable");
		}
		
		System.out.println("Pour utiliser la m�thode approch�e gloutonne, tapez : gloutonne");
		System.out.println("Pour utiliser la m�thode par programmation dynamique, tapez : dynamique");
		System.out.println("Pour utiliser la m�thode par PSE, tapez : pse");
		Scanner sc = new Scanner(System.in);
		String methode = sc.next(); //m�thode que l'on choisit pour r�soudre le probl�me du sac � dos
		if (sac.r�soudre(methode))
			System.out.println(sac.toString());
		sc.close();
	}
}

