
public abstract class Piece {
	
	protected boolean blanc;
	protected String symbole;
	
	public Piece(boolean blanc) {
		this.blanc = blanc;
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
	
	public void deplacer(Case depart, Case arrivee, Plateau plateau) {
	}
	
	public boolean peutSeDeplacer(Case depart, Case arrivee, Plateau plateau) {
		return false;
	}
}
