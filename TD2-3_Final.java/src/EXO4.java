import java.util.Arrays;

public class EXO4 {
    
    public static void main(String[] args){
			
			System.out.println("Exercice 1 : deux sacs");
			int[] T = {20, 20, 70, 10, 10, 40, 10, 80, 10, 40, 45}, // tailles des objets
				  V = {25, 25, 65, 15, 5, 35, 15, 75, 15, 45, 50}; // valeurs des objets
			int C0 = 25, C1 = 75  ; // contenance des sacs
			System.out.println("Tailles des objets: " + Arrays.toString(T));
			System.out.println("Valeurs des objets  : " + Arrays.toString(V));
			System.out.printf("Contenances des sacs : C0 = %d, C1 = %d\n", C0,C1);
			int[][][] M = calculerM(V,T,C0,C1);
			int n = T.length; // nombre de spots
			System.out.printf("gain maximum des sacs : %d\n", M[n][C0][C1]);
			System.out.println("sous-ensemble d'objets de gain toal maximum :");
			acsm(M,V,T,n,C0,C1);
			System.out.println(); System.out.println();
		
	}
		static int[][][] calculerM(int[] V, int[] T, int C0, int C1){
	    	int n = V.length;
			int[][][] M = new int[n+1][C0+1][C1+1];
			// base				
			for (int c0 = 0; c0 < C0+1; c0++){
				for (int c1 = 0; c1 < C1+1; c1++){
					M[0][c0][c1] = 0;					
				}
			}
			// cas général
			for (int k = 1; k < n+1; k++){
				for (int c0 = 0; c0 < C0+1; c0++){
					for (int c1 = 0; c1 < C1+1; c1++){	
						if(c0-T[k-1]>= 0 && c1-T[k-1]>=0){
							 M[k][c0][c1] = max(M[k-1][c0][c1], M[k-1][c0-T[k-1]][c1]+V[k-1], M[k-1][c0][c1-T[k-1]]+V[k-1]);
						}
						else
						if(c0-T[k-1]>= 0 && c1-T[k-1]<=0){
							M[k][c0][c1]= Math.max(M[k-1][c0][c1], M[k-1][c0-T[k-1]][c1]+V[k-1]);
						}
						else 
						if(c0-T[k-1]<= 0 && c1-T[k-1]>=0){
							M[k][c0][c1]= Math.max(M[k-1][c0][c1], M[k-1][c0][c1-T[k-1]]+V[k-1]);
						}
						else 
							M[k][c0][c1]= M[k-1][c0][c1];
					}
				}
			}            
			return M;
		} 
		    // Forme du terme dominant du temps de calcul : alpha x (n x C0 x C1) 
		    // où alpha est une constante.
		static public int max(int A, int B, int C){
			if(A>B && B > C){return A;}
			if(B>A && B > C){return B;}
			else return C;
		}
		static void acsm(int[][][] M, int[] V, int[] T, int k, int c0, int c1){
		// affiche le 
		// contenu des sacs de contenance c0 et c1 de valeur maximum, contenant un sous-ensemble  
		// des k premiers objets.
			if (k == 0) return;
			// le k-ème objet n'est dans aucun des sacs
			if (M[k][c0][c1] == M[k-1][c0][c1]) { 
				acsm(M,V,T,k-1,c0,c1);
				return;
			}
			// // le k-ème objet est dans le sac S0
			if (M[k][c0][c1] == M[k-1][c0-T[k-1]][c1] + V[k-1]) {
				acsm(M,V,T,k-1,c0-T[k-1],c1);
				System.out.printf("Sac 0 : objet %d, valeur %d, taille %d\n",k-1,V[k-1],T[k-1]);
				return;
			}
			// le k-ème objet est dans le sac S1
			acsm(M,V,T,k-1,c0,c1-T[k-1]);
			System.out.printf("Sac 1 : objet %d, valeur %d, taille %d\n",k-1,V[k-1],T[k-1]);
			return;
		} 
}	
	