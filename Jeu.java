import java.util.ArrayList;
import java.util.Scanner;

public class Jeu {
	
	private Plateau plateau;
	private Joueur joueur1;
	private Joueur joueur2;
	private Joueur joueurCourant;
	private String fichierSauvegarde;
	private ArrayList<Coup> coups;
	

	public static void main(String[] args) {
		Plateau plateau = new Plateau();
		plateau.afficher();
		Scanner myObj = new Scanner(System.in);
		System.out.println("Entrez le numéro de la colonne de la pièce à déplacer (entre 1 et 8) :");
		int colonneDepart = myObj.nextInt() - 1;
		System.out.println("Entrez le numéro de la ligne de la pièce à déplacer (entre 1 et 8) :");
		int ligneDepart = myObj.nextInt() - 1;
		
		System.out.println("Entrez le numéro de la colonne d'arrivée (entre 1 et 8) :");
		int colonneArrivee = myObj.nextInt() - 1;
		System.out.println("Entrez le numéro de la ligne d'arrivée (entre 1 et 8) :");
		int ligneArrivee = myObj.nextInt() - 1;
		Case caseDepart = plateau.getCase(ligneDepart, colonneDepart);
		if (caseDepart == null) {
			System.out.println("Erreur");
			return;
		}
		Case caseArrivee = plateau.getCase(ligneArrivee, colonneArrivee);
		if (caseArrivee == null) {
			System.out.println("Erreur");
			return;
		}
		Piece piece = caseDepart.getPiece();
		if(piece != null && piece.peutSeDeplacer(caseDepart, caseArrivee, plateau)) {
			piece.deplacer(caseDepart,caseArrivee, plateau);
		}
		plateau.afficher();
	}

}
