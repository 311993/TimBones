package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keys implements KeyListener{

	private boolean[] values = new boolean[526];
	
	public Keys() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		try{ 
			values[e.getKeyCode()] = true;
		}catch(IndexOutOfBoundsException error){};
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		try{ 
			values[e.getKeyCode()] = false;
		}catch(IndexOutOfBoundsException error){};
		
	}
	
	public boolean getValue(int i) {
		return values[i];
	}

}