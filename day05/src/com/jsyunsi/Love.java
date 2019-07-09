package com.jsyunsi;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class Love {
	private static final int WIDTH = 30;
	private static final int LENGTH = 30;
	private int x, y, n;
	public Client c;
	private static Image image = null;
	private boolean live = false;
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	static {
		image = tk.getImage(Love.class.getResource("../../images/blood.png"));
	}

	public Love(int x, int y, Client c) {
		this.x = x;
		this.y = y;
		this.c = c;

	}

	public void draw(Graphics g) {
		// ÂúÑª°®ÐÄ
		if (n < 0 | !live) {
			live = false;
			n = this.c.time;
		}
		if (live) {
			n--;
			g.drawImage(image, x, y, WIDTH, LENGTH, null);
		}
	}

	public Rectangle getrect() {
		return new Rectangle(x, y, WIDTH, LENGTH);
	}

	public void lovecollidewithHometank(Tank ht) {
		if ((this.live) && (this.getrect().intersects(ht.getRect()))) {
			this.live = false;
			ht.setLife(200);
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
