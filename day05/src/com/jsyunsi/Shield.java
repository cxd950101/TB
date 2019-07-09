package com.jsyunsi;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class Shield {
	private static final int WIDTH = 30;
	private static final int LENGTH = 30;
	private int x, y;
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image image = null;
	// 存在
	private static boolean live = false;
	public boolean homemetalwall = false;
	int n2=180;
	int mtimes = 200;
	public Client c;
	int n;
	static {
		image = tk.getImage(Shield.class.getResource("../../images/hudun.jpg"));
	}

	public Shield(int x, int y, Client c) {
		this.x = x;
		this.y = y;
		this.c = c;
	}

	public void draw(Graphics g) {
		//护盾图片持续时间
		if (n < 0 | !live) {
			live = false;
			n = this.c.time;
		}
		if (live){
			n--;
			g.drawImage(image, x, y, WIDTH, LENGTH, null);
		}
	}

	public Rectangle getrect() {
		return new Rectangle(x, y, WIDTH, LENGTH);
	}

	public void ShieldcollidewithHometank(Tank ht) {
		if ((this.isLive()) && (this.getrect().intersects(ht.getRect()))) {
			setLive(false);
			homemetalwall = true;
		}
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		Shield.live = live;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}
