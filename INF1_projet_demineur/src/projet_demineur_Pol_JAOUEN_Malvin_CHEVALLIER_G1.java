//Malvin CHEVALLIER et Pol JAOUEN, Groupe 1

import java.util.Scanner;

// Pour la fonction entierAleatoire(a, b)
import java.util.concurrent.ThreadLocalRandom;

public class projet_demineur_Pol_JAOUEN_Malvin_CHEVALLIER_G1 {

	// Renvoie un entier aléatoire uniforme entre a (inclues) et b (inclus)
	public static int entierAleatoire(int a, int b) {
		return ThreadLocalRandom.current().nextInt(a, b + 1);
	}

	static int[][] T; // Variable globale contenant les états des cases (0: non révélée, 1: révélée et
						// 2: drapeau)
	static int[][] Tadj; // Variable globale contenant le nombre de mines adjacent à chaque case et -1 si
							// la case est une mine

	// init prends en entrée des entiers h (hauteur) l (largeur) et n (nombre de
	// bombes) et initialise les tableaux T et Tadj
	// et place les bombes aléatoirement dans Tadj
	public static void init(int h, int l, int n) {
		T = new int[h][l];
		Tadj = new int[h][l];
		// t contient le total de nombres placées
		int t = 0;
		// Tant qu'on a pas placé le nombre de bombes requis
		while (t < n) {
			// On choisis des coordonnées aléatoires dans les tableaux
			int y = entierAleatoire(0, h - 1);
			int x = entierAleatoire(0, l - 1);
			// Si il n'y a pas déjà une bombe à cet emplacement on ajoute une bombe et on
			// ajoute 1 a t
			if (Tadj[y][x] == 0) {
				Tadj[y][x] = -1;
				t++;
			}
		}
	}

	// caseCorrecte prends en entrée deux entiers i et j correspondant à des
	// coordonnées et renvoie "true" si ces coordonnées
	// sont valides (cad qu'elles ne sont pas en dehors des tableaux) et false sinon
	public static boolean caseCorrecte(int i, int j) {
		return ((i >= 0) && (i < T.length) && (j >= 0) && (j < T[0].length));
	}

	// calculerAdjacent ne prends rien en entrée et modifie le tableau Tadj en
	// mettant le nombre de mines adjacentes à chaque
	// case
	public static void calculerAdjacent() {
		// On parcours toutes les cases de la grille
		for (int i = 0; i < T.length; i++) {
			for (int j = 0; j < T[0].length; j++) {
				// Si la case actuelle n'est pas une mine
				if (Tadj[i][j] != -1) {
					// On vérifie chaque case autour de la case actuelle et on incrémente
					// nombreMines de 1 à chaque fois qu'une
					// case adjacente contient une mine (en vérifiant à chaque fois que cette case
					// ne sort pas du tableau)
					int nombreMines = 0;
					if (caseCorrecte(i - 1, j - 1) && Tadj[i - 1][j - 1] == -1)
						nombreMines++;
					if (caseCorrecte(i - 1, j) && Tadj[i - 1][j] == -1)
						nombreMines++;
					if (caseCorrecte(i - 1, j + 1) && Tadj[i - 1][j + 1] == -1)
						nombreMines++;
					if (caseCorrecte(i, j + 1) && Tadj[i][j + 1] == -1)
						nombreMines++;
					if (caseCorrecte(i + 1, j + 1) && Tadj[i + 1][j + 1] == -1)
						nombreMines++;
					if (caseCorrecte(i + 1, j) && Tadj[i + 1][j] == -1)
						nombreMines++;
					if (caseCorrecte(i + 1, j - 1) && Tadj[i + 1][j - 1] == -1)
						nombreMines++;
					if (caseCorrecte(i, j - 1) && Tadj[i][j - 1] == -1)
						nombreMines++;
					// On change la valeur de la case actuelle par le nombre de mines adjacentes
					Tadj[i][j] = nombreMines;
				}
			}
		}
	}

	// afficherLaGrille va afficher la grille dans la console, en marquant les cases
	// contenant des bombes avec un "!" si
	// affMines est à true
	public static void afficherGrille(boolean affMines) {
		// On commence par afficher les lettres des colonnes
		// On décale une première fois pour laisser la place pour la colonne des nombres
		System.out.print("  |");
		// pour chaque colonne de la grille
		for (int i = 0; i < T[0].length; i++) {
			// si l'indice de la colonne est inférieur à 26, alors on prends des lettres
			// majuscules
			if (i < 26) {
				System.out.print((char) ('A' + i));
				// Sinon on prends des lettres minuscules
			} else {
				System.out.print((char) ('a' + (i - 26)));
			}
			System.out.print("|");
		}
		// On affiche ensuite le reste de la grille
		for (int i = 0; i < T.length; i++) {
			// On fait un retour à la ligne
			System.out.println();
			// Si l'indice est inférieur à 10, on ajoute un 0 devant le chiffre pour avoir
			// deux caractères
			if (i < 10) {
				System.out.print("0");
			}
			// On affiche ensuite chaque case de cette ligne, avec un espace si elle n'a pas
			// été découverte, le nombre de mines
			// adjacentes si elle a été découverte, un X si il y a un drapeau et un "!" si
			// affMines est à true et que cette
			// case contient une mine
			System.out.print(i + "|");
			for (int j = 0; j < T[0].length; j++) {
				if (affMines && Tadj[i][j] == -1) {
					System.out.print("!");
				} else if (T[i][j] == 0) {
					System.out.print(" ");
				} else if (T[i][j] == 1) {
					System.out.print(Tadj[i][j]);
				} else if (T[i][j] == 2) {
					System.out.print("X");
				}
				System.out.print("|");
			}
		}
		// retour à la ligne final pour afficher les potentiels autres textes en dessous
		// de la grille
		System.out.println();
	}

	// caseAdjacenteZero renvoie true si au moins une des cases adjacente à la case
	// de coordonnées i et j n'a aucune mine
	// adjacente et qu'elle est révélée, false sinon
	public static boolean caseAdjacenteZero(int i, int j) {
		// On vérifie toutes les cases adjacentes et on renvoie true dès que l'une
		// d'elle satisfait la condition
		// on vérifie aussi avant que cette case adjacente soit bien dans la grille
		if (caseCorrecte(i - 1, j - 1) && Tadj[i - 1][j - 1] == 0 && T[i - 1][j - 1] == 1)
			return true;
		if (caseCorrecte(i - 1, j) && Tadj[i - 1][j] == 0 && T[i - 1][j] == 1)
			return true;
		if (caseCorrecte(i - 1, j + 1) && Tadj[i - 1][j + 1] == 0 && T[i - 1][j + 1] == 1)
			return true;
		if (caseCorrecte(i, j + 1) && Tadj[i][j + 1] == 0 && T[i][j + 1] == 1)
			return true;
		if (caseCorrecte(i + 1, j + 1) && Tadj[i + 1][j + 1] == 0 && T[i + 1][j + 1] == 1)
			return true;
		if (caseCorrecte(i + 1, j) && Tadj[i + 1][j] == 0 && T[i + 1][j] == 1)
			return true;
		if (caseCorrecte(i + 1, j - 1) && Tadj[i + 1][j - 1] == 0 && T[i + 1][j - 1] == 1)
			return true;
		if (caseCorrecte(i, j - 1) && Tadj[i][j - 1] == 0 && T[i][j - 1] == 1)
			return true;
		// Si toutes les cases adjacentes ont au moins une mine adjacente ou qu'elles ne
		// sont pas révélées alors on renvoie false
		return false;
	}

	// revelation prends en entrée deux entier i et j et va chercher à révéler toute
	// une zone de cases sans mines adjacentes
	// dans laquelle la case de coordonnées i et j est comprise
	public static void revelation(int i, int j) {
		// On révèle la case de coordonnées (i,j)
		T[i][j] = 1;
		if (Tadj[i][j] == 0) {
			// On a révélé une nouvelle case dès le départ
			boolean nouvelleCase = true;
			// Tant qu'on a révélé une nouvelle case
			while (nouvelleCase) {
				// On mets nouvelle case à false
				nouvelleCase = false;
				// On parcours le tableau et si on trouve une case n'ayant pas de bombe et
				// satisfaisant caseAdjacenteZero,
				// on révèle cette case et on met nouvelle case à true (pour vérifier si cette
				// révélation ne permet pas de
				// révéler de nouvelles cases)
				for (int y = 0; y < T.length; y++) {
					for (int x = 0; x < T[0].length; x++) {
						if (caseAdjacenteZero(y, x) && T[y][x] == 0) {
							T[y][x] = 1;
							nouvelleCase = true;
						}
					}
				}
			}
		}
	}

	// revelation2 donne le même résultat que revelation, mais de manière plus
	// efficace: plutôt que de parcourir la grille entière
	// plusieurs fois, revelation2 va fonctionner de manière récursive, ne
	// parcourant que les cases nécessaires
	public static void revelation2(int i, int j) {
		// On révèle la case aux coordonnées (i,j)
		T[i][j] = 1;
		// Puis pour chaque case adjacente à cette case, on vérifie si elle n'est pas
		// déjà révélée et si elle satisfait
		// caseAdjacenteZero. Si oui, alors on appelle de nouvea revelation2 sur cette
		// case adjacente
		if (caseCorrecte(i - 1, j - 1) && T[i - 1][j - 1] == 0 && caseAdjacenteZero(i - 1, j - 1))
			revelation2(i - 1, j - 1);
		if (caseCorrecte(i - 1, j) && T[i - 1][j] == 0 && caseAdjacenteZero(i - 1, j))
			revelation2(i - 1, j);
		if (caseCorrecte(i - 1, j + 1) && T[i - 1][j + 1] == 0 && caseAdjacenteZero(i - 1, j + 1))
			revelation2(i - 1, j + 1);
		if (caseCorrecte(i, j + 1) && T[i][j + 1] == 0 && caseAdjacenteZero(i, j + 1))
			revelation2(i, j + 1);
		if (caseCorrecte(i + 1, j + 1) && T[i + 1][j + 1] == 0 && caseAdjacenteZero(i + 1, j + 1))
			revelation2(i + 1, j + 1);
		if (caseCorrecte(i + 1, j) && T[i + 1][j] == 0 && caseAdjacenteZero(i + 1, j))
			revelation2(i + 1, j);
		if (caseCorrecte(i + 1, j - 1) && T[i + 1][j - 1] == 0 && caseAdjacenteZero(i + 1, j - 1))
			revelation2(i + 1, j - 1);
		if (caseCorrecte(i, j - 1) && T[i][j - 1] == 0 && caseAdjacenteZero(i, j - 1))
			revelation2(i, j - 1);
	}

	// actionDrapeau prends en entrée deux entiers et place un drapeau sur la case
	// de coordonnées (i,j) si celles ci n'est pas
	// déjà révélée, et si elle ne contient pas déjà un drapeau. Si la case contient
	// déjà un drapeau, elle retire le drapeau
	public static void actionDrapeau(int i, int j) {
		if (T[i][j] == 1)
			return;
		else if (T[i][j] == 0)
			T[i][j] = 2;
		else if (T[i][j] == 2)
			T[i][j] = 0;
	}

	// revelerCase prends en entrée deux entier i et j et renvoie false si la case
	// aux coordonnées (i,j) contient une bombe,
	// et appelle révèle cette case avec revelation2 et renvoie true sinon
	public static boolean revelerCase(int i, int j) {
		if (Tadj[i][j] == -1) {
			return false;
		} else {
			revelation2(i, j);
			return true;
		}

	}

	// aGagne parcours toute la grille et renvoie false dès qu'elle trouve une case
	// qui n'est pas une bombe et qui n'est pas
	// révélée, et renvoie true sinon. Elle renvoie donc true seulement si le joueur
	// a révélé toutes les cases qui ne sont
	// pas des bombes
	public static boolean aGagne() {
		for (int i = 0; i < T.length; i++) {
			for (int j = 0; j < T[0].length; j++) {
				if (Tadj[i][j] != -1 && T[i][j] != 1) {
					return false;
				}
			}
		}
		return true;
	}

	// verifierFormat prends en entrée une chaîne de caractères et renvoie false si
	// elle n'est pas sous la forme 'r' ou 'd'
	// deux chiffres et une lettre, true sinon
	public static boolean verifierFormat(String entree) {
		if (entree.charAt(0) != 'd' && entree.charAt(0) != 'r')
			return false;
		if (!(entree.charAt(1) <= '9' && entree.charAt(1) >= '0' && entree.charAt(2) <= '9' && entree.charAt(2) >= '0'))
			return false;
		if (!((entree.charAt(3) >= 'a' && entree.charAt(3) <= 'z')
				|| (entree.charAt(3) >= 'A' && entree.charAt(3) <= 'Z')))
			return false;
		return true;
	}

	// conversionCoordonnees prends en entrée une chaîne de caractères et convertit
	// cette chaîne en un tableau d'entiers à
	// 3 entrées contenant les coordonnées en 0 et 1 et si le joueur demande de
	// révéler une case ou de placer un drapeau en 2
	public static int[] conversionCoordonnees(String input) {
		int[] coordonnees = new int[3];
		// On recupere le nombre dans la chaîne de caractère et on le convertit en
		// entier
		coordonnees[0] = Integer.parseInt(input.substring(1, 3));
		// On convertit la lettre en entier
		if (input.charAt(3) <= 'Z') {
			coordonnees[1] = input.charAt(3) - 'A';
		} else {
			coordonnees[1] = input.charAt(3) - 'a' + 26;
		}
		// Si le joueur veut révéler une case, coordonnees[2] est mis à 1, et si il veut
		// placer à 0, coordonnees[2] reste a 0
		if (input.charAt(0) == 'r')
			coordonnees[2] = 1;
		return coordonnees;
	}

	// La fonction jeu qui contient la boucle permettant de jouer
	public static void jeu() {
		// On initialise le scanner pour récupérer les entrées du joueur
		Scanner sc = new Scanner(System.in);
		// Boucle du jeu
		while (true) {
			// On affiche la grille à chaque début de tour
			afficherGrille(false);
			// On récupère les coordonnées
			int[] coordConv = new int[3];
			// Tant que les coordonnées entrées par le joueur sont incorrectes:
			while (true) {
				// On récupère l'entrée du joueur
				System.out.print("Entrez les coordonnées ou tapez \"aide\": ");
				String coordonnees = sc.next();
				// Si le joueur demande de l'aide, on appelle la fonction aide
				if (coordonnees.equals("aide")) {
					aide();
					continue;
				}
				// Tant que le texte entré par le joueur ne correspond pas au format des
				// coordonnees on
				// redemande au joueur de les entrer
				while (!verifierFormat(coordonnees)) {
					System.out.print("Format incorrect. Entrez les coordonnées: ");
					coordonnees = sc.next();
				}
				// On convertit les coordonnees
				coordConv = conversionCoordonnees(coordonnees);
				// Si les coordonnes entrées sont en dehors de la grille de jeu on le signale au
				// joueur et on lui redemande
				// les coordonnées en répétant la boucle
				if (!caseCorrecte(coordConv[0], coordConv[1])) {
					System.out.println("Coordonnées incorrectes.");
				} else {
					// Sinon on sort de la boucle
					break;
				}
			}
			// Si le joueur veut révéler une case
			if (coordConv[2] == 1) {
				// Si le joueur tombe sur une mine (revelerCase renvoie false) alors on le dit
				// au joueur, on affiche la
				// grille avec les bombes et on sort de la boucle de jeu
				if (!revelerCase(coordConv[0], coordConv[1])) {
					System.out.println("Perdu...");
					afficherGrille(true);
					break;
				}
				// Si le joueur gagne, alors on le lui signale et on sort de la boucle de jeu
				if (aGagne()) {
					System.out.println("Gagné!");
					break;
				}
				// Si le joueur veut placer un drapeau, on place le drapeau
			} else {
				actionDrapeau(coordConv[0], coordConv[1]);
			}
		}
		// Demande au joueur si il souhaite rejouer
		System.out.print("Rejouer? (0: oui) ");
		int rejouer = sc.nextInt();
		if (rejouer == 0) {
			System.out.print("Hauteur de la grille: ");
			int hauteur = sc.nextInt();
			while (hauteur > 100 || hauteur < 1) {
				System.out.println("La hauteur de la grille doit être comprise entre 1 et 100");
				System.out.print("Hauteur de la grille: ");
				hauteur = sc.nextInt();
			}
			System.out.print("Largeur de la grille: ");
			int largeur = sc.nextInt();
			while (largeur > 52 || largeur < 1) {
				System.out.println("La largeur de la grille doit être comprise entre 1 et 100");
				System.out.print("Largeur de la grille: ");
				largeur = sc.nextInt();
			}
			System.out.print("Nombre de mines: ");
			int mines = sc.nextInt();
			while (mines < 1 || mines > ((hauteur * largeur) / 2)) {
				System.out.println("Le nombre de mines doit être compris entre 1 et " + ((hauteur * largeur) / 2));
				System.out.print("Nombre de mines: ");
				mines = sc.nextInt();
			}
			init(hauteur, largeur, mines);
			calculerAdjacent();
			jeu();
		}
	}

	public static void main(String[] args) {
		// Initialisation du scanner
		Scanner sc = new Scanner(System.in);

		// Demande au joueur si il veut jouer ou tester l'IA
		System.out.print("Jouer (0) ou tester l'IA (1)? ");
		int mode = sc.nextInt();
		while (mode != 0 && mode != 1) {
			System.out.println("Veuillez taper 0 ou 1");
			mode = sc.nextInt();
		}

		// Demande au joueur les paramètres de la grille
		System.out.print("Hauteur de la grille: ");
		int hauteur = sc.nextInt();
		while (hauteur > 100 || hauteur < 1) {
			System.out.println("La hauteur de la grille doit être comprise entre 1 et 100");
			System.out.print("Hauteur de la grille: ");
			hauteur = sc.nextInt();
		}
		System.out.print("Largeur de la grille: ");
		int largeur = sc.nextInt();
		while (largeur > 52 || largeur < 1) {
			System.out.println("La largeur de la grille doit être comprise entre 1 et 100");
			System.out.print("Largeur de la grille: ");
			largeur = sc.nextInt();
		}
		System.out.print("Nombre de mines: ");
		int mines = sc.nextInt();
		while (mines < 1 || mines > ((hauteur * largeur) / 2)) {
			System.out.println("Le nombre de mines doit être compris entre 1 et " + ((hauteur * largeur) / 2));
			System.out.print("Nombre de mines: ");
			mines = sc.nextInt();
		}
		int grilles = 1;
		// Si le joueur veut tester l'IA, demande sur combien de grilles
		if (mode == 1) {
			System.out.print("Nombre de grilles à générer: ");
			grilles = sc.nextInt();
		}

		// Si le joueur veut jouer normalement, démarre la partie
		if (mode == 0) {
			init(hauteur, largeur, mines);
			calculerAdjacent();
			jeu();
			// Pour tester l'IA
		} else {
			// Mets le nombre de victoires à 0
			int victoires = 0;
			// Créée autant de grilles que le joueur en a demandé
			for (int i = 0; i < grilles; i++) {
				// Initialise la grille
				init(hauteur, largeur, mines);
				calculerAdjacent();
				while (true) {
					// On récupère le coup de l'IA
					int[] ia = intelligenceArtificielle();
					if (ia[2] == 1) {
						// Si l'IA tombe sur une bombe alors on finis la partie
						if (!revelerCase(ia[0], ia[1])) {
							break;
						}
						// Si l'IA gagne la partie alors on augmente le nombre de victoires de 1
						if (aGagne()) {
							victoires++;
							break;
						}
						// Si l'IA veut placer un drapeau alors on place le drapeau
					} else {
						actionDrapeau(ia[0], ia[1]);
					}
				}
			}
			// On affiche le score final de l'IA
			System.out.println("Sur une grille de " + hauteur + "x" + largeur + " avec " + mines + " mines");
			System.out.println("L'IA a gagné " + victoires + " parties sur " + grilles + " ("
					+ (victoires * 100 / grilles) + "%)");
		}

	}

	// Fonctions pour l'IA (ex5)

	// Renvoie un tableau contenant les positions des cases adjacentes non révélées
	public static int[][] adjacentNonRevele(int i, int j) {
		int[][] a = new int[8][2];
		int b = 0;
		if (caseCorrecte(i - 1, j - 1) && T[i - 1][j - 1] == 0) {
			a[b][0] = i - 1;
			a[b][1] = j - 1;
			b++;
		}
		if (caseCorrecte(i - 1, j) && T[i - 1][j] == 0) {
			a[b][0] = i - 1;
			a[b][1] = j;
			b++;
		}
		if (caseCorrecte(i - 1, j + 1) && T[i - 1][j + 1] == 0) {
			a[b][0] = i - 1;
			a[b][1] = j + 1;
			b++;
		}
		if (caseCorrecte(i, j + 1) && T[i][j + 1] == 0) {
			a[b][0] = i;
			a[b][1] = j + 1;
			b++;
		}
		if (caseCorrecte(i + 1, j + 1) && T[i + 1][j + 1] == 0) {
			a[b][0] = i + 1;
			a[b][1] = j + 1;
			b++;
		}
		if (caseCorrecte(i + 1, j) && T[i + 1][j] == 0) {
			a[b][0] = i + 1;
			a[b][1] = j;
			b++;
		}
		if (caseCorrecte(i + 1, j - 1) && T[i + 1][j - 1] == 0) {
			a[b][0] = i + 1;
			a[b][1] = j - 1;
			b++;
		}
		if (caseCorrecte(i, j - 1) && T[i][j - 1] == 0) {
			a[b][0] = i;
			a[b][1] = j - 1;
			b++;
		}
		int[][] c = new int[b][2];
		for (int x = 0; x < b; x++) {
			c[x][0] = a[x][0];
			c[x][1] = a[x][1];
		}
		return c;
	}

	// Renvoie le nombre de drapeaux adjacents
	public static int adjacentDrapeaux(int i, int j) {
		int a = 0;
		if (caseCorrecte(i - 1, j - 1) && T[i - 1][j - 1] == 2)
			a++;
		if (caseCorrecte(i - 1, j) && T[i - 1][j] == 2)
			a++;
		if (caseCorrecte(i - 1, j + 1) && T[i - 1][j + 1] == 2)
			a++;
		if (caseCorrecte(i, j + 1) && T[i][j + 1] == 2)
			a++;
		if (caseCorrecte(i + 1, j + 1) && T[i + 1][j + 1] == 2)
			a++;
		if (caseCorrecte(i + 1, j) && T[i + 1][j] == 2)
			a++;
		if (caseCorrecte(i + 1, j - 1) && T[i + 1][j - 1] == 2)
			a++;
		if (caseCorrecte(i, j - 1) && T[i][j - 1] == 2)
			a++;
		return a;
	}

	// La fonction aide affiche les cases où il y a potentiellement une mine et
	// celles où il ne peut pas y en avoir, en se basant sur les drapeaux et les
	// cases révélées
	public static void aide() {
		int[][] estimations = new int[T.length][T[0].length]; // 0 quand on ne sait pas, 1 quand il y a une mine et 2
																// quand il n'y a pas de mine
		// Pour toutes les cases de la grille
		for (int i = 0; i < T.length; i++) {
			for (int j = 0; j < T[0].length; j++) {
				// si cette case est révélée
				if (T[i][j] == 1) {
					// On récupère les cases adjacentes qui ne sont pas révélées
					int[][] nonReveles = adjacentNonRevele(i, j);
					// Si il y a moins ou autant de cases non révélées que de mines adjacentes
					// restantes (nombre de mines adjacentes moins nombre de drapeaux adjacents)
					if ((nonReveles.length <= Tadj[i][j] - adjacentDrapeaux(i, j))) {
						// Alors toutes les cases non révéles restantes sont forcément des mines
						for (int x = 0; x < nonReveles.length; x++) {
							// Evite de modifier une case qui aurait déjà été marquée comme sûre
							if (estimations[nonReveles[x][0]][nonReveles[x][1]] == 0) {
								// Marque la case comme contenant une mine
								estimations[nonReveles[x][0]][nonReveles[x][1]] = 1;
							}
						}
						// Si il y a autant de drapeaux adjacents que de mines adjacents, alors les
						// cases non revelees restantes ne sont pas des mines
					} else if (Tadj[i][j] == adjacentDrapeaux(i, j)) {
						for (int x = 0; x < nonReveles.length; x++) {
							// Marque la case comme ne contenant pas de mine
							estimations[nonReveles[x][0]][nonReveles[x][1]] = 2;
						}
					}
				}
			}
		}

		// Donne les informations au joueur
		for (int i = 0; i < estimations.length; i++) {
			for (int j = 0; j < estimations[0].length; j++) {
				if (estimations[i][j] == 1) {
					System.out.print("Potentielle mine en ");
					if (i < 10) {
						System.out.print("0");
					}
					System.out.print(i);
					if (j < 26) {
						System.out.print((char) ('A' + j));
					} else {
						System.out.println((char) ('a' + (j - 26)));
					}
					System.out.println();
				} else if (estimations[i][j] == 2) {
					System.out.print("Pas de mine en ");
					if (i < 10) {
						System.out.print("0");
					}
					System.out.print(i);
					if (j < 26) {
						System.out.print((char) ('A' + j));
					} else {
						System.out.println((char) ('a' + (j - 26)));
					}
					System.out.println();
				}
			}
		}
	}

	// Renvoie la probabilité qu'une case de coordonnées (i,j) contienne une mine
	public static float probaCase(int i, int j) {
		// Au départ la proba qu'il y ait une mine est de 0
		float p = 0;
		// Si la case est révélée
		if (T[i][j] == 1) {
			// Si il y a au moins une case adjacente non révélée
			if (adjacentNonRevele(i, j).length != 0) {
				// Calcul de la probabilité qu'il y ait une mine sur les cases adjacentes non
				// revelees (nombre de mines restantes/nombre de cases non révélées)
				p = (((Tadj[i][j] - adjacentDrapeaux(i, j))) / ((float) adjacentNonRevele(i, j).length));
			}
		}
		return p;
	}

	// Ajoute les probabilités de toutes les cases adjacentes et multiplie le total
	// par 1000 pour obtenir un score final de la case, qui sera utilisé par l'IA
	public static int calculScoreCase(int i, int j) {
		float proba = 0;
		if (caseCorrecte(i - 1, j - 1) && T[i - 1][j - 1] == 1)
			proba = (probaCase(i - 1, j - 1) + proba - (probaCase(i - 1, j - 1) * proba));
		if (caseCorrecte(i - 1, j) && T[i - 1][j] == 2)
			proba = (probaCase(i - 1, j) + proba - (probaCase(i - 1, j) * proba));
		if (caseCorrecte(i - 1, j + 1) && T[i - 1][j + 1] == 1)
			proba = (probaCase(i - 1, j + 1) + proba - (probaCase(i - 1, j + 1) * proba));
		if (caseCorrecte(i, j + 1) && T[i][j + 1] == 1)
			proba = (probaCase(i, j + 1) + proba - (probaCase(i, j + 1) * proba));
		if (caseCorrecte(i + 1, j + 1) && T[i + 1][j + 1] == 1)
			proba = (probaCase(i + 1, j + 1) + proba - (probaCase(i + 1, j + 1) * proba));
		if (caseCorrecte(i + 1, j) && T[i + 1][j] == 1)
			proba = (probaCase(i + 1, j) + proba - (probaCase(i + 1, j) * proba));
		if (caseCorrecte(i + 1, j - 1) && T[i + 1][j - 1] == 1)
			proba = (probaCase(i + 1, j - 1) + proba - (probaCase(i + 1, j - 1) * proba));
		if (caseCorrecte(i, j - 1) && T[i][j - 1] == 1)
			proba = (probaCase(i, j - 1) + proba - (probaCase(i, j - 1) * proba));
		return (int) (proba * 1000);
	}

	// Fonction de l'IA
	public static int[] intelligenceArtificielle() {
		// Estimation des positions de mines/de cases sécurisées
		int[][] estimations = new int[T.length][T[0].length]; // 0 quand on ne peut pas savoir, -2 quand il y a une mine
																// et -1
																// quand il n'y a pas de mine et sinon le score de la
																// case
		// Parcours toutes les case de la grille
		for (int i = 0; i < T.length; i++) {
			for (int j = 0; j < T[0].length; j++) {
				// Si cette case est révélée (l'IA ne peut faire les calculs qu'à partir des
				// cases révélées, sinon il y a triche)
				if (T[i][j] == 1) {
					// Récupère les cases adjacentes non révélées
					int[][] nonReveles = adjacentNonRevele(i, j);
					// Si il y a forcément une mine
					if (Tadj[i][j] == adjacentDrapeaux(i, j)) {
						for (int x = 0; x < nonReveles.length; x++) {
							// Si la case n'a pas déjà été marquée comme contenant une mine
							if (estimations[nonReveles[x][0]][nonReveles[x][1]] != -2) {
								// On marque la case comme contenant étant sûre
								estimations[nonReveles[x][0]][nonReveles[x][1]] = -1;
							}
						}
						// Sinon si le nombre de cases non révélées est inférieur ou égal 4 (pour ne
						// garder que les estimations les plus précises)
					} else if (nonReveles.length <= 4) {
						// Pour chaque case adjacente non révélée
						for (int x = 0; x < nonReveles.length; x++) {
							// Si elle n'est pas déjà marquée comme contenant une mine certaine ou comme ne
							// contenant pas de mine
							if (estimations[nonReveles[x][0]][nonReveles[x][1]] >= 0) {
								// Si il y a autant de mines restantes que de cases adjacentes non révélées, les
								// cases adjacentes non révélées contiennent des mines
								if ((Tadj[i][j] - adjacentDrapeaux(i, j)) == nonReveles.length) {
									estimations[nonReveles[x][0]][nonReveles[x][1]] = -2;
								} else {
									// Sinon les cases adjacentes non révélées prennent comme valeur leur score
									estimations[nonReveles[x][0]][nonReveles[x][1]] = calculScoreCase(nonReveles[x][0],
											nonReveles[x][1]);
								}
							}
						}
					}
				}
			}
		}
		// On commence par révéler toutes les cases possibles
		for (int i = 0; i < estimations.length; i++) {
			for (int j = 0; j < estimations[0].length; j++) {
				// Si on est sûr qu'il n'y a pas de mine, on découvre la case
				if (estimations[i][j] == -1) {
					return new int[] { i, j, 1 };
					// Si on est sûrs qu'il y ait une mine, on place un drapeau
				} else if (estimations[i][j] == -2) {
					return new int[] { i, j, 0 };
				}
			}
		}

		// Si il n'y a pas/plus de cases sûres à révéler ou a marquer, on marque la case
		// avec le plus haut score
		float max = 0;
		int maxX = 0;
		int maxY = 0;
		// Recherche de la case avec le plus haut score
		for (int i = 0; i < estimations.length; i++) {
			for (int j = 0; j < estimations[0].length; j++) {
				if (estimations[i][j] > max) {
					maxX = i;
					maxY = j;
					max = estimations[i][j];
				}
			}
		}
		// On marque la case qui a la plus haut score (qui est la plus probable d'avoir
		// une mine)
		if (max > 0) {
			return new int[] { maxX, maxY, 0 };
		}

		// Si il n'y a pas de cases à révéler ou de drapeaux à placer on révèle une case
		// au hasard (en début de partie)
		return new int[] { entierAleatoire(0, estimations.length - 1), entierAleatoire(0, estimations[0].length - 1),
				1 };
	}

	// Calcule les statistiques de victoire de l'IA pour des grilles 8x8 avec 10
	// mines, 16x16 avec 40 mines et 30x16 avec 99 mines
	// (pas appellée dans main car une autre fonction permettant d'utiliser un
	// certain nombre de grilles de taille personnalisées)
	public static void statistiquesVictoiresIA() {
		// Statistiques pour la grille 8x8 avec 10 mines sur 1000 grilles
		int victoires = 0;
		for (int i = 0; i < 1000; i++) {
			init(8, 8, 10);
			calculerAdjacent();
			while (true) {
				// On récupère le coup de l'IA
				int[] ia = intelligenceArtificielle();
				if (ia[2] == 1) {
					// Si l'IA tombe sur une bombe alors on finis la partie
					if (!revelerCase(ia[0], ia[1])) {
						break;
					}
					// Si l'IA gagne la partie alors on augmente le nombre de victoires de 1
					if (aGagne()) {
						victoires++;
						break;
					}
					// Si l'IA veut placer un drapeau alors on place le drapeau
				} else {
					actionDrapeau(ia[0], ia[1]);
				}
			}
		}
		// On affiche le score final de l'IA
		System.out.println("Sur une grille de 8x8 avec 10 mines");
		System.out.println("L'IA a gagné " + victoires + " parties sur 1000 (" + (victoires * 100 / 1000) + "%)");

		// Statistiques pour la grille 16x16 avec 40 mines sur 1000 grilles
		victoires = 0;
		for (int i = 0; i < 1000; i++) {
			init(16, 16, 40);
			calculerAdjacent();
			while (true) {
				// On récupère le coup de l'IA
				int[] ia = intelligenceArtificielle();
				if (ia[2] == 1) {
					// Si l'IA tombe sur une bombe alors on finis la partie
					if (!revelerCase(ia[0], ia[1])) {
						break;
					}
					// Si l'IA gagne la partie alors on augmente le nombre de victoires de 1
					if (aGagne()) {
						victoires++;
						break;
					}
					// Si l'IA veut placer un drapeau alors on place le drapeau
				} else {
					actionDrapeau(ia[0], ia[1]);
				}
			}
		}
		// On affiche le score final de l'IA
		System.out.println("Sur une grille de 16x16 avec 40 mines");
		System.out.println("L'IA a gagné " + victoires + " parties sur 1000 (" + (victoires * 100 / 1000) + "%)");

		// Statistiques pour la grille 30x16 avec 99 mines sur 1000 grilles
		victoires = 0;
		for (int i = 0; i < 1000; i++) {
			init(30, 16, 99);
			calculerAdjacent();
			while (true) {
				// On récupère le coup de l'IA
				int[] ia = intelligenceArtificielle();
				if (ia[2] == 1) {
					// Si l'IA tombe sur une bombe alors on finis la partie
					if (!revelerCase(ia[0], ia[1])) {
						break;
					}
					// Si l'IA gagne la partie alors on augmente le nombre de victoires de 1
					if (aGagne()) {
						victoires++;
						break;
					}
					// Si l'IA veut placer un drapeau alors on place le drapeau
				} else {
					actionDrapeau(ia[0], ia[1]);
				}
			}
		}
		// On affiche le score final de l'IA
		System.out.println("Sur une grille de 30x16 avec 99 mines");
		System.out.println("L'IA a gagné " + victoires + " parties sur 1000 (" + (victoires * 100 / 1000) + "%)");
	}

}