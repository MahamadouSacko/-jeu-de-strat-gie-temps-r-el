package gameModel.Map;

import gameModel.Coordinate;


public class PlayeOneHome implements MapUnite{
	private Coordinate position;
	public PlayeOneHome(Coordinate position) {
		this.setPosition(position);
	}
	@Override
	public Type getType() {
		// TODO Auto-generated method stub
		return Type.PlayeOneHome;
	}
	public Coordinate getPosition() {
		return position;
	}
	public void setPosition(Coordinate position) {
		this.position = position;
	}
}
