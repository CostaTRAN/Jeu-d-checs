
public class Pion extends Piece {
	private boolean premierCoup;
	
	public Pion(boolean blanc) {
		super(blanc);
		if (blanc) {
			symbole = "PB";
		} else {
			symbole = "PN";
		}
		premierCoup = true;
	}
	
	public boolean peutSeDeplacer(Case depart,Case arrivee, Plateau plateau) {
		int deplacementLignePossible;
		if (!arrivee.estVide()) {
			if (blanc) {
				deplacementLignePossible = 1;
			} else {
				deplacementLignePossible = -1;
			}
			return (arrivee.getColonne() == depart.getColonne() + 1 || arrivee.getColonne() == depart.getColonne() - 1) 
					&& arrivee.getLigne() == depart.getLigne() + deplacementLignePossible;
		}
		
		if (depart.getColonne() != arrivee.getColonne())
			return false;

		if (premierCoup) {
			if (blanc) {
				deplacementLignePossible = 2;
			} else {
				deplacementLignePossible = -2;
			}
		} else {
			if (blanc) {
				deplacementLignePossible = 1;
			} else {
				deplacementLignePossible = -1;
			}
		}
			
		return arrivee.getLigne() == depart.getLigne() + deplacementLignePossible;	
	}
	
	public void deplacer(Case depart, Case arrivee, Plateau plateau) {
		if(depart.getPiece() != this) {
			return;
		}
		arrivee.setPiece(this);
		depart.setPiece(null);
		if(premierCoup)
			premierCoup = false;
	}
}
