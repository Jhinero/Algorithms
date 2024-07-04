import java.util.Arrays;
public class PLSSC{

	public static void main(String[] args){ 
		int[] S = toIntTab(args);
		System.out.printf("S = %s\n", Arrays.toString(S));
		Liste[] G = graphe(S);
		System.out.println("G ="); afficher(G, S);
		Liste[] Gprime = symetrique(G);
		System.out.println("G' ="); 
		afficher(Gprime, S);
		int[][] LA = calculerLA(Gprime);
		int[] L = LA[0], A = LA[1];
		System.out.printf("S = %s\n", Arrays.toString(S));
		System.out.printf("L = %s\n", Arrays.toString(L));
		System.out.printf("A = %s\n", Arrays.toString(A));
		System.out.printf("Une plssc de S = %s : %s\n", Arrays.toString(S), plssc(LA, S));
	}

	static void afficher(Liste[] G, int[] S){int n = G.length;
	// affiche le graphe G (table de liste d'arcs). Fonction vue en cours.
		for (int i = 0; i < n; i++){
			System.out.printf("S[%d]=%d : ", i, S[i]);
			for (Liste l = G[i]; !vide(l); l = reste(l)){
				System.out.printf("S[%d]=%d ", premier(l), S[premier(l)]);
			}
			System.out.println();
		}
	}
	static Liste[] graphe(int[] S){ int n = S.length;
	// construit le graphe G
		Liste[] G = new Liste[n];
		for (int i = 0; i < n; i++) G[i] = null;
		for (int j = 1; j < n; j++) 
			for (int i = 0; i < j; i++)
				if (S[i] < S[j])
					G[i] = new Liste(j, G[i]);
		return G;
	}
	
	// La classe Liste et les fonctions vide, premier et reste ont été vues en cours.
	static class Liste{ int p; 
		Liste r;
		Liste(int p, Liste r){
			this.p = p; 
			this.r = r;
		}
	}
	static boolean vide(Liste l){ return l == null; }
	static int premier(Liste l){ return l.p; }
	static Liste reste(Liste l){ return l.r; }
	
	static Liste[] symetrique(Liste[] G){ int n = G.length;
	// retourne le graphe symétrique du graphe G (sens des flèches inversé). Vue en cours.
		Liste[] Gprime = new Liste[n];
		for (int i = 0; i < n; i++){
			for (Liste l = G[i]; !vide(l); l = reste(l)){
				int j = premier(l); // arc i-->j de G
				Gprime[j] = new Liste(i, Gprime[j]); // arc j-->i de Gprime
			}
		}
		return Gprime;
	}
	static int[][] calculerLA(Liste[] Gprime){ int n = Gprime.length;
	// retourne L[0:n] de terme général l(j) et A[0:n] de terme général a(j) = arg l(j)
		// l(j) = 1 pour tout j tel que pred(j) = Ø
		// l(j) = 1+max(l(i)) pour tout j tel que pred(j) ≠ Ø
		int[] L = new int[n], A = new int[n];
		// L[0:n] : l(j) = longueur du plus long chemin de 0 à j
		// A[0:n] : a(j) = arg l(j)
		for (int j = 0; j < n ; j++) 
			if (vide(Gprime[j])) {
				L[j] = 1; 
				A[j] = -1;
			}
			else { // calcul de L[j] = 1 + max(L[i]) i dans pred(j)
				L[j] = -1; // ou INTEGER.MIN_VALUE, au choix. 
				for (Liste l = Gprime[j]; !vide(l); l = reste(l)){ int i = premier(l); 
					// i est prédécesseur de j
					if (L[i] > L[j]){
						L[j] = L[i];
						A[j] = i;
					}
				}
				// L[j] = max(L[i]) i dans pred(j)
				L[j] = 1 + L[j]; // L[j] = 1 + max(L[i]) i dans pred(j)
			}
		return new int[][] {L, A};
	}

	static String plssc(int[] A, int[] S, int j){
	// retourne une plus longue séquence croissante de 0 à j inclus
		if (A[j]==-1) return "S["+j+"]" + "=" + S[j] + " "; // S[j] = s_j
		return // une plssc jusqu'en A[j] + "S[j] = s_j"
			plssc(A, S, A[j]) + "S[" + j + "]=" + S[j] + " "; 
	}

	static String plssc(int[][] LA, int[] S){ int n = S.length;
	// Retourne une plssc de S. 
		if (n == 0) return ""; // S est vide. Une plssc de S est la séquence vide.
	/* ici : S  est non vide.
	Soit jstar = argmax(L), indice du premier maximum de L. Une plssc finit en jstar.
	1) calculer jstar = argmax(L)
	2) afficher une plssc finissant en jstar */
		int[] L = LA[0], A = LA[1];
		int jstar =  argmax(L); 
		System.out.printf("Une plssc finit en j = %d, sa longueur est l(%d) = %d\n", 
			jstar, jstar, L[jstar]
		);
		return plssc(A, S, jstar);
	}
	
	static int argmax(int[] L){ int n = L.length; 
	// Retourne argmax L[0:n]. 
	// Nous posons agmax L[0:0] = argmax Ø = -1.
		if (n == 0) return -1; 
	// Ici : n > 0.
	/*	Invariant I(jstar,j): jstar = argmax(L[0:j]). 
	Arrêt : j = n.
	Initialisation : jstar = 0, j = 1. Vraie car L[0:1] = [L[0]] donc 0 = argmax(L[0:1]) 
	Progression : I(jstar, j) et j != n et ...
			... L[j] > L[jstar] ==> I(j, j+1)
			... L[j] ≤ L[jstar] ==> I(jstar, j+1)
 	*/
		int j = 1, jstar = 0; // I(jstar, j)
		while (j != n) // I(jstar, j) et j != n
			if (L[j] > L[jstar]){ 
				// I(j, j+1)
				jstar = j; 
				// I(jstar,j+1)
				j = j+1; 
				// I(jstar,j)			
			}
			else 
				// I(jstar, j+1)
				j = j+1; 
				// I(jstar,j)
		// I(jstar,n) c'est-à-dire : jstar = argmax(L[0:n])
		return jstar;
	}
	/*Voici le même calcul avec un "for", pour mettre en évidence sa moindre lisibilité.*/
	static int argmaxFor(int[] L){ int n = L.length;
		if (n == 0) return -1;
		int jstar = 0; // <<< aucun commentaire pertinent ne peut être écrit à cet endroit.
		for ( int j = 1; // I(j,jstar) 
			  j < n; // I(jstar,n), c'est-à-dire jstar = argmax(T[0:n]) 
			  // I(jstar,j+1)
			  j++ 
			  // I(jstar,j)
			)
			if (L[jstar] < L[j]) // I(j,j+1)
				jstar = j; // I(jstar,j+1)
			// else : I(jstar,j+1)
		return jstar;
	}

	static int[] toIntTab(String[] args){ int n = args.length;
	// retourne les données de la ligne de commande dans un tableau d'entiers.
		int[] T = new int[n];
		for (int i = 0; i < n; i++)
			T[i] = Integer.parseInt(args[i]);
		return T;
	}
}