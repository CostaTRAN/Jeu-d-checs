

public class Dame extends Piece {
	
	public Dame(boolean blanc, int ligne, int colonne) {
		super(blanc, ligne, colonne);
		if (blanc) {
			symbole = "DB";
		} else {
			symbole = "DN";
		}
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
		 On fusionne la vérification des déplacement de la tour et du fou
		 */
		
		if (colonneArrivee != this.colonne && ligneArrivee != this.ligne
			&& ligneArrivee - colonneArrivee != this.ligne - this.colonne 
			&& ligneArrivee + colonneArrivee != this.ligne + this.colonne) {
			return false;
		}
		
		/*
		 Si la première vérification est validée alors on reprend les vérifications de la tour et du fou pour voir si une pièce 
		 se trouve entre la case d'arrivée et la case de départ
		 */
	
		if(colonneArrivee == this.colonne) {
			if(ligneArrivee > this.ligne) {
				for(int ligne = this.ligne + 1 ; ligne < ligneArrivee ; ligne++) {
					Piece p = plateau.getPiece(ligne, colonneArrivee);
					if (p != null) {
						return false;
					}
				}
			} else {
				for(int ligne = this.ligne - 1 ; ligne > ligneArrivee ; ligne--) {
					Piece p = plateau.getPiece(ligne, colonneArrivee);
					if (p != null) {
						return false;
					}
				}
			}
		} 
		if (ligneArrivee == this.ligne) {
			if(colonneArrivee > this.colonne) {
				for(int colonne = this.colonne + 1 ; colonne < colonneArrivee ; colonne++) {
					Piece p = plateau.getPiece(ligneArrivee, colonne);
					if (p != null) {
						return false;
					}
				}
			} else {
				for(int colonne = this.colonne - 1 ; colonne > colonneArrivee ; colonne--) {
					Piece p = plateau.getPiece(ligneArrivee, colonne);
					if (p != null) {
						return false;
					}
				}
			}
		}
		if(ligneArrivee - colonneArrivee == this.ligne - this.colonne) {
			if(ligneArrivee > this.ligne) {
				for(int ligne = this.ligne + 1 ; ligne < ligneArrivee ; ligne++) {
					Piece p = plateau.getPiece(ligne, ligne - this.ligne + this.colonne);
					if (p != null) {
						return false;
					}
				}
			} else {
				for(int ligne = this.ligne - 1 ; ligne > ligneArrivee ; ligne--) {
					Piece p = plateau.getPiece(ligne, ligne - this.ligne + this.colonne);
					if (p != null) {
						return false;
					}
				}
			}
		} 
		if(ligneArrivee + colonneArrivee == this.ligne + this.colonne) {
			if(ligneArrivee > this.ligne) {
				for(int ligne = this.ligne + 1 ; ligne < ligneArrivee ; ligne++) {
					Piece p = plateau.getPiece(ligne, this.ligne + this.colonne - ligne);
					if (p != null) {
						return false;
					}
				}
			} else {
				for(int ligne = this.ligne - 1 ; ligne > ligneArrivee ; ligne--) {
					Piece p = plateau.getPiece(ligne, this.ligne + this.colonne - ligne);
					if (p != null) {
						return false;
					}
				}
			}
		}
		return true;
	}
}
