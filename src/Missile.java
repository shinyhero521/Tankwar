import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.sun.org.apache.bcel.internal.generic.RETURN;

public class Missile {

	private static final int XSPEED = 10;
	private static final int YSPEED = 10;
	private boolean bLive = true;

	// 定义Missile高度，宽
	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;
	int x, y;
	Tank.Direction dir;

	public Missile(int x, int y, Tank.Direction dir) {

		this.x = x;
		this.y = y;
		this.dir = dir;
	}

	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.BLACK);
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);
		move();
	}

	private void move() {
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
		if (x < 0 || y < 0 || x > TankClient.GAME_WIDTH || y > TankClient.GAME_HEIGHT) {
			bLive = false;
		}
	}

	public boolean isbLive() {
		return bLive;
	}

	// 子弹的框框
	public Rectangle getRect() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}

	// 是否打中坦克（子弹的框框是否和坦克t的框框相交）
	public boolean hitTank(Tank t) {
		//这里 && t.isLive() 非常厉害，如果不加这个的话，那么打到死去的坦克 子弹依然消失
		if (this.getRect().intersects(t.getRect())&& t.isLive()) {
			t.setLive(false);
			//这里自创的当子弹击中坦克，然后让子弹bLive = false
			this.bLive=false;
			return true;
		}
		return false;
	}

}
