
public class Cavalier extends Piece {
	public Cavalier(boolean blanc) {
		super(blanc);
		if (blanc) {
			symbole = "CB";
		} else {
			symbole = "CN";
		}
	}
}
