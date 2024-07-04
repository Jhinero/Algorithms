import java.util.Random;
import java.util.Arrays;
public class NbOccurance{ 

	public static void main(String[] Args){
 	  	{ 	System.out.println("\n1) Nombres d'occurrences des valeurs de T");
  	 		int n = 10, inf = 0, sup = 6; // valeurs de T aléatoires dans [0:6] 
  	 		int[] T = tableauAléatoire(n,inf,sup); // T[0:n] à valeurs au hasard dans [inf:sup]
  	 		System.out.printf("T : %s, max(T) = %d\n", Arrays.toString(T), max(T));
  	 		int[] N = nombreOccurrences(T);
  	 		int[] IE = intervalleEntiers(inf, sup);
  	 		System.out.printf("[%d:%d] :\t\t\t%s\n", inf, sup, Arrays.toString(IE));
  	 		System.out.printf("nb occurrences :\t%s\n", Arrays.toString(N));
  	 	}
  		{ 	System.out.println("\n2) Renverser T");
  			int m = 10, inf = 0, sup = 6; 
  	 		int[] T = tableauAléatoire(m,inf,sup); // T[0:m] à valeurs au hasard dans [inf:sup]
  	 		System.out.println("T :\t\t" + Arrays.toString(T));
   		 	renverser(T);
   			System.out.println("renversé(T) :\t" + Arrays.toString(T));
   		}
  		{ 	System.out.println("\n3) Ensemble des valeurs de T");
  			int m = 10, inf = 0, sup = 6; 
   			int[] T = tableauAléatoire(m,inf,sup); // T[0:m] à valeurs au hasard dans [inf:sup]
   			Arrays.sort(T); 
   			System.out.println("T :\t\t" + Arrays.toString(T));
			int[] set = bagToSet(T); // set = T[0:k] = ens(T[0:n])
	 		System.out.printf("ens(T) = \t%s\n", Arrays.toString(set));  	 	
  	 	}
  	 }
	static int[] intervalleEntiers(int inf, int sup){ int n = sup-inf;
	// retourne [inf, inf+1, ..., sup-1] c'est-à-dire [inf:sup].
		int[] T = new int[n]; 
		for (int i = 0; i < n; i++)
			T[i] = i;
		return T;
	}	 
  	static int[] tableauAléatoire(int n, int inf, int sup){ int [] T = new int[n];
  	// retourne T[0:n] à valeurs dans [inf:sup] 
  		Random r = new Random();
  		for (int i = 0; i < n; i++)
  			T[i] = inf + r.nextInt(sup-inf);
  		return T;
  	} 
 	static int[] nombreOccurrences(int[] T){ int n = T.length ; int m = max(T);
 	/* Les valeurs de T[0:n] sont dans l'intervalle [0:max+1]
 	I(k) : N est le tableau des nombres d'occurrences des valeurs dans le k-préfixe de T
 	Init : k = 0, N = [..., 0, ...]
 	Arrêt : k = m
 	Progression : I(k) et k!=0 et N[T[k]]++ ==> I(k+1)
 	*/
 		int[] N = new int[m+1];
 		for (int i = 0; i < m+1; i++) N[i] = 0; // inutile en Java
 		int k = 0 ;  
 		// I(k)
 		while (k != n ){ // I(k) et k!=n
 			N[T[k]]++ ; // I(k+1)
 			k++; // I(k)
 		} // I(n) donc N contient les nombres d'occurrences des valeurs de T.
 		return N;
 	}
  
 	static void renverser(int[] T){ int n = T.length;
 	/* I(k) : le k-préfixe de T est le renversé du (n-k) suffixe de T
 	Remarque : la tranche de tableau restant à traiter est T[k:n-k].
 	[t_{n}...t_{n-k}|xxxxxxxxx|t_{k}...t_{0}}]
 	                 k         n-k
 	                  à traiter
 	Initialisation : k = 0
 	Arrêt : La tranche de tableau restant à traiter est vide ou réduite à un élément.
 	Progression : I(k) et n-2*k >= 1 et t_k permutée avec T[n-k-1] ==> I(k+1) */
 		int k = 0; // I(k)
 		while (n-2*k >= 1){ permuter(T,k,n-k-1); // I(k+1)
 			k = k+1; // I(k)
 		}
 		// I(k) et n-2*k <= 1 Donc T contient ses valeurs dans l'ordre inverse (c'est la sortie S)	
 	}
  	static void permuter(int[] T, int i, int j){int ti=T[i]; T[i]=T[j]; T[j]=ti;}
 	static int max(int[] T){int n = T.length;
 	/* I(m,k) : m = max(T[0:k]); Initialisation : m = -∞, k = 0; Arrêt k = n;
 	Progression : I(m,k) et k≠n et ...
 		• t_k <= m ==> I(m,k+1)
 		• t_k > m ==> I(t_k, k+1) */
 		int k = 0, m = Integer.MIN_VALUE;
 		while (k != n) // I(m,k) et k≠n
 			if (T[k] <= m) // I(m,k+1)
 				k = k+1; // I(m,k) 
 			else {// I(t_k, k+1)
 				m = T[k]; // I(m,k+1)
 				k = k+1; // I(m,k)
 			}
 		// I(m,n) c'est-à-dire m = max(T[0:n])
 		return m;
 	}
 	static int[] bagToSet(int[] T){ int n = T.length;
 	// T est croissant. Exemple : 2 2 3 4 4 4 6 7
 	/* I(k,j) : le k-préfixe de T contient e(j) (ensemble des valeurs du j-suffixe de T)
 	(Il est sous-entendu que T contient une permutation des valeurs de T.)
 	Initialisation : j=..., k=...  
 	Arrêt : j = .... Alors, nous avons ...
 	Progression : I(k,j) et j!=n et ...
 		• t_j == ... ==> I(k,j+1)
 		• t_j ≠ t_{k-1} et ... ==> I(k+1,j+1)
 	*/
 		int j= 1 ,k= 1; // I(k,j)
 		while (j!=n)
 			if (T[j]==T[k-1]) // I(k,j+1)
 				j++; // I(k,j)
 			else{ T[j] = T[k-1]; // I(k+1;j+1)
 				permuter(T,j,k); // I(...,...) 
 				k++;j++;// I(k,j)
 			}
 		// I(k,n) c'est à dire la sortie souhaitée
 		return Arrays.copyOfRange(T,0,k);
 	}
 }