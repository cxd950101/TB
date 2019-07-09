package com.jsyunsi;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.List;

public class Bomb {
	private static final int WIDTH = 30;
	private static final int LENGTH = 30;
	private int x, y;
	private static Image image = null;
	private boolean live = false;
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private Client c;
	int n;
	static {
		image = tk.getImage(Love.class.getResource("../../images/bomb.jpg"));
	}

	public Bomb(int x, int y, Client c) {
		this.x = x;
		this.y = y;
		this.c = c;
	}

	public void draw(Graphics g) {
		// ±¬Õ¨
		if (n < 0 | !this.live) {
			this.live = false;
			n = this.c.time;
		}
		if (this.live) {
			n--;
			g.drawImage(image, x, y, WIDTH, LENGTH, null);
		}
	}

	public Rectangle getrect() {
		return new Rectangle(x, y, WIDTH, LENGTH);
	}

	public void bombcollidewithHometank(Tank homeTank, List<Tank> tanks) {
		// TODO Auto-generated method stub
		if ((this.live) && (this.getrect().intersects(homeTank.getRect()))) {
			if (this.c.maxtank > 0) {
				this.c.maxtank -= 1;
				while (this.live) {
					int n = (int) (Math.random() * tanks.size());
					if (tanks.get(n).isLive()) {
						tanks.get(n).setLive(false);
						this.live = false;
					}
				}
			}
		}
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
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
