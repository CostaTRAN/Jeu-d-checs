public class Joueur {
	private boolean blanc;
	private String couleur;
	
	/*
	 Constructeur
	 */

	public Joueur(boolean blanc) {
		this.blanc = blanc;
		if (blanc) {
			couleur = "blanc";
		} else {
			couleur = "noir";
		}
	}

	/*
	 Getters
	 */
	public boolean isBlanc() {
		return blanc;
	}

	public String getCouleur() {
		return this.couleur;
	}
}