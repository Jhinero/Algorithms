class fiboLinéaire{
	public static void main(String[] Args){
		for (int n = 0; n < 10; n++)
			System.out.printf("fiboLin1(%d), fiboLin2(%d) = %d, %d\n",
				n, n, fiboLin1(n), fiboLin2(n)
			);
	}
	static long fiboLin1(int n){
	// I1(d,e,f,k) : d = fibo(k-2) et e = fibo(k-1) et f = fibo(k)
		long d = 0;
		long e = 1;
		long f = 1;
		long k = 2;
		if(n == 0){return d;}
		else if(n == 1){return e;}
		else{
			while (k != n) {
				d = e;
				e = f;
				f = d + f;
				k++;
			}
		}
		return f;
	}
	static long fiboLin2(int n){
	// I1(d,e,k) : d = fibo(k-2) et e = fibo(k-1)
		long d = 0;
		long e = 1;
		long k = 2;
		if(n == 0){return d;}
		else if(n == 1){return e;}
		else{
			while (k != n) {
				e = d + e;
				d = e - d;
				k++;
			}
		}
		return e;		
	}
	/* 	Sans utiliser la variable intermédiaire f, le corps de boucle devient :
		e = d + e // d = fibo(k-2) et e = fibo(k)
		d = e - d; // d = fibo(k) - fibo(k-2) = fibo(k-1)  et   e = fibo(k)
		// d = fibo(k-1) et e = fibo(k), autrement dit : I(d,e,k+1)
		k = k+1
		// I(d,e,k)   	
	*/ 
}