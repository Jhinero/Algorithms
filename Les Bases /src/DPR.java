

import java.util.Arrays;					
import java.util.Random;
class DPR {
/* Exemples de programmes "Diviser pour régner"
-----------------------------------------------
1) QuickSort. 
2) MergeSort (tableaux et listes)
3) Somme des valeurs d'un tableau
...

*/
// 1) Tri rapide
	static void quickSort(int[] T){ int n = T.length; 
		qs(T,0,n);
	}

	static void qs(int[] T, int i, int j){ int n = j-i;
		if (n <= 1) return;
		int[] k1k2 = partition(T,i,j); 
		// T[i:k1] < T[k1:k2] < T[k2:j], T[k1:k2] constant
		int k1 = k1k2[0], k2 = k1k2[1];
		qs(T,i,k1); // T[i:k1] croissant
		qs(T,k2,j); // T[j2:j] croissant
		// T[i:k1] < T[k1:k2] < T[k2:j], T[k1:k2] constant, T[i:k1] et T[i:k2] croissants
		// donc T[i:j] croissant.
	}
	static Random r = new Random();
	static int[] partition(int[] T, int i, int j){
		permuter(T,i,i+r.nextInt(j-i));
		// I(k1,k2,jp) : T[i:k1] ≤ T[k1:k2] et T[k1:k2] ≤ T[jp:j] et T[k1:k2] constant
		int k1 = i, k2 = i+1, jp = j; 
		while (jp-k2 > 0)
			if (T[k2] == T[k1]) k2 = k2+1; 
			else
			if (T[k2] > T[k1]){ permuter(T, k2, jp-1);
				jp = jp-1;
			}
			else { permuter(T, k1, k2);
				k1++;
				k2++;
			}
		return new int[] {k1,k2};
	}
	static void permuter(int[]T, int i, int j){ int ti = T[i]; T[i] = T[j]; T[j] = ti; }

	// 2) Tri fusion d'un tableau
	static void mergeSort(int[] T){int n = T.length; 
		ms(T,0,n);}
	static void ms(int[] T, int i, int j){ int n = j-i;
		if (n <= 1) return;
		int k = (i+j)/2;
		int[] G = Arrays.copyOfRange(T,i,k);
		int[] D = Arrays.copyOfRange(T,k,j);
		mergeSort(G); // G croissant
		mergeSort(D); // D croissant
		// G et D croissants sont fusionnés dans T à partir de l'indice i
		merge(G,D,T,i);
		// T[i:j] croissant
	}
	static void merge(int[] G, int[] D, int[] T, int i){ int ng = G.length, nd = D.length;
	// fusion de G et D croissants dans T à partir de l'indice i
		int g = 0, d = 0, j = 0; 
		while (g < ng && d < nd)
			if (G[g] <= D[d]){ T[i+j] = G[g]; 
				g = g+1;
				j = j+1;
			}
			else { T[i+j] = D[d];
				d = d+1; 
				j = j+1;
			}
		for (; g < ng; g++) {T[i+j] = G[g]; j = j+1; }
		for (; d < nd; d++) {T[i+j] = D[d]; j = j+1; }
	}

// 2 bis) tri fusion d'une liste
	static LE mergeSort(LE L) { // tri de la liste d'entier L par algorithme mergeSort
		LE l = L; L = null; // afin que la séparation de L en 2 listes l1 et l2 se fasse
		// sans quantité de mémoire supplémentaire
		
		if (vide(l) || vide(reste(l))) return l;
		// "fendre" la liste l en 2 sous-listes l1 et l2
		// l1 contiendra les valeurs de l, de rang pair
		// l2 contiendra les valeurs de l, de rang impair
		// les longueurs de ces deux listes sont égales (à un près)
		LE l1 = null, l2 = null;
		while (!vide(l)){
			l1 = new LE(premier(l), l1);
			l = reste(l);
			if (!vide(l)) {
				l2 = new LE(premier(l), l2);
				l = reste(l);
			}
		}
		l1 = mergeSort(l1); // l1 est croissante
		l2 = mergeSort(l2); // l2 est croissante
		// nous les "renversons" pour pouvoir les fusionner en une liste l croissante.
		// renverser l1 dans r1
		LE r1 = null; 
		while (!vide(l1)){ r1 = new LE(premier(l1),r1); l1 = reste(l1); }
		// renverser l2 dans r2	
		LE r2 = null; 
		// fusionner r1 et r2 dans l. Résultat : l est croissante, 
		// elle contient les éléments de r1 et les éléments de r2.
		while (!vide(l2)){ r2 = new LE(premier(l2),r2); l2 = reste(l2); }
		// r1 et r2 sont décroissantes
		l = null;
		while (!vide(r1)&&!vide(r2))
			if (premier(r1) >= premier(r2)){
				l = new LE(premier(r1),l);
				r1 = reste(r1);
			}
			else {
				l = new LE(premier(r2),l);
				r2 = reste(r2);
			}
		// si r1 non vide, la "vider" dans l
		while (!vide(r1)) {
			l = new LE(premier(r1),l);
			r1 = reste(r1);
		}
		// si r2 non vide, la "vider" dans l
		while (!vide(r2)) {
			l = new LE(premier(r2),l);
			r2 = reste(r2);		
		}
		return l; 
		/* Remarque : si nous avions accès au "dernier élément de l", nous n'aurions pas
		à renverser l1 et l2 pour les fusionner dans l.
		Mais dans le cours nous n'avons pas vu cet accès au dernier élément de l, par 
		manque de temps. Alors... on fait "sans" ! Le temps de calcul est plus élevé mais
		la complexité reste en Theta(n log n). Ainsi, la version ci-dessus est optimale
		au regard de la complexité. */
	}

	static class LE{int p; LE r;
		LE(int p, LE r){
			this.p=p; this.r=r;}
	}
	static int premier(LE l){return l.p;}
	static LE reste(LE l){return l.r;}
	static boolean vide(LE l){return l==null;}

	// 3) Somme, max, min, des valeurs d'un tableau
	static int somme(int[] T) { int n = T.length; return somme(T, 0, n); }
	static int somme(int[] T, int i, int j){ int n = j-i; 
		if (n == 0) return 0;
		if (n == 1) return T[i];
		int k = (i+j)/2;
		return somme(T, i, k) + T[k] + somme(T, k+1, j); 
	} 
	static int max(int[] T){ int n = T.length; return max(T, 0, n); }
	static int max(int[] T, int i, int j){ int n = j-i; 
		if (n == 0) return Integer.MIN_VALUE; // max(Ø) = -∞
		if (n == 1) return T[i];
		int k = (i+j)/2;
		int Mg = max(T, i, k);
		int Md = max(T, k+1, j);
		if (Mg >= T[k] && Mg >= Md) return Mg;
		if (T[k] >= Md) return T[k];
		return Md;
	}

	static int min(int[] T){ int n = T.length; return min(T, 0, n); }
	static int min(int[] T, int i, int j){ int n = j-i; 
		if (n == 0) return Integer.MAX_VALUE; // min(Ø) = +∞
		if (n == 1) return T[i];
		int k = (i+j)/2;
		int Mg = min(T, i, k);
		int Md = min(T, k+1, j);
		if (Mg <= T[k] && Mg <= Md) return Mg;
		if (T[k] <= Md) return T[k];
		return Md;
	}
	static int[] tableauAleatoire(int n){ int[] T = new int[n];
	// retourne T[0:n] à valeurs aléatoires dans [0:n].
		for (int i = 0; i < n; i++)
			T[i] = r.nextInt(n);
		return T;
	}
	static LE listeAleatoire(int n){
	// retourne une liste de longueur n, à valeurs aléatoires dans [0:n]
		LE l = null; 
		for (int i = 0; i < n; i++)
			l = new LE(r.nextInt(n), l);
		return l;
	}
	static void afficher(LE l){
		if (vide(l)) return;
		System.out.print(premier(l)+" ");
		afficher(reste(l));
	}

    public static void main(String[] args){
		System.out.println("\nExemples de programmes \"diviser pour régner\"");
		System.out.println("\n\ntri d'un tableau par quickSort et mergeSort");
			int n = 10;
			int[] T = tableauAleatoire(n);
			int[] T1 = T.clone();
			System.out.printf("T avant quickSort = %s\n", Arrays.toString(T1));
			quickSort(T1);
			System.out.printf("T après quickSort = %s\n", Arrays.toString(T1));
			T1 = T.clone();
			System.out.printf("T avant mergeSort = %s\n", Arrays.toString(T1));
			mergeSort(T1);
			System.out.printf("T après mergeSort = %s\n", Arrays.toString(T1));
		System.out.println("\ntri d'une liste par mergeSort");
			LE l = listeAleatoire(n);
			System.out.print("l avant mergeSort = "); afficher(l); System.out.println(); 
			l = mergeSort(l);
			System.out.print("l après mergeSort = "); afficher(l); System.out.println(); 
		System.out.println("\nsomme, min et max d'un tableau");
			n = 5;
		T = tableauAleatoire(n);
		System.out.printf("T = %s\n", Arrays.toString(T));
		System.out.printf("somme(T), min(T), max(T) = %d, %d, %d\n\n", 
			somme(T), min(T), max(T));	
	}

	
}

