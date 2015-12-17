package com.estrelsteel.engine1;

import java.applet.Applet;
import java.awt.BorderLayout;

import javax.swing.JFrame;

import com.estrelsteel.engine1.handler.CoreHandler;

@SuppressWarnings("serial")
public class Launcher extends Applet {
	private static Engine1 engine = new Engine1();
	
	@Override
	public void init() {
		setLayout(new BorderLayout());
		add(engine, BorderLayout.CENTER);
		setFocusable(true);
		setMinimumSize(Engine1.dimension);
		setMaximumSize(Engine1.dimension);
		setPreferredSize(Engine1.dimension);
		setVisible(true);
		engine.applet = true;
	}
	
	@Override
	public void start() {
		engine.start();
		return;
	}
	
	@Override
	public void stop() {
		engine.stop();
		return;
	}
	
	public static void main(String[] args) {
		engine.setFocusable(true);
		engine.setMinimumSize(Engine1.dimension);
		engine.setMaximumSize(Engine1.dimension);
		engine.setPreferredSize(Engine1.dimension);
		
		JFrame frame = new JFrame(engine.title + " " + engine.version + " (" + engine.build + ")");
		
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		engine.coreHandler = new CoreHandler(engine);
		
		frame.addWindowListener(engine.coreHandler);
		frame.addWindowFocusListener(engine.coreHandler);
		frame.addComponentListener(engine.coreHandler);
		
		frame.add(engine, BorderLayout.CENTER);
		
		frame.setSize(WIDTH, HEIGHT);
		frame.pack();
		
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		engine.start();
		
	}
}
