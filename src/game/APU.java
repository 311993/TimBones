package game;

import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Control;
import javax.sound.sampled.Control.Type;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;

public class APU implements Clip {

	@Override
	public void drain() {
		// TODO Auto-generated method stub

	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub

	}

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isRunning() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public AudioFormat getFormat() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getBufferSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int available() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getFramePosition() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getLongFramePosition() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getMicrosecondPosition() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getLevel() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public javax.sound.sampled.Line.Info getLineInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void open() throws LineUnavailableException {
		// TODO Auto-generated method stub

	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isOpen() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Control[] getControls() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isControlSupported(Type control) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Control getControl(Type control) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addLineListener(LineListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeLineListener(LineListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void open(AudioFormat format, byte[] data, int offset, int bufferSize) throws LineUnavailableException {
		// TODO Auto-generated method stub

	}

	@Override
	public void open(AudioInputStream stream) throws LineUnavailableException, IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getFrameLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getMicrosecondLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setFramePosition(int frames) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setMicrosecondPosition(long microseconds) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLoopPoints(int start, int end) {
		// TODO Auto-generated method stub

	}

	@Override
	public void loop(int count) {
		// TODO Auto-generated method stub

	}

}