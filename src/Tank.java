import java.awt.*;
import java.awt.event.*;

//面向对象的思维
public class Tank {
	public static final int XSPEED = 5;
	public static final int YSPEED = 5;

	// 定义坦克高度，宽
	public static final int WIDTH = 30;
	public static final int HEIGHT = 30;
	private int x, y;
	private boolean bL = false, bR = false, bU = false, bD = false;
	private boolean good;
	private boolean live = true;

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	// 枚举方向写法
	enum Direction {
		L, LU, U, RU, R, RD, D, LD, STOP
	};

	private Direction dir = Direction.STOP;
	private Direction ptDir = Direction.D;
	TankClient tc;

	// 枚举结束，并且默认是stop
	public Tank(int x, int y, boolean good) {

		this.x = x;
		this.y = y;
		this.good = good;
	}

	/**
	 * 持有对方的引用 这里比较牛逼，TankClient tc; ##持有对方的引用##;这里创一个 tc ， 然后在TankClient里 Tank
	 * myTank = new Tank(50, 50,this); 太厉害了
	 */

	public Tank(int x, int y, boolean good, TankClient tc) {
		this(x, y, good);
		this.tc = tc;
	}

	// 坦克的框框
	public Rectangle getRect() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}

	public void draw(Graphics g) {
		// 如果非活着，直接不画了return
		if (!live)
			return;
		Color c = g.getColor();
		if (good)
			g.setColor(Color.cyan);
		else
			g.setColor(Color.green);
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);
		move();
		// 这里是根据炮筒的方向画出的线
		switch (ptDir) {
		case L:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x, y + Tank.HEIGHT / 2);
			break;
		case LU:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x, y);
			break;
		case U:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x + Tank.WIDTH / 2, y);
			break;
		case RU:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x + Tank.WIDTH, y);
			break;
		case R:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x + Tank.WIDTH, y + Tank.HEIGHT / 2);
			break;
		case RD:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x + Tank.WIDTH, y + Tank.HEIGHT);
			break;
		case D:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x + Tank.WIDTH / 2, y + Tank.HEIGHT);
			break;
		case LD:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x, y + Tank.HEIGHT);
			break;
		}
	};

	void move() {
		switch (dir) {
		case L:
			x -= XSPEED;
			break;
		case LU:
			x -= XSPEED;
			y -= YSPEED;
			break;
		case U:
			y -= YSPEED;
			break;
		case RU:
			x += XSPEED;
			y -= YSPEED;
			break;
		case R:
			x += XSPEED;
			break;
		case RD:
			x += XSPEED;
			y += YSPEED;
			break;
		case D:
			y += YSPEED;
			break;
		case LD:
			x -= XSPEED;
			y += YSPEED;
			break;
		case STOP:
			break;
		}
		if (this.dir != Direction.STOP) {
			this.ptDir = this.dir;
		}
		// 限制坦克别走出框
		if (x < 0) {
			x = 0;
		}
		if (y < 30) {
			y = 30;
		}
		if (y + Tank.HEIGHT > TankClient.GAME_HEIGHT) {
			y = TankClient.GAME_HEIGHT - Tank.HEIGHT;
		}
		if (x + Tank.WIDTH > TankClient.GAME_WIDTH) {
			x = TankClient.GAME_WIDTH - Tank.WIDTH;
		}
	}

	public void keyPress(KeyEvent e) {
		int key = e.getKeyCode();
		// 调键盘Tank方向
		switch (key) {

		case KeyEvent.VK_LEFT:
			bL = true;
			break;
		case KeyEvent.VK_UP:
			bU = true;
			break;
		case KeyEvent.VK_RIGHT:
			bR = true;
			break;
		case KeyEvent.VK_DOWN:
			bD = true;
			break;

		}
		locateDirection();
	};

	// 子弹打的动作
	public void fire() {
		int x = this.x + Tank.WIDTH / 2 - Missile.WIDTH / 2;
		int y = this.y + Tank.HEIGHT / 2 - Missile.HEIGHT / 2;
		// 这里ptDir 是用了 子弹是坦克的 所以由坦克管理，子弹的方向不在missile类里，在坦克类里。

		Missile m = new Missile(x, y, ptDir,this.tc);
		tc.missiles.add(m);
		

	}

	/**
	 * 这里是用枚举的direction，然后通过布尔值判断方向，最后添加到上面 locateDirection();
	 */
	void locateDirection() {
		// 加else的目的是如果不加 if还得往下走（继续走下一个if），加上之后就走一个if
		if (bL && !bU && !bR && !bD)
			dir = Direction.L;
		else if (bL && bU && !bR && !bD)
			dir = Direction.LU;
		else if (!bL && bU && !bR && !bD)
			dir = Direction.U;
		else if (!bL && bU && bR && !bD)
			dir = Direction.RU;
		else if (!bL && !bU && bR && !bD)
			dir = Direction.R;
		else if (!bL && !bU && bR && bD)
			dir = Direction.RD;
		else if (!bL && !bU && !bR && bD)
			dir = Direction.D;
		else if (bL && !bU && !bR && bD)
			dir = Direction.LD;
		else if (!bL && !bU && !bR && !bD)
			dir = Direction.STOP;
	}

	public void keyRelease(KeyEvent e) {
		int key = e.getKeyCode();
		// 调键盘Tank方向
		switch (key) {
		case KeyEvent.VK_CONTROL:
			fire();
			break;
		case KeyEvent.VK_LEFT:
			bL = false;
			break;
		case KeyEvent.VK_UP:
			bU = false;
			break;
		case KeyEvent.VK_RIGHT:
			bR = false;
			break;
		case KeyEvent.VK_DOWN:
			bD = false;
			break;

		}
		/*
		 * if (!bL && !bU && !bR && !bD) dir = Direction.STOP;
		 */
		// 这个可以用上面的代码表示 如果抬起键盘 就 stop；或者直接用下面的代码 简洁；或者不用这个代码就是跟贪吃蛇一样随意动。
		locateDirection();
	}
}
