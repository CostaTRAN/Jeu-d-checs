
public class Tour extends Piece {
	public Tour(boolean blanc) {
		super(blanc);
		if (blanc) {
			symbole = "TB";
		} else {
			symbole = "TN";
		}
	}
}
