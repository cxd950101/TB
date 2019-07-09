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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class Client extends Frame implements ActionListener {

	/**
	 * ̹�˴�ս����
	 */
	private static final long serialVersionUID = 1L;
	// �����Ŀ�Ⱥ͸߶�
	public static final int FRAME_WIDTH = 800;
	public static final int FRAME_LENGTH = 600;
	private boolean notover = true;
	// �ѶȲٿ�
	private static int score = 0;
	private static int Maxtank = 200;
	// ��ȡ��Ļ�ߴ�
	Toolkit tk = Toolkit.getDefaultToolkit();
	Dimension screenSize = tk.getScreenSize();
	int screenWidth = screenSize.width;
	int screenLength = screenSize.height;
	int maxtank = 20;
	// ������Ϸ�˵�
	MenuBar jmb = null;
	Menu jm1, jm2, jm3, jm4 = null;
	MenuItem jmi1, jmi2, jmi3, jmi4, jmi5, jmi6, jmi7, jmi8, jmi9 = null;
	//
	private boolean printable = true;
	// ���廭��
	Image screenImage = null;

	// ����̹�˱�ը
	List<BombTank> bombtanks = new ArrayList<BombTank>();
	List<MetalWall> homemetalwall = new ArrayList<MetalWall>();
	//
	Shield shield = null;
	River river = null;
	Home home = null;
	Love love = null;
	Bomb bomb = null;
	Star star = null;
	Start start = null;
	// ���϶���Tree����
	List<Tree> trees = new ArrayList<Tree>();
	List<MetalWall> metalwall = new ArrayList<MetalWall>();
	List<CommonWall> homewall = new ArrayList<CommonWall>();
	List<CommonWall> otherwall = new ArrayList<CommonWall>();
	public int time = 180;
	// �����ҷ�̹��
	Tank homeTank = null;
	// ̹�˼���
	List<Tank> tanks = new ArrayList<Tank>();
	int[] x = new int[Maxtank];
	int[] y = new int[Maxtank];
	private static int starx, stary, shieldx, shieldy, bombx, bomby, lovex, lovey;
	int n , sm = 140;
	public int fourn = (int) (Math.random() * 40) + 160;
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
		if (start.isLive()) {
			start.draw(g);
		} else if (!start.isLive()) {
			g.drawImage(screenImage, 0, 0, null);
		}
		if (!bomb.isLive() && !love.isLive() && !star.isLive() && !shield.isLive() && fourn == 0) {
			fourn = 200;
			int four = (int) (Math.random() * 4);
			switch (four) {
			case 0:
				bomb.setX((int) (Math.random() * FRAME_WIDTH - 50));
				bomb.setY((int) (Math.random() * FRAME_LENGTH - 100) + 60);
				bomb.setLive(true);
				break;
			case 1:
				shield.setX((int) (Math.random() * FRAME_WIDTH - 50));
				shield.setY((int) (Math.random() * FRAME_LENGTH - 100) + 60);
				shield.setLive(true);
				break;
			case 2:
				star.setX((int) (Math.random() * FRAME_WIDTH - 50));
				star.setY((int) (Math.random() * FRAME_LENGTH - 100) + 60);
				star.setLive(true);
				break;
			case 3:
				love.setX((int) (Math.random() * FRAME_WIDTH - 50));
				love.setY((int) (Math.random() * FRAME_LENGTH - 100) + 60);
				love.setLive(true);
				break;
			}
		}
		if (fourn > 0)
			fourn--;
		if (shield.homemetalwall){
			n--;
		}
		if(n==0){
			shield.homemetalwall=false;
			n=200;
		}
		System.out.println(n);
	}

	private void framePaint(Graphics g) {
		// TODO Auto-generated method stub
		//����
		for (int i = 0; i < trees.size(); i++) {
			trees.get(i).draw(g);
		}
		// ����ը
		for (int i = 0; i < bombtanks.size(); i++) {
			bombtanks.get(i).draw(g);
		}
		river.draw(g); // ����
		home.draw(g); // �� ӥ
		homeTank.draw(g); // �ҷ�̹��
		homebullet.draw(g); // �ҷ��ӵ�
		// ����ǽ
		for (int i = 0; i < metalwall.size(); i++) {
			metalwall.get(i).draw(g);
			for (int j = 0; j < bullets.size(); j++) {
				// �ӵ������ǽ����ײ
				bullets.get(j).BcollidewithMetalwall(metalwall.get(i));
			}
		}
		// ������ͨǽ
		for (int i = 0; i < otherwall.size(); i++) {
			otherwall.get(i).draw(g);
		}
		// �ұߵ���ͨǽ
		if (!shield.homemetalwall) {
			for (int i = 0; i < homewall.size(); i++) {
				homewall.get(i).draw(g);
			}
		} else if (shield.homemetalwall) {
			// �ұߵĽ���ǽ
			for (int i = 0; i < homemetalwall.size(); i++) {
				homemetalwall.get(i).draw(g);
			}
		}
		// ���з�̹��
		for (int i = 0; i < tanks.size(); i++) {
			tanks.get(i).draw(g);
		}

		// collide��ײ����
		collidewith();
		// ������

		// ���ӵ�
		for (int i = 0; i < bullets.size(); i++) {
			Bullet b = bullets.get(i);
			b.draw(g);
		}
		// �÷�
		g.setColor(Color.green);
		g.setFont(new Font("", Font.BOLD, 30));
		if (maxtank == 0) {
			g.setColor(Color.RED);
			g.setFont(new Font("", Font.BOLD, 70));
			bullets.clear();
			g.drawString("�������", 250, 300);
		}
		if (homeTank.getLife() == 0)
			homeTank.setLive(false);
		if (!homeTank.isLive()) {
			g.setColor(Color.red);
			g.setFont(new Font("", Font.BOLD, 70));
			g.drawString("game over", 250, 300);
		}
		g.setFont(new Font("TimesRoman", Font.BOLD, 30));
		g.setColor(Color.green);
		g.drawString("ʣ��з�̹��:" + maxtank, 100, 100);
		g.drawString("����ֵ:" + homeTank.getLife(), 400, 100);
		g.drawString("�÷�:" + score, 600, 100);
		love.lovecollidewithHometank(homeTank);
		bomb.bombcollidewithHometank(homeTank, tanks);
		// ���� �����ǽ
		star.starcollidewithTank(homeTank, homebullet);
		// ����ͼ
		star.draw(g); // ����
		bomb.draw(g); // ը��
		love.draw(g); // ��������
		shield.draw(g); // ����

	}

	private void collidewith() {
		// TODO Auto-generated method stub
		// �����ǽ����ʱ������
		if (sm < 0) {
			homebullet.setSm(false);
			sm = 200;
		}
		if (homebullet.isSm()) {
			sm--;
		}
		homeTank.tankwithRiver(river); // �����˺�
		for (int i = 0; i < otherwall.size(); i++) {
			homeTank.tankwithCommonWall(otherwall.get(i)); // ��������ͨǽ
		}
		for (int i = 0; i < metalwall.size(); i++) {
			homeTank.tankwithMetalWall(metalwall.get(i)); // �����˽���ǽ
		}
		for (int j = 0; j < homewall.size(); j++) {
			homeTank.tankwithCommonWall(homewall.get(j)); // �����˼ҵ�ǽ
		}
		for (int i = 0; i < tanks.size(); i++) {
			tanks.get(i).tankwithRiver(river); // �з�̹�˹����˺�
			// ̹��
			tanks.get(i).collideWithTank(tanks);
			for (int j = 0; j < metalwall.size(); j++) {
				tanks.get(i).tankwithMetalWall(metalwall.get(j));
			}
			for (int j = 0; j < otherwall.size(); j++) {
				tanks.get(i).tankwithCommonWall(otherwall.get(j));
			}
			for (int j = 0; j < homewall.size(); j++) {
				tanks.get(i).tankwithCommonWall(homewall.get(j));
			}
		}
		// �ҷ�̹�˺͵з�̹����ײ
		homeTank.collideWithTank(tanks);

		// ���ܺ��ҷ�̹�˴���
		shield.ShieldcollidewithHometank(homeTank);

		// ����ҵĽ���ǽ
		for (int j = 0; j < bullets.size(); j++) {
			if (shield.homemetalwall)
				bullets.get(j).BcollidewithMetalwall1(homemetalwall);
		}
		// �ӵ���ײ
		for (int i = 0; i < bullets.size(); i++) {
			Bullet b = bullets.get(i);
			// �ӵ��Ե�
			b.BcollidewithB(bullets);
			// �ӵ���ײ�ҷ�̹��
			b.BcollideWithHometank(homeTank);
			// �ӵ���ײhome
			b.BcollideWithHome(home);
			// �ӵ���ײ�з�̹��
			b.BcollideWithTank(tanks);
			// �ӵ���ײ������ͨǽ
			b.BcollidewithCommonWall(otherwall);
			// �ӵ���ײ�ҵ���ͨǽ
			if (!shield.homemetalwall) {
				b.BcollidewithCommonWall(homewall);
			}
			// �ӵ���ײ�ҵĽ���ǽ
			if (homebullet.isSm()) {
				b.BcollidewithMetalwall1(metalwall);
			}
			// �Ƴ��ӵ�
			if (!b.isLive())
				bullets.remove(i);
		}
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
		BombTank bt = new BombTank(-100, -100, this);
		bombtanks.add(bt);
		// �����߳�
		new Thread(new PaintThread()).start();
		// ��Ӽ��̼����¼�
		this.addKeyListener(new KeyMonitor());
		this.addMouseListener(new MouseMonitor());

	}

	private void rejmb() {
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
	//��ʼ��
	public void restart() {
		// TODO Auto-generated method stub
		start = new Start(this);
		fourimage();
		star = new Star(starx, stary, this);
		bomb = new Bomb(bombx, bomby, this);
		love = new Love(lovex, lovey, this);
		shield = new Shield(shieldx, shieldy, this);
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
		for (int i = 0; i < 5; i++) {
			homemetalwall.add(new MetalWall(320, 520 + i * 20));
			homemetalwall.add(new MetalWall(400, 520 + i * 20));
		}
		for (int i = 0; i < 3; i++) {
			homemetalwall.add(new MetalWall(340 + i * 20, 520));
		}
	}

	private void fourimage() {
		// TODO Auto-generated method stub
		shieldx = (int) (Math.random() * FRAME_WIDTH);
		shieldy = (int) (Math.random() * FRAME_LENGTH);
		bombx = (int) (Math.random() * FRAME_WIDTH);
		bomby = (int) (Math.random() * FRAME_LENGTH);
		starx = (int) (Math.random() * FRAME_WIDTH);
		stary = (int) (Math.random() * FRAME_LENGTH);
		lovex = (int) (Math.random() * FRAME_WIDTH);
		lovey = (int) (Math.random() * FRAME_LENGTH);
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
			while (true) {
				while (printable) {
					if (notover)
						repaint();
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

	}
	//������м������ò��ֱ�������ʼ��
	public void restartall() {
		maxtank = 20;
		tanks.clear();
		score = 0;
		bullets.clear();
		homeTank.setLife(2);
		notover = true;
		n = 200;
		restart();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand().equals("Begin")) {
			printable = false;
			Object[] options = { "ȷ��", "ȡ��" };
			int response = JOptionPane.showOptionDialog(this, "��ȷ�����¿�ʼ��", "", JOptionPane.YES_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			if (response == 0) {
				printable = true;
				new Thread(new PaintThread()).start();
				restartall();
			} else {
				printable = true;
				new Thread(new PaintThread()).start();
			}
		} else if (e.getActionCommand().equals("Exit")) {
			printable = false;
			Object[] options = { "ȷ��", "ȡ��" };
			int response = JOptionPane.showOptionDialog(this, "��ȷ��Ҫ�˳���", "", JOptionPane.YES_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			if (response == 0) {
				System.exit(0);
			} else {
				printable = true;
				new Thread(new PaintThread()).start();
			}
		} else if (e.getActionCommand().equals("Stop")) {
			printable = false;
			start.setLive(true);
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
			printable = false;
			JOptionPane.showMessageDialog(null, "��A��W��S��D���Ʒ���J�����ӵ�����R���¿�ʼ", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
			printable = true;
			new Thread(new PaintThread()).start();

		}

	}

	private class KeyMonitor extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			homeTank.keyPressed(e);
			switch (e.getKeyCode()) {
			case KeyEvent.VK_R:
				restartall();
			}
		}

		public void keyReleased(KeyEvent e) {
			homeTank.keyReleased(e);
		}
	}

	private class MouseMonitor extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			start.MouseClicked(e);
		}
	}


}
