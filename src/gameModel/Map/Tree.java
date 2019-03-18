package gameModel.Map;

import gameModel.Coordinate;

public class Tree implements MapUnite {
	private Coordinate position;
	public Tree(Coordinate position) {
		super();
		this.setPosition(position);
	}

	@Override
	public Type getType() {
		return Type.TREE;
	}
	public Coordinate getPosition() {
		return position;
	}
	public void setPosition(Coordinate position) {
		this.position = position;
	}

}
