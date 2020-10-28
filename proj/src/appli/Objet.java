// @author DEVOS Emma, CHAIX Iris
// Version Finale 

package appli;

@SuppressWarnings("rawtypes")
public class Objet implements Comparable{
	private String nom;
	private int valeur;
	private int poids;
	private float rapport;
	private int stockage;
	
	public Objet(String n, float p, float v){
		this.nom = n;
		this.valeur = (int) v;
		this.poids = (int) p;
		this.rapport = v/p;
		this.stockage = 0;
	}
	
	/*
	 * Comparaison d'un objet donn� en param�tre avec un autre objet
	 */
	@Override
	public int compareTo(Object o){
		if (this.rapport > ((Objet)o).getRapport()){
			return 1;
		}
		else
			if (this.rapport == ((Objet)o).getRapport()){
				return 0;
			}
			else{
				return -1;
			}
		
	}
	


	public int getValeur() {
		return this.valeur;
	}

	public int getPoids() {
		return this.poids*Appli.nbToMultiply; 
		// On utilise nbToMultiply pour tout multiplier et avoir le poids des items en int
	}
	
	public float getRapport(){
		return this.rapport;
	}
	
	
	

	public void setStockage(int n) {
		this.stockage = n;
	}
	
	public int getStockage() {
		return this.stockage;
	}

	
	
	
	public String toString(){
		return this.nom;
	}
	
}
