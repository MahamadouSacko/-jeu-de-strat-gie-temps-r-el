package gameModel.Map;

public interface MapUnite {
	public enum Type{CASE,Muddy,TREE,PlayeOneHome,Piece,Teleport,PlayerTwoHome};
	abstract Type getType();
}
