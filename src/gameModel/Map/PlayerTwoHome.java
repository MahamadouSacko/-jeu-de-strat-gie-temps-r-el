package gameModel.Map;

import gameModel.Coordinate;


public class PlayerTwoHome implements MapUnite {
	private Coordinate position;
	public PlayerTwoHome(Coordinate position) {
		this.setPosition(position);
	}
	@Override
	public Type getType() {
		// TODO Auto-generated method stub
		return Type.PlayerTwoHome;
	}
	public Coordinate getPosition() {
		return position;
	}
	public void setPosition(Coordinate position) {
		this.position = position;
	}
}
