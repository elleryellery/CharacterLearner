import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame{
	private static final int WIDTH =1210;
	private static final int HEIGHT=650;
	
	public Main () {
		super("Character Learner");
		setSize(WIDTH, HEIGHT);

		Game play = new Game();
		((Component) play).setFocusable(true);
		
		setBackground(Color.WHITE);
		getContentPane().add(play);
		setVisible(true);
		
		addWindowListener( new WindowListener() {

			@Override
			public void windowActivated(WindowEvent arg0) {
				
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				
			}

			@Override
			public void windowClosing(WindowEvent arg0) {

			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				
			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				
			}

			@Override
			public void windowOpened(WindowEvent arg0) {

			}  
		});
	}

	public static void main(String[] args) {
        PinyinInterpreter.CreateDictionary();
        FileReader.UploadCharacters();
        FileReader.AddCharacters();
		Main run = new Main();
	}
}
