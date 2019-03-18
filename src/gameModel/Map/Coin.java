package gameModel.Map;

import gameModel.Coordinate;

public class Coin implements MapUnite{
	private Coordinate position;
	private Coordinate door;
	private int quantity;
	public Coin(Coordinate position,Coordinate door,int t) {
		this.position=position;
		this.door=door;
		this.quantity=t;
	}
	@Override
	public Type getType() {
		return Type.Piece;
	}
	public Coordinate getPosition() {
		return position;
	}
	public void setPosition(Coordinate position) {
		this.position = position;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String toString() {
		return "C:"+quantity;
	}
	public Coordinate getDoor() {
		return door;
	}
	public void setDoor(Coordinate door) {
		this.door = door;
	}
}
