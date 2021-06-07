
public class Coup {
	private int ligneDepart;
	private int colonneDepart;
	private int ligneArrivee;
	private int colonneArrivee;
	/*
	 * Notation internationale pour numPromotionChoisie
	 * numPromotionChoisie vaut soit : 0 si pas de promotion, 1 pour Dame, 2 pour Tour, 3 pour Fou ou 4 pour Cavalier
	 */
	private int numPromotionChoisie;
	
	public Coup(int ligneDepart, int colonneDepart, int ligneArrivee, int colonneArrivee) {
		this.ligneDepart = ligneDepart;
		this.colonneDepart = colonneDepart;
		this.ligneArrivee = ligneArrivee;
		this.colonneArrivee = colonneArrivee;
		this.numPromotionChoisie = 0;
	}
	
	public int getLigneDepart() {
		return ligneDepart;
	}
	
	public void setLigneDepart(int ligneDepart) {
		this.ligneDepart = ligneDepart;
	}
	
	public int getColonneDepart() {
		return colonneDepart;
	}
	
	public void setColonneDepart(int colonneDepart) {
		this.colonneDepart = colonneDepart;
	}
	
	public int getLigneArrivee() {
		return ligneArrivee;
	}
	
	public void setLigneArrivee(int ligneArrivee) {
		this.ligneArrivee = ligneArrivee;
	}
	
	public int getColonneArrivee() {
		return colonneArrivee;
	}
	
	public void setColonneArrivee(int colonneArrivee) {
		this.colonneArrivee = colonneArrivee;
	}
	
	public String toString() {
		if (numPromotionChoisie < 1 || numPromotionChoisie > 4)
			return "" + (colonneDepart + 1) + (ligneDepart + 1) + (colonneArrivee + 1) + (ligneArrivee + 1);
		else
			return "" + (colonneDepart + 1) + (ligneDepart + 1) + (colonneArrivee + 1) + (ligneArrivee + 1) + numPromotionChoisie;
	}

	public int getNumPromotionChoisie() {
		return numPromotionChoisie;
	}

	public void setNumPromotionChoisie(int numPromotionChoisie) {
		this.numPromotionChoisie = numPromotionChoisie;
	}
}
