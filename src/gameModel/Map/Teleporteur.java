package gameModel.Map;

import gameModel.Coordinate;


public class Teleporteur implements MapUnite {
	private Coordinate position;
	private Coordinate posArrivee;
	private Coordinate door;
	public Teleporteur(Coordinate position,Coordinate door) {
		this.position=position;
		this.door=door;
   }
	@Override
	public Type getType() {
		return Type.Teleport;
	}
	
	public Coordinate getPosition() {
		return position;
	}
	public void setPosition(Coordinate position) {
		this.position = position;
	}
	public Coordinate getDoor() {
		return door;
	}
	public void setDoor(Coordinate door) {
		this.door = door;
	}
	public Coordinate getPosArrivee() {
		return posArrivee;
	}
	public void setPosArrivee(Coordinate posArrivee) {
		this.posArrivee = posArrivee;
	}
}
