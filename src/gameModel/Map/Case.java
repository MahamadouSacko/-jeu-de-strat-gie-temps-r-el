package gameModel.Map;

import gameModel.Coordinate;


public class Case implements MapUnite {
	private Coordinate position;
	public Case(Coordinate position) {
		this.setPosition(position);
	}
	@Override
	public Type getType() {
		return Type.CASE;
	}
	public Coordinate getPosition() {
		return position;
	}
	public void setPosition(Coordinate position) {
		this.position = position;
	}
}
