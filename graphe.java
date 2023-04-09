package FW;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class graphe {

		private HashMap<Integer, HashMap<Integer, Integer>> graphe;
	   
		public ArrayList<ArrayList<Integer>> matriceAdj;

		// Constructeur du graphe
		// paramètre g, la hashmap qui contient le graphe
	    public graphe(HashMap<Integer, HashMap<Integer, Integer>> g) {
	        this.graphe= g;
	        
	         matriceAdj = new ArrayList<>();
	        for(int i  = 0; i < graphe.size(); i++) {
	            matriceAdj.add(new ArrayList<>());
	            for(int j = 0; j < graphe.size(); j++)
	                matriceAdj.get(i).add(0);
	        }
	        for (Map.Entry<Integer, HashMap<Integer, Integer>> e1 : graphe.entrySet()){ //key = noeud dep
	            for(Map.Entry<Integer, Integer> e2 : e1.getValue().entrySet()) { //key = noeud arr
	                matriceAdj.get(e1.getKey()).set(e2.getKey(), 1);
	            }
	        }
	    }


	    
	    // Fonction qui convertit la HashMap en matrice de distance
	    // retourne la matrice de distance
	    public ArrayList <ArrayList<Cellule>> enMatrice(){
	        ArrayList<ArrayList<Cellule>> l = new ArrayList<>();
	        for(int i  = 0; i < graphe.size(); i++) {
	            l.add(new ArrayList<>());
	            for(int j = 0; j < graphe.size(); j++)
	                l.get(i).add(i == j ? new Cellule(false, 0) :null);
	        }
	        for (Map.Entry<Integer, HashMap<Integer, Integer>> e1 : graphe.entrySet()){ 
	            for(Map.Entry<Integer, Integer> e2 : e1.getValue().entrySet()) { 
	                l.get(e1.getKey()).set(e2.getKey(), new Cellule(false, e2.getValue()));
	            }
	        }
	        for(int i = 0; i < graphe.size(); i++){
	            for(int j = 0; j < graphe.size(); j++){
	                if(l.get(i).get(j) == null)
	                    l.get(i).set(j, new Cellule(true, 0));
	            }
	        }
	        return l;
	    }

	    public static class constrGraphe
	    {
	        HashMap<Integer, HashMap<Integer, Integer>> graphe;


	        public constrGraphe(){
	            this.graphe= new HashMap<>();
	        }


	        constrGraphe from_file(String path) throws IOException {
	            BufferedReader reader = new BufferedReader(new FileReader(path));
	            // Lit la taille du graphe 
	            String ln = reader.readLine();
	            if (ln == null)
	                throw new IllegalArgumentException("Fichier non reglementaire : la premiere ligne ne doit pas etre vide l:1");
	            if(!Operateur.isInteger(ln)){
	                throw new IllegalArgumentException("Fichier non reglementaire : Le nombre de noeuds n'est pas un chiffre de type entier l:1");
	            }
	            int taille = Integer.parseInt(ln);
	            if(taille < 0)
	                throw new IllegalArgumentException("Fichier non reglementaire : Le nombre de noeuds n'est pas un chiffre positif l:1");

	            for(int i = 0; i < taille; i++)
	                graphe.put(i, new HashMap<>());

	            // Lit le nombre d'extrémités 
	            ln = reader.readLine();
	            if (ln == null)
	                throw new IllegalArgumentException("Fichier non reglementaire : la seconde ligne ne doit pas etre vide l:2");
	            if(!Operateur.isInteger(ln))
	                throw new IllegalArgumentException("Fichier non reglementaire : Le nombre d'arcs n'est pas un chiffre de type entier l:2");
	            int arcs = Integer.parseInt(ln);
	            if (arcs < 0)
	                throw new IllegalArgumentException("Fichier non reglementaire : Le nombre d'arcs n'est pas un chiffre positif l:2");
	            // Lit les extrémités 
	            for (int i = 0; i < arcs; i++){
	                ln = reader.readLine();
	                if(ln == null)
	                    throw new IllegalArgumentException("Fichier non reglementaire : Trop peu d'arcs decrits l:" + (i+3));
	                String[] data = ln.split(" ");
	                if (data.length != 3)
	                    throw new IllegalArgumentException("Fichier non reglementaire : Pas le bon nombre d'arguments pour decrire un arc. Attendu 3, dans le fichier " + data.length + " l:"+ (i+3));
	                for(int j = 0; j < 2; j++)
	                {
	                    if(!Operateur.isInteger(data[j]))
	                        throw new IllegalArgumentException("Fichier non reglementaire : Le nom du noeud nÂ°"+(j + 1)+" n'est pas un chiffre de type entier l:" + (i+3));
	                    int temp = Integer.parseInt(data[j]);
	                    if (temp < 0)
	                        throw new IllegalArgumentException("Fichier non reglementaire : Le nom du noeud nÂ°"+(j + 1)+" n'est pas un chiffre positif l:" + (i+3));
	                    if (temp >= taille)
	                        throw new IllegalArgumentException("Fichier non reglementaire : Le nom du noeud nÂ°"+(j + 1)+" n'existe pas l:"+(i+3));
	                }
	                if(!Operateur.isInteger(data[2]))
	                    throw new IllegalArgumentException("Fichier non reglementaire : Le poids de l'arc n'est pas un chiffre de type entier l:" + (i+3));
	                if(graphe.get(Integer.parseInt(data[0])).containsKey(Integer.parseInt(data[1])))
	                    throw new IllegalArgumentException("Fichier non reglementaire : L'arc decrit dans cette ligne est un doublon l:"+ (i+3));
	                graphe.get(Integer.parseInt(data[0])).put(Integer.parseInt(data[1]),Integer.parseInt(data[2]));
	            }
	            return this;
	        }
	        
	        graphe constr()
	        {
	            return new graphe(this.graphe);
	        }

	    }

	


}
