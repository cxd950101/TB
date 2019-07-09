package com.jsyunsi;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.List;

public class Bullet {
	private static final int WIDTH = 10;
	private static final int LENGTH = 10;
	private int x, y;
	Direction direction;
	private int speed = 10;
	private boolean good;
	private boolean live = true;
	private Client c;
	private boolean sm = false;
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] images = null;
	static {
		images = new Image[] { tk.getImage(Tank.class.getResource("../../images/BulletU.gif")),
				tk.getImage(Tank.class.getResource("../../images/BulletD.gif")),
				tk.getImage(Tank.class.getResource("../../images/BulletL.gif")),
				tk.getImage(Tank.class.getResource("../../images/BulletR.gif")), };
	}

	public Bullet(int x, int y, Direction direction, boolean good, Client c) {
		super();
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.good = good;
		this.c = c;
	}

	public void draw(Graphics g) {
		
		if (isLive()) {
			switch (direction) {
			case U:
				g.drawImage(images[0], x, y, WIDTH, LENGTH, null);
				break;
			case D:
				g.drawImage(images[1], x, y, WIDTH, LENGTH, null);
				break;
			case L:
				g.drawImage(images[2], x, y, WIDTH, LENGTH / 2, null);
				break;
			case R:
				g.drawImage(images[3], x, y, WIDTH, LENGTH / 2, null);
				break;
			case S:

				break;
			default:
				break;
			}
			move();
		}
	}

	private void move() {
		// TODO Auto-generated method stub
		switch (direction) {
		case U:
			y -= speed;
			break;
		case D:
			y += speed;
			break;
		case L:
			x -= speed;
			break;
		case R:
			x += speed;
			break;
		default:
			break;
		}
		if (x <= 0 || (x > Client.FRAME_WIDTH))
			this.setLive(false);
		if ((y <= 0) || (y > Client.FRAME_LENGTH))
			this.setLive(false);
	}

	// ����
	public Rectangle getrect() {
		return new Rectangle(x, y, WIDTH, LENGTH);

	}

	public void BcollideWithTank(List<Tank> tanks) {
		if (good) {
			for (int i = 0; i < tanks.size(); i++) {
				Tank t = tanks.get(i);
				if ((t.isLive())&&(this.getrect().intersects(t.getRect()))) {
					// ̹�˱�ըͼƬ����
					BombTank bt = new BombTank(tanks.get(i).getX(), tanks.get(i).getY(), this.c);
					this.c.bombtanks.add(bt);
					tanks.get(i).setLive(false);
					this.setLive(false);
					this.c.maxtank-=1;
					// this.c.bullets.remove(this);
				}
			}
		}

	}

	// ��ʦ�����һ��
	public void BcollideWithHometank2(Tank t) {

		if ((t.isLive()) && (this.good != t.isGood()) && (this.getrect().intersects(t.getRect()))) {
			if (!t.isGood()) {
				this.c.bullets.remove(this);
				this.c.tanks.remove(t);
			} else if (t.isGood()) {
				this.c.homeTank.setLife(this.c.homeTank.getLife() - 50);
				this.setLive(false);
			}
		}

	}

	public void BcollideWithHometank(Tank ht) {
		if ((ht.isLive()) && (this.good != ht.isGood()) && (this.getrect().intersects(ht.getRect()))) {
			this.c.homeTank.setLife(this.c.homeTank.getLife() - 50);
			ht.setX(280);ht.setY(560);
			this.setLive(false);
			// this.c.bullets.remove(this);
		}
	}

	public void BcollidewithMetalwall1(List<MetalWall> wall) {
		for (int i = 0; i < wall.size(); i++) {
			if ((this.good) && (this.getrect().intersects(wall.get(i).getRect()))) {
				wall.remove(i);
				this.setLive(false);
				// this.c.bullets.remove(this);
			}
		}
	}

	public void BcollidewithMetalwall(MetalWall wall) {
		if ((!this.sm) && (this.getrect().intersects(wall.getRect()))) {
			this.setLive(false);
			// this.c.bullets.remove(this);
		}
	}

	public void BcollidewithCommonWall(List<CommonWall> wall) {
		for (int i = 0; i < wall.size(); i++) {
			if (this.getrect().intersects(wall.get(i).getRect())) {
				wall.remove(i);
				this.setLive(false);
				// this.c.bullets.remove(this);
			}
		}
	}

	public void BcollidewithB(List<Bullet> bullets) {
		for (int i = 0; i < bullets.size(); i++) {
			Bullet b = bullets.get(i);
			if ((this.good != b.good) && this != bullets.get(i) && (this.getrect().intersects(b.getrect()))) {
				this.setLive(false);
				b.setLive(false);
				// this.c.bullets.remove(this);
			}
		}
	}

	public void BcollideWithHome(Home home) {
		if ((home.isLive())&&this.getrect().intersects(home.getRect())) {
			BombTank bt = new BombTank(home.getX(), home.getY(), this.c);
			this.c.bombtanks.add(bt);
			this.c.homeTank.setLive(false);
			this.c.home.setLive(false);
			this.setLive(false);
			// this.c.bullets.remove(this);
		}
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public void bomb() {
		// BombTank bt = new BombTank()
	}

	public boolean isSm() {
		return sm;
	}

	public void setSm(boolean sm) {
		this.sm = sm;
	}

}
