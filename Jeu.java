import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Jeu {
	
	private Plateau plateau;
	private Joueur joueurBlanc;
	private Joueur joueurNoir;
	private Joueur joueurCourant;
	private ArrayList<Coup> historiqueDesCoups;
	private static final String SAUVEGARDE = "sauvegarde.txt";
	
	public Jeu() {
		this.plateau = new Plateau();
		this.joueurBlanc = new Joueur(true);
		this.joueurNoir = new Joueur(false);
		this.joueurCourant = joueurBlanc;
		this.historiqueDesCoups = new ArrayList<Coup>();
	}
	
	private void afficherHistoriqueDesCoups() {
		if (!historiqueDesCoups.isEmpty()) {
			System.out.println("Voici l'historique des coups de la partie : ");
		}
		
		int numTour = 0;
		for(int numCoup = 0 ; numCoup < historiqueDesCoups.size() ; numCoup++) {
			Coup coupJoue = historiqueDesCoups.get(numCoup);
			if(numCoup % 2 == 0) {
				numTour++;
				System.out.print(numTour + ". " + coupJoue);
				if (numCoup == historiqueDesCoups.size() - 1) {
					System.out.println();
				} else {
					System.out.print(" ");
				}
			} else {
				System.out.println(coupJoue);
			}
		}
	}
	
	private void afficherPlateau() {
		System.out.println(plateau); //Affichage du plateau
	}
	
	private boolean estEnPat() {
		if(plateau.estEnPat(joueurCourant.isBlanc())) {
			System.out.println("Le joueur "+ joueurCourant.getCouleur() + " est en PAT !");
			System.out.println("Match nul !");
			return true;
		}
		
		return false;
	}
	
	private Coup entrerCoup() {
		System.out.println("C'est au joueur "+ joueurCourant.getCouleur() + " de jouer"); 
		//On demande au jour d'entrer interactivement un coup
		
		boolean demanderCoup = true;
		Scanner input = new Scanner(System.in); //Pour sélectionner la case de départ et la case d'arrivée
		char choix;
		int colonneDepart = 0;
		int ligneDepart = 0;
		int colonneArrivee = 0;
		int ligneArrivee = 0;
		
		while(demanderCoup) {
			System.out.println("Entrez le numéro de la colonne de la pièce à déplacer (entre 1 et 8 ou S pour sauvegarder) :");
			choix = input.next().charAt(0);
			if (choix >= '1' && choix <= '8') {
				colonneDepart = choix - '1';
			} else if (choix == 'S') {
				sauvegarder();
				continue;
			} else {
				System.out.println("Le numéro saisie est incorrect");
				continue;
			}
			
			System.out.println("Entrez le numéro de la ligne de la pièce à déplacer (entre 1 et 8 ou S pour sauvegarder) :");
			choix = input.next().charAt(0);
			if (choix >= '1' && choix <= '8') {
				ligneDepart = choix - '1';
			} else if (choix == 'S') {
				sauvegarder();
				continue;
			} else {
				System.out.println("Le numéro saisie est incorrect");
				continue;
			}
			
			System.out.println("Entrez le numéro de la colonne d'arrivée (entre 1 et 8 ou S pour sauvegarder) :");
			choix = input.next().charAt(0);
			if (choix >= '1' && choix <= '8') {
				colonneArrivee = choix - '1';
			} else if (choix == 'S') {
				sauvegarder();
				continue;
			} else {
				System.out.println("Le numéro saisie est incorrect");
				continue;
			}
			
			System.out.println("Entrez le numéro de la ligne d'arrivée (entre 1 et 8 ou S pour sauvegarder) :");
			choix = input.next().charAt(0);
			if (choix >= '1' && choix <= '8') {
				ligneArrivee = choix - '1';
			} else if (choix == 'S') {
				sauvegarder();
				continue;
			} else {
				System.out.println("Le numéro saisie est incorrect");
				continue;
			}
			
			if (!plateau.caseExiste(ligneDepart, colonneDepart)) {
				System.out.println("La case de départ sélectionné n'existe pas");
				continue;
			}
			
			if (!plateau.caseExiste(ligneArrivee, colonneArrivee)) {
				System.out.println("La case d'arrivée sélectionné n'existe pas");
				continue;
			}
			
			Piece piece = plateau.getPiece(ligneDepart, colonneDepart);
			
			if (piece == null) {
				System.out.println("Pas de piece");
				continue;
			}
			
			if(piece.isBlanc() != joueurCourant.isBlanc()) {
				System.out.println("Pas de la même couleur");
				continue;
			}
			
			demanderCoup = false;
		}
				
		return new Coup(ligneDepart, colonneDepart, ligneArrivee, colonneArrivee);
	}
	
	private boolean jouerCoup(Coup coup) {
		Piece pieceDepart = plateau.getPiece(coup.getLigneDepart(), coup.getColonneDepart()); //On récupère la pièce de départ

		if(!pieceDepart.peutSeDeplacer(coup.getLigneArrivee(), coup.getColonneArrivee(), plateau)) { //On vérifie si la pièce de départ peut se déplacer
			System.out.println("Le coup n'est pas valable");
			return false;	
		}
		
		Piece pieceArrivee = plateau.getPiece(coup.getLigneArrivee(), coup.getColonneArrivee());
		pieceDepart.simulerDeplacement(coup.getLigneArrivee(), coup.getColonneArrivee(), plateau); //On simule le déplacement de la pièce de départ
		
		if(plateau.getRoi(joueurCourant.isBlanc()).estEnEchec(plateau)) { //On vérifie si on met notre propre roi en échec
			pieceDepart.simulerDeplacement(coup.getLigneDepart(), coup.getColonneDepart(), plateau); //On remet la piece à son point de départ
			if(pieceArrivee != null) {
				plateau.setPiece(coup.getLigneArrivee(), coup.getColonneArrivee(), pieceArrivee); //On remet la piece d'arrivée à son point de départ
			}
			System.out.println("Le roi ne peut pas se mettre en échec...");
			return false;
		}
		
		pieceDepart.simulerDeplacement(coup.getLigneDepart(), coup.getColonneDepart(), plateau); //On remet la piece à son point de départ dans tous les cas
		if(pieceArrivee != null) { //Si il y a une piece ennemie
			plateau.setPiece(coup.getLigneArrivee(), coup.getColonneArrivee(), pieceArrivee); //On remet la pièce ennemie à sa case de départ
		}
		
		pieceDepart.deplacer(coup, plateau, historiqueDesCoups); //On déplace juste la pièce à sa case d'arrivée
		plateau.interdirePriseEnPassant(!joueurCourant.isBlanc());
		return true;
	}
	
	private boolean estEnEchecEtMat() {
		if (!plateau.getRoi(!joueurCourant.isBlanc()).estEnEchecEtMat(plateau)) {
			return false;
		}
		
		afficherHistoriqueDesCoups();
		System.out.println(plateau);
		System.out.println("Echec et mat ! Le joueur " + joueurCourant.getCouleur() + " a gagné !");
		return true;
	}
	
	private void changerJoueur() {
		if(joueurCourant == joueurBlanc) {
			joueurCourant = joueurNoir;
		} else {
			joueurCourant = joueurBlanc;
		}
	}
	
	private void sauvegarder() {
		try {
			FileWriter fichier = new FileWriter(SAUVEGARDE);
			BufferedWriter sortie = new BufferedWriter(fichier);
			
			for(int numCoup = 0 ; numCoup < historiqueDesCoups.size() ; numCoup++) {
				sortie.write(historiqueDesCoups.get(numCoup).toString());
				if (numCoup != historiqueDesCoups.size() - 1) {
					sortie.write(" ");
				}
			}
			
			sortie.close();
			
			System.out.println("La partie a été sauvegardé dans le fichier " + SAUVEGARDE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void chargerPartie() {
		try {
			FileReader fichier = new FileReader(SAUVEGARDE);
			BufferedReader entree = new BufferedReader(fichier);
			String ligne = entree.readLine();
			
			if (ligne == null) {
				System.out.println("La partie du fichier " + SAUVEGARDE + " a été chargée");
				entree.close();
				return;
			}
			
			String[] listeDesCoups = ligne.split(" ");
			boolean coupInvalide = false;
			
			for (String coupNotation : listeDesCoups) {
				if (coupNotation.length() != 4 && coupNotation.length() != 5) {
					continue;
				}
				
				for (int i = 0; i < 4; i++) {
					if (coupNotation.charAt(i) < '1' || coupNotation.charAt(i) > '8') {
						coupInvalide = true;
						break;
					}
				}
				
				if (coupInvalide) {
					continue;
				}
				
				if (coupNotation.length() == 5 && (coupNotation.charAt(4) < '1' || coupNotation.charAt(4) > '4')) {
					continue;
				}
				
				int ligneDepart = coupNotation.charAt(1) - '1';
				int colonneDepart = coupNotation.charAt(0) - '1';
				int ligneArrivee = coupNotation.charAt(3) - '1';
				int colonneArrivee = coupNotation.charAt(2) - '1';
				int numPromotionChoisie;
				Coup coup = new Coup(ligneDepart, colonneDepart, ligneArrivee, colonneArrivee);
				
				if (coupNotation.length() == 5) {
					numPromotionChoisie = coupNotation.charAt(4) - '0';
					coup.setNumPromotionChoisie(numPromotionChoisie);
				}
				
				jouerCoup(coup);
				changerJoueur();
			}
			
			System.out.println("La partie du fichier " + SAUVEGARDE + " a été chargée");
			entree.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void demanderChargementPartie() {
		File fichier = new File(SAUVEGARDE);
		if(!fichier.exists() || fichier.isDirectory()) { 
		    return;
		}
		
		boolean choixNonValide = true;
		Scanner input = new Scanner(System.in);
		System.out.println("Voulez-vous charger la partie sauvegardée dans le fichier " + SAUVEGARDE + " ? (O pour Oui ou N pour Non)");
		while(choixNonValide) {
			char choix = input.next().charAt(0);
			switch (choix) {
			case 'O':
				chargerPartie();
				choixNonValide = false;
				break;
			case 'N':
				choixNonValide = false;
				break;
			default:
				System.out.println("Erreur, veuillez saisir le bon caractère.");
				break;
			}
		}
	}
	
	private void jouer() {
		/*
		 La boucle commence
		 */
		while(true) { //Tant que echecEtMat est faux, la partie continue			
			afficherHistoriqueDesCoups();
			
			afficherPlateau();
			
			if(estEnPat()) {
				break;
			}
			
			Coup coup = entrerCoup();

			/*
		 	Vérification : Si le coup est valable, on déplace la pièce et on affiche le plateau pour mettre à jour
			 */
			if(!jouerCoup(coup)) {
				continue;
			}
			
			if (estEnEchecEtMat()) {
				break;
			}
			
			changerJoueur();
		}
	}
	
	public static void main(String[] args) {
		Jeu jeu = new Jeu();
		jeu.demanderChargementPartie();
		jeu.jouer();
	}
}
