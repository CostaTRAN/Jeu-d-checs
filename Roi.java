
public class Roi extends Piece {
	
	public Roi(boolean blanc) {
		super(blanc);
		if (blanc) {
			symbole = "RB";
		} else {
			symbole = "RN";
		}
	}
	
	
}
