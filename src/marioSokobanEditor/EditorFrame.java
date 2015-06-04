package marioSokobanEditor;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class EditorFrame extends JFrame {	// This is Window Game 
	public EditorFrame(){
		this.setTitle("MODE CREATION");
		this.setSize(408,408);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(new EditorBoard());
		this.setVisible(true);
	}
}
