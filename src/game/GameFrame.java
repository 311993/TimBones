package game;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.Timer;

public class GameFrame extends JFrame{
	public static final int WIDTH = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static final int HEIGHT = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	public static GameCanvas canvas;
     
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

		canvas.paint(canvas.getGraphics());
    }
}
