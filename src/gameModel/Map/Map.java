package gameModel.Map;

import java.util.ArrayList;
import java.util.List;

import gameModel.Coordinate;
import gameModel.Map.MapUnite.Type;
import gameModel.Unity.PlayerObserver;
import gameModel.Unity.UnityObservable;

public class Map {
	private PlayerObserver playerOne;
	private PlayerObserver playerTwo;
	private MapUnite [] [] mapUnite;

	private int height;
	private int width;
	private static int nbcase=11;
	private int coinInmap;
	private List<Coordinate> outputTeleport;

	public Map(PlayerObserver one,PlayerObserver two,int [][]tab) {
		this.playerOne=one;
		this.playerTwo=two;
		coinInmap=0;
		outputTeleport=new ArrayList<>();
		CreateMap(tab);
		CreatUnitPlayerOne(tab);
		CreatUnitPlayerTwo(tab);
		this.height= tab.length;
		this.width=tab[0].length;
		initTeleporteur();

	}
	public PlayerObserver getPlayerOne() {
		return playerOne;
	}
	public PlayerObserver getPlayerTwo() {
		return playerTwo;
	}
	public MapUnite[][] getMapUnite() {
		return mapUnite;
	}
	public MapUnite getMapElement(int x,int y) {
		return mapUnite[x][y];
	}
	public void setMapElement(int x,int y,MapUnite u) {
		mapUnite[x][y]=u;
	}
	public void setMapUnite(MapUnite[][] mapUnite) {
		this.mapUnite = mapUnite;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public void CreatUnitPlayerOne(int[][] tab) {
		int k=0;
		for (int i = 0; i < tab.length; i++) {
			for (int j = 0; j < tab[0].length; j++) {
				switch (tab[i][j]) {
				case 10:
					playerOne.getUnite().add(new UnityObservable(new Coordinate(i, j),50,playerOne.getHomePos()));
					playerOne.getUnite().get(k).register(playerOne);
					k++;
					break;
				default:
					break;

				}
			}
		}

	}
	public void CreatUnitPlayerTwo(int[][] tab) {
		int k=0;
		for (int i = 0; i < tab.length; i++) {
			for (int j = 0; j < tab[0].length; j++) {
				switch (tab[i][j]) {
				case 11:
					playerTwo.getUnite().add(new UnityObservable(new Coordinate(i, j),50,playerTwo.getHomePos()));
					playerTwo.getUnite().get(k).register(playerTwo);
					k++;
					break;
				default:
					break;

				}
			}
		}
	}

	public List<Coordinate> getOutputTeleport() {
		return outputTeleport;
	}
	public void setOutputTeleport(List<Coordinate> outputTeleport) {
		this.outputTeleport = outputTeleport;
	}
	public int getCoinInmap() {
		return coinInmap;
	}
	public void setCoinInmap(int coinInmap) {
		this.coinInmap = coinInmap;
	}

	public MapUnite[][] MapFordraw(int decalageX,int decalageY) {
		MapUnite[][] temp=new MapUnite[nbcase][nbcase];
		for (int i =0; i < nbcase; i++) {
			for (int j =0; j <nbcase; j++) {
				temp[i][j]=mapUnite[i+decalageX][j+decalageY];
			}
		}
		return temp;
	}

	public void initTeleporteur() {
		int k=outputTeleport.size()-1;
		for (int i = 0; i < mapUnite.length; i++) {
			for (int j = 0; j < mapUnite[0].length; j++) {
				if(mapUnite[i][j].getType()==Type.Teleport) {
					Teleporteur t=(Teleporteur)mapUnite[i][j];
					t.setPosArrivee(outputTeleport.get(k));
					mapUnite[i][j]=t;
					k--;
				}
			}
		}
	}

	public void CreateMap(int[][] tab) {
		mapUnite=new MapUnite[tab.length][tab[0].length];
		for (int i = 0; i < mapUnite.length; i++) {
			for (int j = 0; j < mapUnite[0].length; j++) {
				switch (tab[i][j]) {
				case 0:
					mapUnite[i][j]=new Case(new Coordinate(i, j)); 
					break;
				case 1:
					mapUnite[i][j]=new Tree(new Coordinate(i, j));
					break;
				case 2:
					mapUnite[i][j]=new Muddy(new Coordinate(i, j)); 
					break;
				case 3:
					mapUnite[i][j]=new Teleporteur(new Coordinate(i, j),new Coordinate(i, j+1));
					mapUnite[i][j+1]=new Case(new Coordinate(i, j+1)); 
					j++;
					break;
				case 4:
					mapUnite[i][j]=new Case(new Coordinate(i, j));
					mapUnite[i][j+1]=new Teleporteur(new Coordinate(i, j+1),new Coordinate(i, j));
					j++;
					break;
				case 5:
					mapUnite[i][j]=new Coin(new Coordinate(i, j),new Coordinate(i, j+1),20);
					j++;
					mapUnite[i][j]=new Case(new Coordinate(i, j));
					coinInmap++;
					break;
				case 6:
					mapUnite[i][j]=new PlayeOneHome(new Coordinate(i, j));
					break;
				case 7:
					mapUnite[i][j]=new Case(new Coordinate(i, j)); 
					playerOne.setHomePos(new Coordinate(i, j));
					break;
				case 8:
					mapUnite[i][j]=new PlayerTwoHome(new Coordinate(i, j));
					break;
				case 9:
					mapUnite[i][j]=new Case(new Coordinate(i, j)); 
					playerTwo.setHomePos(new Coordinate(i, j));
					break;
				case 13:
					mapUnite[i][j]=new Case(new Coordinate(i, j)); 
					mapUnite[i][j+1]=new Coin(new Coordinate(i, j+1),new Coordinate(i, j),20);
					coinInmap++;
					j++;
					break;
				case 12:
					mapUnite[i][j]=new Case(new Coordinate(i, j)); 
					outputTeleport.add(new Coordinate(i, j));
					break;
				case 14:
					mapUnite[i][j]=new Case(new Coordinate(i, j)); 
					playerOne.getSquaresAround().add(new Coordinate(i, j));
					break;
				case 15:
					mapUnite[i][j]=new Case(new Coordinate(i, j)); 
					playerTwo.getSquaresAround().add(new Coordinate(i, j));
					break;
				default:
					mapUnite[i][j]=new Case(new Coordinate(i, j)); 
					break;
				}
			}
		}
	}

	public boolean recolte(Coordinate pos) {
		if(getMapElement(pos.getX(), pos.getY()).getType()==Type.Piece) {
			Coin u=(Coin)getMapElement(pos.getX(), pos.getY());
			if(u.getQuantity()<=0) {
				setMapElement(pos.getX(), pos.getY(), new Case(new Coordinate(pos.getX(), pos.getY())));
				setCoinInmap(getCoinInmap()-1);
				return false;
			}else {
				u.setQuantity(u.getQuantity()-10);
				if(u.getQuantity()<=0) {
					setMapElement(pos.getX(), pos.getY(), new Case(new Coordinate(pos.getX(), pos.getY())));
					setCoinInmap(getCoinInmap()-1);
					return true;
				}else {
					setMapElement(pos.getX(), pos.getY(), u);
					return true;
				}
			}
		}
		return false;

	}
	public boolean revenir(Coordinate pos) {
		if(getMapElement(pos.getX(), pos.getY()).getType()==Type.Piece) {
			return true;
		}
		return false;

	}
	public Coordinate getTeleporArrive(Coordinate pos) {
		Teleporteur tel=(Teleporteur)getMapElement(pos.getX(), pos.getY());
		return tel.getPosArrivee();	
	}
	public Coordinate getTeleporDor(Coordinate pos) {
		Teleporteur tel=(Teleporteur)getMapElement(pos.getX(), pos.getY());
		return tel.getDoor();	
	}
	public Boolean nextPosisMuddy(Coordinate pos) {
		if(getMapElement(pos.getX(), pos.getY()).getType()==Type.Muddy) {
			return true;
		}
		return false;
	}
	public Coordinate getCoinDor(Coordinate pos) {
		Coin c=(Coin)getMapElement(pos.getX(), pos.getY());
		return c.getDoor();	
	}
}
