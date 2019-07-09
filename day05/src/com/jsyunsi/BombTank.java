package com.jsyunsi;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class BombTank {
	private int x, y;
	private boolean live = true;
	private Client c;
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] images = null;
	private static int step = 0;
	static {
			images = new Image[]{
					tk.getImage(BombTank.class.getResource("../../images/0.gif")),
					tk.getImage(BombTank.class.getResource("../../images/1.gif")),
					tk.getImage(BombTank.class.getResource("../../images/2.gif")),
					tk.getImage(BombTank.class.getResource("../../images/3.gif")),
					tk.getImage(BombTank.class.getResource("../../images/4.gif")),
					tk.getImage(BombTank.class.getResource("../../images/5.gif")),
					tk.getImage(BombTank.class.getResource("../../images/6.gif")),
					tk.getImage(BombTank.class.getResource("../../images/7.gif")),
					tk.getImage(BombTank.class.getResource("../../images/8.gif")),
					tk.getImage(BombTank.class.getResource("../../images/9.gif")),
					tk.getImage(BombTank.class.getResource("../../images/10.gif")),
		};
	}

	public BombTank(int x, int y, Client c) {
		this.x = x;
		this.y = y;
		this.c = c;
	}

	public void draw(Graphics g) {
		if (step == 11) {
			live = false;
			step = 0;
		}
		if (live) {
			g.drawImage(images[step], x, y, null);
			step++;
		}
		if (!live) {
			this.c.bombtanks.remove(this);
			return;
		}
	}

}
