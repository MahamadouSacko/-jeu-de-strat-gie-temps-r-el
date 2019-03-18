package gameModel;


import javax.swing.JOptionPane;

import gameModel.Map.Map;
import gameModel.Unity.PlayerObserver;
import gameModel.Unity.UnityObservable;
import gameModel.Unity.UnityObservable.Muddy;
import gameView.Gui;

public class Game{
	private Map map;
	private Gui graphique;
	private Gui graphique2;
	public Game(int [][]tab) {
		this.map=new Map(new PlayerObserver(this,250,0,4,"One"),new PlayerObserver(this,250,19,4,"Two"),tab);
	}
	public Game() {
		this.run();
	}
	public Map getMap() {
		return map;
	}
	public void setMap(Map map) {
		this.map = map;
	}
	public void initObserver(GameObserver o,GameObserver p, PlayerObserver player ) {
		for (int i = 0; i < player.getUnite().size(); i++) {
			player.getUnite().get(i).register(o);
			player.getUnite().get(i).register(p);
		}
		player.register(o);
		player.register(p);
	}
	public void addObserver(GameObserver o,GameObserver p,UnityObservable u ) {
		u.register(o);
		u.register(p);
	}
	public void step(PlayerObserver player) {
		for (int i = 0; i < player.getUnite().size(); i++) {
			if(player.getUnite().get(i).getStateMuddy()==Muddy.Non){
				player.getUnite().get(i).move();
			}
		}
	}
	public void step2(PlayerObserver player) {
		for (int i = 0; i < player.getUnite().size(); i++) {
			if(player.getUnite().get(i).getStateMuddy()==Muddy.Yes){
				player.getUnite().get(i).move();
			}
		}
	}
	public Gui getGraphique() {
		return graphique;
	}
	public void setGraphique(Gui graphique) {
		this.graphique = graphique;
	}
	public Gui getGraphique2() {
		return graphique2;
	}
	public void setGraphique2(Gui graphique2) {
		this.graphique2 = graphique2;
	}
	public void run() {
		/* 0 sol vierge*/ /* 1 Arbre*/ /* 2 zone boueuses*/ /* 3 teleporteurs*/ /* 4 entre du teleporteur*/
		/* 5 piles de pieces d’or*/  /*  13 entre pour le pile de pieces d’or*/
		/* 6 maison mere du 1er jouer*/ /* 7 entre du maison mere du 1er jouer*/ /* 10 position de se 3 premier unite*/
		/* 8 maison mere du 2er jouer*/ /* 9 entre du maison mere du 2er jouer*/ /* 11 position de se 3 premier unite*/
		/*12 sortie de teleporteur*/  /* 14 case libre du 1 er jouer*/
		int[][] tab = { //
				/*0  1  2  3  4  5  6  7  8  9  10 11 12 13 14 15 16 17 18 19*/
				/*0*/{ 0, 0, 0, 0, 0, 0, 0, 0, 14, 6, 6, 14, 0, 0, 0, 0, 0, 0, 0, 0 },
				/*1*/{ 0, 0, 0, 0, 0, 0, 0, 0, 14, 6, 6, 14, 0, 1, 0, 0, 0, 0, 0, 0 }, 
				/*2*/{ 0, 0, 0, 0, 0, 0, 0, 0, 14, 14, 7, 14, 0, 0, 0, 0, 0, 0, 0, 0 }, 
				/*3*/{ 0, 0, 0, 0, 1, 0, 0, 10, 14, 10, 14, 10, 0, 0, 0, 1, 0, 0, 0, 0 }, 
				/*4*/{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0 }, 
				/*5*/{ 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 13, 5 }, 
				/*6*/{ 0, 0, 0, 0, 0, 3, 4, 0, 0, 0, 2, 0, 0, 1, 0, 4, 3, 0, 1, 0 }, 
				/*7*/{ 0, 1, 0, 1, 0, 0, 0, 1, 0, 2, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0 }, 
				/*8*/{ 0, 0, 0, 0, 1, 0, 0, 0, 1, 2, 2, 2, 1, 0, 0, 1, 0, 12, 0, 0 }, 
				/*9*/{ 0, 0, 0, 12, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				/*10*/{0, 1, 0, 1, 0, 5, 13, 1, 0, 1, 0, 1, 0, 1, 0, 13, 5, 0, 0, 0 },
				/*11*/{0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0 },
				/*12*/{0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 12, 0, 0, 0, 1, 0, 0, 0, 0, 0 },
				/*13*/{0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
				/*14*/{0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				/*15*/{0, 0, 0, 0, 0, 0, 4, 3, 1, 5, 13, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				/*16*/{1, 0, 0, 0, 0, 0, 1, 2, 2, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },
				/*17*/{0, 1, 12, 0, 1, 0, 0, 2, 2, 1, 0, 0, 1, 0, 0, 13, 5, 0, 0, 0 },
				/*18*/{0, 0, 0, 1, 0, 0, 0, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				/*19*/{5, 13, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 12, 0, 0, 0 },
				/*20*/{0, 0, 0, 1, 0, 5, 13, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				/*21*/{0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0 },
				/*22*/{0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0 },
				/*23*/{0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 3, 0, 0, 0 },
				/*24*/{0, 0, 0, 0, 3, 4, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0 },
				/*25*/{0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0 },
				/*26*/{0, 1, 0, 0, 0, 0, 0, 11, 15, 11, 15, 11, 0, 0, 1, 0, 0, 0, 0, 0 },
				/*27*/{0, 0, 0, 0, 0, 0, 0, 0, 15, 9, 15,15, 0, 0, 1, 0, 1, 0, 0, 0 },
				/*28*/{0, 0, 0, 1, 0, 0, 0, 0, 15, 8, 8, 15, 0, 0, 0, 0, 0, 0, 0, 0 },
				/*29*/{0, 0, 0, 0, 0, 0, 0, 0, 15, 8, 8, 15, 0, 0, 0, 0, 0, 0, 0, 0 }};
		Game game = new Game(tab);
		Gui graph=new Gui(game,tab.length,tab[0].length,game.getMap().getPlayerOne(),100,100,"playerOne");
		Gui graph2=new Gui(game,tab.length,tab[0].length,game.getMap().getPlayerTwo(),800,100,"PlayerTwo");
		game.initObserver(graph,graph2,game.getMap().getPlayerOne());
		game.initObserver(graph,graph2,game.getMap().getPlayerTwo());
		game.getMap().getPlayerOne().setEnemy(game.getMap().getPlayerTwo());
		game.getMap().getPlayerTwo().setEnemy(game.getMap().getPlayerOne());
		game.setGraphique(graph);
		game.setGraphique2(graph2);
		long start = System.currentTimeMillis( );
		long start2 = System.currentTimeMillis( );
		while (game.getMap().getCoinInmap()>0) {
			long end = System.currentTimeMillis( );
			long diff = end - start;
			long diff2 = end - start2;
			if(diff>600) {
				game.step(game.getMap().getPlayerOne());
				game.step(game.getMap().getPlayerTwo());
				start = System.currentTimeMillis( );
			}
			if(diff2>1000) {
				game.step2(game.getMap().getPlayerOne());
				game.step2(game.getMap().getPlayerTwo());
				start2 = System.currentTimeMillis( );
			}
		}
		if(game.getMap().getPlayerOne().getTresor()>game.getMap().getPlayerTwo().getTresor()) {
			JOptionPane.showMessageDialog(null, "playerOne win");
		}else if(game.getMap().getPlayerOne().getTresor()<game.getMap().getPlayerTwo().getTresor()) {
			JOptionPane.showMessageDialog(null, "playerTwo win");
		}else {
			JOptionPane.showMessageDialog(null, " Egalites");
		}

	}


}
