package gameModel.Path;

import java.util.LinkedList;
import java.util.List;

import gameModel.Map.MapUnite;
import gameModel.Map.MapUnite.Type;


public class ShortPath {

	private int width;
	private int height;
	private Node[][] noeud;

	public ShortPath(MapUnite[][] carte){
		this.width = carte.length;
		this.height =carte[0].length;
		this.noeud = new Node[width][height];
		for (int x = 0; x < width; x++){
			for (int y = 0; y < height; y++)
			{
				noeud[x][y] = new Node(x, y,carte[x][y].getType()==Type.CASE||carte[x][y].getType()==Type.Muddy);
			}
		}	}


	public Node getNoeud(int x, int y){
		if (x >= 0 && x < width && y >= 0 && y < height){
			return noeud[x][y];
		}else{
			return null;
		}
	}

	private List<Node> getAdjacent(Node noeud, List<Node> listFerme){
		List<Node> noeudAdjacent = new LinkedList<Node>();
		int x = noeud.getX();
		int y = noeud.getY();

		Node voisin;
		if (x > 0){
			voisin = getNoeud(x-1, y);
			if (voisin != null && voisin.getWalkable() && !listFerme.contains(voisin)){
				noeudAdjacent.add(voisin);
			}
		}

		if (x < width){
			voisin = getNoeud(x+1, y);
			if (voisin != null && voisin.getWalkable() && !listFerme.contains(voisin)){
				noeudAdjacent.add(voisin);
			}
		}
		if (y<height){
			voisin = this.getNoeud(x, y+1);
			if (voisin != null && voisin.getWalkable() && !listFerme.contains(voisin)){
				noeudAdjacent.add(voisin);
			}
		}
		if (y>0){
			voisin = this.getNoeud(x, y-1);
			if (voisin != null && voisin.getWalkable() && !listFerme.contains(voisin)){
				noeudAdjacent.add(voisin);
			}
		}

		
		return noeudAdjacent;
	}



	private Node plusPetitF(List<Node> list){
		Node plusPetitNoeud = list.get(0);
		for (int i = 0; i < list.size(); i++){
			if (list.get(i).getF() < plusPetitNoeud.getF()){
				plusPetitNoeud = list.get(i);
			}
		}
		return plusPetitNoeud;
	}



	public final List<Node> trouveChemin(int startX, int startY, int endX, int endY){
		if (startX == endX && startY == endY){
			return new LinkedList<Node>();
		}
		List<Node> listeFermes = new LinkedList<Node>();
		List<Node> listeOuvers = new LinkedList<Node>();
		listeOuvers.add(noeud[startX][startY]);
		Node debut= noeud[startX][startY];
		while (!listeOuvers.isEmpty()){
			Node courant = plusPetitF(listeOuvers);
			listeOuvers.remove(courant);
			listeFermes.add(courant);
			if ((courant.getX() == endX) && (courant.getY() == endY)){
				LinkedList<Node> chemin = new LinkedList<Node>();
				Node noeud = courant;
				while (!noeud.equals(debut)){
					chemin.addFirst(noeud);
					noeud = noeud.getParent();
				}
				return chemin;
			}
			List<Node> noeudAdjacent = getAdjacent(courant, listeFermes);
			for (Node adjacent : noeudAdjacent){
				if (!listeOuvers.contains(adjacent)){
					adjacent.setParent(courant);
					adjacent.setH(noeud[endX][endY]);
					adjacent.setG(courant);
					listeOuvers.add(adjacent);
				}else if (adjacent.getG() > adjacent.calculDeG(courant)){
					adjacent.setParent(courant);
					adjacent.setG(courant);
				}
			}

		}
		return new LinkedList<Node>();
	}


}
