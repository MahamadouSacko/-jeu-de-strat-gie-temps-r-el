package gameView;

import java.util.LinkedList;
public class ListOfGrill {
	private int pas ;
	private LinkedList<CordGrille> lg;
	private CordGrille grille;
	public ListOfGrill(int pas,CordGrille grille) {
		super();
		this.pas = pas;
		this.lg=new LinkedList<CordGrille>();
		this.grille=grille;
		/*on construit d'abord les lignes verticales*/
		constructionV();
		/*on construit ensuite les lignes horizontale */
		constructionH();
	}

	/*x = position en x du début de la grille et d'un petit carreau
	  y = position en y du début de la grille et d'un petit carreau
      w = largeur de la grille
	  h = hauteur de la grille
      pas = pas de la grille 
	 */
	public void constructionV() {
		int x=grille.getX();
		int y=grille.getY();
		int h=grille.getH();
		int w=grille.getW();
		while(h>0) {
			lg.add(new CordGrille(x+pas*h,y,x+pas*w,y+h*pas));
			h--;
		}
		lg.add(new CordGrille(x,y,x,y*pas*h));
	}
	public void constructionH() {
		int x=grille.getX();
		int y=grille.getY();
		int h=grille.getH();
		int w=grille.getW();
		while(w>0) {
			lg.add(new CordGrille(x,y+w*pas,x+pas*w,y+h*pas));
			w--;
		}
		lg.add(new CordGrille(x,y,x*pas*w,y));
	}
	public LinkedList<CordGrille> getLg() {
		return lg;
	}
}
