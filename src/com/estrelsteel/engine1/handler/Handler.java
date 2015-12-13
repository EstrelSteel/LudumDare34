package com.estrelsteel.engine1.handler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.estrelsteel.engine1.Engine1;
import com.estrelsteel.engine1.world.World;

public abstract class Handler implements KeyListener, MouseListener {
	
	private String name;
	
	public Handler(String name) {
		this.name = name;
	}
	
	public static void loadHandlers(Engine1 engine, World world) {
		engine.addKeyListener(engine.playerHandler);
		engine.addMouseListener(engine.playerHandler);
		engine.addKeyListener(engine.lvl1ButtonHandler);
		engine.addMouseListener(engine.lvl1ButtonHandler);
		engine.addKeyListener(engine.mmButtonHandler);
		engine.addMouseListener(engine.mmButtonHandler);
		engine.addKeyListener(engine.lvl2ButtonHandler);
		engine.addMouseListener(engine.lvl2ButtonHandler);
		engine.addKeyListener(engine.lvl3ButtonHandler);
		engine.addMouseListener(engine.lvl3ButtonHandler);
		engine.addKeyListener(engine.lvl4ButtonHandler);
		engine.addMouseListener(engine.lvl4ButtonHandler);
		engine.addKeyListener(engine.lvl5ButtonHandler);
		engine.addMouseListener(engine.lvl5ButtonHandler);
		engine.addKeyListener(engine.lvl6ButtonHandler);
		engine.addMouseListener(engine.lvl6ButtonHandler);
		engine.addKeyListener(engine.lvl7ButtonHandler);
		engine.addMouseListener(engine.lvl7ButtonHandler);
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public abstract void mouseClicked(MouseEvent e);

	@Override
	public abstract void mouseEntered(MouseEvent e);

	@Override
	public abstract void mouseExited(MouseEvent e);
		
	@Override
	public abstract void mousePressed(MouseEvent e);
		
	@Override
	public abstract void mouseReleased(MouseEvent e);

	@Override
	public abstract void keyPressed(KeyEvent e);
		
	@Override
	public abstract void keyReleased(KeyEvent e);		

	@Override
	public abstract void keyTyped(KeyEvent e);

}
