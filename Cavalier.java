
public class Cavalier extends Piece {
	public Cavalier(boolean blanc, int ligne, int colonne) {
		super(blanc, ligne, colonne);
		if (blanc) {
			symbole = "CB";
		} else {
			symbole = "CN";
		}
	}
	
	public boolean peutSeDeplacer (int ligneArrivee, int colonneArrivee, Plateau plateau) {
		
		if (this.ligne == ligneArrivee && this.colonne == colonneArrivee) {
			return false;
		}

		Piece pieceArrivee = plateau.getPiece(ligneArrivee, colonneArrivee);
		if (pieceArrivee != null && pieceArrivee.isBlanc() == this.isBlanc()) {
            return false;
        }
		
		/*
		 On initialise deux variables calculant la valeur absolue de la différence entre la ligne/colonne de départ et celle d'arrivée
		 Si le produit de ces deux variables est égal à 2 alors on retourne true
		 */
		int diffLigne = Math.abs(this.ligne - ligneArrivee);
        int diffColonne = Math.abs(this.colonne - colonneArrivee);
        return diffLigne * diffColonne == 2;
	}
}
