package appli;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Appli {
	public static final int nbToMultiply = 10; //tf is this help
	
	public static void main(String[] args) throws Exception{
		float poids_max = 150; // on est passé de 15 a 150 pour faciliter les calculs en passant le poids des items en int (on a tout multiplié par 10)
		SacADos sac;
		try {
			sac = new SacADos("C:\\Users\\emmae\\JAVA\\proj\\items.txt" ,poids_max);
		} 
		
		catch (FileNotFoundException e) {
			throw new FileNotFoundException("Le fichier est introuvable");
		}
		
		System.out.println("Pour utiliser la méthode approchée gloutonne, tapez : gloutonne");
		System.out.println("Pour utiliser la méthode par programmation dynamique, tapez : dynamique");
		System.out.println("Pour utiliser la méthode par PSE, tapez : pse");
		Scanner sc = new Scanner(System.in);
		String methode = sc.next(); //méthode que l'on choisit
		//sac.résoudre(methode);
		if (sac.résoudre(methode))
			System.out.println(sac.toString());
		sc.close();
	}
}

