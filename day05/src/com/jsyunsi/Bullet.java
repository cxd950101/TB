package com.jsyunsi;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.List;

public class Bullet {
	private static final int WIDTH = 10;
	private static final int LENGTH = 10;
	private int x, y;
	private Direction direction;
	private int speed = 10;
	private boolean good;
	private boolean live = true;
	private Client c;

	private boolean bJ = false;

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
		}
		if (x<=0 || (x >Client.FRAME_WIDTH))
			this.setLive(false);
		if((y<=0)|| (y>Client.FRAME_LENGTH))
			this.setLive(false);
	}

	// 区域
	public Rectangle getrect() {
		return new Rectangle(x, y, WIDTH, LENGTH);

	}

	/*
	 * public void collidewithBall(Ball ball) { if
	 * (this.getrect().intersects(ball.getrect())) { this.a.list.remove(ball);
	 * this.c.score += 10; } }
	 */
	public void BcollideWithTank(List<Tank> tanks) {
		if (good) {
			for (int i = 0; i < tanks.size(); i++) {
				Tank t = tanks.get(i);
				if ((this.getrect().intersects(t.getRect()))) {
					//坦克爆炸图片集合
					BombTank bt = new BombTank(tanks.get(i).getX(),tanks.get(i).getY(),this.c);
					this.c.bombtanks.add(bt);
					tanks.remove(i);
					this.setLive(false);
				}
			}
		}

	}

	public void BcollideWithHometank2(Tank t) {
		
		if ((this.good !=t.isGood())&&(this.getrect().intersects(t.getRect()))){
			if(!t.isGood()){
				//w
				this.c.bullets.remove(this);
				this.c.tanks.remove(t);
			}else
			{
				
			}
		}

	}
	public void BcollideWithHometank(Tank hometank) {
		if (!good)
			if (this.getrect().intersects(hometank.getRect())) {
				this.c.homeTank.setLife(this.c.homeTank.getLife()-1);
				this.setLive(false);
			}
	}

	public void BcollidewithMetalwall(MetalWall wall) {
		if (this.getrect().intersects(wall.getRect())) {
			this.setLive(false);
		}
	}

	public void BcollidewithCommonWall(List<CommonWall> wall) {
		for (int i = 0; i < wall.size(); i++) {
			if (this.getrect().intersects(wall.get(i).getRect())) {
				wall.remove(i);
				this.setLive(false);
			}
		}
	}

	public void BcollidewithB(List<Bullet> bullets) {
		for (int i = 0; i < bullets.size(); i++) {
			Bullet b =bullets.get(i);
			if ((this.good !=b.good) && this!=bullets.get(i)&&(this.getrect().intersects(b.getrect()))) {
				this.setLive(false);
			}
		}
	}
	public void BcollideWithHome(Home home){
		if(this.getrect().intersects(home.getRect())){
			this.c.notover=false;
		}
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}
	
	public void bomb(){
		//BombTank bt = new BombTank()
	}

}
