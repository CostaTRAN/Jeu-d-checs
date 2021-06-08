import java.util.ArrayList;
import java.util.Scanner;

public class Pion extends Piece {
	/*
	 On doit pouvoir savoir si c'est le premier coup du pion ou non
	 */
	private boolean premierCoup;
	private boolean peutEtrePrisEnPassant;
	/*
	 Constructeur
	 */
	public Pion(boolean blanc, int ligne, int colonne) {
		super(blanc, ligne, colonne);
		if (blanc) {
			symbole = "PB";
		} else {
			symbole = "PN";
		}
		premierCoup = true;
		peutEtrePrisEnPassant = false;
	}
	
	/*
	 Getters et setters
	 */
	public boolean getPeutEtrePrisEnPassant() {
		return peutEtrePrisEnPassant;
	}
	
	public void setPeutEtrePrisEnPassant(boolean peutEtrePrisEnPassant) {
		this.peutEtrePrisEnPassant = peutEtrePrisEnPassant;
	}
	
	/*
	 Une méthode pour récupérer le pion qui peut être prise en passant
	 */
	private Pion getPionPrisEnPassant(int ligneArrivee, int colonneArrivee, Plateau plateau) {
		if (this.ligne == ligneArrivee && this.colonne == colonneArrivee) {
			return null;
		}
		
		int deplacementLignePossible;
		Piece pieceArrivee = plateau.getPiece(ligneArrivee, colonneArrivee);
		
		if (pieceArrivee != null) {
			return null;
		}
		
		if (colonne == colonneArrivee) {
			return null;
		}
		
		if (blanc) {
			deplacementLignePossible = 1; //Les blancs montent en ligne donc +1
		} else {
			deplacementLignePossible = -1; //Les noirs descendent en ligne donc -1
		}
		
		if ((colonneArrivee == colonne + 1 || colonneArrivee == colonne - 1) 
				&& ligneArrivee == ligne + deplacementLignePossible) {
			Piece piecePriseEnPassant = plateau.getPiece(this.ligne, colonneArrivee);
			if(piecePriseEnPassant == null) {
				return null;
			}
			
			if(piecePriseEnPassant.isBlanc() == isBlanc()) {
				return null;
			}
			
			if(!(piecePriseEnPassant instanceof Pion)) {
				return null;
			}
			
			Pion pionPriseEnPassant = (Pion) piecePriseEnPassant;
			if (pionPriseEnPassant.getPeutEtrePrisEnPassant()) {
				return pionPriseEnPassant;
			}
			return null;
		}
		return null;
	}
	
	/*
	 Une méthode pour vérifier le déplacement du pion
	 */

	public boolean peutSeDeplacer(int ligneArrivee, int colonneArrivee, Plateau plateau) {
		
		if (this.ligne == ligneArrivee && this.colonne == colonneArrivee) {
			return false;
		}
		
		int deplacementLignePossible;
		Piece pieceArrivee = plateau.getPiece(ligneArrivee, colonneArrivee);
		
		if (pieceArrivee != null) { // Si l'arrivée n'est pas vide on regarde si la pièce sur la case d'arrivée est de la même couleur
			if(pieceArrivee.isBlanc() == isBlanc()) {
				return false;
			}
				
			if (blanc) {
				deplacementLignePossible = 1; //Les blancs montent en ligne donc +1
			} else {
				deplacementLignePossible = -1; //Les noirs descendent en ligne donc -1
			}
			return (colonneArrivee == colonne + 1 || colonneArrivee == colonne - 1) 
					&& ligneArrivee == ligne + deplacementLignePossible; //Le pion prend la pièce ennemie diagonale
		}
		
		/*
		 On vérifie si la case d'arrivée est bien sur la même colonne que la case de départ
		 */
		
		if (colonne != colonneArrivee) {
			/*
			 On vérifie la prise en passant
			 */
			return getPionPrisEnPassant(ligneArrivee, colonneArrivee, plateau) != null;
		}
		
		/*
		 On vérifie la couleur de la pièce
		 Si c'est le premier coup de la pièce, on lui donne la possibilité de faire un déplacement de 1 ou 2 vers l'avant
		 Sinon il ne peut que faire un déplacement de 1 vers l'avant
		 */

		if (blanc) { 
			if(premierCoup) {
				deplacementLignePossible = 2;
				if(ligneArrivee == ligne + deplacementLignePossible) {
					Piece pieceIntermediaire = plateau.getPiece(ligneArrivee - 1, colonneArrivee);
					if(pieceIntermediaire != null) {
						return false;
					}
					return true;
				}
			} else {
				deplacementLignePossible = 1;
			}
			return ligneArrivee > ligne && ligneArrivee <= ligne + deplacementLignePossible;
		} else {
			if(premierCoup) {
				deplacementLignePossible = -2;
				if(ligneArrivee == ligne + deplacementLignePossible) {
					Piece pieceIntermediaire = plateau.getPiece(ligneArrivee + 1, colonneArrivee);
					if(pieceIntermediaire != null) {
						return false;
					}
					return true;
				}
			} else {
				deplacementLignePossible = -1;
			}
			return ligneArrivee < ligne && ligneArrivee >= ligne + deplacementLignePossible;
		}
	}
	
	/*
	 Une méthode pour vérifier si le pion arrive au bout du plateau
	 */
	private boolean peutEtrePromu() {
		return (blanc && ligne == 7) || (!blanc && ligne == 0);
	}
	
	/*
	 Une méthode pour recharger ou initialiser la promotion d'un pion
	 */
	private void verifierPromotion(Plateau plateau, ArrayList<Coup> historiqueDesCoups) {
		if(!peutEtrePromu()) {
			return;
		}
		
		Coup dernierCoup = historiqueDesCoups.get(historiqueDesCoups.size() - 1);
		int numPromotionChoisie = dernierCoup.getNumPromotionChoisie();
		switch (numPromotionChoisie) {
		case 1:
			plateau.setPiece(ligne, colonne, new Dame(blanc, ligne, colonne));
			return;
		case 2:
			plateau.setPiece(ligne, colonne, new Tour(blanc, ligne, colonne));
			return;
		case 3:
			plateau.setPiece(ligne, colonne, new Fou(blanc, ligne, colonne));
			return;
		case 4:
			plateau.setPiece(ligne, colonne, new Cavalier(blanc, ligne, colonne));
			return;
		default:
			break;
		}
		
		System.out.println("Votre pion peut être promu !");

		boolean demanderPiece = true;
		Scanner input = new Scanner(System.in);
		while(demanderPiece) {
			System.out.println("Choisissez la promotion du pion : D (Dame), T (Tour), F (Fou), C (Cavalier)");
			char promotion = input.next().charAt(0);
			switch (promotion) {
			case 'D':
				System.out.println("Le pion est promu en une dame");
				plateau.setPiece(ligne, colonne, new Dame(blanc, ligne, colonne));
				dernierCoup.setNumPromotionChoisie(1);
				demanderPiece = false;
				break;
			case 'T':
				System.out.println("Le pion est promu en une tour");
				plateau.setPiece(ligne, colonne, new Tour(blanc, ligne, colonne));
				dernierCoup.setNumPromotionChoisie(2);
				demanderPiece = false;
				break;
			case 'F':
				System.out.println("Le pion est promu en un fou");
				plateau.setPiece(ligne, colonne, new Fou(blanc, ligne, colonne));
				dernierCoup.setNumPromotionChoisie(3);
				demanderPiece = false;
				break;
			case 'C':
				System.out.println("Le pion est promu en un cavalier");
				plateau.setPiece(ligne, colonne, new Cavalier(blanc, ligne, colonne));
				dernierCoup.setNumPromotionChoisie(4);
				demanderPiece = false;
				break;
			default:
				System.out.println("Erreur, veuillez saisir le bon caractère.");
				break;
			}
		}
	}
	
	/*
	 La méthode déplacer est réécrite pour l'adapter aux déplacements du pion
	 */
	public void deplacer(Coup coup, Plateau plateau, ArrayList<Coup> historiqueDesCoups) {
		int ligneDepart = this.ligne;
		int colonneDepart = this.colonne;
		
		Pion pionPriseEnPassant = getPionPrisEnPassant(coup.getLigneArrivee(), coup.getColonneArrivee(), plateau);
		
		super.deplacer(coup, plateau, historiqueDesCoups);

		if(premierCoup) {
			premierCoup = false;
			if(coup.getColonneArrivee() == colonneDepart) {
				if(blanc) {
					if(coup.getLigneArrivee() == ligneDepart + 2) {
						peutEtrePrisEnPassant = true;
					}
				} else {
					if(coup.getLigneArrivee() == ligneDepart - 2) {
						peutEtrePrisEnPassant = true;
					}
				}
			}
		}
		
		if (pionPriseEnPassant != null) {
			plateau.setPiece(pionPriseEnPassant.getLigne(), pionPriseEnPassant.getColonne(), null);
		}
		
		verifierPromotion(plateau, historiqueDesCoups);
	}
}
