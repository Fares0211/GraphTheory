package FW;

import java.util.ArrayList;

// classe qui contient diverses opérations relatives à l'exécution de FloydWarshall
public class Operateur {

	    public static boolean isInteger(String s){
	        try
	        {
	            int i = Integer.parseInt(s);
	            return true;
	        }
	        catch (NumberFormatException e)
	        {
	            return false;
	        }
	    }

	    // fonction qui affiche la matrice d'adjacence du graphe
	    public static void afficherMatriceAdj(ArrayList<ArrayList<Integer>> m){
	        int max = 0;
	        for (ArrayList<Integer> tab : m){
	            for (Integer i : tab){
	                max = Math.max(max, i.toString().length());
	            }
	        }
	        max = Math.max(max, Integer.toString(m.size()).length());
	        
	        //affiche le nom des colonnes
	        for (int j = 0; j < max; j++)
	            System.out.print(' ');
	        System.out.print("  ");
	        for (int i = 0; i < m.size();i++){
	            if (Integer.toString(i).length() < max)
	            {
	                for (int j = 0; j < max - Integer.toString(i).length(); j++)
	                    System.out.print(' ');
	            }
	            System.out.print(i);
	            System.out.print(' ');
	        }
	        System.out.println("");
	        for (int j = 0; j < max; j++)
	            System.out.print(' ');
	        System.out.print("  ");
	        for (int i = 0; i < m.size();i++){
	                for (int j = 0; j <= max; j++)
	                    System.out.print('_');
	        }
	        System.out.println();
	        
	        int it = 0;
	        for (ArrayList<Integer> tab : m){
	            //affiche le nom de la ligne
	            System.out.print(it);
	            if (Integer.toString(it).length() < max)
	            {
	                for (int j = 0; j < max - Integer.toString(it).length(); j++)
	                    System.out.print(' ');
	            }
	            System.out.print(' ');
	            System.out.print('|');

	            it++;
	            for (Integer i : tab){
	                if (i.toString().length() < max)
	                {
	                    for (int j = 0; j < max - i.toString().length(); j++)
	                        System.out.print(' ');
	                }
	                System.out.print(i);
	                System.out.print(' ');
	            }
	            System.out.println();
	        }
	    }

	    // fonction qui affiche la matrice les valeurs du poids des arcs du graphe
	    public static void afficherMatriceVal(ArrayList<ArrayList<Cellule>> m) {
	        int max = 0;
	        for (ArrayList<Cellule> tab : m) {
	            for (Cellule i : tab) {
	                max = Math.max(max, i.toString().length());
	            }
	        }
	        max = Math.max(max, Integer.toString(m.size()).length());
	        //affiche le nom des colonnes
	        for (int j = 0; j < max; j++)
	            System.out.print(' ');
	        System.out.print("  ");
	        for (int i = 0; i < m.size(); i++) {
	            if (Integer.toString(i).length() < max) {
	                for (int j = 0; j < max - Integer.toString(i).length(); j++)
	                    System.out.print(' ');
	            }
	            System.out.print(i);
	            System.out.print(' ');
	        }
	        System.out.println("");
	        for (int j = 0; j < max; j++)
	            System.out.print(' ');
	        System.out.print("  ");
	        for (int i = 0; i < m.size(); i++) {
	            for (int j = 0; j <= max; j++)
	                System.out.print('_');
	        }
	        System.out.println();
	        int it = 0;
	        for (ArrayList<Cellule> tab : m) {
	            //affiche le nom de la ligne
	            System.out.print(it);
	            if (Integer.toString(it).length() < max) {
	                for (int j = 0; j < max - Integer.toString(it).length(); j++)
	                    System.out.print(' ');
	            }
	            System.out.print(' ');
	            System.out.print('|');

	            it++;
	            for (Cellule i : tab) {
	                if (i.toString().length() < max) {
	                    for (int j = 0; j < max - i.toString().length(); j++)
	                        System.out.print(' ');
	                }
	                System.out.print(i);
	                System.out.print(' ');
	            }
	            System.out.println("");
	        }
	    }
	    
	  
	    // fonction qui calcule le chemin le plus court entre deux sommets
	    public static void cheminPlusCourt(ArrayList<ArrayList<Integer>> ch,ArrayList<ArrayList<Cellule>> n,  int dep, int arr){
	        if (n.get(dep).get(arr).estInfini())
	            System.out.println("ce n'est pas possible d'aller de "+ dep + " a " + arr);
	        else {
	            System.out.print("Le chemin le plus cours pour aller de "+ dep + " a " + arr+ " est : ");
	                ArrayList<Integer> list = new ArrayList<>();
	                list.add(dep);
	                list.add(arr);
	                int i = 0;
	                while (i + 1 < list.size())
	                {
	                    if (ch.get(list.get(i)).get(list.get(i+1)) == -1)
	                        i++;
	                    else
	                    {
	                        list.add(i+1,ch.get(list.get(i)).get(list.get(i+1)));
	                    }
	                }
	                for (Integer d : list){
	                    System.out.print(d + " ");
	                }
	                System.out.println("");
	            }
	        }
	

}
