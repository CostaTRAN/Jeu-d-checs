
public class Fou extends Piece {
	public Fou(boolean blanc, int ligne, int colonne) {
		super(blanc, ligne, colonne);
		if (blanc) {
			symbole = "FB";
		} else {
			symbole = "FN";
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
		 On vérifie si la case d'arrivée est bien sur la diagonale de la case de départ
		 */
		if(ligneArrivee - colonneArrivee != this.ligne - this.colonne && 
				ligneArrivee + colonneArrivee != this.ligne + this.colonne)
			return false;
		
		/*
		 On vérifie si c'est une diagonale montante ou descendante
		 On vérifie si la pièce souhaite aller vers le haut ou vers le bas en comparant la ligne d'arrivée est supérieur à 
		 la ligne de départ
		 Avec une boucle on vérifie si il y a une pièce entre la case d'arrivée et la case de départ sur la diagonale
		 
		 Équation 1 : arriveeLigne - arriveeColonne = departLigne - departColonne
		 Équation 2 : arriveeLigne + arriveeColonne = departLigne + departColonne
		 */
		
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
		} else {
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
