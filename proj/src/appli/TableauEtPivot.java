// @author DEVOS Emma, CHAIX Iris
// Version Finale 

package appli;

/*
 * Est utiliser pour la methode quiclsort dans sac � dos
 * Permet de stocker sa valeur de retour (fonction de r�partition)
 */

public class TableauEtPivot {
	
	private Objet[] tab;
	private int pivot;
	
	public void setTab(Objet[] tab){
		this.tab = tab;
	}
	
	public Objet[] getTab(){
		return this.tab;
	}
	
	
	public void setPivot(int p){
		this.pivot = p;
	}

	public int getPivot(){
		return this.pivot;
	}
	
	
}
