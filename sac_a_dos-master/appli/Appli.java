package appli;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Appli {
	public static final int nbAMultiplier = 10;
	
	public static void main(String[] args) throws Exception{
		double poidsLimite = 40;
		SacADos sac;
		try {
			sac = new SacADos("C:\\Users\\Emma\\Desktop\\AAV Sem3\\sac_a_dos-master\\appli\\itemsEval.txt" ,poidsLimite);
		} 
		catch (FileNotFoundException e) {
			throw new FileNotFoundException("Fichier introuvable");
		}
		
		System.out.println("Méthode glouton tapez : glouton");
		System.out.println("Méthode dynamique tapez : dynamique");
		System.out.println("Méthode PSE tapez : pse");
		Scanner sc = new Scanner(System.in);
		String cmd = sc.next();
		sac.résoudre(cmd);		
		System.out.println(sac.toString());
		sc.close();
	}
}

