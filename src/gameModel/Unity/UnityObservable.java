package gameModel.Unity;
import java.util.ArrayList;
import java.util.List;

import gameModel.Coordinate;
import gameModel.GameObserver;
import gameModel.Path.Node;
import gameModel.Unity.UnityEvent.EventType;
import gameModel.Unity.UnityState.State;
public  class UnityObservable  {

	private int life;
	private int myIndex;

	private  List<Node> path;
	private List<GameObserver> observers;
	private UnityState state;

	private Order action;
	private Direction direction;
	private Muddy stateMuddy;
	
	private Coordinate position;
	private Coordinate homePos;
	private Coordinate minePos;
	private Coordinate coinPosition;
	private Coordinate posA;
	private Coordinate posB;
	private Coordinate positionGoToWent;
	private Coordinate EnemyPos;
	private Coordinate teleporteurDoor;
	private Coordinate teleporterPosition;

	private boolean walking;
	private boolean firstclick;

	public enum Muddy{Yes,Non}
	public enum Direction {Home,Gold,StayHome,PosA,PosB,Teleporteur,Stay}
	public enum Order{Attack,move,Find,Follow,}


	public UnityObservable(Coordinate position,int life,Coordinate home) {
		this.position=position;
		this.life=life;
		this.walking=false;
		state = new NormalState(this);
		observers = new ArrayList<>();
		homePos=home;
		firstclick=false;
		action=Order.move;
		positionGoToWent=position;
		stateMuddy=Muddy.Non;
	}
	public int getLife() {
		return life;
	}
	protected void setLife(int life) {
		this.life = life;
	}
	void notifyObserver(List<UnityEvent> events) {
		for (GameObserver gameObserver : observers) {
			gameObserver.notify(events);
		}
	}
	public Coordinate getPosition() {
		return position;
	}
	protected void setPosition(Coordinate position) {
		this.position = position;
	}
	protected boolean isWalking() {
		return walking;
	}
	protected void setWalking(boolean walking) {
		this.walking = walking;
	}
	public List<Node> getPath() {
		return path;
	}
	protected void setPath(List<Node> path) {
		this.path = path;
	}
	public Coordinate getMinePosition() {
		return minePos;
	}
	public void setMinePosition(Coordinate goldPos) {
		minePos = goldPos;
	}
	protected Coordinate getIndexCoinPosition() {
		return coinPosition;
	}
	public void setIndexCoinPosition(Coordinate coinPosition) {
		this.coinPosition = coinPosition;
	}
	public State getState() {
		return state.getState();
	}
	public Coordinate getHomePos() {
		return homePos;
	}
	protected void setHomePos(Coordinate homePos) {
		this.homePos = homePos;
	}
	protected Direction getDirection() {
		return direction;
	}
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	protected int getMyIndex() {
		return myIndex;
	}
	public void setMyIndex(int myPos) {
		this.myIndex = myPos;
	}
	public Coordinate getPositionA() {
		return posA;
	}
	public void setPositionsA(Coordinate posA) {
		this.posA = posA;
		firstclick=true;
	}
	public Coordinate getPositionB() {
		return posB;
	}
	public void setPositionB(Coordinate posB) {
		this.posB = posB;
		firstclick=false;
	}
	public boolean isFirstclick() {
		return firstclick;
	}
	protected void setFirstclick(boolean firstclisk) {
		this.firstclick = firstclisk;
	}
	public Order getAction() {
		return action;
	}
	public void setAction(Order action) {
		this.action = action;
	}
	public Coordinate getPositionGoToWent() {
		return positionGoToWent;
	}
	public void setPositionGoToWent(Coordinate posGoal) {
		positionGoToWent = posGoal;
	}
	public Coordinate getEnemyPos() {
		return EnemyPos;
	}
	protected void setEnemyPos(Coordinate enemyPos) {
		EnemyPos = enemyPos;
	}
	public Coordinate getTeleporteurDoor() {
		return teleporteurDoor;
	}
	public void setTeleporteurDoor(Coordinate teleDoor) {
		teleporteurDoor = teleDoor;
	}
	public Coordinate getTeleporterPosition() {
		return teleporterPosition;
	}
	public void setTeleporterPosition(Coordinate telePos) {
		teleporterPosition = telePos;
	}
	public Muddy getStateMuddy() {
		return stateMuddy;
	}
	public void setStateMuddy(Muddy stateMuddy) {
		this.stateMuddy = stateMuddy;
	}


	public void register(GameObserver o) {
		observers.add(o);
	}

	public void unregister(GameObserver o) {
		observers.remove(o);
	}
	public void followPath(List<Node> chemin){
		this.setPath(chemin);
		setWalking(true);
	}
	public void changeState(UnityState state) {
		this.state = state;
	}

	protected void notifyMove() {
		List<UnityEvent> events = new ArrayList<>();
		events.add(new UnityEvent(myIndex, EventType.Move));
		notifyObserver(events);
	}
	protected void notifyPlayerByHarvest() {
		if(direction==Direction.Gold) {
			List<UnityEvent> events = new ArrayList<>();
			events.add(new UnityEvent(myIndex, EventType.Come));
			notifyObserver(events);
		}else {
			List<UnityEvent> events = new ArrayList<>();
			events.add(new UnityEvent(myIndex, EventType.Start));
			notifyObserver(events);
		}
	}
	protected void notifyPlayerBySentinel() {
		if(direction==Direction.PosA) {
			List<UnityEvent> events = new ArrayList<>();
			events.add(new UnityEvent(myIndex, EventType.PosA));
			notifyObserver(events);
		}else if(direction==Direction.PosB){
			List<UnityEvent> events = new ArrayList<>();
			events.add(new UnityEvent(myIndex, EventType.PosB));
			notifyObserver(events);
		}
	}
	protected void notifyPlayerByAttacker() {
		List<UnityEvent> events = new ArrayList<>();
		events.add(new UnityEvent(myIndex, EventType.AttackAttacher));
		notifyObserver(events);
	}
	protected void notifyPlayerBySentinelForAttack() {
		List<UnityEvent> events = new ArrayList<>();
		events.add(new UnityEvent(myIndex, EventType.AttackSentinel));
		notifyObserver(events);
	}
	protected void notifyPlayerForTeleporter() {
		List<UnityEvent> events = new ArrayList<>();
		events.add(new UnityEvent(myIndex, EventType.Teleporter));
		notifyObserver(events);
	}
	protected void notifyPlayerForMuddy() {
		List<UnityEvent> events = new ArrayList<>();
		events.add(new UnityEvent(myIndex, EventType.Muddy));
		notifyObserver(events);
	}

	public void move() {
		if(action==Order.Attack) {
			state.action();
			notifyMove();
		}else if(action==Order.move||action==Order.Follow) {
			if(direction==Direction.Teleporteur) {
				if(getPosition().equals(teleporteurDoor)) {
					notifyPlayerForTeleporter();
					notifyMove();
					return;
				}
			}
			if (getPath() == null){
				setWalking(false);
				return;
			}if (getPath().size() <= 0){
				setWalking(false);
				setPath(null);
				return ;
			}

			getPosition().setX(getPath().get(0).getX());
			getPosition().setY(getPath().get(0).getY());
			getPath().remove(0);
			notifyPlayerForMuddy();
			notifyMove();
			state.action();
		}else if(action==Order.Find) {
			state.action();
			notifyMove();
		}
	}

}
