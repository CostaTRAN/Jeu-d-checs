
public class Reine extends Piece {
	
	public Reine(boolean blanc) {
		super(blanc);
		if (blanc) {
			symbole = "DB";
		} else {
			symbole = "DN";
		}
	}
}
