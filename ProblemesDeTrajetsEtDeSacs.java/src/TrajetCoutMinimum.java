import java.util.Arrays;

public class TrajetCoutMinimum {
    

    
    public static void main(String[] args){
			
		{	System.out.println("Exercice 2 : trajet de coût minimum");
			// D1 : 0--(10)-->1, 1--(20)-->2, 2--(100)-->3, 3--(30)-->4 
			int[] D1 = {10,20,100,30,-1}, 
			// D2 : 0--(40)-->2, 1--(50)-->3, 2--(60)-->4
				  D2 = {40,50,60,-1,-1};
			System.out.printf("D1 = %s\n", Arrays.toString(D1));
			System.out.printf("D2 = %s\n", Arrays.toString(D2));
			int[] M = calculerM(D1,D2);
			System.out.printf("M = %s\n", Arrays.toString(M));
			int n = D1.length;
			System.out.printf("trajet de coût minmum de la ville 0 à la ville %d :\n",n-1);
			afficherTrajectMinimum(M,D1,D2,n-1); System.out.println();
			System.out.printf("Coût de ce trajet : %d\n", M[n-1]);
			System.out.println();
		} 
		
	}
    static int[] calculerM(int[] D1, int[] D2){ int n = D1.length;
		int[] M = new int[n];
		M[0] = 0; M[1] = D1[0];
		for (int j = 2; j < n; j++)
			M[j] = min(M[j-1]+D1[j-1], M[j-2]+D2[j-2]);
		return M;
	} // Forme du terme dominant du temps de calcul : alpha x n
	  // car corps de boucle en temps majoré par une constante, exécuté n-2 fois
	
	static void afficherTrajectMinimum(int[] M, int[] D1, int[] D2, int j){
	// Affiche un trajet de coût minimum de 0 à j inclus.
		if (j==0){ // le trajet ne contient que la ville 0. L'afficher.
			System.out.print(j);
			return; 
		}
		// trajet min jusqu'en j-1 puis --(d1(j-1))--> j
		if (M[j] == M[j-1] + D1[j-1]) { 
			// 1) affichier le trajet min. de 0 à j-1
			afficherTrajectMinimum(M, D1, D2, j-1);
			// 2) afficher "--(d1(j-1))--> j"
			System.out.printf("--(%d)-->%d", D1[j-1], j);
		}
		else{ // trajet min jusqu'en j-2 puis --(d2(j-2))--> j
			// 1) afficher le trajet min. de 0 à j-1
			afficherTrajectMinimum(M, D1, D2, j-2);
			// 2) afficher "--(d2(j-2))--> j-2"
			System.out.printf("--(%d)-->%d", D2[j-2], j);
		}
	}
		    // Forme du terme dominant du temps de calcul : alpha x (n x C0 x C1) 
		    // où alpha est une constante.


		static public int min(int A, int B){
			if(A<B ){return A;}
			else return B;
			
		}
		
}	

