import java.util.ArrayList;

public abstract class Piece {
	
	/*
	 Une pièce est caractérisé par sa couleur et son symbole en fonction de sa couleur
	 */
	
	protected boolean blanc;
	protected String symbole;
	protected int ligne;
	protected int colonne;
	
	/*
	 Constructeur de la pièce
	 */
	
	public Piece(boolean blanc, int ligne, int colonne) {
		this.blanc = blanc;
		this.ligne = ligne;
		this.colonne = colonne;
	}
	
	/*
	 Getters
	 */
	
	public int getLigne() {
		return ligne;
	}

	public int getColonne() {
		return colonne;
	}

	public boolean isBlanc() {
		return this.blanc;
	}

	public String getSymbole() {
		return this.symbole;
	}
	
	/*
	 Une méthode pour le déplacement des pièces, on met la pièce sur la case d'arrivée, on retire la pièce de départ pour éviter les doublons,
	 le coup est ajouté dans l'historique des coups, on écrase la ligne et colonne de départ par la ligne et colonne d'arrivée
	 */
	public void deplacer(Coup coup, Plateau plateau, ArrayList<Coup> historiqueDesCoups) {
		plateau.setPiece(coup.getLigneArrivee(), coup.getColonneArrivee(), this);
		plateau.setPiece(ligne, colonne, null);
		historiqueDesCoups.add(coup);
		ligne = coup.getLigneArrivee();
		colonne = coup.getColonneArrivee();
	}
	
	/*
	 Une méthode pour simuler un déplacement qui sert dans d'autres méthodes
	 */
	
	public void simulerDeplacement(int ligneArrivee, int colonneArrivee, Plateau plateau) {
		plateau.setPiece(ligneArrivee, colonneArrivee, this);
		plateau.setPiece(ligne, colonne, null);
		ligne = ligneArrivee;
		colonne = colonneArrivee;
	}
	
	/*
	 Une méthode abstraite qui est définit dans les classes filles permettant de vérifier le déplacement du type de la pièce
	 */
	public abstract boolean peutSeDeplacer(int ligneArrivee, int colonneArrivee, Plateau plateau);
	
	/*
	 Une méthode pour récupérer tous les coups possibles dans un plateau
	 */
	public ArrayList<Coup> getCoupsPossibles(Plateau plateau){
		ArrayList<Coup> coupsPossibles = new ArrayList<Coup>();
		for(int ligne = 0 ; ligne < 8 ; ligne++) {
			for(int colonne = 0 ; colonne < 8 ; colonne++) {
				if(this.peutSeDeplacer(ligne, colonne, plateau)) {
					coupsPossibles.add(new Coup(this.ligne, this.colonne, ligne, colonne));
				}
			}
		}
		return coupsPossibles;
	}
}