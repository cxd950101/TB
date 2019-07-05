package com.jsyunsi;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class River {
	//´óÐ¡
	public static final int WIDTH=40;
	public static final int LENGTH=100;
	//×ø±ê
	private int x,y;
	private static Toolkit tk=Toolkit.getDefaultToolkit();
	private static Image image=null;
	static{
		image = tk.getImage(River.class.getResource("../../images/river.jpg"));
	}
	public void draw(Graphics g){
		g.drawImage(image, x, y, WIDTH, LENGTH, null);
	}
	
	public Rectangle getRect(){
		return new Rectangle(x,y,WIDTH,LENGTH);
	}

	public River(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
}
