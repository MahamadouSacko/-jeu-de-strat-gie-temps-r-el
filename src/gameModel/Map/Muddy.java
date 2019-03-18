package gameModel.Map;

import gameModel.Coordinate;


public class Muddy implements MapUnite {
	private Coordinate position;
	public Muddy(Coordinate position) {
		this.position=position;
	}
	@Override
	public Type getType() {
		return Type.Muddy;
	}
	public Coordinate getPosition() {
		return position;
	}
	public void setPosition(Coordinate position) {
		this.position = position;
	}

}
