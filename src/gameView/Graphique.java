package gameView;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.JComponent;

import gameModel.Game;
import gameModel.Map.Coin;
import gameModel.Map.MapUnite;
import gameModel.Map.MapUnite.Type;
import gameModel.Unity.PlayerObserver;
import gameModel.Unity.UnityState;




public class Graphique extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4673125144231580622L;
	private static Map<UnityState.State, Color> colorUnite = new HashMap<UnityState.State, Color>();
	private static Map<String, Color> colorPlayer = new HashMap<String, Color>();
	private static Map<MapUnite.Type, Color> colorMap = new HashMap<MapUnite.Type, Color>();
	public final static int WIDTH = 570;//1220/2;
	public final static int HEIGHT =660;//851/2;
	private ListOfGrill lg;
	private Game game;
	private PlayerObserver player;
	private static int Scale=50;
	static {
		colorUnite.put(UnityState.State.NORMAL, Color.BLACK);
		colorUnite.put(UnityState.State.ATTACKER, Color.RED);
		colorUnite.put(UnityState.State.SENTINEL, Color.ORANGE);
		colorUnite.put(UnityState.State.HARVEST, Color.YELLOW);
	}
	static {
		colorMap.put(MapUnite.Type.CASE, Color.WHITE);
		colorMap.put(MapUnite.Type.Piece, new Color(255, 204, 51));
		colorMap.put(MapUnite.Type.Muddy, new Color(128, 0,0));
		colorMap.put(MapUnite.Type.PlayeOneHome, Color.BLUE);
		colorMap.put(MapUnite.Type.PlayerTwoHome, Color.RED);
		colorMap.put(MapUnite.Type.Teleport, new Color(102, 0,153));
		colorMap.put(MapUnite.Type.Teleport, new Color(102, 0,153));
		colorMap.put(MapUnite.Type.TREE, new Color(0, 102,0));
	}
	static {
		colorPlayer.put("One", Color.BLUE);
		colorPlayer.put("Two", Color.RED);
	}

	public Graphique(Game game,int w,int h,PlayerObserver player) {
		lg= new ListOfGrill(50,new CordGrille(0,0,w,h));
		this.game=game;
		this.player=player;
		setSize(WIDTH, HEIGHT);
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		drawMap(g,game.getMap().MapFordraw(player.getDecalageX(),player.getDecalageY()));
		drawPlayer(g,game.getMap().getPlayerOne());
		drawPlayer(g,game.getMap().getPlayerTwo());
		drawLines(g, lg.getLg());
		drawPanel(g);

	}
	private void drawLines(Graphics g, LinkedList<CordGrille> lg) {
		for (int i = 0; i < lg.size(); i++) {
			CordGrille l = lg.get(i);
			g.setColor(Color.BLACK);
			g.drawLine(l.getX(),l.getY(),l.getW(),l.getH());
		}
	}

	public void drawMap(Graphics g,MapUnite [] [] mapUnite) {
		for (int i = 0; i <mapUnite.length; i++) {
			for(int j = 0; j <mapUnite[0].length; j++) {
				if(mapUnite[i][j].getType()==Type.Piece) {
					g.setColor(colorMap.get(mapUnite[i][j].getType()));
					g.fill3DRect(i*Scale, j*Scale, Scale, Scale,false);
					g.setColor(Color.YELLOW);
					Coin u=(Coin)mapUnite[i][j];
					g.drawString(u.toString(),i*Scale+(Scale-15)/2, j*Scale+(Scale-15)/2);
				}else if(mapUnite[i][j].getType()==Type.Teleport) {
					g.setColor(Color.white);
					g.fill3DRect(i*Scale, j*Scale, Scale, Scale,false);
					g.setColor(colorMap.get(mapUnite[i][j].getType()));
					g.fillOval(i*Scale,  j*Scale, Scale, Scale);
					g.setColor(Color.cyan);
					g.drawString("T",i*Scale+Scale/2, j*Scale+Scale/2);
				}else {
					g.setColor(colorMap.get(mapUnite[i][j].getType()));
					g.fill3DRect(i*Scale, j*Scale, Scale, Scale,false);
				}
			}
		}
	}
	public void drawPanel(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect(450,500, 100,50);
		g.setColor(Color.WHITE);
		g.drawString("Tresor: "+player.getTtresor(),470 ,515);
		g.drawString("nb Unite:"+player.getUnite().size(),470 ,535);
	}
	public void drawPlayer(Graphics g,PlayerObserver player) {
		for (int i = 0; i <player.getUnite().size(); i++) {
			int x=player.getUnite().get(i).getPosition().getX()-this.player.getDecalageX();
			int y=player.getUnite().get(i).getPosition().getY()-this.player.getDecalageY();
			g.setColor(colorPlayer.get(player.getName()));
			g.fill3DRect(x*Scale,y*Scale, Scale, Scale,false);
			g.setColor(colorUnite.get(player.getUnite().get(i).getState()));
			g.fillOval(x*Scale, y*Scale, Scale, Scale);
			g.setColor(Color.WHITE);
			g.drawString(""+player.getUnite().get(i).getLife(),x*Scale, y*Scale);
			
		}
	}
}
