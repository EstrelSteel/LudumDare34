package com.estrelsteel.engine1.handler;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.estrelsteel.engine1.Engine1;

public class PlayerHandler extends Handler {
	
	public enum PlayerControls {
		UP(87, 79, 38),
		DOWN(83, 76, 40),
		RIGHT(68, 186, 39),
		LEFT(65, 75, 37),
		SPACE(32, 13, 9);
		
		private int primary;
		private int secondary;
		private int tertiary;
		private boolean pressed;
		
		PlayerControls(int primary, int secondary, int tertiary) {
			this.primary= primary;
			this.secondary = secondary;
			this.tertiary = tertiary;
			this.pressed = false;
		}
		
		public int getPrimaryKey() {
			return primary;
		}
		
		public int getSecondaryKey() {
			return secondary;
		}
		
		public int getTertiaryKey() {
			return tertiary;
		}
		
		public boolean isPressed() {
			return pressed;
		}
		
		public void setPressed(boolean pressed) {
			this.pressed = pressed;
		}
	}
	
	public PlayerHandler(String name) {
		super(name);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(!Engine1.twoButtonControl && (PlayerControls.UP.getPrimaryKey() == e.getKeyCode()  || PlayerControls.UP.getSecondaryKey() == e.getKeyCode() || PlayerControls.UP.getTertiaryKey() == e.getKeyCode())) {
			PlayerControls.UP.setPressed(true);
		}
		if(!Engine1.twoButtonControl && (PlayerControls.DOWN.getPrimaryKey() == e.getKeyCode()  || PlayerControls.DOWN.getSecondaryKey() == e.getKeyCode() || PlayerControls.DOWN.getTertiaryKey() == e.getKeyCode())) {
			PlayerControls.DOWN.setPressed(true);
		}
		if(!Engine1.twoButtonControl && (PlayerControls.RIGHT.getPrimaryKey() == e.getKeyCode()  || PlayerControls.RIGHT.getSecondaryKey() == e.getKeyCode() || PlayerControls.RIGHT.getTertiaryKey() == e.getKeyCode())) {
			PlayerControls.RIGHT.setPressed(true);
		}
		if(!Engine1.twoButtonControl && (PlayerControls.LEFT.getPrimaryKey() == e.getKeyCode()  || PlayerControls.LEFT.getSecondaryKey() == e.getKeyCode() || PlayerControls.LEFT.getTertiaryKey() == e.getKeyCode())) {
			PlayerControls.LEFT.setPressed(true);
		}
		if(PlayerControls.SPACE.getPrimaryKey() == e.getKeyCode()  || PlayerControls.SPACE.getSecondaryKey() == e.getKeyCode() || PlayerControls.SPACE.getTertiaryKey() == e.getKeyCode()) {
			PlayerControls.SPACE.setPressed(true);
		}
		if(Engine1.twoButtonControl && !(PlayerControls.SPACE.getPrimaryKey() == e.getKeyCode()  || PlayerControls.SPACE.getSecondaryKey() == e.getKeyCode() || PlayerControls.SPACE.getTertiaryKey() == e.getKeyCode())) {
			Engine1.moveDirection++;
			if(Engine1.moveDirection >= 4) {
				Engine1.moveDirection = 0;
			}
			if(Engine1.moveDirection == 0) {
				PlayerControls.UP.setPressed(true);
				PlayerControls.DOWN.setPressed(false);
				PlayerControls.RIGHT.setPressed(false);
				PlayerControls.LEFT.setPressed(false);
			}
			else if(Engine1.moveDirection == 1) {
				PlayerControls.UP.setPressed(false);
				PlayerControls.DOWN.setPressed(false);
				PlayerControls.RIGHT.setPressed(true);
				PlayerControls.LEFT.setPressed(false);
			}
			else if(Engine1.moveDirection == 2) {
				PlayerControls.UP.setPressed(false);
				PlayerControls.DOWN.setPressed(true);
				PlayerControls.RIGHT.setPressed(false);
				PlayerControls.LEFT.setPressed(false);
			}
			else {
				PlayerControls.UP.setPressed(false);
				PlayerControls.DOWN.setPressed(false);
				PlayerControls.RIGHT.setPressed(false);
				PlayerControls.LEFT.setPressed(true);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(!Engine1.twoButtonControl && (PlayerControls.UP.getPrimaryKey() == e.getKeyCode()  || PlayerControls.UP.getSecondaryKey() == e.getKeyCode() || PlayerControls.UP.getTertiaryKey() == e.getKeyCode())) {
			PlayerControls.UP.setPressed(false);
		}
		if(!Engine1.twoButtonControl && (PlayerControls.DOWN.getPrimaryKey() == e.getKeyCode()  || PlayerControls.DOWN.getSecondaryKey() == e.getKeyCode() || PlayerControls.DOWN.getTertiaryKey() == e.getKeyCode())) {
			PlayerControls.DOWN.setPressed(false);
		}
		if(!Engine1.twoButtonControl && (PlayerControls.RIGHT.getPrimaryKey() == e.getKeyCode()  || PlayerControls.RIGHT.getSecondaryKey() == e.getKeyCode() || PlayerControls.RIGHT.getTertiaryKey() == e.getKeyCode())) {
			PlayerControls.RIGHT.setPressed(false);
		}
		if(!Engine1.twoButtonControl && (PlayerControls.LEFT.getPrimaryKey() == e.getKeyCode()  || PlayerControls.LEFT.getSecondaryKey() == e.getKeyCode() || PlayerControls.LEFT.getTertiaryKey() == e.getKeyCode())) {
			PlayerControls.LEFT.setPressed(false);
		}
		if(PlayerControls.SPACE.getPrimaryKey() == e.getKeyCode()  || PlayerControls.SPACE.getSecondaryKey() == e.getKeyCode() || PlayerControls.SPACE.getTertiaryKey() == e.getKeyCode()) {
			PlayerControls.SPACE.setPressed(false);
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

}
