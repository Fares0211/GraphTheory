package FW;

// classe qui définit la nature d'une cellule de la matrice des valeurs 
// (une valeure finie => les sommets sont directement liés ; infini sinon)
public class Cellule {

	    private long valeur;
	    private boolean infini;


	    public Cellule(boolean inf, long valeur){
	        this.valeur = valeur;
	        this.infini = inf;
	    }

	    public boolean estInfini()
	    {
	        return this.infini;
	    }

	    public long valeur()
	    {
	        return this.valeur;
	    }


	    @Override
	    public String toString()
	    {return this.infini ? "inf" : Long.toString(this.valeur);}
	    
}

	    
	
