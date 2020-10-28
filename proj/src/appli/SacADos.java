// @author DEVOS Emma, CHAIX Iris
// Version Finale 

package appli;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class SacADos {
	private int poids_max;
	private Objet[] listeObjets; 
	
	@SuppressWarnings({"deprecation"})
	public SacADos(String chemin, int poids_max) throws FileNotFoundException{
		
		// Initialisation du nombre nb d'ojets dans le fichier à 0
		int nb = 0; 
		try {
			Scanner scanner1 = new Scanner(new File(chemin));
			while (scanner1.hasNextLine()){
				++nb;
				scanner1.nextLine();
			}
			scanner1.close();
		}
		catch (FileNotFoundException e) {
			throw new FileNotFoundException("Le fichier est introuvable");
		}
				
		this.listeObjets = new Objet[nb];
		this.poids_max = poids_max;
		String s;
		String[] tab = new String[3];
		int i = 0;
		try {
			Scanner scanner2 = new Scanner(new File(chemin));
			while (scanner2.hasNextLine()){
				s = scanner2.nextLine(); 
				tab = s.split("\\s+" + ";" + "\\s+");
				this.listeObjets[i] = new Objet(tab[0], new Float(tab[1]), new Float(tab[2])); 
				++i;
			}
			scanner2.close();
		} 
		catch (FileNotFoundException e) {
			throw new FileNotFoundException("Le fichier est introuvable");
		}
	}
	
	public boolean résoudre(String methode) throws Exception{
		if (methode.equals("gloutonne")){
			this.gloutonne();
			return true;
		}
		if (methode.equals("dynamique")){
			this.dynamique();
			return true;
		}
		if (methode.equals("pse")){
			this.pse();
			return true;
		}
		else {
			System.out.println("Erreur de saisie");
			return false;
		}
		
	}
	
	
	
	/**********************       METHODE GLOUTONNE       **********************/
	
	
	public void gloutonne(){
		 
		this.listeObjets = quickSort(this.listeObjets, 0, this.listeObjets.length-1); 
		
		for (int i=0; i<this.listeObjets.length; ++i){
			this.listeObjets[i].setStockage(1);
			// Modification de l'attribut stockage pour savoir si un objet se trouve ou non dans le sac
			if (poidsFinalSac(this.listeObjets)>poids_max){
				this.listeObjets[i].setStockage(0);
			}
		}
	}
	
	
	/*
	 * Fonction pour effectuer un tri rapide sur un tableau donné en paramètre
	 */
	public Objet[] quickSort(Objet[] listeObjets, int begin, int end){
		if (begin < end){
			TableauEtPivot infos = new TableauEtPivot();
			int p = choixPivot(listeObjets, begin, end);
			infos = repartitionPivot(listeObjets, begin, end, p);
			quickSort(infos.getTab(), begin, infos.getPivot()-1); 
			quickSort(infos.getTab(), infos.getPivot()+1, end); 
		}
		return listeObjets; //retourne le tableau trié
	}
	
	/*
	 * On choisit le pivot
	 * Retourne l'indice du pivot
	 */
	public int choixPivot(Objet[] listeObjets, int begin, int end){
		return (begin + end) / 2;
	}
	
	/*
	 * Fonction de répartition autour du pivot
	 * On retourne infos, qui contient un tableau trié et son pivot 
	 */
	public TableauEtPivot repartitionPivot(Objet[] listeObjets, int begin, int end, int pivot){
		TableauEtPivot infos = new TableauEtPivot();
		listeObjets = echangerValeurs(listeObjets, pivot, end);
		int j = begin;
		for (int i = begin ; i<end ; ++i){
			if (listeObjets[i].compareTo(listeObjets[end]) > 0){
				echangerValeurs(listeObjets,i,j);
				j++;
			}
		}
		listeObjets = echangerValeurs(listeObjets, end, j);
		infos.setTab(listeObjets);
		infos.setPivot(j);
		return infos;
	}
	
	/*
	 * Fonction d'échange dans un tableau de deux valeurs dont les indices est donné en parametres
	 */
	public Objet[] echangerValeurs(Objet[] listeObjets, int i, int j){
		Objet tmp = listeObjets[i];
		listeObjets[i] = listeObjets[j];
		listeObjets[j] = tmp;
		return listeObjets;
	}

	
	
	/**********************       METHODE DYNAMIQUE       **********************/
	
	
	public void dynamique(){
		
		int[][] tab = new int[listeObjets.length][(int) ((poids_max*Appli.nbToMultiply)+1)];
		
		// On remplit d'abord la premiere colonne du tableau
		for (int i=0; i<=poids_max*Appli.nbToMultiply; ++i){ 
			if (listeObjets[0].getPoids()*Appli.nbToMultiply > i){
				tab[0][i]=0;
			}
			else{
				tab[0][i]=(int) (listeObjets[0].getValeur());
			}
		}
		
		// On remplit le reste du tableau
		for (int i=1; i<listeObjets.length; ++i){
			for (int j=0; j<=poids_max*Appli.nbToMultiply; ++j){
				if (listeObjets[i].getPoids()*Appli.nbToMultiply > j){
					tab[i][j] = tab[i-1][j];
				}
				else{
					tab[i][j] = (int) (Math.max(tab[i-1][j], tab[i-1][(int) (j-(listeObjets[i].getPoids()*Appli.nbToMultiply))]+listeObjets[i].getValeur())); 
				}
			}
		}
		
		// On récupère dans la dernière ligne le poids minimal pour un avoir un max d'objet dans son sac
		int i=listeObjets.length-1;
		int j=(int) (poids_max*Appli.nbToMultiply);
		while (tab[i][j]==tab[i][j-1]){
			--j;
		}
		
		// On récupère les objets
		while(j>0){
			while(i>0 && tab[i][(int) j]==tab[i-1][(int) j]){ 
			// Si on a la même valeur
				--i;
			}
			
			j=j-(int) (listeObjets[i].getPoids()*Appli.nbToMultiply); 
			// On retire le poids de l'objet précédent
			if (j>=0){ 
				this.listeObjets[i].setStockage(1); 
				// Modification de stockage 
			}
			--i;
		}
	}
	
	/**********************       METHODE PSE       **********************/	
	
	public void pse(){
		Objet[] tabObjets = new Objet[this.listeObjets.length];

		ABR arbre = new ABR(this.listeObjets, this.poids_max, tabObjets, 0);
		
		arbre.Solution();
		
		Objet[] tabSolution = arbre.getTabMeilleureVal();
		
		for (int i=0; i<this.listeObjets.length; ++i){
			if (tabSolution[i] != null){
				this.listeObjets[i].setStockage(1);
				// Modification de stockage 
			}
		}
	}
	
	
	/**********************       FONCTIONS UTILES       **********************/
	
	/*
	 * Retourne le poids total pour une liste d'objets donnée en paramètre
	 */
	public int poidsFinalSac(Objet[] listeObjets){
		int resultat = 0;
		for(int i = 0; i<listeObjets.length; ++i){
			if (listeObjets[i] != null){
				resultat += listeObjets[i].getStockage() * listeObjets[i].getPoids();
			}
		}
		return resultat;
	}
	
	/*
	 * Retourne la valeur totale pour une liste d'objets donnée en paramètre
	 */
	public int valeurFinaleSac(Objet[] listeObjets){
		int resultat=0;
		for(int i=0; i<listeObjets.length; ++i){
			if (listeObjets[i] != null){
				resultat += listeObjets[i].getStockage() * listeObjets[i].getValeur();
			}
		}
		return resultat;
	}
	
	/*
	 * Retourne l'objet d'indice i
	 */
	public Objet getObjet(int i){
		return this.listeObjets[i];
	}
	
	/*
	 * Retourne la liste des objets pour ce sac à dos
	 */
	public Objet[] getListeObjets(){
		return this.listeObjets;
	}
	
	/*
	 * Retourne le poids max pour ce sac à dos
	 */
	public int getPoidsMax(){
		return this.poids_max;
	}
	
	/*
	 * Affiche le poids total et la valeur totale du sac après sa résolution
	 */
	public String toString(){
		String s = "";
		s += "Le poids total du sac est de : " + this.poidsFinalSac(this.listeObjets) + System.lineSeparator();
		s += "La valeur totale du sac est de : " + this.valeurFinaleSac(this.listeObjets) + System.lineSeparator();
		for (Objet o : this.listeObjets){
			if (o.getStockage() == 1){
				s+= o.toString() + System.lineSeparator();
			}
		}
		return s;
	}
}
