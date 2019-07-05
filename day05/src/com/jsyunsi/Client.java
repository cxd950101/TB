package com.jsyunsi;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
public class Client extends Frame implements ActionListener {

	/**
	 * ̹�˴�ս����
	 */
	private static final long serialVersionUID = 1L;
	// �����Ŀ�Ⱥ͸߶�
	public static final int FRAME_WIDTH = 800;
	public static final int FRAME_LENGTH = 600;

	private boolean reStart = true;
	public boolean notover = true;
	public boolean boom = false;
	// �ѶȲٿ�
	public static int score = 0;
	public static String Text, Text2;
	public static int Maxtank = 200;
	public static int speed;
	// ��ȡ��Ļ�ߴ�
	Toolkit tk = Toolkit.getDefaultToolkit();
	Dimension screenSize = tk.getScreenSize();
	int screenWidth = screenSize.width;
	int screenLength = screenSize.height;
	// ������Ϸ�˵�
	MenuBar jmb = null;
	Menu jm1, jm2, jm3, jm4 = null;
	MenuItem jmi1, jmi2, jmi3, jmi4, jmi5, jmi6, jmi7, jmi8, jmi9 = null;
	//
	private boolean printable = true;
	// ���廭��
	Image screenImage = null;

	//����̹�˱�ը
	List<BombTank> bombtanks = new ArrayList<BombTank>();
	
	//
	River river = null;
	Home home = null;
	// ���϶���Tree����
	List<Tree> trees = new ArrayList<Tree>();
	List<MetalWall> metalwall = new ArrayList<MetalWall>();
	List<CommonWall> homewall = new ArrayList<CommonWall>();
	List<CommonWall> otherwall = new ArrayList<CommonWall>();

	// �����ҷ�̹��
	Tank homeTank = null;
	// ̹�˼���
	List<Tank> tanks = new ArrayList<Tank>();
	int[] x = new int[Maxtank];
	int[] y = new int[Maxtank];

	// �ӵ�
	List<Bullet> bullets = new ArrayList<Bullet>();
	Bullet homebullet = null;

	// main
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Client();
	}

	//
	public void update(Graphics g) {
		screenImage = this.createImage(screenWidth, screenLength);
		Graphics gps = screenImage.getGraphics();
		framePaint(gps);
		// ÿ�θ��ǻ������ﵽˢ��Ч��
		g.drawImage(screenImage, 0, 0, null);
	}

	private void framePaint(Graphics g) {
		// TODO Auto-generated method stub
		river.draw(g);
		home.draw(g);
		homeTank.draw(g);
		homebullet.draw(g);
		for (int i = 0; i < metalwall.size(); i++) {
			metalwall.get(i).draw(g);
			for (int j = 0; j < bullets.size(); j++) {
				bullets.get(j).BcollidewithMetalwall(metalwall.get(i));
			}
		}
		for (int i = 0; i < otherwall.size(); i++) {
			otherwall.get(i).draw(g);
		}
		for (int i = 0; i < homewall.size(); i++) {
			homewall.get(i).draw(g);
		}
		for (int i = 0; i < tanks.size(); i++) {
			tanks.get(i).draw(g);
		}
		for (int i = 0; i < trees.size(); i++) {
			trees.get(i).draw(g);
		}
		// collide��ײ����
		collidewith();
		// ���ӵ�
		for (int i = 0; i < bullets.size(); i++) {
			int j = tanks.size();
			Bullet b = bullets.get(i);
			b.draw(g);
			// �ӵ���ײ�з�
			b.BcollideWithTank(tanks);
			b.BcollidewithCommonWall(homewall);
			b.BcollidewithCommonWall(otherwall);
			if (tanks.size() < j) {
				score += 10;
			}
			//�ӵ��Ե�
			b.BcollidewithB(bullets);
			b.BcollideWithHometank(homeTank);
			//�ӵ���ײhome
			b.BcollideWithHome(home);
			if (!b.isLive())
				bullets.remove(i);
		}
		// �÷�
		g.setColor(Color.green);
		g.setFont(new Font("", Font.BOLD, 30));
		Text = "�÷֣�" + score;
		if (score == 200) {
			Text = "�������";
			g.setColor(Color.RED);
			notover=false;
		}
		g.drawString(Text, 600, 100);
		Text2 = "������" + homeTank.getLife();
		g.drawString(Text2, 400, 100);
		if (!notover){
			g.setColor(Color.black);
			g.setFont(new Font("",Font.BOLD,70));
			g.drawString(Text, 250, 300);
		}
		//����ը
		for (int i = 0; i < bombtanks.size(); i++) {
			bombtanks.get(i).draw(g);
		}

	}

	private void collidewith() {
		// TODO Auto-generated method stub
		homeTank.tankwithRiver(river);

		for (int i = 0; i < tanks.size(); i++) {
			tanks.get(i).tankwithRiver(river);
			// ̹��
			tanks.get(i).collideWithTank(tanks);
			for (int j = 0; j < metalwall.size(); j++) {
				homeTank.tankwithMetalWall(metalwall.get(j));
				tanks.get(i).tankwithMetalWall(metalwall.get(j));
			}
			for (int j = 0; j < otherwall.size(); j++) {
				homeTank.tankwithCommonWall(otherwall.get(j));
				tanks.get(i).tankwithCommonWall(otherwall.get(j));
			}
			for (int j = 0; j < homewall.size(); j++) {
				homeTank.tankwithCommonWall(homewall.get(j));
				tanks.get(i).tankwithCommonWall(homewall.get(j));
			}
		}
		// �ҷ�̹�˺͵з�̹����ײ
		homeTank.collideWithTank(tanks);

		/*
		 * for (int i = 0; i < tanks.size(); i++) {
		 * homeTank.tankwithTank(tanks.get(i)); } for (int i = 0; i <
		 * tanks.size(); i++) { for (int j = 0; j < tanks.size(); j++) { if (i
		 * != j)�� tanks.get(i).tankwithTank(tanks.get(j));
		 * tanks.get(i).tankwithTank(homeTank); �� } }
		 */

	}

	//
	public Client() {

		restart();
		// ��ʼ����Ϸ�˵�
		rejmb();

		// ���봰��
		this.setMenuBar(jmb);
		// ���ô��ڿɼ�
		this.setVisible(true);
		// ���ڲ��ɱ�
		// this.setResizable(false);
		// ���崰�ڴ�С
		this.setSize(FRAME_WIDTH, FRAME_LENGTH);
		// ���ô���λ�þ���
		this.setLocation(screenWidth / 2 - FRAME_WIDTH / 2, screenLength / 2 - FRAME_LENGTH / 2);
		// ���ñ�����ɫ
		this.setBackground(Color.gray);
		// ���ñ���
		this.setTitle("̹�˴�ս");
		// ���ô��ڹر�ģʽ
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		// �����߳�
		new Thread(new PaintThread()).start();
		// ��Ӽ��̼����¼�
		this.addKeyListener(new KeyMonitor());

	}

	private void rejmb() {
		//����
		BombTank bombTank = new BombTank(300,300,this);
		// TODO Auto-generated method stub
		jmb = new MenuBar();
		jm1 = new Menu("��Ϸ");
		jmi1 = new MenuItem("��ʼ����Ϸ");
		jmi2 = new MenuItem("�˳�");
		jm1.setFont(new Font("TimesRoman", Font.BOLD, 15));
		jmi1.setFont(new Font("TimesRoman", Font.BOLD, 15));
		jmi2.setFont(new Font("TimesRoman", Font.BOLD, 15));
		jmb.add(jm1);
		jm1.add(jmi1);
		jm1.add(jmi2);

		jm2 = new Menu("��ͣ/����");
		jmi3 = new MenuItem("��ͣ");
		jmi4 = new MenuItem("����");
		jm2.setFont(new Font("TimesRoman", Font.BOLD, 15));
		jmi3.setFont(new Font("TimesRoman", Font.BOLD, 15));
		jmi4.setFont(new Font("TimesRoman", Font.BOLD, 15));
		jmb.add(jm2);
		jm2.add(jmi3);
		jm2.add(jmi4);

		jm3 = new Menu("��Ϸ����");
		jmi5 = new MenuItem("��Ϸ����1");
		jmi6 = new MenuItem("��Ϸ����2");
		jmi7 = new MenuItem("��Ϸ����3");
		jmi8 = new MenuItem("��Ϸ����4");
		jm3.setFont(new Font("TimesRoman", Font.BOLD, 15));
		jmi5.setFont(new Font("TimesRoman", Font.BOLD, 15));
		jmi6.setFont(new Font("TimesRoman", Font.BOLD, 15));
		jmi7.setFont(new Font("TimesRoman", Font.BOLD, 15));
		jmi8.setFont(new Font("TimesRoman", Font.BOLD, 15));
		jmb.add(jm3);
		jm3.add(jmi5);
		jm3.add(jmi6);
		jm3.add(jmi7);
		jm3.add(jmi8);

		jm4 = new Menu("����");
		jmi9 = new MenuItem("��Ϸ˵��");
		jm4.setFont(new Font("TimesRoman", Font.BOLD, 15));
		jmi9.setFont(new Font("TimesRoman", Font.BOLD, 15));
		jmb.add(jm4);
		jm4.add(jmi9);

		// ��
		jmi1.addActionListener(this);
		jmi1.setActionCommand("Begin");
		jmi2.addActionListener(this);
		jmi2.setActionCommand("Exit");
		jmi3.addActionListener(this);
		jmi3.setActionCommand("Stop");
		jmi4.addActionListener(this);
		jmi4.setActionCommand("Continue");
		jmi5.addActionListener(this);
		jmi5.setActionCommand("Level1");
		jmi6.addActionListener(this);
		// shift+enter ���м�����һ��
		jmi6.setActionCommand("Level2");
		jmi7.addActionListener(this);
		jmi7.setActionCommand("Level3");
		jmi8.addActionListener(this);
		jmi8.setActionCommand("Level4");
		jmi9.addActionListener(this);
		jmi9.setActionCommand("Help");
	}

	private void restart() {
		// TODO Auto-generated method stub
		river = new River(100, 100);
		home = new Home(340, 542);
		// ��ʼ��̹��
		insettank();
		// ��ʼ���ҷ�̹�� 280/560
		homebullet = new Bullet(homeTank.getX(), homeTank.getY(), homeTank.direction, true, this);

		// ��ʼ��Tree
		for (int i = 0; i < 4; i++) {
			for (int n = 0; n < 4; n++) {
				trees.add(new Tree(n * 226 + i * 30, 370));
			}
		}
		// ��ʼ�� MetalWall
		for (int i = 0; i < 10; i++) {
			metalwall.add(new MetalWall(140 + i * 30, 120));
			metalwall.add(new MetalWall(140 + i * 30, 150));
			metalwall.add(new MetalWall(610, 400 + i * 30));
		}
		// ��ʼ��CommonWall
		for (int i = 0; i < 12; i++) {
			otherwall.add(new CommonWall(200, 400 + i * 20));
			otherwall.add(new CommonWall(220, 400 + i * 20));
			otherwall.add(new CommonWall(490, 400 + i * 20));
			otherwall.add(new CommonWall(510, 400 + i * 20));
		}
		for (int i = 0; i < 17; i++) {
			otherwall.add(new CommonWall(210 + i * 20, 300));
			otherwall.add(new CommonWall(210 + i * 20, 280));
		}
		for (int i = 0; i < 15; i++) {
			otherwall.add(new CommonWall(510 + i * 20, 130));
			otherwall.add(new CommonWall(510 + i * 20, 190));
		}
		// ��ʼ���ҵ�ǽ
		for (int i = 0; i < 5; i++) {
			homewall.add(new CommonWall(320, 520 + i * 20));
			homewall.add(new CommonWall(400, 520 + i * 20));
		}
		for (int i = 0; i < 3; i++) {
			homewall.add(new CommonWall(340 + i * 20, 520));
		}
	}

	private void insettank() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 20; i++) {
			if (i < 9) {
				tanks.add(new Tank(200 + i * 50, 60, Direction.D, false, this));
			} else if (i < 15) {
				tanks.add(new Tank(5, 200 + (i - 9) * 50, Direction.R, false, this));
			} else if (i < 20) {
				tanks.add(new Tank(750, 230 + (i - 15) * 45, Direction.L, false, this));

			}
		}
		homeTank = new Tank(280, 560, Direction.S, true, this);
		// ��ʼ���ӵ���Tank��

	}

	private class PaintThread implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (printable) {
				if (notover)
				repaint();
				if (homeTank.getLife() < 1) {
					notover = false;
				}
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand().equals("Begin")) {
			tanks.clear();
			score = 0;
			bullets.clear();
			homeTank.setLife(2);
			notover=true;
			restart();
		} else if (e.getActionCommand().equals("Exit")) {
			System.exit(0);
		} else if (e.getActionCommand().equals("Stop")) {
			printable = false;
		} else if (e.getActionCommand().equals("Continue")) {
			if (!printable) {
				printable = true;
				new Thread(new PaintThread()).start();
			}
		} else if (e.getActionCommand().equals("Level1")) {
			for (int i = 0; i < tanks.size(); i++) {
				tanks.get(i).setSpeedbad(6);
			}
		} else if (e.getActionCommand().equals("Level2")) {
			for (int i = 0; i < tanks.size(); i++) {
				tanks.get(i).setSpeedbad(10);
			}
		} else if (e.getActionCommand().equals("Level3")) {
			for (int i = 0; i < tanks.size(); i++) {
				tanks.get(i).setSpeedbad(15);
			}
		} else if (e.getActionCommand().equals("Level4")) {
			for (int i = 0; i < tanks.size(); i++) {
				tanks.get(i).setSpeedbad(20);
			}
		} else if (e.getActionCommand().equals("Help")) {
			System.out.println("Help");
		}

	}

	private class KeyMonitor extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			homeTank.keyPressed(e);
			/*
			 * for (int i = 0; i < tanks.size(); i++) {
			 * bullet.get(i).keyPressed(e); }
			 */

		}

		public void keyReleased(KeyEvent e) {
			homeTank.keyReleased(e);
			/*
			 * for (int i = 0; i < tanks.size(); i++) {
			 * bullet.get(i).keyPressed(e); }
			 */

		}
	}
	
}
