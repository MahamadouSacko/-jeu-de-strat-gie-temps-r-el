package gameModel.Unity;
import java.util.ArrayList;
import java.util.List;

import gameModel.Coordinate;
import gameModel.Game;
import gameModel.GameObserver;
import gameModel.Path.Node;
import gameModel.Path.ShortPath;
import gameModel.Unity.UnityObservable.Direction;
import gameModel.Unity.UnityObservable.Muddy;
import gameModel.Unity.UnityObservable.Order;
import gameModel.Unity.UnityEvent.EventType;
import gameModel.Unity.UnityState.State;

public class PlayerObserver implements GameObserver {
	private ArrayList<UnityObservable> unite;
	private List<GameObserver> observers;
	private List<Coordinate> squaresAround;

	private String name;
	private Coordinate homePos;
	private PlayerObserver Enemy;
	private Game game;

	private int tresor;
	private int decalageX;
	private int decalageY;

	public PlayerObserver( Game game,int tresor,int x,int y,String name) {
		this.tresor=tresor;
		this.game=game;
		this.unite=new ArrayList<>();
		squaresAround=new ArrayList<>();
		observers = new ArrayList<>();
		this.decalageX=x;
		this.decalageY=y;
		this.name=name;
	}
	public void register(GameObserver o) {
		observers.add(o);
	}

	public void unregister(GameObserver o) {
		observers.remove(o);
	}

	void notifyObserver(List<UnityEvent> events) {
		for (GameObserver gameObserver : observers) {
			gameObserver.notify(events);
		}
	}
	public void notifyMove() {
		List<UnityEvent> events = new ArrayList<>();
		events.add(new UnityEvent(0, EventType.Move));
		notifyObserver(events);
	}
	public ArrayList<UnityObservable> getUnite() {
		return unite;
	}

	public int getTresor() {
		return tresor;
	}
	public Coordinate getHomePos() {
		return homePos;
	}
	public void setHomePos(Coordinate homePos) {
		this.homePos = homePos;
	}
	public int getDecalageX() {
		return decalageX;
	}
	public void setDecalageX(int decalage) {
		this.decalageX = decalage;
	}
	public int getDecalageY() {
		return decalageY;
	}
	public PlayerObserver getEnemy() {
		return Enemy;
	}
	public void setEnemy(PlayerObserver enemy) {
		Enemy = enemy;
	}
	public void setTresor(int tresor) {
		this.tresor = tresor;
	}
	public void setDecalageY(int decalageY) {
		this.decalageY = decalageY;
	}
	public String getTtresor() {
		return ""+tresor;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Coordinate> getSquaresAround() {
		return squaresAround;
	}
	public void setSquaresAround(List<Coordinate> squaresAround) {
		this.squaresAround = squaresAround;
	}

	public int getUniteElement(int x,int y) {
		for (int i = 0; i <unite.size(); i++) {
			if(unite.get(i).getPosition().equals(new Coordinate(x, y))) {
				return i ;
			}
		}
		return -1;
	}

	public void majIndexForUnity() {
		for (int i = 0; i <unite.size(); i++) {
			unite.get(i).setMyIndex(i);
		}
	}

	@Override
	public void notify(List<UnityEvent> events) {
		for (UnityEvent unityEvent : events) {
			if(unityEvent.getType()==EventType.Come||unityEvent.getType()==EventType.Start) {
				treatNotifyHarvest(unityEvent);
			}else if(unityEvent.getType()==EventType.PosA||unityEvent.getType()==EventType.PosB||unityEvent.getType()==EventType.AttackSentinel) {
				treatNotifySentinel(unityEvent);
			}else if(unityEvent.getType()==EventType.Teleporter) {
				treatNotifyTeleport(unityEvent);
			}else if(unityEvent.getType()==EventType.AttackAttacher) {
				treatNotifyAttacker(unityEvent);
			}else if(unityEvent.getType()==EventType.Muddy) {
				treatNotifyMuddy(unityEvent);
			}

		}

	}

	public void SearchPath(int i,Coordinate target,Coordinate current) {
		ShortPath che=new ShortPath(game.getMap().getMapUnite());
		int startX=current.getX();
		int  startY=current.getY();
		int mx=target.getX();
		int my =target.getY();
		List<Node> chemin=che.trouveChemin(startX, startY, mx, my);
		unite.get(i).followPath(chemin);
	}


	public void treatNotifyTeleport (UnityEvent unityEvent) {
		int indice=unityEvent.getpos();
		unite.get(indice).setPosition(game.getMap().getTeleporArrive(unite.get(indice).getTeleporterPosition()));
		unite.get(indice).setDirection(Direction.Stay);
		if(unite.get(indice).getState()==State.ATTACKER)unite.get(indice).setAction(Order.Find);
	}
	public void treatNotifyMuddy (UnityEvent unityEvent) {
		int indice=unityEvent.getpos();
		if(game.getMap().nextPosisMuddy(unite.get(indice).getPosition())) {
			unite.get(indice).setStateMuddy(Muddy.Yes);
		}else {
			unite.get(indice).setStateMuddy(Muddy.Non);
		}
	}
	public void treatNotifyHarvest(UnityEvent unityEvent) {
		int indice=unityEvent.getpos();
		if(unityEvent.getType()==EventType.Come) {
			if(game.getMap().recolte(unite.get(indice).getIndexCoinPosition())) {
				if(game.getMap().revenir(unite.get(indice).getIndexCoinPosition())) {
					SearchPath(indice, unite.get(indice).getHomePos(),unite.get(indice).getPosition());
					unite.get(indice).setDirection(Direction.Home);
				}else {
					unite.get(indice).setDirection(Direction.StayHome);
					SearchPath(indice, unite.get(indice).getHomePos(),unite.get(indice).getPosition());
				}
			}else {
				unite.get(indice).setDirection(Direction.StayHome);
				SearchPath(indice, unite.get(indice).getHomePos(),unite.get(indice).getPosition());
			}
		}else if(unityEvent.getType()==EventType.Start) {
			setTresor(getTresor()+10);
			SearchPath(indice, unite.get(indice).getMinePosition(),unite.get(indice).getPosition());
		}
	}

	public void treatNotifySentinel(UnityEvent unityEvent) {
		int i=unityEvent.getpos();
		if(unityEvent.getType()==EventType.PosA) {
			if(unite.get(i).getState()==State.SENTINEL){
				SearchPath(i, unite.get(i).getPositionB(),unite.get(i).getPosition());
			}
		}else if(unityEvent.getType()==EventType.PosB) {
			if(unite.get(i).getState()==State.SENTINEL){
				SearchPath(i, unite.get(i).getPositionA(),unite.get(i).getPosition());
			}
		}else if(unityEvent.getType()==EventType.AttackSentinel) {
			int index=FindEnemy(unite.get(i).getPosition().getX(), unite.get(i).getPosition().getY());
			if(index!=-1) {
				unite.get(i).setAction(Order.Attack);
				Enemy.getUnite().get(index).setLife(Enemy.getUnite().get(index).getLife()-5);
				if(Enemy.getUnite().get(index).getLife()<=0){
					Enemy.getUnite().remove(index);
					Enemy.majIndexForUnity();
				}
			}else {
				unite.get(i).setAction(Order.move);
			}
		}
	}
	public void treatNotifyAttacker(UnityEvent unityEvent) {
		int i=unityEvent.getpos();
		int index=FindEnemy(unite.get(i).getPosition().getX(), unite.get(i).getPosition().getY());
		if(index!=-1) {
			unite.get(i).setAction(Order.Attack);
			Enemy.getUnite().get(index).setLife(Enemy.getUnite().get(index).getLife()-5);
			if(Enemy.getUnite().get(index).getLife()<=0){
				Enemy.getUnite().remove(index);
				Enemy.majIndexForUnity();
			}
		}else {
			index=MarkupArea(unite.get(i).getPosition().getX(), unite.get(i).getPosition().getY());
			if(index!=-1) {
				unite.get(i).setAction(Order.Follow);
				SearchPath(i,Enemy.getUnite().get(index).getPosition(),unite.get(i).getPosition());
			}else {
				if(unite.get(i).getAction()!=Order.Find) {
					SearchPath(i,unite.get(i).getPositionGoToWent(),unite.get(i).getPosition());
					unite.get(i).setAction(Order.move);
					Enemy.majIndexForUnity();
				}
			}
		}
	}

	public int FindEnemy(int x ,int y) {
		Coordinate hx;
		Coordinate hy;
		if((x-2)>0&&(y-2)>0) {
			hx=new Coordinate(x-2, y-2);
		}else if((x-1)>0&&(y-1)>0) {
			hx=new Coordinate(x-1, y-1);
		}else {
			hx=new Coordinate(x, y);
		}
		if((x+2)<game.getMap().getHeight()&&(y+2)<game.getMap().getWidth()) {
			hy=new Coordinate(x+2, y+2);
		}else if((x-1)<game.getMap().getHeight()&&(x-1)<game.getMap().getWidth()) {
			hy=new Coordinate(x+1, y+1);
		}else {
			hy=new Coordinate(x, y);
		}
		for (int i = 0; i <Enemy.getUnite().size(); i++) {
			if(hx.getX()<=Enemy.getUnite().get(i).getPosition().getX()&&hy.getX()>=Enemy.getUnite().get(i).getPosition().getX()) {
				if(hx.getY()<=Enemy.getUnite().get(i).getPosition().getY()&&hy.getY()>=Enemy.getUnite().get(i).getPosition().getY()) {
					return i;
				}
			}
		}
		return -1;

	}
	public int MarkupArea(int x ,int y) {
		Coordinate hx=new Coordinate(x-3, y-3);
		Coordinate hy=new Coordinate(x+3, y+3);
		for (int i = 0; i <Enemy.getUnite().size(); i++) {
			if(hx.getX()<=Enemy.getUnite().get(i).getPosition().getX()&&hy.getX()>=Enemy.getUnite().get(i).getPosition().getX()) {
				if(hx.getY()<=Enemy.getUnite().get(i).getPosition().getY()&&hy.getY()>=Enemy.getUnite().get(i).getPosition().getY()) {
					return i;
				}
			}
		}
		return -1;

	}


}
