public class Joueur {
	private boolean blanc;
	private String couleur;

	public Joueur(boolean blanc) {
		this.blanc = blanc;
		if (blanc) {
			couleur = "blanc";
		} else {
			couleur = "noir";
		}
	}

	public boolean isBlanc() {
		return blanc;
	}

	public void setBlanc(boolean blanc) {
		this.blanc = blanc;
	}
	
	public String getCouleur() {
		return this.couleur;
	}
}
