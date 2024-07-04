import java.util.Arrays;
class FiboLogarithmique{
/* 
	M = {{0,1}{1,1}}, F = {fibo(k-2), fibo(k-1)}, M x F = {fibo(k-1),fibo(k)}.
	Soit F1 = {fibo(0),fibo(1)} = {0,1}
	Alors : 
		-- M F1 = {fibo(1),fibo(2)} = F2
		-- M F2 = M M F1 = M^2 F1 = F3
		...
		-- M Fn-1 = M^(n-1) F1 = Fn
	1) calculer Fn
		a) calculer P = M ^ (n-1) par dichotomie pour avoir la complexité Theta(log n)
		b) calculer Fn = P x F1, ce calcul est en Theta(1)
	2) Retourner Fn[1]
*/
	public static void main(String[] Args){
		for (int n = 0; n < 60; n++){
			System.out.printf("fiboLog(%d) = %d\n", n, fiboLog(n));
		}
	}
	static long fiboLog(int n){
		if (n==0) return 0;
		if (n==1) return 1;
		// n ≥ 2
		long[][] M = {{0,1},{1,1}};
		long[] F1 = {0,1};
		long[][] P = puissanceDichotomique(M,n-1);
		long[] Fn = produitMatriceVecteur(P,F1);
		return Fn[1];
	} // complexité Theta(log n) multiplications car P est calculé en Theta(log n)
	// multiplications, et Fn est calculé en Theta(1) multiplications.
	static long[][] puissanceDichotomique(long[][] M, int k){
	// retourne P = M puissance k. Version récursive. Voir exercice 18 feuille 10
		if (k==0) return new long[][] {{1,0},{0,1}}; // matrice identité
		if (k%2 == 0) return 
			puissanceDichotomique(
				carré(M), // 8 multiplications, soit Theta(1) multiplications
				k>>1
			);
		// k impair
		return 
			produitMatricesDeuxDeux( 
				M, 
				puissanceDichotomique(
					carré(M), 
					k>>1
				)
			); 
	}
}