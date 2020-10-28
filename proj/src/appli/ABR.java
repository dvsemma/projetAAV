// @author DEVOS Emma, CHAIX Iris
// Version Finale 

package appli;

public class ABR {
	
	private Objet[] valeur;
	private ABR arbreGauche, arbreDroit;
	
	private int taille;
	private static int borneInf; 
	// Valeur qui est nécessairement inférieure à la valeur de la meilleure solution possible.
	private int borneSup;
	// Valeur maximum possible à partir d'un noeud
	
	private static Objet[] tabMeilleureVal; 
	// tableau de la meilleure valeur trouvée pour l'instant
	
	
	/*
	 *  Constructeur récursif
	 *  Constructeur qui permet la creation de combinaisons d'objets dans le sac à dos
	 *  Les combinaisons crées n'ont que des valeurs non-negligeables
	 */
	public ABR(Objet[] listeObj, int poids_max, Objet[] tabVal, int i){
		if (i <= listeObj.length) {
			
			// On recopie le tableau listeObj donné en parametre dans this.valeur 
			this.valeur = new Objet[listeObj.length];
			for (int j=0; j<listeObj.length; ++j){
				if (tabVal[j] != null){
					this.valeur[j] = tabVal[j];
				}
			}
			
			this.taille = i;
			this.calculBorneSup(listeObj);
			this.calculBorneInf();
			
			if (i != listeObj.length){
				this.arbreGauche = new ABR(listeObj, poids_max, tabVal, i+1);
			
				tabVal[i] = listeObj[i];
				// On ne garde que les combinaisons de valeur non-negligeable
				if (this.poidsTotal(tabVal) <= poids_max && this.borneSup > ABR.borneInf){
					this.arbreDroit = new ABR(listeObj, poids_max, tabVal, i+1);
				}
				tabVal[i] = null; 
			}
			
		}
	}
	
	/*
	 * Fonction récursive
	 * On cherche la meilleure combinaison de valeurs qui offre donc la meilleur solution 
	 */
	public void Solution(){
		if (this.valeurTotale() == ABR.borneInf){
			ABR.tabMeilleureVal = this.valeur;
		}
		else {
			if (this.arbreGauche == null && this.arbreDroit == null){
				return;
			}
			if (this.arbreGauche == null){
				this.arbreDroit.Solution();
			}
			if (this.arbreDroit == null){
				this.arbreGauche.Solution();
			}
			if (this.arbreDroit != null && this.arbreGauche != null){
				this.arbreDroit.Solution();
				this.arbreGauche.Solution();
			}
		}	
	}
	
	public int getBorneInf(){
		return ABR.borneInf;
	}
	
	public int getBorneSup(){
		return this.borneSup;
	}
	
	/*
	 * Modification de borneInf lorsqu'une meilleure valeur est trouvée
	 */
	public void calculBorneInf(){
		if (this.valeurTotale() > ABR.borneInf){
			ABR.borneInf = this.valeurTotale();
		}
	}
	
	/*
	 * Calcule la borne superieure pour chaque noeud   
	 */
	
	public void calculBorneSup(Objet[] listeObj){
		int resultat = 0;
		resultat += this.valeurTotale(); // valeur totale du noeud courant
		for (int i = this.taille; i<listeObj.length; ++i){
			resultat += listeObj[i].getValeur(); // ajout des valeurs des objets restants
		}
		this.borneSup = resultat;
	}
	
	/*
	 * Retourne la valeur totale du tableau d'objets this.value
	 */
	public int valeurTotale(){
		int resultat = 0;
		for(int i = 0; i<this.valeur.length; ++i){
			if (this.valeur[i] != null){
				resultat += this.valeur[i].getValeur();
			}
		}
		return resultat;
	}
	
	/*
	 * Retourne le poids total du tableau d'objets this.value
	 */
	public int poidsTotal(){
		int resultat = 0;
		for(int i = 0; i<this.valeur.length; ++i){
			if (this.valeur[i] != null){
				resultat += this.valeur[i].getValeur();
			}
		}
		return resultat;
	}
	
	/*
	 * Retourne la valeur totale d'un tableau d'objets donné en paramètre
	 */ 
	public int valeurTotale(Objet[] listeObj){
		int resultat = 0;
		for(int i = 0; i < listeObj.length; ++i){
			if (listeObj[i] != null){
				resultat += listeObj[i].getValeur();
			}
		}
		return resultat;
	}
	
	/*
	 * Retourne le poids total d'un tableau d'objets donné en paramètre
	 */
	public int poidsTotal(Objet[] listeObj){
		int resultat = 0;
		for(int i = 0; i < listeObj.length; ++i){
			if (listeObj[i] != null){
				resultat += listeObj[i].getPoids();
			}
		}
		return resultat;
	}

	public Objet[] getTabMeilleureVal(){
		return ABR.tabMeilleureVal;
	}
	
	public int getTaille(){
		return this.taille;
	}
	
}


