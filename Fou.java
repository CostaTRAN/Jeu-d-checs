
public class Fou extends Piece {
	public Fou(boolean blanc) {
		super(blanc);
		if (blanc) {
			symbole = "FB";
		} else {
			symbole = "FN";
		}
	}
}
