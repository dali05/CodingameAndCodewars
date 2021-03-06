package coderPower;
/**
 *
 On vous convoque dans le bureau du président, vous êtes désormais conseiller stratégique réseau de la République libre du Liberland.
 Vous ne vous attendiez pas à cela, mais vous n'êtes pas trop déçu, compte tenu des maigres efforts fournis en réalité.

 Vous vous retrouvez dans une salle de commandement, avec des tas de gens trop sérieux pour vous, vous flairez le piège.
 En effet, on vous charge de coordonner des milliers d'ingénieur réseau à travers le pays afin de mettre en place des liaisons 56k dans le pays.
 Les ingénieurs réseaux vont dans des endroits avec un câble de diagnostic RJ11, regardent si tout va bien et au moindre pépin, utilisent leur super câble de diagnostic.
 Or, le hic c'est le Liberland étant un nouveau pays, il y a beaucoup de gens motivés, mais pas beaucoup de ressources, vous avez des câbles de diagnostic avec broches plaqué or,
 mais vous n'en avez pas beaucoup !

 Ainsi, votre supérieur vous charge de jeter un coup d'œil au plan journalier d'opérations,
 une liste de requêtes que tous les ingénieurs réseaux soumettent pour leurs vadrouilles dans le pays dans le but de rendre le Liberland un peu plus moderne.
 Vous devez attribuer les numéros de câbles à chaque requête d'ingénieur réseau, s'il est possible de satisfaire toutes leurs demandes simultanément,
 sinon vous devez en informer votre supérieur aussitôt.

 Bien sûr, vous n'aimez pas faire les choses à la main, donc vous allez automatiser cette tâche.

 Données

 Entrée

 Ligne 1: deux entiers séparés par un espace, N le nombre de câbles RJ11 et M le nombre de requêtes de vos ingénieurs, N est compris entre 1 et 500 et M est compris entre 1 et 3N.
 Ligne 2 à M+1: deux entiers séparés par un espace représentant la date de début et de fin d'une requête d'usage d'un cable RJ11. Les entiers représentent
 le nombre de secondes écoulées depuis le 26 novembre 2019. Ces entiers sont compris entre 0 et 2500. Les transferts de cable sont instantanés :
 si un usage se termine à un temps T, le câble qu'il utilise peut être utilisé pour un autre usage commençant à l'instant T.

 Sortie

 Une série de M entiers compris entre 1 et N et séparés par des espaces indiquant le numéro câble attribué à chaque requête ou la chaine pas possible,
 si a un moment donné vous n'avez pas assez de câble pour satisfaire toutes les requêtes. Il y a bien sûr plusieurs solutions, vous pouvez choisir celle que vous voulez.

 Exemple

 Entrée

 6 7
 1 3
 1 4
 1 5
 1 6
 1 7
 2 9
 3 11

 Sortie

 1 2 3 4 5 6 1

 En effet vous pouvez attribuer vos 6 câbles aux 6 premières requêtes. Pour la 7ème requête commençant à l'instant 3,
 vous pouvez utiliser le cable 1 qui a été attribué à une requête se terminant à l'instant 3.

 Exemple

 Entrée

 6 7
 1 3
 1 4
 2 8
 1 5
 1 6
 1 7
 1 9

 Sortie

 pas possible

 En effet, après avoir assigné vos 6 câbles aux 6 requêtes commençant à l'instant 1. Vous n'avez plus de câble libre à l'instant 2 pour fournir la requête commençant à l'instant 2.
 */

import java.util.*;

public class RegisteredJack11 {

	public static void main( String[] argv ) throws Exception{
		Scanner in = new Scanner(System.in);
		/**
		 * Une PriorityQueue est une file de priorité sur laquelle on peut faire 3 operation :
		 * inserer un element, extraire l'element avec la plus grande clé et tester si la file est vide
		 */
		PriorityQueue<Integer> cables = new PriorityQueue<>();
		PriorityQueue<Req> requests = new PriorityQueue<>(
				Comparator.comparingInt(
						(Req rr) -> rr.from
				).thenComparingInt(rr -> rr.to));
		//nombre de cables RJ11
		int n = in.nextInt();
		//nombre de requetes des ingenieurs
		int m = in.nextInt();
		//tableau vide taille m = tableau des resultat dans lequel
		//on va ranger les numero de requetes (les index de la queue
		int[] ans = new int[m];
		//on ajoute les cables dans la queue cables
		for(int i = 1; i <= n; ++i) {
			cables.add(i);
		}
		//requete avec date debut et date fin de la requetes
		//lal iste est triee dans l'ordre par rapport a la date de debut puis la date de fin
		//d'ou le comparator
		for(int i = 0; i < m; ++i) {
			Req r = new Req(in.nextInt(), in.nextInt(), i);
			requests.add(r);
		}
		//historique des cables utilisés
		List<Req> seen = new ArrayList<>();
		boolean[] rem = new boolean[m];

		//je vais traiter toutes les requetes
		while(!requests.isEmpty()) {
			//prendre la premiere requete
			Req cur = requests.poll();
			if(cables.isEmpty()) {
				//si on a lus de cables disponibles, on verifie si des cables n'ont pas ete liberé entretemps
				for(Req req: seen) {
					//si l'heure de debut de la requete non satisfaites est plus grande que l'heure de fin de la premiere requete
					//de l'historique seen,on va liberer un cable
					if(req.to <= cur.from && !rem[req.index]) {
						cables.add(ans[req.index]);
						rem[req.index] = true;
					}
				}
			}
			if(cables.isEmpty()) {
				System.out.println("pas possible");
				return;
			} else {
				//si des cables sont disponibles, on en retire le premier de la Queue et on
				//ajoute dans le tableau l'indice de la requete qui a ete satisfaite
				ans[cur.index] = cables.poll();
				//et on ajoute le cable dans la liste des cables en cours d'utilisation
				seen.add(cur);
			}
		}
		for(int i = 0; i < m; ++i) {
			if(i > 0) System.out.print(" ");
			System.out.print(ans[i]);
		}
		System.out.println();
	}
	static class Req {
		int from, to, index;
		public Req(int from, int to, int index) {
			this.from = from;
			this.to = to;
			this.index = index;
		}
	}
}
