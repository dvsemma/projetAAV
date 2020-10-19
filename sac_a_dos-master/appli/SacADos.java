package appli;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class SacADos {
	private float poids_max;
	private Objet[] listeObjets; // les 3 algorithmes vont changer l'attribut stockage pour chaque Objet
	
	// changer les doubles en float
	public SacADos(String chemin, float poids_max) throws FileNotFoundException{
		
		// initialisation de nbObj
		int nb = 0; // nombre d'objets dans le fichier
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
				this.listeObjets[i] = new Objet(tab[0], new Float(tab[1]), new Float(tab[2])); // Double???? new Double(tab[1]), new Double(tab[2]
				++i;
			}
			scanner2.close();
		} 
		catch (FileNotFoundException e) {
			throw new FileNotFoundException("Le fichier est introuvable");
		}
	}
	
	public void résoudre(String methode) throws Exception{
		if (methode.equals("gloutonne")){
			this.gloutonne();
		}
		if (methode.equals("dynamique")){
			this.dynamique();
		}
		if (methode.equals("pse")){
			this.pse();
		}
	}
	
	//////////////////////////////////       ALGO GLOUTON        /////////////////////////////////////
	
	public void gloutonne(){
		 
		this.listeObjets = quickSort(this.listeObjets, 0, this.listeObjets.length-1); 
		
		for (int i=0; i<this.listeObjets.length; ++i){
			this.listeObjets[i].setStockage(1);
			// modification de l'attribut stockage (pour connaître les objets stockés dans le sac)
			if (poidsFinalSac(this.listeObjets)>poids_max){
				this.listeObjets[i].setStockage(0);
			}
		}
	}
	
	
	/*
	 * fonction qui prend un tableau pour effectuer un tri rapide dessus
	 * @return un tableau trié
	 */
	public Objet[] quickSort(Objet[] listeObjets, int begin, int end){
		if (begin < end){
			TableauEtPivot infos = new TableauEtPivot();
			int p = choixPivot(listeObjets, begin, end);
			infos = repartition(listeObjets, begin, end, p);
			quickSort(infos.getTab(), begin, infos.getPivot()-1); 
			quickSort(infos.getTab(), infos.getPivot()+1, end); 
		}
		return listeObjets;
	}
	
	/*
	 * choix du pivot
	 * @return indice du pivot
	 */
	public int choixPivot(Objet[] listeObjets, int begin, int end){
		return (begin + end) / 2;
	}
	
	/*
	 * fonction de répartition (met les éléments < pivot a gauchet et > pivot à droite
	 * @return un struct (tableau + pivot)
	 */
	public TableauEtPivot repartitionPivot(Objet[] listeObjets, int begin, int end, int pivot){
		TableauEtPivot infos = new TableauEtPivot();
		listeObjets = echangerValeurs(listeObjets, pivot, end);
		int j = begin;
		for (int i = begin ; i<end ; ++i){
			if (listeObjets[i].compareTo(listeObjets[end]) > 0){ // tri décroissant
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
	 * fonction pour échanger deux valeurs dans le tableau
	 */
	public Objet[] echangerValeurs(Objet[] listeObjets, int i, int j){
		Objet tmp = listeObjets[i];
		listeObjets[i] = listeObjets[j];
		listeObjets[j] = tmp;
		return listeObjets;
	}

	////////////////////////////////////       ALGO DYNAMIQUE        /////////////////////////////
	
	public void dynamique(){
		
		int[][] tab = new int[listeObjets.length][(int) ((poids_max*Appli.nbToMultiply)+1)];
		
		// remplissage premiere colonne
		for (int i=0; i<=poids_max*Appli.nbToMultiply; ++i){ // et non pas i<poidsLimite car taille=poidsLimite+1
			if (listeObjets[0].getPoids()*Appli.nbToMultiply > i){
				tab[0][i]=0;
			}
			else{
				tab[0][i]=(int) (listeObjets[0].getValeur());
			}
		}
		
		// remplissage des autres lignes du tableau
		for (int i=1; i<listeObjets.length; ++i){
			for (int j=0; j<=poids_max*Appli.nbToMultiply; ++j){ // et non pas i<poidsLimite car taille=poidsLimite+1
				if (listeObjets[i].getPoids()*Appli.nbToMultiply > j){
					tab[i][j] = tab[i-1][j];
				}
				else{
					tab[i][j] = (int) (Math.max(tab[i-1][j], tab[i-1][(int) (j-(listeObjets[i].getPoids()*Appli.nbToMultiply))]+listeObjets[i].getValeur())); 
					// pour faire toutes les combinaisons possibles
				}
			}
		}
		
		// on récupère dans la dernière ligne le poids minimal nécessaire pour faire le bénéfice optimal
		int i=listeObjets.length-1;
		int j=(int) (poids_max*Appli.nbToMultiply);
		while (tab[i][j]==tab[i][j-1]){
			--j;
		}
		
		// on récupère ensuite les objets
		while(j>0){
			while(i>0 && tab[i][(int) j]==tab[i-1][(int) j]){ 
			// si sans l'objet on fait la même valeur (même nombre sur la colonne)
				--i;
			}
			
			j=j-(int) (listeObjets[i].getPoids()*Appli.nbToMultiply); 
			// on ne prend plus en compte le poids de l'objet précédent (on retire son poids)
			if (j>=0){ 
				this.listeObjets[i].setStockage(1); 
				// modification de l'attribut stockage (pour connaître les objets stockés dans le sac)
			}
			--i;
		}
	}
	
	////////////////////////////////////////       ALGO PSE       //////////////////////////////
	
	public void pse(){
		Objet[] tabObjets = new Objet[this.listeObjets.length];

		ABR arbre = new ABR(this.listeObjets, this.poidsLimite, tabObjets, 0);
		
		arbre.Solution();
		
		Objet[] tabSolution = arbre.getTabMeilleureVal();
		
		for (int i=0; i<this.listeObjets.length; ++i){
			if (tabSolution[i] != null){
				this.listeObjets[i].setStockage(1);
				// modification de l'attribut stockage (pour connaître les objets stockés dans le sac)
			}
		}
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////
	
	/*
	 * fonction qui retourne le poids total pour une liste d'objets
	 */
	public float poidsFinalSac(Objet[] listeObjets){
		float resulat = 0.0;
		for(int i = 0; i<listeObjets.length; ++i){
			if (listeObjets[i] != null){
				resultat += listeObjets[i].getStockage() * listeObjets[i].getPoids();
			}
		}
		return resultat;
	}
	
	/*
	 * fonction qui retourne la valeur totale pour une liste d'objets
	 */
	public float valeurFinaleSac(Objet[] listeObjets){
		float resultat=0.0;
		for(int i=0; i<listeObjets.length; ++i){
			if (listeObjets[i] != null){
				resultat += listeObjets[i].getStockage() * listeObjets[i].getValeur();
			}
		}
		return resultat;
	}
	
	/*
	 * obtention de l'objet i
	 */
	public Objet getObjet(int i){
		return this.listeObjets[i];
	}
	
	/*
	 * retourne la liste des objets pour ce SacADos
	 */
	public Objet[] getListeObjets(){
		return this.listeObjets;
	}
	
	/*
	 * retourne le poids limite pour ce SacADos
	 */
	public float getPoidsMax(){
		return this.poids_max;
	}
	
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
