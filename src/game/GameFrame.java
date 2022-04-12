package game;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.Timer;

public class GameFrame extends JFrame implements ActionListener {
	public static final int WIDTH = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static final int HEIGHT = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	public static GameCanvas canvas;
	
	public static boolean lastEsc;
     
    public GameFrame() {
    	super("Tim Bones");

		setSize(WIDTH,HEIGHT);
		
		canvas = new GameCanvas(WIDTH,HEIGHT);

		getContentPane().add(canvas);
		
		getContentPane().setBackground(Color.BLACK);
		
		setFocusable(true);
		setUndecorated(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		lastEsc = false;
    }
    
    public void draw(){
    	Timer timer = new Timer(1000/60, this);
    	timer.setRepeats(true);
    	timer.start();
    }

	@Override
	public void actionPerformed(ActionEvent e) {	
		
		/*if(!Keys.getValue(27) && lastEsc){
			setUndecorated(!isUndecorated());
		}
		
		lastEsc = Keys.getValue(27);
		*/
		canvas.paint(canvas.getGraphics());
		
	}
}
