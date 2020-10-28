package appli;

public class ABR {
	
	private Objet[] valeur;
	private ABR arbreGauche, arbreDroit;
	
	private int taille;
	private static int borneInf; 
	// meilleure valeur trouvée pour l'instant (utile pour la construction ET pour solution()
	private int borneSup;
	// la valeur max que pourra avoir la combinaison finale à partir d'un noeud
	
	private static Objet[] tabMeilleureVal; 
	// tableau correspondant à la meilleure valeur trouvée dans l'arbre (par borne inférieure lors de la construction)
	
	
	/*
	 *  Constructeur récursif
	 *  Construction des combinaisons possibles ET qui ont un intérêt
	 */
	public ABR(Objet[] listeObj, int poids_max, Objet[] tabVal, int i){
		if (i <= listeObj.length) {
			
			// recopiage dans this.value le tableau tabObj
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
				if (this.poidsTotal(tabVal) <= poids_max && this.borneSup > ABR.borneInf){
					// vérification pour raccourcir l'arbre (les combinaisons sans intérêts ne sont pas créées)
					this.arbreDroit = new ABR(listeObj, poids_max, tabVal, i+1);
				}
				tabVal[i] = null; // pour supprimer le dernier objet dans tabObj MAIS AUSSI dans this.value (car référence)
			}
			
		}
	}
	
	/*
	 * fonction récursive pour trouver la combinaison (en initialisant l'attribut statique tabMeilleureValeur)_ 
	 * à partir de la meilleure valeur trouvée dans tout le tableau (qui est obtenue avec borneInferieure en construisant l'arbre)
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
	 * mis à jour de l'attribut statique borneInferieure lorsqu'une meilleure valeur (correspondant à une combinaison) est trouvée
	 * mis à jour lors de la construction de l'arbre
	 */
	public void calculBorneInf(){
		if (this.valeurTotale() > ABR.borneInf){
			ABR.borneInf = this.valeurTotale();
		}
	}
	
	/*
	 * calcul pour chaque noeud (ABR) la valeur max que pourra avoir la combinaison finale à partir d'un noeud
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
	 * retourne la valeur totale de this.value (tableau d'objets)
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
	 * retourne le poids total de this.value (tableau d'objets)
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
	 * retourne la valeur totale d'un tableau d'objets
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
	 * retourne le poids total d'un tableau d'objets
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


