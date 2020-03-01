package coderPower;
/**
 * Vous glissez malencontreusement avec vos babouches sur votre tapis volant et tombez à travers des sables mouvants
 * dans une caverne magique où se trouve un fabuleux trésor.
 *
 * Le trésor est composé de différentes épices et poudres plus précieuses les unes que les autres dont on vous donne
 * le prix au kilogramme ainsi que la quantité totale de chacune des poudres contenues dans la caverne.
 * Le trésor comporte également des pierres précieuses, chacune a un poids et une valeur indivisible.
 * Vous n'avez avec vous qu'une vielle lampe à huile de contenance finie (vous avez cru quoi ? ce n'est pas magique hein !)
 * et vous devez indiquer quelles poudres et pierres précieuses emporter dedans pour maximiser la valeur du trésor
 * que vous allez rapporter à la surface (avide que vous êtes) !
 *
 *
 * Indice : si vous avez reconnu ici le "problème du sac à dos" (https://fr.wikipedia.org/wiki/Problème_du_sac_à_dos),
 * vous êtes sur la bonne piste, mais le très grand nombre d'objets change un peu la donne !
 *
 * Données
 *
 * Entrée
 *
 * Ligne 1 : 3 entiers séparés par des espaces : N le nombre de pierres précieuses, M le nombre de types de poudres,
 * C la quantité (en gramme) de pierres ou poudre que peux contenir la lampe, chacun compris entre 1 et 100.
 * Lignes 2 à N + 1 : 2 entiers séparés par des espaces, respectivement la valeur (en pièces d'or) et le poids (en grammes)
 * de chaque pierre précieuse, compris respectivement entre 1 et 1000 et 1 et C
 * Lignes N + 2 à N + M + 2 : 2 entiers séparés par des espaces, respectivement le prix au poids (en pièces d'or par gramme)
 * et la quantité disponible (en grammes) de chaque type de poudre, compris respectivement entre 1 et 100 et 1 et C.
 *
 * Sortie
 *
 * La valeur maximale que peux contenir la lampe en pierres précieuses et poudres !
 *
 * Exemple
 *
 * Entrée
 *
 * 2 2 100
 * 600 40
 * 1000 50
 * 20 40
 * 15 80
 *
 * Sortie
 *
 * 1950
 *
 * Commentaire
 *
 * Dans cet exemple l'optimal est de prendre l'objet de valeur 1000, puis 40 grammes de poudre à 20 pièces d'or par gramme,
 * puis 10 grammes de poudre à 15 pièces d'or par gramme. On arrive alors à une valeur de 1000 + 800 + 150 = 1950
 */

import java.util.Scanner;

public class ProblemeDuSacADos {

    static int[] s, c, p, cp;
    static int n, m, cap;
    static Integer[][] dp;

    static int compute(int index, int capacity) {
        if (index > n + m - 1) {
            return 0;
        }
        if (dp[index][capacity] != null) {
            return dp[index][capacity];
        }
        int best = 0;
        if (index < n) {
            best = Math.max(best, compute(index + 1, capacity));
            if (capacity - c[index] >= 0) {
                best = Math.max(best, compute(index + 1, capacity - c[index]) + s[index]);
            }
        } else {
            best = Math.max(best, compute(index + 1, capacity));
            int curIndex = index - n;
            for (int i = 1; i <= Math.min(cp[curIndex], capacity); ++i) {
                best = Math.max(best, compute(index + 1, capacity - i) + i * p[curIndex]);
            }
        }
        return dp[index][capacity] = best;
    }

    public static void main(String[] argv) throws Exception {
        Scanner in = new Scanner(System.in);
        n = in.nextInt();
        m = in.nextInt();
        cap = in.nextInt();
        s = new int[n];
        c = new int[n];
        p = new int[m];
        cp = new int[m];
        dp = new Integer[n + m + 1][cap + 1];

        for (int i = 0; i < n; ++i) {
            s[i] = in.nextInt();
            c[i] = in.nextInt();
        }
        for (int i = 0; i < m; ++i) {
            p[i] = in.nextInt();
            cp[i] = in.nextInt();
        }
        System.out.println(compute(0, cap));
    }
}
