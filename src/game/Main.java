package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.Timer;

public class Main  implements ActionListener {
	
	public GameFrame frame;
	private static Main main;    
	private static boolean lastEsc;
	public static double deltaMillis, lastMillis, avgMillis;
	
    /**
     * Creates a new instance of <code>ProjectileMain</code>.
     */
    public Main() {
    	frame = new GameFrame(true);
    	frame.focus();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
    	System.setProperty( "sun.java2d.uiScale", "1.0" );
    	main = new Main();
    		
    	lastMillis = System.currentTimeMillis();
    	avgMillis = 0;
    	Timer timer = new Timer(1000/60, main);
    	timer.setRepeats(true);
    	timer.start();
        
    }
    
    @Override
	public void actionPerformed(ActionEvent e) {	
		
    	deltaMillis = System.currentTimeMillis() - lastMillis;
    	lastMillis += deltaMillis;
    	
    	if(avgMillis < 0.01) {
    		avgMillis = deltaMillis;
    	}else{
    		avgMillis = (avgMillis*99 + deltaMillis)/100;
    	}
    	
		if(!Keys.getValue(27) && lastEsc){
			frame.dispose();
			frame = new GameFrame(!frame.isUndecorated());
		}
		
		lastEsc = Keys.getValue(27);
		
		main.frame.draw();
		
	}
}