package appli;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Appli {
	public static final int nbToMultiply = 10; //tf is this help
	
	public static void main(String[] args) throws Exception{
		float poids_max = 150; // on est pass� de 15 a 150 pour faciliter les calculs en passant le poids des items en int (on a tout multipli� par 10)
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
		String methode = sc.next(); //m�thode que l'on choisit
		//sac.r�soudre(methode);
		if (sac.r�soudre(methode))
			System.out.println(sac.toString());
		sc.close();
	}
}

