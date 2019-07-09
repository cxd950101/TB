package com.jsyunsi;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.List;

public class Tank {
	//
	public static final int WIDTH = 35;
	public static final int LENGTH = 35;
	private int x, y;
	private int oldx, oldy;
	private int n = (int) (Math.random() * 10) + 5;
	public Direction direction = Direction.U;
	public Direction olddirection = Direction.U;
	private boolean bL, bU, bR, bD = false;
	private boolean good;
	private int tankn = 10;
	private boolean tankb = true;
	private int speed = 6;
	private int speedbad = 0;
	private int life = 200;

	private Client c;
	private boolean live = true;
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] images = null;
	static {
		images = new Image[] { tk.getImage(Tank.class.getResource("../../images/tankU.gif")),
				tk.getImage(Tank.class.getResource("../../images/tankD.gif")),
				tk.getImage(Tank.class.getResource("../../images/tankL.gif")),
				tk.getImage(Tank.class.getResource("../../images/tankR.gif")),
				// 我方坦克
				tk.getImage(Tank.class.getResource("../../images/U.gif")),
				tk.getImage(Tank.class.getResource("../../images/D.gif")),
				tk.getImage(Tank.class.getResource("../../images/L.gif")),
				tk.getImage(Tank.class.getResource("../../images/R.gif")) };
	}

	public void draw(Graphics g) {
		if (!tankb) {
			tankn--;
		}
		if (tankn == 0) {
			tankb = true;
			tankn = 10;
		}
		if (!live) {
			return;
		}
		if (good) {
			switch (direction) {
			case U:
				g.drawImage(images[4], x, y, WIDTH, LENGTH, null);
				olddirection = direction;
				break;
			case D:
				g.drawImage(images[5], x, y, WIDTH, LENGTH, null);
				olddirection = direction;
				break;
			case L:
				g.drawImage(images[6], x, y, WIDTH, LENGTH, null);
				olddirection = direction;
				break;
			case R:
				g.drawImage(images[7], x, y, WIDTH, LENGTH, null);
				olddirection = direction;
				break;
			case S:
				if (olddirection == Direction.U) {
					g.drawImage(images[4], x, y, WIDTH, LENGTH, null);
				} else if (olddirection == Direction.D) {
					g.drawImage(images[5], x, y, WIDTH, LENGTH, null);
				} else if (olddirection == Direction.L) {
					g.drawImage(images[6], x, y, WIDTH, LENGTH, null);
				} else if (olddirection == Direction.R) {
					g.drawImage(images[7], x, y, WIDTH, LENGTH, null);
				}
				break;
			}
		}
		if (!good) {
			speed = speedbad;
			Direction[] dir = Direction.values();
			if (n == 0) {
				int rn = (int) (Math.random() * 4);
				direction = dir[rn];
				n = (int) (Math.random() * 10) + 5;
			}
			n--;
			switch (direction) {
			case U:
				g.drawImage(images[0], x, y, WIDTH, LENGTH, null);
				break;
			case D:
				g.drawImage(images[1], x, y, WIDTH, LENGTH, null);
				break;
			case L:
				g.drawImage(images[2], x, y, WIDTH, LENGTH, null);
				break;
			case R:
				g.drawImage(images[3], x, y, WIDTH, LENGTH, null);
				break;
			case S:
				break;
			default:
				break;
			}
			if ((int) (Math.random() * 40) > 38) {
				fire();
			}

		}
		move();
	}

	private void move() {
		oldx = x;
		oldy = y;
		// TODO Auto-generated method stub
		switch (direction) {
		case U: {
			if (y > 60) {
				y -= speed;
			}
			break;
		}
		case D: {
			if (y < (Client.FRAME_LENGTH - 35)) {
				y += speed;
			}
			break;
		}
		case R: {
			if (x < (Client.FRAME_WIDTH - 35)) {
				x += speed;
			}
			break;
		}
		case L: {
			if (x > 5) {
				x -= speed;
			}
			break;
		}
		default:
			break;
		}
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_W:
			bU = true;
			break;
		case KeyEvent.VK_S:
			bD = true;
			break;
		case KeyEvent.VK_A:
			bL = true;
			break;
		case KeyEvent.VK_D:
			bR = true;
			break;
		case KeyEvent.VK_J:
			if (live) {
				if (tankb) {
					fire();
					tankb = false;
				}
			}
		}
		decideDirection();
	}

	private void decideDirection() {
		// TODO Auto-generated method stub
		if (bU && !bD && !bL && !bR) {
			direction = Direction.U;
		} else if (!bU && bD && !bL && !bR) {
			direction = Direction.D;
		} else if (!bU && !bD && bL && !bR) {
			direction = Direction.L;
		} else if (!bU && !bD && !bL && bR) {
			direction = Direction.R;
		} else if (!bU && !bD && !bL && !bR) {
			direction = Direction.S;
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_W:
			bU = false;
			break;
		case KeyEvent.VK_S:
			bD = false;
			break;
		case KeyEvent.VK_A:
			bL = false;
			break;
		case KeyEvent.VK_D:
			bR = false;
			break;
		}
		decideDirection();
	}

	public Tank(int x, int y, Direction direction, boolean good, Client c) {
		super();
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.good = good;
		this.c = c;
	}

	public Rectangle getRect() {
		return new Rectangle(x, y, WIDTH, LENGTH);
	}

	// 坦克和河水
	public void tankwithRiver(River river) {
		if (this.getRect().intersects(river.getRect())) {
			changeToOldDirection();
		}
	}

	// 坦克和金属墙
	public void tankwithMetalWall(MetalWall metalwall) {
		if (this.getRect().intersects(metalwall.getRect())) {
			changeToOldDirection();
		}
	}

	// 坦克和普通墙
	public void tankwithCommonWall(CommonWall commonwall) {
		if (this.getRect().intersects(commonwall.getRect())) {
			changeToOldDirection();
		}
	}

	// 坦克和家碰撞
	public void tankwithHome(Home home) {
		if (this.getRect().intersects(home.getRect())) {
			changeToOldDirection();
		}
	}

	public void tankwithTank(Tank tank) {
		if (this.isLive() && tank.isLive() && this.getRect().intersects(tank.getRect())) {
			changeToOldDirection();
			tank.changeToOldDirection();
		}
	}

	public void collideWithTank(List<Tank> tanks) {
		for (int i = 0; i < tanks.size(); i++) {
			Tank t = tanks.get(i);
			if (this.isLive() && t.isLive() && (this != t) && (this.getRect().intersects(t.getRect()))) {
				this.changeToOldDirection();
				t.changeToOldDirection();

			}
		}
	}

	public int getSpeedbad() {
		return speedbad;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setSpeedbad(int speedbad) {
		this.speedbad = speedbad;
	}

	private void changeToOldDirection() {
		x = oldx;
		y = oldy;
	}

	public void fire() {
		// TODO Auto-generated method stub
		int x = this.x + WIDTH / 2 - 5;
		int y = this.y + LENGTH / 2 - 2;
		Direction dir = this.direction;
		if (this.direction == Direction.S)
			dir = olddirection;
		boolean good = this.good;
		this.c.bullets.add(new Bullet(x, y, dir, good, this.c));
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public boolean isGood() {
		return good;
	}

	public void setGood(boolean good) {
		this.good = good;
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

}
