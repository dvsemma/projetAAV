package appli;

@SuppressWarnings("rawtypes")
public class Objet implements Comparable{
	private String nom;
	private float valeur;
	private float poids;
	private float rapport;
	private int stockage;
	
	public Objet(String n, float p, float v){
		this.nom = n;
		this.valeur = v;
		this.poids = p;
		this.rapport = v/p;
		this.stockage = 0;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * comparaison de cet objet avec un autre objet (par rapport au rapport)
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
	


	public float getValeur() {
		return this.valeur;
	}

	public float getPoids() {
		return this.poids;
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
