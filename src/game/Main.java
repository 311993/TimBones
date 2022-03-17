package game;

import java.io.IOException;
import java.util.Arrays;

public class Main {
	
	public GameFrame frame;    
    /**
     * Creates a new instance of <code>ProjectileMain</code>.
     */
    public Main() {
    	frame = new GameFrame();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
    	System.setProperty( "sun.java2d.uiScale", "1.0" );
    	Main main = new Main();
    		
        
    	main.frame.draw();
    }
}