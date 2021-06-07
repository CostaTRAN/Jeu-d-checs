import java.util.ArrayList;

public class Plateau {
	private Piece[][] cases;
	private Roi roiBlanc;
	private Roi roiNoir;
	
	/*
	Initialisation du plateau de jeu avec le constructeur
	*/
	public Plateau() {
		cases = new Piece[8][8];
		roiBlanc = new Roi(true,0,4);
		roiNoir = new Roi(false,7,4);
		
		cases[0][0] = new Tour(true,0,0);
		cases[0][1] = new Cavalier(true,0,1);
		cases[0][2] = new Fou(true,0,2);
		cases[0][3] = new Dame(true,0,3); 
		cases[0][4] = roiBlanc;
		cases[0][5] = new Fou(true,0,5);
		cases[0][6] = new Cavalier(true,0,6);
		cases[0][7] = new Tour(true,0,7);
		
		cases[1][0] = new Pion(true,1,0);
		cases[1][1] = new Pion(true,1,1);
		cases[1][2] = new Pion(true,1,2);
		cases[1][3] = new Pion(true,1,3);
		cases[1][4] = new Pion(true,1,4);
		cases[1][5] = new Pion(true,1,5);
		cases[1][6] = new Pion(true,1,6);
		cases[1][7] = new Pion(true,1,7);
		
		cases[7][0] = new Tour(false,7,0);
		cases[7][1] = new Cavalier(false,7,1);
		cases[7][2] = new Fou(false,7,2);
		cases[7][3] = new Dame(false,7,3); 
		cases[7][4] = roiNoir;
		cases[7][5] = new Fou(false,7,5);
		cases[7][6] = new Cavalier(false,7,6);
		cases[7][7] = new Tour(false,7,7);
		
		cases[6][0] = new Pion(false,6,0);
		cases[6][1] = new Pion(false,6,1);
		cases[6][2] = new Pion(false,6,2);
		cases[6][3] = new Pion(false,6,3);
		cases[6][4] = new Pion(false,6,4);
		cases[6][5] = new Pion(false,6,5);
		cases[6][6] = new Pion(false,6,6);
		cases[6][7] = new Pion(false,6,7);  
		
		
		//cases[4][3] = new Case(4,3,new Dame(false));
		//cases[3][3] = new Case(3,3,new Fou(true));
		//cases[1][0] = new Pion(false,1,0);
		//cases[4][4] = new Pion(true,4,4);
		
		/*Vérification promotion
		cases[6][0] = new Pion(true,6,0);
		
		/*Vérification PAT
		cases[6][0] = new Dame(true, 6, 0);
		cases[1][3] = new Tour(true, 1, 3);
		cases[1][5] = new Tour(true, 1, 5);
		cases[7][0] = new Pion(false, 7, 0);*/
	} 
	
	/*
	 Affichage du plateau de jeu
	 */
	public String toString() {
		String s = "";
		for(int ligne = 7 ; ligne >= 0 ; ligne--) {
			s += " -------------------------\n";
			s += ligne + 1; //coordonnées en ligne
			for(int colonne = 0 ; colonne < 8 ; colonne++) {
				if(cases[ligne][colonne] != null) {
					s += "|"+ cases[ligne][colonne].getSymbole();
				} else {
					s += "|  ";
				}
			}
			s += "|";
			s += "\n";
		}
		s += " -------------------------\n";
		s += "  A  B  C  D  E  F  G  H\n"; //coordonnées en colonne
		return s;
	}
	
	/*
	 getters et setters
	 */
	
	public Piece getPiece(int ligne, int colonne) {
		if (ligne>7 || ligne<0 || colonne>7 || colonne<0) {
			return null;
		}
		return cases[ligne][colonne];
	}
	
	public void setPiece(int ligne, int colonne, Piece piece) {
		if (ligne>7 || ligne<0 || colonne>7 || colonne<0) {
			return;
		}
		cases[ligne][colonne] = piece;
	}
	
	public ArrayList<Piece> getPieces(boolean blanc){
		ArrayList<Piece> pieces = new ArrayList<Piece>();
		for (int ligne = 0 ; ligne < 8 ; ligne++) {
			for (int colonne = 0 ; colonne < 8 ; colonne++) {
				if (cases[ligne][colonne] == null) {
					continue;
				}
				Piece piece = cases[ligne][colonne];
				if(piece.isBlanc() == blanc) {
					pieces.add(piece);
				}
			}
		}
		return pieces;
	}
	
	public boolean caseExiste(int ligne, int colonne) {
		return ligne>=0 && ligne<=7 && colonne>=0 && colonne<=7;
	}

	public Roi getRoi(boolean blanc) {
		if(blanc) {
			return roiBlanc;
		}
		return roiNoir;
	}
	
	public void interdirePriseEnPassant(boolean blanc) {
		for (int ligne = 0 ; ligne < 8 ; ligne++) {
			for (int colonne = 0 ; colonne < 8 ; colonne++) {
				if(cases[ligne][colonne] == null || !(cases[ligne][colonne] instanceof Pion) 
						|| cases[ligne][colonne].isBlanc() != blanc) {
					continue;
				}
				
				Pion pion = (Pion)cases[ligne][colonne];
				if(pion.getPeutEtrePrisEnPassant()) {
					pion.setPeutEtrePrisEnPassant(false);
				}
			}
		}
	}
	
	public boolean estEnPat(boolean blanc) {
		if(getRoi(blanc).estEnEchec(this)) {
			return false;
		}
		
		ArrayList<Piece> mesPieces = getPieces(blanc);
		for(Piece maPiece : mesPieces) {
			ArrayList<Coup> coups = maPiece.getCoupsPossibles(this);
			if(!coups.isEmpty()) {
				return false;
			}
		}
		return true;
	}
}
