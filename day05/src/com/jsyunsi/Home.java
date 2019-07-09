package com.jsyunsi;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class Home {
		public static final int WIDTH = 55;
		public static final int LENGTH = 55;
		private boolean Live=true;
		private int x, y;
		private static Toolkit tk = Toolkit.getDefaultToolkit();
		private static Image image = null;
		static {
			image = tk.getImage(MetalWall.class.getResource("../../images/home.jpg"));
		}

		public Home(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public void draw(Graphics g) {
			if(!Live)
				return;
			g.drawImage(image, x, y, WIDTH, LENGTH, null);
		}

		public Rectangle getRect() {
			return new Rectangle(x, y, WIDTH, LENGTH);
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

		public boolean isLive() {
			return Live;
		}

		public void setLive(boolean live) {
			Live = live;
		}

}
