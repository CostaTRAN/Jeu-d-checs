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
	
	/*
	 Constructeur
	 */
	public Coup(int ligneDepart, int colonneDepart, int ligneArrivee, int colonneArrivee) {
		this.ligneDepart = ligneDepart;
		this.colonneDepart = colonneDepart;
		this.ligneArrivee = ligneArrivee;
		this.colonneArrivee = colonneArrivee;
		this.numPromotionChoisie = 0;
	}
	
	/*
	 Getters
	 */
	public int getLigneDepart() {
		return ligneDepart;
	}

	
	public int getColonneDepart() {
		return colonneDepart;
	}

	
	public int getLigneArrivee() {
		return ligneArrivee;
	}
	
	
	public int getColonneArrivee() {
		return colonneArrivee;
	}
	
	public int getNumPromotionChoisie() {
		return numPromotionChoisie;
	}
	
	/*
	 Un setter pour le numéro de la promotion choisie pour la promotion
	 */

	public void setNumPromotionChoisie(int numPromotionChoisie) {
		this.numPromotionChoisie = numPromotionChoisie;
	}
	
	/*
	 Affichage d'un coup
	 */
	public String toString() {
		if (numPromotionChoisie < 1 || numPromotionChoisie > 4)
			return "" + (colonneDepart + 1) + (ligneDepart + 1) + (colonneArrivee + 1) + (ligneArrivee + 1);
		else
			return "" + (colonneDepart + 1) + (ligneDepart + 1) + (colonneArrivee + 1) + (ligneArrivee + 1) + numPromotionChoisie;
	}
}