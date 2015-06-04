package marioSokobanEditor;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class EditorBoard extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener{
	String editor [][] = new String[12][12];
	String imageSelect[] = {"WALL","BOX","MARIO","GOAL"};
	String imageCurrent = "WALL";
	int mX,mY;
	int indexInc = 0;
	Image mario, wall, box, goal;
	FileWriter fw;
	FileReader fr;
	
	
	public EditorBoard() {
		// Création d'objets image
		ImageIcon iMario = new ImageIcon("Sprites/mario_bas.gif");
		mario = iMario.getImage();
		
		ImageIcon iWall = new ImageIcon("Sprites/mur.jpg");
		wall = iWall.getImage();
		
		ImageIcon iBox = new ImageIcon("Sprites/caisse.jpg");
		box = iBox.getImage();
		
		ImageIcon iGoal = new ImageIcon("Sprites/objectif.png");
		goal = iGoal.getImage();
		
		setFocusable(true);
		
		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
		addKeyListener(this);
		
	}
	
	// Va dessiner notre plateau Exemple : Si il y a le mot "WALL" à indice ij alors il va le placer à cette endroit
	public void paint (Graphics g) {	
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		
		for (int i=0 ; i<=11 ; i++) {
			for (int j=0 ; j<=11 ; j++) {
				if (editor[i][j] == "WALL") {
					g2d.drawImage(wall, i * 34, j * 34, null);
				}
				if (editor[i][j] == "BOX") {
					g2d.drawImage(box, i * 34, j * 34, null);
				}
				if (editor[i][j] == "MARIO") {
					g2d.drawImage(mario, i * 34, j * 34, null);
				}
				if (editor[i][j] == "GOAL") {
					g2d.drawImage(goal, i * 34, j * 34, null);
				}
			}
		}
		
		if (imageCurrent == "WALL") {
			g2d.drawImage(wall, mX, mY, null);
		}
		else if (imageCurrent == "BOX") {
			g2d.drawImage(box, mX, mY, null);
		}
		else if (imageCurrent == "MARIO") {
			g2d.drawImage(mario, mX, mY, null);
		}
		else if (imageCurrent == "GOAL") {
			g2d.drawImage(goal, mX, mY, null);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		char key = e.getKeyChar();
		if (key == 's') { 
			try {
				fw = new FileWriter(JOptionPane.showInputDialog(null,"Entrez le chemin de la sauvegarde :","EDITEUR DE MAP",JOptionPane.QUESTION_MESSAGE));
				
				// Parcourt le tableau pour trouver chaque string 
				for (int i = 0; i < 12 ; i++) {
					for (int j = 0; j < 12; j++) {
						// Ecrit dans un fichier le numero correspondant au string à l'indice ij
						// on inverse i et j 
						if (editor[j][i] == "WALL") {
							fw.write("0");
						}
						else if (editor[j][i] == "MARIO") {
							fw.write("1");
						}
						else if (editor[j][i] == "BOX") {
							fw.write("2");
						}
						else if (editor[j][i] == "GOAL") {
							fw.write("3");
						}
						else if (editor[j][i] == null) {
							fw.write(" ");
						}
					}
					fw.write("\r\n");
				}
				fw.close();
			}
			catch (Exception ex) {}
		}
		else if (key == 'l') {
			try {
				fr = new FileReader(JOptionPane.showInputDialog(null,"Entrez le chemin de lecture :","EDITEUR LE CHEMIN DE LECTURE",JOptionPane.QUESTION_MESSAGE));
				int i = 0;
				int x = 0, y = 0;
				
				while ((i = fr.read()) != -1) {
					char strImg = (char) i;
					
					if(strImg == '0') {
						editor[x][y] = "WALL";
					}
					else if(strImg == '1') {
						editor[x][y] = "MARIO";
					}
					else if(strImg == '2') {
						editor[x][y] = "BOX";
					}
					else if(strImg == '3') {
						editor[x][y] = "GOAL";
					}
					else if(strImg == ' ') {
						editor[x][y] = null;
					}
					else if(strImg == '\r' || strImg == '\n') {
						x--;
					}
					
					if (x == 11) {
						y++;
						x = 0;
					}
					else {
						x++;
					}
					
				}
			}
			catch (Exception ex) {}
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int rotation = e.getWheelRotation();	// Recupere la rotation de la molette de la souris
		System.out.println(rotation);
		
		/** Si la valeur récuperé est négative => on décrémente jusqu'a 1 minimum **/
		if (rotation < 0) {
			if (indexInc > 0) {
				indexInc--;
			}
		}
		/** Si la valeur récupéré est positive => on incrémente jusqu'a 3 maximum **/
		else if (rotation > 0) {
			if (indexInc < 3) {
				indexInc++;
			}
		}
		imageCurrent = imageSelect[indexInc];
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mX = e.getX() - 17;		// La position de la souris sera toujours sur le centre de l'image en x
		mY = e.getY() - 17;		// idem en y
		
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int x = e.getX() / 34;	// Recupere la postion courante du curseur
		int y = e.getY() / 34;
		
		/** Clique gauche on pose l'élement clique droit on le supprime **/
		if (e.getButton() == MouseEvent.BUTTON1) {
			editor[x][y] = imageCurrent;
		}
		else if (e.getButton() == MouseEvent.BUTTON3) {
			editor[x][y] = null;
		}
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
