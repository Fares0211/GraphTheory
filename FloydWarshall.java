package FW;

import static java.lang.Double.NEGATIVE_INFINITY;
import static java.lang.Double.POSITIVE_INFINITY;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;

public class FloydWarshall {

	private int n; // nombre de sommets
	private boolean calcule; //booléen qui détermine si l'algorithme a pu calculer les plus courts chemins (ce qui implique l'absence de circuit absorbant)
	private double[][] copie; //la copie de la matrice d'adjacence initiale 
	private Integer[][] suivant; //matrice des chemins

	private static final int REACHES_NEGATIVE_CYCLE = -1; //constante de classe qui représente le circuit absorbant commé étant à -1

	// En entrée, cette classe prend une matrice d'adjacence avec les poids entre les sommets, où
	// POSITIVE_INFINITY est utilisé pour indiquer que deux sommets ne sont pas directemment connectés.
	public FloydWarshall(double[][] matrice) {
		n = matrice.length;
		copie = new double[n][n];
		suivant = new Integer[n][n];

		// Copie de la matrice d'entrée et initialise 'suivant' pour la reconstruction des chemins
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (matrice[i][j] != POSITIVE_INFINITY) suivant[i][j] = j;
				copie[i][j] = matrice[i][j];
			}
		}
	}

	// Execute Floyd-Warshall pour calculer la plus courte distance entre chaque paire de sommets
	// Retourne la matrice de tous les plus courts chemins
	public double[][] matricePlusCourtsChem() {
		calcul();
		return copie;
	}

	// Execution de l'algortihme de Floyd-Warshall 
	public void calcul() {
		if (calcule) return;

		// Calcul par Floyd-Warshall de la matrice "copie" (Wn)  de la matrice "suivant" (matrice des plus courts chemins)
		for (int k = 0; k < n; k++) {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (copie[i][k] + copie[k][j] < copie[i][j]) {
						copie[i][j] = copie[i][k] + copie[k][j];
						suivant[i][j] = suivant[i][k];
					}
				}
			}
		}

		// Détection des circuits absorbants 
		for (int k = 0; k < n; k++)
			for (int i = 0; i < n; i++)
				for (int j = 0; j < n; j++)
					if (copie[i][k] + copie[k][j] < copie[i][j]) {
						copie[i][j] = NEGATIVE_INFINITY;
						suivant[i][j] = REACHES_NEGATIVE_CYCLE;
					}

		calcule = true;
	}

	  // Reconstruit le chemin le plus court (des sommets) de «debut» à  «fin» inclus.
	  // Retourne Un tableau d'index de sommets du chemin le plus court de «début» à «fin». 
	  // Si 'debut' et 'fin' ne sont pas connectés, alors est retourné un tableau nul. 
	  // Si le chemin le plus court de «debut» à la «fin» sont accessibles par un circuit absorbant.


	public List<Integer> reconstructPlusCourtChem(int debut, int fin) {
		calcul();
		List<Integer> chemin = new ArrayList<>();
		if (copie[debut][fin] == POSITIVE_INFINITY) return chemin;
		int at = debut;
		for (; at != fin; at = suivant[at][fin]) {
			// Retourne "null" étant donné qu'il y a une infinité de plus courts chemins
			if (at == REACHES_NEGATIVE_CYCLE) return null;
			chemin.add(at);
		}
		// Retourne null étant donné qu'il y a une infinité de plus courts chemins
		if (suivant[at][fin] == REACHES_NEGATIVE_CYCLE) return null;
		chemin.add(fin);
		return chemin;
	}



	// Créé un graphe avec n sommets. La matrice d'adjacence est construite de façon à ce que la valeur de départ d'un sommet à lui-même est 0
	public static double[][] creerGraphe(int n) {
		double[][] matrice = new double[n][n];
		for (int i = 0; i < n; i++) {
			java.util.Arrays.fill(matrice[i], POSITIVE_INFINITY);
			matrice[i][i] = 0;
		}
		return matrice;
	}

	public static void main(String[] args) throws IOException {

		///////// Page d'accueil 

		System.out.println();  
		System.out.println();
		System.out.println("************PAGE D'ACCUEIL DU PROJET DE THEORIE DES GRAPHES***************");
		System.out.println(" Recherche de chemin de valeur minimale par l’algorithme de Floyd-Warshall");
		System.out.println("");
		System.out.println("              L3-New, Groupe I, équipe 2");
		System.out.println("Farès Fadili, Bintou Traore, Andy Fordant, Ismail Bartolo");
		System.out.println();
		System.out.println("*************************************************************************");
		System.out.println();
		boolean repeat = true;

		File file;
		FW.graphe.constrGraphe gb;
		int i = 0;
		while(repeat){

			//Choix du graphe
			System.out.println();
			System.out.println("--------------------------------------------------------------------");
			System.out.println("                          CHOIX DU GRAPHE                           ");
			System.out.println("--------------------------------------------------------------------");
			System.out.println("");
			System.out.print("Veuillez selectionner un graphe > ");


			// Lecture du fichier
			Scanner sc = new Scanner(System.in);
			i = sc.nextInt();
			file = new File("Files/test"+i+".txt");
			while (!file.exists()){
				System.out.print("Graphe non existant. Veuillez selectionner un autre nombre >");
				sc = new Scanner(System.in);
				i = sc.nextInt();
				file = new File("Files/test"+i+".txt");
			}

			//Construction du graphe
			gb = new graphe.constrGraphe();
			graphe g = gb.from_file("Files/test"+i+".txt").constr();

			//Construction de la matrice d'adjacence 
			System.out.println();
			System.out.println("Matrice d'adjacence (M): ");
			System.out.println();
			Operateur.afficherMatriceAdj(g.matriceAdj);
			System.out.println();
			System.out.println();


			////////////////////////////////////////////////////////

			// Construction de la matrice des valeurs
			int n=g.enMatrice().size();
			double[][] m = creerGraphe(n);

			System.out.println("Matrice des valeurs (W0) ");

			System.out.print("   ");
			for (int x = 0; x < g.enMatrice().size(); x++) {
				System.out.print(x+"\t");}
			System.out.println();
			System.out.print("  ");
			for (int x = 0; x < g.enMatrice().size(); x++) {
				System.out.print("---\t");}
			System.out.println(); 
			for (int x = 0; x < g.enMatrice().size(); x++) {
				System.out.print(x+ "| ");
				for (int y = 0; y < g.enMatrice().size(); y++) { 
					if (!g.enMatrice().get(x).get(y).estInfini()) {
						m[x][y]=(int)g.enMatrice().get(x).get(y).valeur(); 
						System.out.print((int)m[x][y]+"\t");
					}
					else
					{
						m[x][y]=POSITIVE_INFINITY;
						System.out.print("Inf\t");
					}
				}
				System.out.println(" ");  
			}

			System.out.println();
			System.out.println();


			FloydWarshall calculateur = new FloydWarshall(m);
			double[][] distance = calculateur.matricePlusCourtsChem();


			System.out.println();

			//Reconstruction des plus courts chemins de tous les sommets vers chacun des autres
			System.out.println("Matrice des valeurs (Wn) ");
			System.out.print("   ");
			for (int x = 0; x < g.enMatrice().size(); x++) {
				System.out.print(x+"\t");}
			System.out.println();
			System.out.print("  ");
			for (int x = 0; x < g.enMatrice().size(); x++) {
				System.out.print("---\t");}
			System.out.println(); 



			for (int x = 0; x < n; x++) { 
				System.out.print(x+ "| ");
				for (int y = 0; y <n ; y++) { 
					if ( distance[x][y]==POSITIVE_INFINITY) { 
						System.out.print("inf\t"); }
					else { 
						System.out.print(distance[x][y]+"\t"); 
					}
				} 
				System.out.println(" ");
			}
			System.out.println();
			System.out.println();
			System.out.println();

			System.out.println();
			System.out.println("------------------------------------------------------------------------");
			System.out.println("      CHOIX DES SOMMETS POUR CALCULER LE CHEMIN LE PLUS COURT           ");
			System.out.println("------------------------------------------------------------------------");
			System.out.println("");

			// demande à l'utilisateur des deux sommets dont il veut le plus court chemin
			int r;
			int s;
			System.out.print("Entrez un premier sommet, compris entre 0 et " + (n-1) + " > ");
			Scanner sc2 = new Scanner(System.in);
			r = sc2.nextInt();
			System.out.print("Entrez un second sommet, compris entre 0 et " + (n-1) + " > ");
			Scanner sc3 = new Scanner(System.in);
			s = sc3.nextInt();

			List<Integer> chemin = calculateur.reconstructPlusCourtChem(r, s);
			String str;
			// présence de circuit absorbant => calcul impossible
			if (chemin == null) {
				str = "POSSEDE UNE INFINITE DE SOLUTIONS ! (cas de circuit absorbant)";
			}
			// absence de circuit absorbant => calcul du plus court chemin et de son poids
			else if (chemin.size() == 0) {
				str = String.format("N'EXISTE PAS (le sommet %d n'atteint pas le sommet %d)", r, s);
			}
			else {
				str =
						String.join(
								" -> ",
								chemin.stream()
								.map(Object::toString)
								.collect(java.util.stream.Collectors.toList()));
				str = "est: [" + str + "]";
			}

			System.out.printf("Le plus court chemin du sommet %d au sommet %d %s ", r, s, str);
			System.out.print(" de longueur " + distance[r][s]);

			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
		}



	}







}
