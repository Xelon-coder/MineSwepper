// Ceci est un squelette √† REMPLIR pour le projet INF1 sur le jeu de d√©mineur
//
// - N'oubliez pas de renseigner vos deux noms
// Pr√©nom Nom Groupe : √©l√®ve 1/2
// Pr√©nom Nom Groupe √©l√®ve 2/2
//
// - Pour chaque question, le squelette donne le nom de la fonction √† √©crire mais *pas* la signature
//   il faut remplir les types d'entr√©es et de sorties (indiqu√©s par ?) et remplir l'int√©rieur du code de chaque fonction.
//
// - L'unique fichier de code que vous soumettrez sera ce fichier Java, donc n'h√©sitez pas √† le commenter abondamment.
//   inutile d'exporter votre projet comme archive Zip et de rendre ce zip.
//   Optionnel : vous pouvez aussi joindre un document PDF donnant des explications suppl√©mentaires (si vous utilisez OpenOffice/LibreOffice/Word, exportez le document en PDF), avec √©ventuellement des captures d'√©cran montrant des √©tapes affich√©es dans la console
//
// - Regardez en ligne sur le Moodle pour le reste des consignes, et dans le fichier PDF du sujet du projet
//   https://foad.univ-rennes1.fr/mod/assign/view.php?id=534254
//
// - A rendre avant le vendredi 04 d√©cembre, maximum 23h59.
//
// - ATTENTION Le projet est assez long, ne commencez pas au dernier moment !
//
// - Enfin, n'h√©sitez pas √† contacter l'√©quipe p√©dagogique, en posant une question sur le forum du Moodle, si quelque chose n'est pas clair.
//

// Pour utiliser des scanners pour lire des entr√©es depuis le clavier
// utilis√©s en questions 4.d] pour la fonction jeu()
import java.util.Scanner;

// Pour la fonction entierAleatoire(a, b) que l'on vous donne ci-dessous
import java.util.concurrent.ThreadLocalRandom;

import javax.rmi.ssl.SslRMIClientSocketFactory;

// L'unique classe de votre projet
public class projet_demineur {

	// Donn√©, utile pour la question 1.b]
	public static int entierAleatoire(int a, int b){
		// Renvoie un entier al√©atoire uniforme entre a (inclus) et b (inclus).
		return ThreadLocalRandom.current().nextInt(a, b + 1);
	}

	// Exercice 1 : Initialisation des tableaux

	// Question 1.a] d√©clarez les variables globales T et Tadj ici
	static int[][] T; //Voici les variables globale
	static int[][] Tadj;
	
	static int[][] TabIA;

	// Question 1.b] Fonction init
	public static void init(int h, int l, int n) { // ATTENTION, vous devez modifier la signature de cette fonction
		T = new int[h][l];
		Tadj = new int[h][l];
		int coos[][] = new int [h][l];
		int x = 0;
		for (int y=0; y<T.length; y++) {
			for (int z=0; z<T[0].length; z++) {
				coos[y][z] = x;
				x++;
			}
		}
		for (int i=0; i<n; i++) {
			int tmp = entierAleatoire(0, (h*l)-1);
			for (int j=0; j<Tadj.length; j++) {
				for (int k=0; k<Tadj[j].length; k++) {
//					if (j == tmp/h && k == tmp%h) Tadj[j][k] = -1;
					if (coos[j][k] == tmp) Tadj[j][k] = -1;
				}
			}
		}
	}

	// Question 1.c] Fonction caseCorrecte
	public static boolean caseCorrecte(int i, int j) { // ATTENTION, vous devez modifier la signature de cette fonction)
		return (T.length*T[0].length > i*j);
	}

	// Question 1.d] Fonction calculerAdjacent
	public static void calculerAdjacent()
	{
		for (int i=0; i<Tadj.length; i++) {
			for (int j=0; j<Tadj[0].length; j++){
				if (Tadj[i][j] != -1) {
					int nb = 0;
					if (i!=0 && Tadj[i-1][j] == -1) nb++;
					if (i!=0 && j!=0 && Tadj[i-1][j-1] == -1) nb++;
					if (i!=0 && j!=Tadj[0].length-1 && Tadj[i-1][j+1] == -1) nb++;
					if (i!=Tadj.length-1 && j!=0 && Tadj[i+1][j-1] == -1) nb++;
					if (i!=Tadj.length-1 && Tadj[i+1][j] == -1) nb++; 
					if (i!=Tadj.length-1 && j!=Tadj[0].length-1 && Tadj[i+1][j+1] == -1) nb++;
					if (j!=0 && Tadj[i][j-1] == -1) nb++;
					if (j!=Tadj[0].length-1 && Tadj[i][j+1] == -1) nb++;
					Tadj[i][j] = nb;
				}
			}
		}
	}

	//
	// Exercice 2 : Affichage de la grille
	//

	// Question 2.a]
	public static void afficherGrille(boolean affMines) { // ATTENTION, vous devez modifier la signature de cette fonction
		int y = 0;
		System.out.print("  ");
		for (int i=0; i<T[0].length; i++) {
			if (i<26) System.out.print("|"+(char)('A'+i));
			else {
				System.out.print("|"+(char)('a'+y));
				y++;
			}
		}
		System.out.println("|");
		for (int i=0; i<T.length; i++) {
			if (i < 10) System.out.print("0");
			System.out.print(i);
			for (int j=0; j<T[0].length; j++) {
				if (Tadj[i][j] == -1 && affMines) System.out.print("|!");
				else if (T[i][j] == 2) System.out.print("|X");
				else if (T[i][j] == 1) System.out.print("|"+Tadj[i][j]);
				else System.out.print("| ");
			}
			System.out.println("|");
		}
		System.out.println();
		// Note : Dans un premier temps, consid√©rer que la grille contiendra au plus 52 colonnes
		// (une pour chaque lettre de l'alphabet en majuscule et minuscule) et au plus 100 lignes
		// (entiers de 0 √† 99).
	}


	//
	// Exercice 3 : R√©v√©ler une case
	//

	// Question 3.a]
	public static boolean caseAdjacenteZero(int i, int j) { // ATTENTION, vous devez modifier la signature de cette fonction
		return (Tadj[i+1][j] != -1 || Tadj[i-1][j] != -1 || Tadj[i][j+1] != -1 || Tadj[i][j-1] != -1);
	}

	// Question 3.b]
	public static void revelation(int i, int j) { // ATTENTION, vous devez modifier la signature de cette fonction
		
		
	}


	// Question 3.c] Optionnel
	public static void revelation2(int i, int j) { // ATTENTION, vous devez modifier la signature de cette fonction
		if (Tadj[i][j] != 0 && T[i][j] != 2) {
			T[i][j] = 1;
		}
		else if (Tadj[i][j] != -1 && Tadj[i][j] == 0){
			T[i][j] = 1;
			if (i<Tadj.length-1 && Tadj[i+1][j] != -1 && T[i+1][j] != 1) revelation2(i+1, j);
			if (i>0 && Tadj[i-1][j] != -1 && T[i-1][j] != 1) revelation2(i-1, j);
			if (j<Tadj[0].length-1 && Tadj[i][j+1] != -1 && T[i][j+1] != 1) revelation2(i, j+1);
			if (j>0 && Tadj[i][j-1] != -1 && T[i][j-1] != 1) revelation2(i, j-1);
			if (i<Tadj.length-1 && j<Tadj[0].length-1 && Tadj[i+1][j+1] != -1 && T[i+1][j+1] != 1) revelation2(i+1, j+1);
			if (i>0 && j>0 && Tadj[i-1][j-1] != -1 && T[i-1][j-1] != 1) revelation2(i-1, j-1);
			if (j>0 && i<Tadj.length-1 && Tadj[i+1][j-1] != -1 && T[i+1][j-1] != 1) revelation2(i+1, j-1);
			if (i>0 && j<Tadj[0].length-1 && Tadj[i-1][j+1] != -1 && T[i-1][j+1] != 1) revelation2(i-1, j+1);
		}
	}

	// Question 3.d]
	public static void actionDrapeau(int i, int j) { // ATTENTION, vous devez modifier la signature de cette fonction
		if (T[i][j] == 0) {
			T[i][j] = 2;
		}
		else if (T[i][j] == 2) {
			T[i][j] = 0;
		}
	}
	
	
	// Question 3.e]
	public static boolean revelerCase(int i, int j) { // ATTENTION, vous devez modifier la signature de cette fonction
		if (Tadj[i][j] == -1) return false;
		else {
			revelation2(i, j);
			return true;
		}
	}


	//
	// Exercice 4 : Boucle de jeu
	//

	// Question 4.a]
	public static boolean aGagne() {
		int nb = 0;
		for (int i=0; i<T.length; i++) {
			for (int j=0; j<T[0].length; j++){
				if (T[i][j] == 1) nb++;
			}
		}
		int n = 0;
		for (int i=0; i<Tadj.length; i++) {
			for (int j=0; j<Tadj[0].length; j++) {
				if (Tadj[i][j] == -1) n++;
			}
		}
		return ((T.length*T[0].length)-nb == n);
	}

	// Question 4.b]
	public static boolean verifierFormat(String s) { // ATTENTION, vous devez modifier la signature de cette fonction
		int taille = 0; 
		if (s.charAt(0) != 'r') {
			if (s.charAt(0) != 'd') {
				return false;
			}
		}
		if (s.length() == 4) taille = 3;
		else taille = 2;
		if (s.charAt(taille) < 'A' || s.charAt(taille) > 'G'+T[0].length) {
			return false;
		}
		if (s.charAt(1) < '0' || s.charAt(1) > '9') return false;
		if (s.length() == 4 && (s.charAt(2) < '0' || s.charAt(2) > '9')) return false;
		return true;
	}

	// Question 4.c]
	public static int[] conversionCoordonnees(String input) { // ATTENTION, vous devez modifier la signature de cette fonction
		int tab[] = new int[3];
		if (input.length() == 4) {
			tab[0] = (input.charAt(2)-48)+(input.charAt(1)-48)*(10);
			if (input.charAt(3)-65 < 25) tab[1] = input.charAt(3)-65;
			else tab[1] = input.charAt(3)-71;
			if (input.charAt(0) == 100) tab[2] = 0;
			else tab[2] = 1;
		}
		else {
			tab[0] = input.charAt(1)-48;
			if (input.charAt(2)-65 < 25) tab[1] = input.charAt(2)-65;
			else tab[1] = input.charAt(2)-71;
			if (input.charAt(0) == 100) tab[2] = 0;
			else tab[2] = 1;
		}
		return tab;
	}

	// Question 4.d]
	public static int jeu() {
		int l = 16;
		int h = 32;
		int n = 40; 
		boolean isGood = true;
//		Scanner sc = new Scanner(System.in);
//		System.out.println("Quelle largeur voulez-vous ?");
//		l = sc.nextInt();
//		while (l < 1 || l>52) {
//			System.out.println("Quelle largeur voulez-vous ? Et arrÍtez de troller cette fois ci");
//			l = sc.nextInt();
//		}
//		System.out.println("Quelle longueur voulez-vous?");
//		h = sc.nextInt();
//		while (h < 1 || h>100) {
//			System.out.println("Quelle longueur voulez-vous? Et arrÍtez de troller cette fois ci");
//			h = sc.nextInt();
//		}
//		System.out.println("Combien de mines voulez-vous?");
//		n = sc.nextInt();
//		while (n < 0 || n > h*l) {
//			System.out.println("Combien de mines voulez-vous? Et arrÍtez de troller cette fois ci");
//			n = sc.nextInt();
//		}
		init(h, l, n);
		TabIA = new int [h][l];
		calculerAdjacent();
		boolean Good = false;
		while (isGood) {
	//		afficherGrille(false);
	//		System.out.println("Choisissez une case ou  demander de l'aide");
	//		String s = sc.next();
	//		if (s.equals("aide")) {
	//			aide();
	//		}
	//		else {
	//			while(!verifierFormat(s)) {
	//				System.out.println("Choisissez une case correct");
	//				s = sc.next();
	//			}
	//			int tab[] = conversionCoordonnees(s);
	//			if (tab[2] == 0) {
	//				actionDrapeau(tab[0], tab[1]);
	//			}
	//			else{
				int tab[] = intelligenceArtificielle();
					revelerCase(tab[0], tab[1]);
					if(!revelerCase(tab[0], tab[1])) {
	//					afficherGrille(true);
						System.out.println("Perdu");
						isGood = false;
						return 0;
					}
					revelation2(tab[0], tab[1]);
	//		}
				if (aGagne()) {
	//				afficherGrille(false);
					System.out.println("GG ‡ toi tu viens de gagner");
					isGood = false;
					return 1;
				}
				Good = true;
		}
		return 0;
	//	sc.close();
	}

	// Question 4.e]
	// Votre *unique* m√©thode main
	public static void main(String[] args) {
		double k = 0;
		for (int i=0; i<1; i++) {
			if(intelligenceArtificielle2(8, 8, 10) == 1) k++;
		}
		System.out.println(k/10+" %");
	}
	
	public static int intelligenceArtificielle2(int h, int l, int n) {
		boolean isGood = true;
//		Scanner sc = new Scanner(System.in);
//		System.out.println("Quelle largeur voulez-vous ?");
//		l = sc.nextInt();
		while (l < 1 || l>52) {
//			System.out.println("Quelle largeur voulez-vous ? Et arrÍtez de troller cette fois ci");
//			l = sc.nextInt();
		}
//		System.out.println("Quelle longueur voulez-vous?");
//		h = sc.nextInt();
		while (h < 1 || h>100) {
//			System.out.println("Quelle longueur voulez-vous? Et arrÍtez de troller cette fois ci");
//			h = sc.nextInt();
		}
//		System.out.println("Combien de mines voulez-vous?");
//		n = sc.nextInt();
		TabIA = new int [h][l];
		for (int i=0; i<TabIA.length; i++) {
			for (int j=0; j<TabIA[i].length; j++) {
				TabIA[i][j] = 3;
			}
		}
		int tab[] = new int[3];
		while (n < 0 || n > h*l) {
//			System.out.println("Combien de mines voulez-vous? Et arrÍtez de troller cette fois ci");
//			n = sc.nextInt();
		}
		init(h, l, n);
		calculerAdjacent();
		boolean Good = true;
		while (isGood) {
			if (Good) {
				PasBombe();
				PoseDrapeau();
				PasBombe();
			}
			PoseDrapeau();
			for (int i=0; i<T.length; i++) {
				for (int j=0; j<T[i].length; j++) {
					System.out.print(TabIA[i][j]+" " );
				}
				System.out.println();
			}
			boolean Break = true;
			for (int i=0; i<T.length; i++) {
				for (int j=0; j<T[i].length; j++) {
					if (TabIA[i][j] == 1 && Break) {
						tab[0] = i;
						tab[1] = j;
						Break = false;
						break;
					}
					if(!Break) break;
				}
			}
			if (Break) {
				for (int i=0; i<T.length; i++) {
					for (int j=0; j<T[i].length; j++) {
						if (TabIA[i][j] == 3 && TabIA[i][j] != 0 && Break) {
							tab[0] = i;
							tab[1] = j;
							Break = false;
							break;
						}
					}
					
				} 
			}
			revelerCase(tab[0], tab[1]);
			for(int i =0; i<T.length; i++) {
				for (int j=0; j<T[i].length; j++) {
					if (T[i][j] == 1) {
						TabIA[i][j] = 0;
					}
				}
			}
			if(!revelerCase(tab[0], tab[1])) {
				afficherGrille(true);
				isGood = false;
				int k = 0;
				return k;
			}
			revelation2(tab[0], tab[1]);
			TabIA[tab[0]][tab[1]] = 0;
			if (aGagne()) {
				afficherGrille(false);

				System.out.println("GG ‡ toi tu viens de gagner");
				isGood = false;
				int k = 1;
				return k;
			}
			afficherGrille(false);
			PoseDrapeau();
			PasBombe();
			PoseDrapeau();
			Good = true;
		}
		int k = 1;
		return k;
	}


//
// Exercice 5 bonus challenge : Pour aller plus loin
public static void PasBombe() {
	for (int a=0; a<T.length; a++) {
		for (int b=0; b<T[a].length; b++) {
			int nb = 0;
			int Wich = 0;
			if (a > 0 && b > 0 && (TabIA[a-1][b-1] == 2)) {
				nb++;
			}
			if (b > 0 && TabIA[a][b-1] == 2) {
				nb++;
			}
			if (a < T.length-1 && b > 0 && TabIA[a+1][b-1] == 2) {
				nb++;
			}
			if (a > 0 && TabIA[a-1][b] == 2) {
				nb++;
			}
			if (a < T.length-1 && TabIA[a+1][b] == 2) {
				nb++;
			}
			if (a > 0 && b < T[a].length-1 && TabIA[a-1][b+1] == 2) {
				nb++;
			}
			if (b < T[a].length-1 && TabIA[a][b+1] == 2) {
				nb++;
			}
			if (a < T.length-1 && b < T[a].length-1 && TabIA[a+1][b+1] == 2) {
				nb++;
			}
			if ((a == 0 && b == 0) || (a == 0 && b == T[a].length-1) || (a == T.length-1 && b == 0) || (a == T.length-1 && b == T[a].length-1)) {
				Wich = 3;
			}
			else if (a == 0 || a == T.length || b == 0 || b == T[a].length) {
				Wich = 5;
			}
			else {
				Wich = 8;
			}
			if (nb == Tadj[a][b]) {     							// si TabIA = 1 alors aucun riqsue qu'il n'y ait une bombe
				if (a > 0 && b > 0 && T[a-1][b-1] == 0) {
					TabIA[a-1][b-1] = 1;
				} 
				if (b > 0 && T[a][b-1] == 0) {
					TabIA[a][b-1] = 1;
				}
				if (a < T.length-1 && b > 0 && T[a+1][b-1] == 0) {
					TabIA[a+1][b-1] = 1;
				}
				if (a > 0 && T[a-1][b] == 0) {
					TabIA[a-1][b] = 1;
				}
				if (a < T.length-1 && T[a+1][b] == 0) {
					TabIA[a+1][b] = 1;
				}
				if (a > 0 && b < T[a].length-1 && T[a-1][b+1] == 0) {
					TabIA[a-1][b+1] = 1;
				}
				if (b < T[a].length-1 && T[a][b+1] == 0) {
					TabIA[a][b+1] = 1;
				}
				if (a < T.length-1 && b < T[a].length-1 && T[a+1][b+1] == 0) {
					TabIA[a+1][b+1] = 1;
				}
			}
		}
	}
}

public static void PoseDrapeau() {
	for (int a=0; a<T.length; a++) {
		for (int b=0; b<T[a].length; b++) {
			int nb = 0;
			int Wich = 0;
			if (a > 0 && b > 0 && T[a-1][b-1] == 1) {
				nb++;
			}
			if (b > 0 && T[a][b-1] == 1) {
				nb++;
			}
			if (a < T.length-1 && b > 0 && T[a+1][b-1] == 1) {
				nb++;
			}
			if (a > 0 && T[a-1][b] == 1) {
				nb++;
			}
			if (a < T.length-1 && T[a+1][b] == 1) {
				nb++;
			}
			if (a > 0 && b < T[a].length-1 && T[a-1][b+1] == 1) {
				nb++;
			}
			if (b < T[a].length-1 && T[a][b+1] == 1) {
				nb++;
			}
			if (a < T.length-1 && b < T[a].length-1 && T[a+1][b+1] == 1) {
				nb++;
			}
			if ((a == 0 && b == 0) || (a == 0 && b == T[a].length-1) || (a == T.length-1 && b == 0) || (a == T.length-1 && b == T[a].length-1)) {
				Wich = 3;
			}
			else if (a == 0 || a == T.length || b == 0 || b == T[a].length) {
				Wich = 5;
			}
			else {
				Wich = 8;
			}
			if ((Wich - nb) == Tadj[a][b]) {
				if (a > 0 && b > 0 && T[a-1][b-1] == 0) {
					TabIA[a-1][b-1] = 2;
				}
				if (b > 0 && T[a][b-1] == 0) {
					TabIA[a][b-1] = 2;
				}
				if (a < T.length-1 && b > 0 && T[a+1][b-1] == 0) {
					TabIA[a+1][b-1] = 2;
				}
				if (a > 0 && T[a-1][b] == 0) {
					TabIA[a-1][b] = 2;
				}
				if (a < T.length-1 && T[a+1][b] == 0) {
					TabIA[a+1][b] = 2;
				}
				if (a > 0 && b < T[a].length-1 && T[a-1][b+1] == 0) {
					TabIA[a-1][b+1] = 2;
				}
				if (b < T[a].length-1 && T[a][b+1] == 0) {
					TabIA[a][b+1] = 2;
				}
				if (a < T.length-1 && b < T[a].length-1 && T[a+1][b+1] == 0) {
					TabIA[a+1][b+1] = 2;
				}
			}
		}
	}
}
	
	public static int[] intelligenceArtificielle() {
        //Estimation des positions de mines/de cases sÈcurisÈes
    //0 quand on ne sait pas, 1 quand il y a une mine et 2 quand il n'y a pas de mine
	init(8, 9, 10);
        int[][] estimations = new int[T.length][T[0].length];
    for(int i=0; i<T.length; i++) {
           for(int j=0; j<T[0].length; j++) {
            if(T[i][j]==1) {
                int[][] nonReveles = adjacentNonRevele(i,j);
                //Si il y a forcÈment une mine on la marque
                if((nonReveles.length<=Tadj[i][j])&&((Tadj[i][j]-adjacentDrapeaux(i,j))>0)&&(Tadj[i][j]-adjacentDrapeaux(i,j)==nonReveles.length)) {
                    for(int x=0; x<nonReveles.length; x++) {
                        if(estimations[nonReveles[x][0]][nonReveles[x][1]]==0) {
                            estimations[nonReveles[x][0]][nonReveles[x][1]] = 1;
                        }
                    }
                //si il ne peut pas y avoir de mine on marque cette case comme case ‡ rÈvÈler
                }else if(Tadj[i][j]==adjacentDrapeaux(i,j)) {
                    for(int x=0; x<nonReveles.length; x++) {
                        estimations[nonReveles[x][0]][nonReveles[x][1]] = 2;
                    }
                }
            }
        }
    }
    //On commence par rÈvÈler toutes les cases possibles
    for(int i=0; i<estimations.length; i++) {
        for(int j=0; j<estimations[0].length; j++) {
            if(estimations[i][j]==2) {
                              return new int[] {i,j,1};
            }
        }
    }
        //Si il n'y a pas/plus de cases ‡ rÈvÈler on place tous les drapeaux
        for(int i=0; i<estimations.length; i++) {
        for(int j=0; j<estimations[0].length; j++) {
            if(estimations[i][j]==1) {
                              return new int[] {i,j,0};
            }
        }
    }
        //Si il n'y a pas de cases ‡ rÈvÈler ou de drapeaux ‡ placer on rÈvËle une case au hasard
    return new int[] {entierAleatoire(0,estimations.length-1), entierAleatoire(0,estimations[0].length-1),1};
}

//Renvoie une liste des positions des cases adjacentes non rÈvÈlÈes
public static int[][] adjacentNonRevele(int i, int j) {
	int[][] a = new int[8][2];
	int b = 0;
	if(caseCorrecte(i-1,j-1)&&T[i-1][j-1]==0) {a[b][0]=i-1; a[b][1] = j-1; b++;}
	if(caseCorrecte(i-1,j)&&T[i-1][j]==0) {a[b][0]=i-1; a[b][1] = j; b++;}
	if(caseCorrecte(i-1,j+1)&&T[i-1][j+1]==0) {a[b][0]=i-1; a[b][1] = j+1; b++;}
	if(caseCorrecte(i,j+1)&&T[i][j+1]==0) {a[b][0]=i; a[b][1] = j+1; b++;}
	if(caseCorrecte(i+1,j+1)&&T[i+1][j+1]==0) {a[b][0]=i+1; a[b][1] = j+1; b++;}
	if(caseCorrecte(i+1,j)&&T[i+1][j]==0) {a[b][0]=i+1; a[b][1] = j; b++;}
	if(caseCorrecte(i+1,j-1)&&T[i+1][j-1]==0) {a[b][0]=i+1; a[b][1] = j-1; b++;}
	if(caseCorrecte(i,j-1)&&T[i][j-1]==0){a[b][0]=i; a[b][1] = j-1; b++;}
	int[][] c = new int[b][2];
	for(int x=0; x<b; x++) {
		c[x][0] = a[x][0];
		c[x][1] = a[x][1];
	}
	return c;
}

//Renvoie le nombre de drapeaux adjacents
public static int adjacentDrapeaux(int i, int j) {
	int a = 0;
	if(caseCorrecte(i-1,j-1)&&T[i-1][j-1]==2) a++;
	if(caseCorrecte(i-1,j)&&T[i-1][j]==2) a++;
	if(caseCorrecte(i-1,j+1)&&T[i-1][j+1]==2) a++;
	if(caseCorrecte(i,j+1)&&T[i][j+1]==2) a++;
	if(caseCorrecte(i+1,j+1)&&T[i+1][j+1]==2) a++;
	if(caseCorrecte(i+1,j)&&T[i+1][j]==2) a++;
	if(caseCorrecte(i+1,j-1)&&T[i+1][j-1]==2) a++;
	if(caseCorrecte(i,j-1)&&T[i][j-1]==2) a++;
	return a;
}

	
	// Question 5.c] Optionnel
	public static void statistiquesVictoiresIA() {
		double k=0;
		for (int i=0; i<1000000; i++) {
			if (jeu() == 1) k++;
		}
		System.out.println(k/1000);
	}
}
