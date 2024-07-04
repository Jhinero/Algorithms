class FiboNaive{
	public static void main(String[] Args){
		String durées = "[";
		for (int n = 0; n < 60; n++){
			long avant = System.currentTimeMillis();
			long fn = fiboNaive(n);
			long après = System.currentTimeMillis();
			long durée = après - avant;
			System.out.printf("fiboNaïve(%d) = %d, temps de calcul = %d ms\n", 
				n, fn, durée);
			durées = durées + durée + ", ";
		}
		durées = durées + "]";
		System.out.println(durées);
	}
	static long fiboNaive(int n){
		if(n >= 2) return fiboNaive(n-2)+fiboNaive(n-1);
		else if(n==0)return 0;
		else  return 1;
	}
}