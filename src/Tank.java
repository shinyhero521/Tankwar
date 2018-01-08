import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

//��������˼ά
public class Tank {
	public static final int XSPEED = 5;
	public static final int YSPEED = 5;
	private int x, y;
	private boolean bL = false, bR = false, bU = false, bD = false;

	// ö�ٷ���д��
	enum Direction {
		L, LU, U, RU, R, RD, D, LD, STOP
	};

	private Direction dir = Direction.STOP;

	// ö�ٽ���������Ĭ����stop
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
		// ������Tank����
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
	 * ��������ö�ٵ�direction��Ȼ��ͨ������ֵ�жϷ��������ӵ����� locateDirection();
	 */
	void locateDirection() {
		// ��else��Ŀ����������� if���������ߣ���������һ��if��������֮�����һ��if
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
		// ������Tank����
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
		// �������������Ĵ����ʾ ���̧����� �� stop������ֱ��������Ĵ��� ��ࣻ���߲������������Ǹ�̰����һ�����⶯��
		locateDirection();
	}
}
