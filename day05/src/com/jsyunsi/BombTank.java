package com.jsyunsi;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BombTank {
	private int x, y;
	private boolean live = true;
	private Client c;
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] images = null;
	private static int step = 0;
	static {
		try {
			images[1] = ImageIO.read(new File("bomb_1.gif"));
			images[2] = ImageIO.read(new File("bomb_2.gif"));
			images[3] = ImageIO.read(new File("bomb_3.gif"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			System.out.println(step);
			step++;
		}
		if (!live) {
			this.c.bombtanks.remove(this);
			return;
		}
	}

}
