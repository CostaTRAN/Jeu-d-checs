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
	 Getters et setters
	 */
	
	public int getLigne() {
		return ligne;
	}

	public void setLigne(int ligne) {
		this.ligne = ligne;
	}

	public int getColonne() {
		return colonne;
	}

	public void setColonne(int colonne) {
		this.colonne = colonne;
	}

	public boolean isBlanc() {
		return this.blanc;
	}

	public void setBlanc(boolean blanc) {
		this.blanc = blanc;
	}

	public void setSymbole(String symbole) {
		this.symbole = symbole;
	}
	
	public String getSymbole() {
		return this.symbole;
	}
	
	public void deplacer(Coup coup, Plateau plateau, ArrayList<Coup> historiqueDesCoups) {
		plateau.setPiece(coup.getLigneArrivee(), coup.getColonneArrivee(), this);
		plateau.setPiece(ligne, colonne, null);
		historiqueDesCoups.add(coup);
		ligne = coup.getLigneArrivee();
		colonne = coup.getColonneArrivee();
	}
	
	/*
	 Ne pas surcharger simulerDeplacement
	 */
	
	public void simulerDeplacement(int ligneArrivee, int colonneArrivee, Plateau plateau) {
		plateau.setPiece(ligneArrivee, colonneArrivee, this);
		plateau.setPiece(ligne, colonne, null);
		ligne = ligneArrivee;
		colonne = colonneArrivee;
	}
	
	public abstract boolean peutSeDeplacer(int ligneArrivee, int colonneArrivee, Plateau plateau);
	
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
