public class Cavalier extends Piece {
	
	/*
	 Constructeur
	 */
	public Cavalier(boolean blanc, int ligne, int colonne) {
		super(blanc, ligne, colonne);
		if (blanc) {
			symbole = "CB";
		} else {
			symbole = "CN";
		}
	}
	
	/*
	 Vérification du déplacement du cavalier
	 */
	public boolean peutSeDeplacer (int ligneArrivee, int colonneArrivee, Plateau plateau) {
		
		//On vérifie si sa case d'arrivée est sa case de départ
		if (this.ligne == ligneArrivee && this.colonne == colonneArrivee) {
			return false;
		}

		//Si le cavalier veut manger une pièce, on vérifie si la pièce d'arrivée est de la même couleur
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