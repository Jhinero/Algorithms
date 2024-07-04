import java.util.Random;
import java.util.Arrays;
public class SegmentationEtPartitionnement{

	public static void main(String[] Args){
   		{ 	System.out.println("np1");
   			int m = 10, inf = -1, sup = 2; 
   			int[] T = tableauAleatoire(m,inf,sup); // T[0:m] à valeurs au hasard dans [inf:sup]
   			System.out.println("Avant : " + Arrays.toString(T));
   			int p = np1(T);
   			System.out.println("Après : " + Arrays.toString(T));
   			System.out.printf("p = %d\n",p);
   			System.out.println();
   		} 
  		{ 	System.out.println("np2");
  			int m = 10, inf = -1, sup = 2; 
   			int[] T = tableauAleatoire(m,inf,sup); // T[0:m] à valeurs au hasard dans [inf:sup]
   			System.out.println("Avant : " + Arrays.toString(T));
    		int p = np2(T);
   			System.out.println("Après : " + Arrays.toString(T));
   			System.out.printf("p = %d\n",p);
   			System.out.println();
   		}
  		{ 	System.out.println("nnp1");
  			int m = 10, inf = -2, sup = 3; 
   			int[] T = tableauAleatoire(m,inf,sup); // T[0:m] à valeurs au hasard dans [inf:sup]
   			System.out.println("Avant : " + Arrays.toString(T));
   			int[] pq = nnp1(T);
   			System.out.println("Après : " + Arrays.toString(T));
   			System.out.printf("p = %d, q = %d\n", pq[0],pq[1]);   	
   			System.out.println();
   		}
  		{ 	System.out.println("nnp2");
  			int m = 10, inf = -2, sup = 3; 
   			int[] T = tableauAleatoire(m,inf,sup); // T[0:m] à valeurs au hasard dans [inf:sup]
   			System.out.println("Avant : " + Arrays.toString(T));
   			int[] pq = nnp2(T);
   			System.out.println("Après : " + Arrays.toString(T));
   			System.out.printf("p = %d, q = %d\n", pq[0],pq[1]);  
   			System.out.println();
   		} 	
		{ 	System.out.println("partition");
		   	int m = 10, inf = -2, sup = 3; 
			int[] T = tableauAleatoire(m,inf,sup); // T[0:m] à valeurs au hasard dans [inf:sup]
			System.out.println("Avant : " + Arrays.toString(T));
			int[] pq = partition2(T,0,9);
			System.out.println("Après : " + Arrays.toString(T));
			System.out.printf("p = %d, q = %d\n", pq[0],pq[1]);  
			System.out.println();
		}	
/* 
		{  
      		System.out.println("Tri rapide avec segmentation en 3 parties");
 		   	Random r = new Random();
        	int n = (int)Math.pow(2,5), inf = 0, sup = 5;
        	int[] T = tableauAleatoire(n,inf,sup);
         	System.out.println("Avant : " + Arrays.toString(T)); 
        	qs(T,0,n);
         	System.out.println("Après : " + Arrays.toString(T));
   			System.out.println();
		}
		
		{ 
			System.out.print("Temps de calcul avec n = 10^20 et 10 valeurs différentes dans T. ");
        	Random r = new Random();
        	int n = (int)Math.pow(2,20), inf = 0, sup = 10;
      	  	int[] T = tableauAleatoire(n,inf,sup);
			long avant = System.currentTimeMillis();
			qs(T,0,n);
			long apres = System.currentTimeMillis();
			System.out.printf("Temps d'exécution : %d ms\n", (apres-avant));
			System.out.println();
		}
		*/
	}


	static int np1(int[] T){ int m = T.length;
		// I(p,q) : T[0:p] < 0 et T[p:q] >=0
		int p = 0,q=0; //I(0,0) Vraie
		while (q!=m) { //I(p,q) et q!=m
			if(T[q]>=0){ // I(p,q+1)
				q++;  //I(p,q)
			}
			else { //I(p,q) et q!=m T[q]<0
				permuter(T,p,q); //I(p+1,q+1)
				p++; //I(p,q+1)
				q++;//I(p,q)
			}
		}//I(p,m)
		return p;
	}

	static int np2(int[] T){ int m = T.length;
	// I(p,q) : T[0:p] < 0 et T[q:m] >=0. 
		int p = 0, q=m; //I(0,m) Vraie
		while (p!=q) { //I(p,q) et p!=q
			if(T[p]<0){ // I(p+1,q)
				p++;  //I(p,q)
			}
			else { //I(p,q) et p!=q Tq>=0
				permuter(T,p,q-1); //I(p,q-1) 
				q--; //I(p,q) 
			}
		} //I(p,p) 
		return p;
	}
	
	static int[] nnp1(int[] T){ int m = T.length;
		// I(p,q,r) : T[0:p] < 0 et T[p:q] = 0 et T[q:r] > 0 
		// Le sous-tableau T[r:m] reste à traiter.
		int p =0 , q=0, r=0; // I(0,0,0)
		// cas génerale
		while(r!=m){ // I(p,q,r) r!=m
			if(T[r]>0){ // I(p,q,r+1)
				r++; // I(p,q,r)
			}
			else if(T[r]==0){ // I(p,q,r) r!=m T[r]=0
				permuter(T, q, r); // I(p,q+1,r+1)
				q++;r++; // I(p,q,r)
			}
			else{
				permuter(T,q,r);
				permuter(T,p,q);
				p++;q++;r++;
			}
		
		}
		return new int[] {p,q}; 
	}
	
	static int[] nnp2(int[] T){ int m = T.length;
	// I(p,q,r) : T[0:p] < 0 et T[p:q] = 0 et T[r:m] > 0 
	// Le sous-tableau T[q:r] reste à traiter.
		int p=0,q=0,r=m; // I(0,0,m) 
		while (q!=r) {
			if(T[q]==0){
				q++;
			}
			else if(T[q]>=0){
				permuter(T, q, r-1);
				r--;
			}
			else {
				permuter(T, p, q);
				p++;q++;
			}
			
		}
		return new int[] {p,q}; 
	}
	
	static void permuter(int[] T, int i, int j){int x = T[i];
		T[i] = T[j]; 
		T[j] = x;
	}
  
	

	static int[] segementation(int[] T,int i,int j){
		int k1 = i;
		int k2 = k1+1;
		int jp = k2; // I(k1,k2,j')
		while(jp!=j){ // I(k1,k2,j') et jp !=j
			if(T[jp]>T[k1]){ // I(k1,k2,j'+1)
				jp++; // I(k1,k2,j')
			}
			else if(T[jp]==T[k1]){ 
				permuter(T, jp, k2); // I(k1,k2+1,j'+1)
				k2++;jp++; // I(k1,k2,j')
			}
			else{
				permuter(T, jp, k2); 
				permuter(T, k1, k2); // I(k1,k2+1,j'+1)
				jp++;k1++;k2++; // I(k1,k2,j')
			}
		}// I(k1,k2,j') et jp =j
		return new int[] {k1,k2}; 
	}
	static int[] partition2(int[] T, int i, int j){
		/* segmentation en 3 parties calculée sur l'invariant 
		I(k1,k2,j') : T[i:k1]<T[k1:k2]<T[j':j] où T[k1:k2] est une tranche de tableau constante */
			int k1=i, k2=k1+1, jprime = j;
			while (jprime != k2) // I(k1,k2,j') et j'≠k2
				if (T[k2] == T[k1]) // I(k1,k2+1,j')
					k2=k2+1; // I(k1,k2,j')
				else 
				if (T[k2] > T[k1]){permuter(T, jprime, k2); // I(k1,k2,j'-1)
					jprime = jprime - 1;  // I(k1,k2,j')
				} 
				else { // T[k2] < T[k1]
					permuter(T,k1,k2); // I(k1+1,k2+1,j') 
					k1 = k1+1; // I(k1,k2+1,j')
					k2 = k2+1; // I(k1,k2,j')
				}
			// I(k1,k2,j'=j) autrement dit : T[i:k1]<T[k1:k2]<T[k2:j']
			return new int[] {k1,k2};
		}


	
 /* 
	static void qs(int[] T, int i, int j){
		if(j-i <= 1) return;
		int k1k2 [] = segmentation(T,i,j);
		int k1 = k1k2 [0];
		int k2 = k1k2 [1];
		qs(T,i,k)
		qs(T,,)
	}


	static int[] ts(int[] T, int i, int j){
   		// I(p,q,r) : T[i:p] < T[p:q] < T[r:j]
   		// Initialisation : T[i:p] vide, T[p:q] contient T[i], T[r:j] vide
  		...
   		return new int[] {p,q};
   }
	*/
   static int[] tableauAleatoire(int n, int inf, int sup){ 
   	// retour T[0:n] à valeurs dans [0:sup]
   		Random r = new Random();
   		int[] T = new int[n];
   		for (int i = 0; i < n; i++) T[i] = inf + r.nextInt(sup - inf);
   		return T;
   	}
}

