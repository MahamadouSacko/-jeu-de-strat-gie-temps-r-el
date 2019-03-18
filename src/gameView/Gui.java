package gameView;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import gameLstener.MyListener;
import gameModel.Game;
import gameModel.GameObserver;
import gameModel.Unity.PlayerObserver;
import gameModel.Unity.UnityEvent;
import gameModel.Unity.UnityEvent.EventType;

public class Gui implements GameObserver {
	private JFrame frame ;
	private Graphique component;
	private MyListener lister;
	private List<GameObserver> observers;
	private JPanel buttonPanel;
	private JMenuBar menubar;
	private JMenu menu;
	private JMenuItem menuitem;
	private JButton SentinelButton,HarvestButton,AttakerButton,AddButton;

	
	private ActionListener SetinelListener = new ActionListener() {            
		public void actionPerformed(ActionEvent e){      
			List<UnityEvent> events = new ArrayList<>();
			events.add(new UnityEvent(0, EventType.SentinelState));
			notifyObserver(events);
			component.setFocusable(true);
			component.requestFocus();
			component.requestFocusInWindow();
		}
	};
	private ActionListener HarvestListener = new ActionListener() {            
		public void actionPerformed(ActionEvent e){      
			List<UnityEvent> events = new ArrayList<>();
			events.add(new UnityEvent(0, EventType.HarvestState));
			notifyObserver(events);
			component.setFocusable(true);
			component.requestFocus();
			component.requestFocusInWindow();
		}
	};
	private ActionListener AttakerListener = new ActionListener() {            
		public void actionPerformed(ActionEvent e){      
			List<UnityEvent> events = new ArrayList<>();
			events.add(new UnityEvent(0, EventType.AttackerState));
			notifyObserver(events);
			component.setFocusable(true);
			component.requestFocus();
			component.requestFocusInWindow();
		}
	};
	private ActionListener AddUnityListener = new ActionListener() {            
		public void actionPerformed(ActionEvent e){ 
			List<UnityEvent> events = new ArrayList<>();
			events.add(new UnityEvent(0, EventType.Add));
			notifyObserver(events);
			component.setFocusable(true);
			component.requestFocus();
			component.requestFocusInWindow();
		}
	};
	public Gui(Game game,int i,int j,PlayerObserver player,int x,int y,String name) {
		component=new Graphique(game,i,j,player);
		
		frame = new JFrame(name);
		frame.setContentPane(component);
		frame.setSize(component.getSize());
		frame.setLocation(x, y);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		observers = new ArrayList<>();
		lister=new MyListener(game,player);
		
		component.addMouseListener(lister);
		component.addKeyListener(lister);
		component.setFocusable(true);
		component.requestFocus();
		component.requestFocusInWindow();
		frame.setLayout(new BorderLayout());
		frame.setJMenuBar(menubar);
		/* on initialise les trois boutons et on y attache les listeners */

		SentinelButton = new JButton("Sentinel");
		SentinelButton.setBackground(Color.ORANGE);
		SentinelButton.addActionListener(SetinelListener);

		HarvestButton = new JButton("Harvest");
		HarvestButton.setBackground(Color.YELLOW);
		HarvestButton.addActionListener(HarvestListener);

		AttakerButton = new JButton("Attaker");
		AttakerButton.setBackground(Color.RED);
		AttakerButton.addActionListener(AttakerListener);

		AddButton = new JButton("AddUnity");
		AddButton.setBackground(Color.WHITE);
		AddButton.addActionListener(AddUnityListener);


		/* on insère les trois panneaux dans un panneau en flow 
           et on ajoute ce panneau à la fenêtre */ 

		buttonPanel = new JPanel( );
		buttonPanel.setBackground(Color.LIGHT_GRAY);
		buttonPanel.setLayout(new FlowLayout( ));
		buttonPanel.add(HarvestButton);
		buttonPanel.add(SentinelButton);
		buttonPanel.add(AttakerButton);
		buttonPanel.add(AddButton);
	
		frame.add(buttonPanel, BorderLayout.SOUTH);
		
		/* on initialise le menu et on y attache les listeners */

		menubar = new JMenuBar();

		menu = new JMenu("Menu");
		
		menubar.add(menu);
		menuitem = new JMenuItem("Restart");

		menu.add(menuitem);
		frame.setJMenuBar(menubar);
		/* on rend la fenêtre visible */
		frame.setVisible(true);
		register(lister);

	}

	public MyListener getLister() {
		return lister;
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

	@Override
	public void notify(List<UnityEvent> events) {
		for (UnityEvent unityEvent : events) {
			if(unityEvent.getType()==EventType.Move ) {
				component.repaint();
				
			}
		}


	}
}
