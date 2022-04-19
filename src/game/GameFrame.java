package game;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.Timer;

public class GameFrame extends JFrame{
	private static final int WIDTH = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private static final int HEIGHT = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	private static GameCanvas canvas;
     
    public GameFrame() {
    	this(true);
    }
    
    public GameFrame(boolean undecorated) {
    	super("Tim Bones");

		setSize(WIDTH,HEIGHT);
		
		if(canvas == null) {
			canvas = new GameCanvas(WIDTH,HEIGHT);
		}
		
		getContentPane().add(canvas);
		getContentPane().setBackground(Color.BLACK);
		
		setFocusable(true);
		setUndecorated(undecorated);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		try {
			setIconImage(ImageIO.read(new File("src\\assets\\timBonesHat.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setVisible(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		toFront();
		
		canvas.requestFocus();
	}

	public void draw(){

		canvas.paint(canvas.getGraphics());
    }
}
