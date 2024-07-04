import java.util.Arrays;

public class RepartitionOptimaleHeure {

    public static void main(String[] Args){
        System.out.println("Exercice 6 : trajet de coût minimum");
        int[] C = {4, 3, 4}; // coefficients des unités
        int[][] E = {
            {8, 10, 10, 12, 12, 12, 12, 12, 12, 16, 16}, // unité 0
            {16, 16, 18, 20, 20, 20, 20, 20, 20, 20, 20}, // unité 1
            {8, 12, 14, 14, 16, 18, 18, 18, 18, 20, 20}   // unité 2
        };
        int n = E.length, H = E[0].length - 1;
        System.out.printf("\n%d unités, %d heures de révision\n", n, H);
        System.out.println("Coefficients des unités : " + Arrays.toString(C));
        System.out.println("Tableau E des notes estimées :");
        afficherTableauE(E);
        int[][][] MA = calculerMA(E, C);
        int[][] M = MA[0], A = MA[1];
        System.out.println("\nTableau M de terme général m(k,t) :");
        afficherTableauM(M);
        System.out.println("\nTableau A de terme général a(k,t) = arg m(k,t) :");
        afficherTableauA(A);
        System.out.printf("\nValeur maximum m(%d,%d) = %d\n", n, H, M[n][H]);
        int SC = somme(C); // somme des coefficients
        System.out.printf("Moyenne : %d/%d = %.2f\n", M[n][H], SC, (float)M[n][H] / (float) SC);
        System.out.println("\nUne répartition optimale du temps de révision :");
        aro(A, E, C, n, H);
        System.out.println();
        // aro(A,E,C,n,6); // Répartition optimale de 6 heures de révision...
        // System.out.println();
    }

    static int[][][] calculerMA(int[][] E, int[] C){
        int n = E.length, H = E[0].length - 1;
        int[][] M = new int[n + 1][H + 1], A = new int[n + 1][H + 1];
        // M de terme général M[k][t] = m(k,t) et A de terme général A[k][t] = arg m(k,t).
        // Base de la récurrence : m(0,t) = 0, qqsoit t, 0 ≤ t < H+1
        for (int t = 0; t < H + 1; t++) {
            M[0][t] = 0;
            A[0][t] = 0; // les valeurs A[0][t] sont quelconques
        }
        for (int k = 1; k < n + 1; k++) // cas général, résolution par k croissant
            for (int t = 0; t < H + 1; t++){ // pour tout nombre d'heures t à répartir
                M[k][t] = Integer.MIN_VALUE;
                for (int h = 0; h < t + 1; h++){
                    int mkth = C[k - 1] * E[k - 1][h] + M[k - 1][t - h];
                    if (mkth > M[k][t]){
                        M[k][t] = mkth;
                        A[k][t] = h; // arg m(k,t) : h heures données à la k-ème unité dans la répartition optimale de t heures sur le sous-ensemble des k premières unités.
                    }
                }
            }
        return new int[][][] {M, A};
    }

    static void aro(int[][] A, int[][] E, int[] C, int k, int t){
        // affichage d'une répartition optimale (RO) de t heures sur le sous-ensemble des k premières unités.
        if (k == 0) return; // RO(0,t) = Ø. Sans rien faire, RO(0,t) a été affichée.
        int h = A[k][t]; // RO(k,t) = RO(k-1,t-h) union { "k-1<--h" }
        aro(A, E, C, k - 1, t - h); // RO(k-1,t-h) a été affichée
        System.out.printf("C[%d] * e(%d,%d) = %d * %d = %d\n", k - 1, k - 1, h, C[k - 1], E[k - 1][h], C[k - 1] * E[k - 1][h]); // { "k-1<--h" } affiché
    }

    static void afficherTableauE(int[][] T){
        int n = T.length;
        for (int i = n - 1; i > -1; i--)
            System.out.printf("unité %d : %s\n", i, Arrays.toString(T[i]));
    }

    static void afficherTableauM(int[][] T){
        int n = T.length;
        for (int k = n - 1; k > -1; k--)
            System.out.printf("k=%d : %s\n", k, Arrays.toString(T[k]));
    }

    static void afficherTableauA(int[][] T){
        int n = T.length;
        for (int k = n - 1; k > -1; k--)
            System.out.printf("k=%d : %s\n", k, Arrays.toString(T[k]));
    }

    static int somme(int[] T){
        int n = T.length;
        int s = 0;
        for (int i = 0; i < n; i++) s = s + T[i];
        return s;
    }
}
