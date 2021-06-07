import java.util.ArrayList;

public class Roi extends Piece {
	private boolean premierCoup;
	
	public Roi(boolean blanc, int ligne, int colonne) {
		super(blanc, ligne, colonne);
		if (blanc) {
			symbole = "RB";
		} else {
			symbole = "RN";
		}
		premierCoup = true;
	}
	
	public boolean isPremierCoup() {
		return premierCoup;
	}
	
	private Tour getTourDuGrandRoque(int ligneArrivee, int colonneArrivee, Plateau plateau) {
		if(!premierCoup || ligneArrivee != this.ligne || colonneArrivee != this.colonne - 2 || estEnEchec(plateau)) {
			return null;
		}
		
		Piece piece = plateau.getPiece(ligneArrivee, 0);
		if(piece == null) {
			return null;
		}
		
		if(piece.isBlanc() != isBlanc()) {
			return null;
		}
		
		if(!(piece instanceof Tour)) {
			return null;
		}
		
		Tour tour = (Tour)piece;
		if(!tour.isPremierCoup()) {
			return null;
		}
		
		for(int colonne = this.colonne - 1 ; colonne > tour.getColonne() ; colonne--) {
			Piece pieceIntermediaire = plateau.getPiece(this.ligne, colonne);
			if(pieceIntermediaire != null) {
				return null;
			}
			
			if(colonne >= colonneArrivee) {
				int ligneDepart = this.ligne;
				int colonneDepart = this.colonne;
				
				simulerDeplacement(ligneDepart, colonne, plateau);
				
				if(estEnEchec(plateau)) {
					System.out.println("Grand roque impossible car le roi est menacé");
					simulerDeplacement(ligneDepart, colonneDepart, plateau);
					return null;
				}
				
				simulerDeplacement(ligneDepart, colonneDepart, plateau);
			}
		}
		
		return tour;
	}
	
	private Tour getTourDuPetitRoque(int ligneArrivee, int colonneArrivee, Plateau plateau) {
		if(!premierCoup || ligneArrivee != this.ligne || colonneArrivee != this.colonne + 2 || estEnEchec(plateau)) {
			return null;
		}
		
		Piece piece = plateau.getPiece(ligneArrivee, 7);
		if(piece == null) {
			return null;
		}
		
		if(piece.isBlanc() != isBlanc()) {
			return null;
		}
		
		if(!(piece instanceof Tour)) {
			return null;
		}
		
		Tour tour = (Tour)piece;
		if(!tour.isPremierCoup()) {
			return null;
		}
		
		for(int colonne = this.colonne + 1 ; colonne < tour.getColonne() ; colonne++) {
			Piece pieceIntermediaire = plateau.getPiece(this.ligne, colonne);
			if(pieceIntermediaire != null) {
				return null;
			}
			
			if(colonne <= colonneArrivee) {
				int ligneDepart = this.ligne;
				int colonneDepart = this.colonne;
				
				simulerDeplacement(ligneDepart, colonne, plateau);
				
				if(estEnEchec(plateau)) {
					System.out.println("Petit roque impossible car le roi est menacé");
					simulerDeplacement(ligneDepart, colonneDepart, plateau);
					return null;
				}
				
				simulerDeplacement(ligneDepart, colonneDepart, plateau);
			}
		}
		
		return tour;
	}
	
	private Tour getTourDuRoque(int ligneArrivee, int colonneArrivee, Plateau plateau) {
		Tour tourDuGrandRoque = getTourDuGrandRoque(ligneArrivee, colonneArrivee, plateau);
		if (tourDuGrandRoque != null) {
			return tourDuGrandRoque;
		}
		
		Tour tourDuPetitRoque = getTourDuPetitRoque(ligneArrivee, colonneArrivee, plateau);
		if (tourDuPetitRoque != null) {
			return tourDuPetitRoque;
		}
		
		return null;
	}
	
	public boolean peutSeDeplacer(int ligneArrivee, int colonneArrivee, Plateau plateau) {
		
		if (this.ligne == ligneArrivee && this.colonne == colonneArrivee) {
			return false;
		}
		
		Piece pieceArrivee = plateau.getPiece(ligneArrivee, colonneArrivee);
		if (pieceArrivee != null) {
			if(pieceArrivee.isBlanc() == isBlanc()) {
				return false;
			}
		}
		
		/*
		 Vérification Grand roque ou petit roque
		 */
		if (getTourDuRoque(ligneArrivee, colonneArrivee, plateau) != null) {
			return true;
		}
		
		if (colonneArrivee != colonne && ligneArrivee != ligne &&
				ligneArrivee - colonneArrivee != this.ligne - this.colonne && 
				ligneArrivee + colonneArrivee != this.ligne + this.colonne) {
			return false;
		}
		
		/*
		 Si la colonne d'arrivée est la même colonne de départ, on vérifie si le coup joué est un déplacement d'une case vers le haut
		 ou vers le bas
		 */
		
		if(colonneArrivee == colonne) {
			return ligneArrivee == ligne + 1 || ligneArrivee == ligne - 1;
		}
		
		/*
		 Si la ligne d'arrivée est la même ligne de départ, on vérifie si le coup joué est un déplacement d'une case vers la gauche ou
		 vers la droite
		 */
		
		if(ligneArrivee == ligne) {
			return colonneArrivee == colonne + 1 || colonneArrivee == colonne -1;
		}
		
		/*
		 On vérifie l'équation 1 du Fou, si c'est true, on compare la ligne d'arrivée et la ligne de départ pour vérifier si on monte
		 ou descend d'une case en diagonale
		 */
		
		if(ligneArrivee - colonneArrivee == ligne - colonne) {
			return ligneArrivee == ligne + 1 || ligneArrivee == ligne - 1;
		}
		
		/*
		 On vérifie l'équation 2 du Fou, si c'est true, on compare la ligne d'arrivée et la ligne de départ pour vérifier si on monte
		 ou descend d'une case en diagonale
		 */
		
		if(ligneArrivee + colonneArrivee == ligne + colonne) {
			return ligneArrivee == ligne + 1 || ligneArrivee == ligne - 1;
		}
		
		return false;
	}
	
	public boolean estEnEchec(Plateau plateau) {
		ArrayList<Piece> piecesAdversaire = plateau.getPieces(!blanc);
		for(Piece pieceAdversaire : piecesAdversaire) {
			if(pieceAdversaire.peutSeDeplacer(this.ligne, this.colonne, plateau)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean estEnEchecEtMat(Plateau plateau) {
		if(!this.estEnEchec(plateau)) {
			return false;
		}
				
		ArrayList<Piece> mesPieces = plateau.getPieces(blanc);
		for(Piece maPiece : mesPieces) {
			ArrayList<Coup> coups = maPiece.getCoupsPossibles(plateau);
			for(Coup coup : coups) {
				Piece pieceArrivee = plateau.getPiece(coup.getLigneArrivee(), coup.getColonneArrivee());
				maPiece.simulerDeplacement(coup.getLigneArrivee(), coup.getColonneArrivee(), plateau);
				if(!this.estEnEchec(plateau)) {
					maPiece.simulerDeplacement(coup.getLigneDepart(), coup.getColonneDepart(), plateau);
					if(pieceArrivee != null) {
						plateau.setPiece(coup.getLigneArrivee(), coup.getColonneArrivee(), pieceArrivee);
					}
					
					System.out.println("Echec !");
					return false;
				}
				maPiece.simulerDeplacement(coup.getLigneDepart(), coup.getColonneDepart(), plateau);
				if(pieceArrivee != null) {
					plateau.setPiece(coup.getLigneArrivee(), coup.getColonneArrivee(), pieceArrivee);
				}
			}
		}
		
		return true;
	}
	
	public void deplacer(Coup coup, Plateau plateau, ArrayList<Coup> historiqueDesCoups) {
		
		Tour tourDuGrandRoque = getTourDuGrandRoque(coup.getLigneArrivee(), coup.getColonneArrivee(), plateau);
		Tour tourDuPetitRoque = getTourDuPetitRoque(coup.getLigneArrivee(), coup.getColonneArrivee(), plateau);
		
		super.deplacer(coup, plateau, historiqueDesCoups);
		if(premierCoup)
			premierCoup = false;
		
		if (tourDuGrandRoque != null) {
			Coup coupDeLaTour = new Coup(tourDuGrandRoque.getLigne(), tourDuGrandRoque.getColonne(), 
					coup.getLigneArrivee(), coup.getColonneArrivee() + 1);
			tourDuGrandRoque.deplacer(coupDeLaTour, plateau, historiqueDesCoups);
			//Le coup de la tour du roque n'est pas ajouté dans l'historique des coups
			historiqueDesCoups.remove(historiqueDesCoups.size() - 1); 
		} else if (tourDuPetitRoque != null) {
			Coup coupDeLaTour = new Coup(tourDuPetitRoque.getLigne(), tourDuPetitRoque.getColonne(), 
					coup.getLigneArrivee(), coup.getColonneArrivee() - 1);
			tourDuPetitRoque.deplacer(coupDeLaTour, plateau, historiqueDesCoups);
			//Le coup de la tour du roque n'est pas ajouté dans l'historique des coups
			historiqueDesCoups.remove(historiqueDesCoups.size() - 1);
		}
	}
}
