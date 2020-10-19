package appli;

//structure pour stocker la valeur de retour de la fonction de répartition du quicksort
public class TabEtPivot {
	
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
