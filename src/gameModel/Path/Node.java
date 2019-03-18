package gameModel.Path;

public class Node {
	private int x;
	private int y;
	private boolean walkable;
	private Node parent;
	private int g;
	private static int CoutDuMouvement = 10;
	private int h;
	public Node(int x, int y, boolean walkable){
		this.x = x;
		this.y = y;
		this.walkable = walkable;
	}
	public void setH(Node goal){
		h = (Math.abs(getX() - goal.getX()) + Math.abs(getY() - goal.getY())) * CoutDuMouvement;
	}
	public int getX(){
		return x;
	}
	public void setG(Node parent){
		g = (parent.getG() + CoutDuMouvement);
	}
	public int calculDeG(Node parent){
		return (parent.getG() + CoutDuMouvement);
	}
	public void setX(int x){
		this.x = x;
	}
	public Node getParent(){
		return parent;
	}
	public void setParent(Node parent){
		this.parent = parent;
	}
	public int getF(){
		return g + h;
	}
	public int getG(){
		return g;
	}
	public int getH(){
		return h;
	}
	public int getY(){
		return y;
	}
	public void setY(int y){
		this.y = y;
	}
	public boolean getWalkable(){
		return walkable;
	}
	public void setWalkable(boolean isBlock){
		this.walkable = isBlock;
	}

}
