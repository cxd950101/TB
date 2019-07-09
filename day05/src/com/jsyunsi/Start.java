package com.jsyunsi;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;

public class Start {
	private static final int WIDTH = Client.FRAME_WIDTH;
	private static final int LENGTH = Client.FRAME_LENGTH;
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image image = null;
	// 存在
	private static boolean live = true;
	public Client c;
	int n=40;
	static {
		image = tk.getImage(Shield.class.getResource("../../images/fenmian.jpg"));
	}

	public Start(Client c) {
		this.c=c;
	}

	public void draw(Graphics g) {
		if (!live) {
			return;
		}
		g.drawImage(image, 0, 0, WIDTH, LENGTH, null);
		g.setFont(new Font("",Font.BOLD,20));
		if (n== 0){
			n=40;
		}
		if(n<20){
		g.drawString("请单击任意一处开始游戏", 280, 430);
		}
		System.out.println(n);
		n--;
	}

	public void MouseClicked(MouseEvent e) {
		if (live) {
			int i = e.getButton();
			if (i == MouseEvent.BUTTON1) {
				live=false;
				this.c.restartall();
			}
		}
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		Start.live = live;
	}
}
