import java.util.ArrayList;

public class Tour extends Piece {
	private boolean premierCoup;
	
	/*
	 Constructeur
	 */
	public Tour(boolean blanc, int ligne, int colonne) {
		super(blanc, ligne, colonne);
		if (blanc) {
			symbole = "TB";
		} else {
			symbole = "TN";
		}
		premierCoup = true;
	}
	
	/*
	 Getter
	 */
	public boolean isPremierCoup() {
		return premierCoup;
	}
	
	/*
	 Vérification du déplacement de la tour
	 */
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
		 Vérification : la case d'arrivée doit être sur la même ligne ou colonne que la case de départ sinon on retourne false
		 */
		
		if (colonneArrivee != colonne && ligneArrivee != ligne) {
			return false;
		}
		
		/*
		 On vérifie si il se déplace à l'horizontale ou en verticale
		 Si c'est en verticale, on vérifie si elle va vers le bas ou vers le haut
		 Sinon on vérifie si elle va vers la gauche ou la droite
		 On vérifie si une pièce se trouve entre la case de départ et la case d'arrivée avec une boucle
		 Dans cette boucle, on vérifie si la case intermédiaire "c" est vide sinon on retourne false
 		 */
		
		if(colonneArrivee == this.colonne) {
			if(ligneArrivee > ligne) {
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
		} else {
			if(colonneArrivee > colonne) {
				for(int colonne = this.colonne + 1 ; colonne < colonneArrivee ; colonne++) {
					Piece p = plateau.getPiece(ligneArrivee, colonne);
					if (p != null) {
						return false;
					}
				}
			} else {
				for(int colonne = this.colonne - 1 ; colonne > colonneArrivee ; colonne--) {
					Piece p = plateau.getPiece(ligneArrivee, colonne);
					if (p == null) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	/*
	 Après son premier déplacement, son premier coup devient false
	 */
	public void deplacer(Coup coup, Plateau plateau, ArrayList<Coup> historiqueDesCoups) {
		super.deplacer(coup, plateau, historiqueDesCoups);
		if(premierCoup)
			premierCoup = false;
	}
}
