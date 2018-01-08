import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

//面向对象的思维
public class Tank {
	public static final int XSPEED = 5;
	public static final int YSPEED = 5;
	private int x, y;
	private boolean bL = false, bR = false, bU = false, bD = false;

	// 枚举方向写法
	enum Direction {
		L, LU, U, RU, R, RD, D, LD, STOP
	};

	private Direction dir = Direction.STOP;

	// 枚举结束，并且默认是stop
	public Tank(int x, int y) {

		this.x = x;
		this.y = y;
	}

	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.cyan);
		g.fillOval(x, y, 30, 30);
		g.setColor(c);
		move();
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
