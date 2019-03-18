package gameLstener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import gameModel.Coordinate;
import gameModel.Game;
import gameModel.GameObserver;
import gameModel.Map.MapUnite.Type;
import gameModel.Path.Node;
import gameModel.Path.ShortPath;
import gameModel.Unity.AttackerState;
import gameModel.Unity.HarvestState;
import gameModel.Unity.NormalState;
import gameModel.Unity.PlayerObserver;
import gameModel.Unity.SentinelState;
import gameModel.Unity.UnityObservable;
import gameModel.Unity.UnityEvent;
import gameModel.Unity.UnityObservable.Direction;
import gameModel.Unity.UnityObservable.Order;
import gameModel.Unity.UnityEvent.EventType;
import gameModel.Unity.UnityState.State;

public class MyListener extends MouseAdapter implements KeyListener,GameObserver {
	private Game game;
	private PlayerObserver player;
	private boolean selection;
	private int index;
	public MyListener(Game game,PlayerObserver player) {
		super();
		this.game = game;
		this.player=player;
		selection=false;
	}
	@Override
	public void mousePressed(MouseEvent e) {
		int mx = (e.getX()/50)+player.getDecalageX();
		int my = (e.getY()/50)+player.getDecalageY();
		if(my>19) {
			deselection();
		}
		if(selection==false) {
			index=player.getUniteElement(mx, my);
			if(index!=-1) {
				selection=true;
			}
		}else {
			player.getUnite().get(index).setMyIndex(index);
			if(game.getMap().getMapElement(mx, my).getType()==Type.CASE||game.getMap().getMapElement(mx, my).getType()==Type.Muddy) {
				movement(mx, my, index);
			}else if(game.getMap().getMapElement(mx, my).getType()==Type.Piece) {
				harvesting(mx, my, index);
			}else if(game.getMap().getMapElement(mx, my).getType()==Type.Teleport) {
				teleportation(mx, my, index);
			}else {
				deselection();
			}

		}
	}
	
	@Override
	public void keyPressed(KeyEvent e){
		if(e.getKeyCode()==39&&(player.getDecalageX()+10)<29) {
			player.setDecalageX(player.getDecalageX()+1);
		}else if(e.getKeyCode()==37&&player.getDecalageX()>0 ) {
			player.setDecalageX(player.getDecalageX()-1);
		}else if(e.getKeyCode()==40&&(player.getDecalageY()+10)<19) {
			player.setDecalageY(player.getDecalageY()+1);
		} else if(e.getKeyCode()==38&&player.getDecalageY()>0) {
			player.setDecalageY(player.getDecalageY()-1);	
		}
		player.notifyMove();
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}
	
	private void movement(int mx,int my,int index) {
		if(player.getUnite().get(index).getState()==State.SENTINEL) {
			if(player.getUnite().get(index).isFirstclick()==false) {
				player.getUnite().get(index).setPositionsA(new Coordinate(mx, my));
			}else {
				player.getUnite().get(index).setPositionB(new Coordinate(mx, my));
				player.getUnite().get(index).setMyIndex(index);
				player.getUnite().get(index).setDirection(Direction.PosA);
				SearchPath(index,player.getUnite().get(index).getPositionA().getX(),player.getUnite().get(index).getPositionA().getY());
				deselection();
			}
		}else {
			if(player.getUnite().get(index).getState()==State.ATTACKER) {
				player.getUnite().get(index).setPositionGoToWent(new Coordinate(mx, my));
				player.getUnite().get(index).setAction(Order.move);
			}
			SearchPath(index, mx, my);
			deselection();
		}
	}
	
	private void harvesting(int mx,int my,int index) {
		if(player.getUnite().get(index).getState()==State.HARVEST) {
			player.getUnite().get(index).setDirection(Direction.Gold);
			player.getUnite().get(index).setIndexCoinPosition(new Coordinate(mx, my));
			Coordinate posCoin=game.getMap().getCoinDor(new Coordinate(mx, my));
			player.getUnite().get(index).setMinePosition(posCoin);
			SearchPath(index,player.getUnite().get(index).getMinePosition().getX(),player.getUnite().get(index).getMinePosition().getY());
			deselection();
		}else {
			deselection();
		}
	}
	
	private void teleportation(int mx,int my,int index) {
		if(player.getUnite().get(index).getState()!=State.SENTINEL) {
			player.getUnite().get(index).setDirection(Direction.Teleporteur);
			player.getUnite().get(index).setTeleporterPosition(new Coordinate(mx, my));
			Coordinate posTelep=game.getMap().getTeleporDor(new Coordinate(mx, my));
			if(player.getUnite().get(index).getState()==State.ATTACKER) {
				player.getUnite().get(index).setPositionGoToWent(posTelep);
				player.getUnite().get(index).setAction(Order.move);
			}
			player.getUnite().get(index).setTeleporteurDoor(posTelep);
			SearchPath(index,player.getUnite().get(index).getTeleporteurDoor().getX(),player.getUnite().get(index).getTeleporteurDoor().getY());
			deselection();
		}else {
			deselection();
		}
	}
	private void SearchPath(int i,int mx,int my) {
		ShortPath che=new ShortPath(game.getMap().getMapUnite());
		int startX=player.getUnite().get(i).getPosition().getX();
		int  startY=player.getUnite().get(i).getPosition().getY();
		List<Node> chemin=che.trouveChemin(startX, startY, mx, my);
		player.getUnite().get(i).followPath(chemin);
	}
	private void deselection() {
		selection=false;
		index=-1;
	}
	@Override
	public void notify(List<UnityEvent> events) {
		if(selection==true) {
			for (UnityEvent unityEvent : events) {
				if(unityEvent.getType()==EventType.NormalState ) {
					player.getUnite().get(index).changeState(new NormalState(player.getUnite().get(index)));
					player.notifyMove();
				}else if(unityEvent.getType()==EventType.SentinelState ) {
					if(player.getTresor()>=20) {
						player.setTresor(player.getTresor()-20);
						player.getUnite().get(index).changeState(new SentinelState(player.getUnite().get(index)));
						player.notifyMove();
					}
				}if(unityEvent.getType()==EventType.AttackerState) {
					if(player.getTresor()>=30) {
						player.setTresor(player.getTresor()-30);
						player.getUnite().get(index).changeState(new AttackerState(player.getUnite().get(index)));
						player.notifyMove();
					}
				}if(unityEvent.getType()==EventType.HarvestState ) {
					player.getUnite().get(index).changeState(new HarvestState(player.getUnite().get(index)));
					player.notifyMove();
				}
			}
			deselection();
		}else {
			for (UnityEvent unityEvent : events) {
				if(unityEvent.getType()==EventType.Add) {
					if(player.getTresor()>=50) {
						player.setTresor(player.getTresor()-50);
						UnityObservable u=new UnityObservable(player.getSquaresAround().get((int) (Math.random()*player.getSquaresAround().size())),50,player.getHomePos());
						u.register(player);
						game.addObserver(game.getGraphique(), game.getGraphique2(), u);
						player.getUnite().add(u);
						player.notifyMove();
					}
				}
			}
			deselection();
		}
	}
}
