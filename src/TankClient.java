import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class TankClient extends Frame {
	// 代码重构 更容易扩展，把定义为常量
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;
	// 这里的 this也非常巧妙，直接引用当前的 TankClient
	Tank myTank = new Tank(50, 50,true, this);
	Tank enemyTank = new Tank(80,80,false,this);
	Image offScreenImage = null;

	List<Missile> missiles = new ArrayList<Missile>();
	List<Explode> explodes = new ArrayList<Explode>();

	// 巧妙的构思，这里用一个容器把子弹储存起来
	public void paint(Graphics g) {
		g.drawString("炮弹数" + missiles.size(), 10, 50);
		myTank.draw(g);
		enemyTank.draw(g);
		for (int i = 0; i < missiles.size(); i++) {
			Missile m = missiles.get(i);
			if (!m.isbLive()) {
				missiles.remove(m);
			} else
				m.draw(g);
				m.hitTank(enemyTank);
		}
		for(int i =0; i<explodes.size();i++){
			Explode e =explodes.get(i);
			e.draw(g);
		}
	}

	/**
	 * 双缓冲 这里是做背景布 防止闪眼 他还没出来，就让刷新出来了
	 */
	public void update(Graphics g) {
		if (offScreenImage == null) {
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
		}
		Graphics goffScreen = offScreenImage.getGraphics();
		Color c = goffScreen.getColor();
		goffScreen.setColor(Color.gray);
		goffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		goffScreen.setColor(c);
		paint(goffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}

	public void lanchFrame() {
		this.setLocation(400, 300);
		this.setSize(GAME_WIDTH, GAME_HEIGHT);
		this.setTitle("tankWar");
		this.setBackground(Color.gray);
		this.addWindowListener(new WindowAdapter() {
			@Override
			// 这个是可以关闭窗口
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		// 这个是不允许放大
		this.setResizable(false);
		// 添加键盘监听
		this.addKeyListener(new KeyMonitor());
		setVisible(true);
		new Thread(new PaintThread()).start();
	}

	public static void main(String[] args) {
		TankClient tc = new TankClient();
		tc.lanchFrame();
	}

	private class PaintThread implements Runnable {

		@Override
		public void run() {
			while (true) {
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

	private class KeyMonitor extends KeyAdapter {

		@Override
		public void keyReleased(KeyEvent e) {
			myTank.keyRelease(e);
		}

		public void keyPressed(KeyEvent e) {
			myTank.keyPress(e);
		}

	}
}
