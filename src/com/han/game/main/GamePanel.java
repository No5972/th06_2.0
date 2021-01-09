package com.han.game.main;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.han.game.control.GetKeys;
import com.han.game.enums.MenuMode;
import com.han.game.model.CFPSMaker;
import com.han.game.model.GameObject;
import com.han.game.model.Player;

/**
 * 主类
 * @author 十七
 *
 */
public class GamePanel extends JPanel implements Runnable, KeyListener, MouseListener,
		MouseMotionListener {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Graphics2D g;
	private Image dbImage;
	private Thread gameLoop;
	private GetKeys getKeys;
	private CFPSMaker fpsMaker;
	private int back_y;
	private int time;
	private int time2;
	private int time3;
	private int time4;

	/**
	 * 0：菜单
	 * 1：说明
	 * 2：退出
	 * 3：加载游戏
	 * 4：死亡
	 * 5：继续
	 * 6：胜利
	 * 7：暂停
	 */
	private int menuMode;

	public Image bgImg;
	public Image pImg;
	public Image bImg;
	public Image eImg;
	public Image eImg2;
	public Image aImg;
	public Image boImg;
	public Image iconImg;
	public Image frontImg;
	public Player player;
	public ObjectsArray enemys;
	public ObjectsArray shoots;
	public ObjectsArray bullets;
	public ObjectsArray boss;
	public ObjectsArray getScores;
	public Point moveP = new Point(0, 0);
	public Point checkP;
	public AudioClip bgm[];
	public int timepaint;
	/**
	 * 是否无敌
	 */
	public boolean isM;

	public GamePanel() {
		dbImage = null;
		player = new Player();
		getKeys = new GetKeys();
		fpsMaker = new CFPSMaker();
		enemys = new ObjectsArray("Enemy", 50);
		shoots = new ObjectsArray("Shoot", 200);
		bullets = new ObjectsArray("Bullet", 800);
		boss = new ObjectsArray("Boss", 10);
		getScores = new ObjectsArray("GetScore", 500);
		menuMode = MenuMode.MAIN_MENU.getMode();
		isM = false;
		time = 0;
		time2 = 0;
		bgm = new AudioClip[32];
		time3 = 0;
		time4 = -1;
		timepaint = 1;


		// 背景
		ImageIcon imageicon1 = new ImageIcon(getClass().getResource("/images/bg.png"));
		bgImg = imageicon1.getImage();
		back_y = -bgImg.getHeight(null) + bgImg.getHeight(null);

		ImageIcon imageicon2 = new ImageIcon(getClass().getResource("/images/player00.png"));
		pImg = imageicon2.getImage();

		ImageIcon imageicon3 = new ImageIcon(getClass().getResource("/images/stg1enm.png"));
		eImg = imageicon3.getImage();

		ImageIcon imageicon5 = new ImageIcon(getClass().getResource("/images/stg6enm2.png"));
		bImg = imageicon5.getImage();

		ImageIcon imageicon6 = new ImageIcon(getClass().getResource("/images/etama3.png"));
		aImg = imageicon6.getImage();

		ImageIcon imageicon7 = new ImageIcon(getClass().getResource("/images/etama4.png"));
		eImg2 = imageicon7.getImage();

		ImageIcon imageicon8 = new ImageIcon(getClass().getResource("/images/boom.png"));
		boImg = imageicon8.getImage();

		ImageIcon imageicon9 = new ImageIcon(getClass().getResource("/images/timg.jpg"));
		iconImg = imageicon9.getImage();
		
		ImageIcon frontImage = new ImageIcon(getClass().getResource("/images/front.png"));
		frontImg = frontImage.getImage();

		// 音乐
		bgm[0]=Applet.newAudioClip(getClass().getResource("/sounds/th06_01.wav"));
		bgm[1]=Applet.newAudioClip(getClass().getResource("/sounds/th06_02.wav"));
		bgm[2]=Applet.newAudioClip(getClass().getResource("/sounds/th06_13.wav"));
		bgm[3]=Applet.newAudioClip(getClass().getResource("/sounds/biubiu.mp3"));
		bgm[4]=Applet.newAudioClip(getClass().getResource("/sounds/break.wav"));
		bgm[5]=Applet.newAudioClip(getClass().getResource("/sounds/skill.wav"));
		bgm[6]=Applet.newAudioClip(getClass().getResource("/sounds/th06_17.wav"));
		bgm[7]=Applet.newAudioClip(getClass().getResource("/sounds/don00.wav"));
		bgm[8]=Applet.newAudioClip(getClass().getResource("/sounds/se_plst00.wav")); // 攻击
		bgm[9]=Applet.newAudioClip(getClass().getResource("/sounds/se_enep00.wav")); // 小怪被毁
		bgm[10]=Applet.newAudioClip(getClass().getResource("/sounds/se_item00.wav")); // 拾取道具

		// 开启焦点-按键
		setFocusable(true);
		// 添加键盘监听事件
		addKeyListener(this);
		// 添加鼠标监听事件
		addMouseListener(this);
		addMouseMotionListener(this);

		GameObject.gameObjectInit(this);
		gameLoop = new Thread(this);
		gameLoop.start();

		new TimeThread().start();

		/**
		 * 添加动画定时器
		 * 2020-05-21 15:30:28
		 */
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				timepaint++;
				if (timepaint == 5) {
					timepaint = 1;
				}
//				System.out.println(timepaint);
			}
		}, 1000, 140);

	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getTime3() {
		return time3;
	}

	public void setTime3(int time3) {
		this.time3 = time3;
	}

	public int getTime4() {
		return time4;
	}

	public void setTime4(int time4) {
		this.time4 = time4;
	}

	/**
	 * 游戏初次载入
	 */
	public void gameSet() {
		menuMode = MenuMode.MAIN_MENU.getMode();
		// 载入角色信息
		player.setData(312, 539, 0, 0, 1, 0, 0, 10, 'n');
		player.setBoom(3 - player.getBoom());
		shoots.allErase();
		bullets.allErase();
		enemys.allErase();
		getScores.allErase();
		time2 = 0;
	}

	/**
	 * 游戏资源更新
	 */
	public void gameUpdate() {
		if (menuMode == MenuMode.LOADING.getMode()) {
			bGM(1, 0);
			bullets.allMove();
			shoots.allMove();
			enemys.allMove();
			boss.allMove();
			getScores.allMove();
			player.move(getKeys);
			if (getKeys.esc) {
				setMenuMode(MenuMode.PAUSE.getMode());
				getKeys.esc = false;
			}

		}
	}

	/**
	 * 游戏资源载入
	 */
	private void gameRender() {
		// 创建窗口，双缓冲
		if (dbImage == null) {
			dbImage = createImage(850, 1000);
			if (dbImage == null)
				return;
			g = (Graphics2D) dbImage.getGraphics();
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}

		// 背景叠加循环
		g.drawImage(bgImg, 0, back_y++, null);
		g.drawImage(bgImg, 0, back_y - bgImg.getHeight(null), null);
		if (back_y == bgImg.getHeight(null)) {
			back_y = 0;
		}

		player.draw(g);
		shoots.allDraw(g);
		enemys.allDraw(g);
		bullets.allDraw(g);
		boss.allDraw(g);
		getScores.allDraw(g);

		if (isM) {
			ImageIcon imageicon = new ImageIcon(getClass().getResource("/images/stg7enm.png"));
			Image dun = imageicon.getImage();
			g.drawImage(dun, (int)player.getPx()-30, (int)player.getPy()-15,
					(int)player.getPx()+34, (int)player.getPy()+49, 0, 96, 64, 160, null);
			time++;
			if (time == 200) {
				isM = false;
				time = 0;
			}
		}

		// boss入场条件
		if (player.getFrame() >= 3300) {
			g.drawRect(30, 100, 500, 10);
			// boss血条阶段管理
			int life = enemys.getObject(0).getLife();

			if (life > 1000) {
				g.setColor(Color.yellow);
				g.fillRect(30, 100, life - 1000, 11);
			} else if (life >= 500) {
				g.setColor(Color.blue);
				g.fillRect(30, 100, life - 500, 11);
			} else if (life >= 0) {
				g.setColor(Color.red);
				g.fillRect(30, 100, life, 11);
			}
		}
		// 玩家血条
		g.setColor(Color.red);
		// g.drawRect(600, 350, 200, 10);
		// g.fillRect(600, 350, player.getLife() * 20, 11);
		g.drawRect(655, 345, 145, 25);
		g.fillRect(655, 345, 145 * player.getLife() / 10, 25);


		// 分数和火力
		paintScore(g);

		if (menuMode != MenuMode.LOADING.getMode()) {
			if (menuMode == MenuMode.MAIN_MENU.getMode()) { // 主菜单
				bGM(0, 1);
				// 游戏开始界面
				ImageIcon imageicon = new ImageIcon(getClass().
						getResource("/images/title00.jpg"));
				Image menu = imageicon.getImage();
				// 标题字体
				imageicon = new ImageIcon(getClass().getResource("/images/front.png"));
				Image title = imageicon.getImage();
				// 开始
				imageicon = new ImageIcon(getClass().getResource("/images/start.png"));
				Image start = imageicon.getImage();
				// 帮助
				imageicon = new ImageIcon(getClass().getResource("/images/help.png"));
				Image help = imageicon.getImage();
				// 退出
				imageicon = new ImageIcon(getClass().getResource("/images/Exit.png"));
				Image Exit = imageicon.getImage();
				////////////////////////////////////////////
				int i = 60;
				g.drawImage(menu, 0, 0, 850, 1000, 0, 0, 640, 480, null);
				g.drawImage(title, 1, 1, 128, 128, 0, 0, 64, 64, null); // 东
				g.drawImage(title, 71 + i, 10, 198 + i, 137, 64, 0, 128, 64, null); // 方
				g.drawImage(title, 141 + 2 * i, 10, 268 + 4 * i, 128 + i, 128, 0, 192, 64, null); // 红
				g.drawImage(title, 211 + 5 * i, 10, 338 + 5 * i, 137, 192, 0, 256, 64, null); // 魔
				g.drawImage(title, 281 + 6 * i, 10, 408 + 6 * i, 137, 0, 64, 64, 128, null); // 乡
				// 按钮
				g.drawImage(start, 600, 400, 800, 500, 0, 0, 100, 32, null);
				g.drawImage(help, 600, 530, 800, 630, 0, 0, 52, 32, null);
				g.drawImage(Exit, 600, 650, 800, 750, 0, 0, 100, 32, null);
				// 鼠标移动到按钮是的效果
				// 开始游戏
				if ((moveP.getX() >= 600 && moveP.getX() <= 800) &&
						(moveP.getY() >= 400 && moveP.getY() <= 500)) {
					if (getKeys.down) {
						moveP.setLocation(700, 580);
						getKeys.down = false;
					}
					if (getKeys.up) {
						moveP.setLocation(700, 700);
						getKeys.up = false;
					}
					if (getKeys.z) {
						setMenuMode(MenuMode.LOADING.getMode());
					}

					Color c = g.getColor();
					g.setColor(Color.white);
					g.drawRect(600, 400, 200, 100);
					g.setColor(c);
				}
				// 说明
				if ((moveP.getX() >= 600 && moveP.getX() <= 800) &&
						(moveP.getY() >= 530 && moveP.getY() <= 630)) {
					if (getKeys.down) {
						moveP.setLocation(700, 700);
						getKeys.down = false;
					}
					if (getKeys.up) {
						moveP.setLocation(700, 450);
						getKeys.up = false;
					}

					if (getKeys.z) {
						setMenuMode(MenuMode.HELP.getMode());
					}

					Color c = g.getColor();
					g.setColor(Color.yellow);
					g.drawRect(600, 530, 200, 100);
					g.setColor(c);
				}
				// 退出
				if ((moveP.getX() >= 600 && moveP.getX() <= 800) &&
						(moveP.getY() >= 650 && moveP.getY() <= 750)) {
					if (getKeys.down) {
						moveP.setLocation(700, 450);
						getKeys.down = false;
					}
					if (getKeys.up) {
						moveP.setLocation(700, 580);
						getKeys.up = false;
					}
					if (getKeys.z) {
						setMenuMode(MenuMode.EXIT.getMode());
					}

					Color c = g.getColor();
					g.setColor(Color.pink);
					g.drawRect(600, 650, 200, 100);
					g.setColor(c);
				}

				if (getKeys.down || getKeys.up) {
					moveP.setLocation(700, 450);
					getKeys.down = false;
					getKeys.up = false;
				}

				// 说明页面
			} else if (menuMode == MenuMode.HELP.getMode()) {
				ImageIcon imageicon = new ImageIcon(getClass().
						getResource("/images/slpl00a.png"));
				Image help1 = imageicon.getImage();
				imageicon = new ImageIcon(getClass().getResource("/images/slpl00b.png"));
				Image help2 = imageicon.getImage();
				g.drawImage(help1, 0, 0, 850, 500, 0, 0, 256, 256, null);
				g.drawImage(help2, 0, 500, 850, 1000, 0, 0, 256, 240, null);
				if ((moveP.getX() >= 705 && moveP.getX() <= 835) &&
						(moveP.getY() >= 920 && moveP.getY() <= 1000)) {
					Color c = g.getColor();
					g.setColor(Color.red);
					g.drawRect(705, 920, 130, 990);
					g.setColor(c);
				}

				if (getKeys.x) {
					setMenuMode(MenuMode.MAIN_MENU.getMode());
				}

				// 退出
			} else if (menuMode == MenuMode.EXIT.getMode()) {
				System.exit(0);
				// 死亡
			} else if (menuMode == MenuMode.DEFEAT.getMode()) {
				ImageIcon imageicon = new ImageIcon(getClass().
						getResource("/images/result.png"));
				Image dead = imageicon.getImage();
				g.drawImage(dead, 0, 0, 850, 1000, 0, 0, 640, 480, null);
				if ((moveP.getX() >= 20 && moveP.getX() <= 310) &&
						(moveP.getY() >= 670 && moveP.getY() <= 850)) {

					if (getKeys.left) {
						moveP.setLocation(430, 720);
						getKeys.left = false;
					}
					if (getKeys.right) {
						moveP.setLocation(430, 720);
						getKeys.right = false;
					}
					if (getKeys.z) {
						setMenuMode(MenuMode.CONTINUE.getMode());
					}

					Color c = g.getColor();
					g.setColor(Color.red);
					g.drawRect(20, 670, 310, 180);
					g.setColor(c);
				}
				if ((moveP.getX() >= 350 && moveP.getX() <= 620) &&
						(moveP.getY() >= 670 && moveP.getY() <= 850)) {

					if (getKeys.left) {
						moveP.setLocation(100, 720);
						getKeys.left = false;
					}
					if (getKeys.right) {
						moveP.setLocation(100, 720);
						getKeys.right = false;
					}
					if (getKeys.z) {
						player.erase();
						gameSet();
						bgm[1].stop();
						bgm[2].stop();
						bgm[7].stop();
						time4 = -1;
						setMenuMode(MenuMode.MAIN_MENU.getMode());
					}

					Color c = g.getColor();
					g.setColor(Color.green);
					g.drawRect(350, 670, 270, 180);
					g.setColor(c);
				}

				if (getKeys.left || getKeys.right) {
					moveP.setLocation(100, 720);
					getKeys.left = false;
					getKeys.right = false;
				}

				// 继续
			} else if (menuMode == MenuMode.CONTINUE.getMode()) {
				bullets.allErase();
				player.setLife(10);
				player.setBoom(3);
				menuMode = MenuMode.LOADING.getMode();
				isM = true;
				// 胜利
			} else if (menuMode == MenuMode.THE_END.getMode()) {
				ImageIcon imageicon = new ImageIcon(getClass().
						getResource("/images/end03.jpg"));
				Image ve = imageicon.getImage();
				g.drawImage(ve, 0, 0, 850, 1000, 0, 0, 640, 480, null);
				Font previousFont = g.getFont();
				g.setFont(new Font("KaiTi", Font.BOLD, 36));
				this.drawStringEx(g, "終 わ り", 300, 20, Color.WHITE, 0.7F);
				this.drawStringEx(g, "最终得点：" + player.getScore(), 200, 60, Color.WHITE, 0.7F);
				g.setFont(previousFont);
				if ((moveP.getX() >= 740 && moveP.getX() <= 815) &&
						(moveP.getY() >= 200 && moveP.getY() <= 720)) {
					Color c = g.getColor();
					g.setColor(Color.black);
					g.drawRect(740, 200, 75, 520);
					g.setColor(c);
				}
				// 暂停（方便截图用）
			} else if (menuMode == MenuMode.PAUSE.getMode()) {
				ImageIcon imageicon = new ImageIcon(getClass().
						getResource("/images/pause.png"));
				Image dead = imageicon.getImage();
				g.drawImage(dead, -50, 50, 750, 950, 0, 0, 850, 1000, null);
				if (getKeys.esc) {
					setMenuMode(MenuMode.LOADING.getMode());
					getKeys.esc = false;
				}

			}
		}

		Font previousFont = g.getFont();
		fpsMaker.makeFPS();
		g.setFont(new Font("KaiTi", Font.BOLD, 20));
		this.drawStringEx(g, "FPS: " + fpsMaker.getFPS(), 740, 940, Color.WHITE, 0.7F);
		g.setFont(previousFont);
	}




	public void setTime2(int time2) {
		this.time2 = time2;
	}

	/**
	 *  bgm[0]=背景音乐
	 *  bgm[1]=道中音乐
	 *  bgm[2]=boss音乐
	 *  bgm[3]=被击音乐
	 *  bgm[4]=击破音乐
	 *  bgm[5]=技能音乐
	 *  bgm[6]=胜利音乐
	 *  bgm[7]=计时音乐
	 * @param i
	 */
	public void bGM(int i, int j) {
		time2++;
		if (time2 == 1) {
			bgm[i].loop();
		}
		bgm[j].stop();
	}

	/**
	 * 加载选项模块
	 * @param i
	 */
	public void setMenuMode(int i) {
		menuMode = i;
	}

	/**
	 *
	 * @param g 画分，画火力，B弹
	 */
	public void paintScore(Graphics2D g) {
		g.setColor(new Color(0xFFFFFF));
		Paint gp = g.getPaint();
		// GradientPaint newGp = new GradientPaint(0,0,Color.WHITE,0,7,Color.GRAY);
		LinearGradientPaint newGp = new LinearGradientPaint(0, 0, 0, 7, new float[]{0F,1F}, new Color[] {Color.RED, Color.GRAY} );
		g.setPaint(newGp);
		g.setFont(new Font("Sylfaen", Font.BOLD, 20));

		this.drawStringEx(g, "" + player.getScore(), 660, 200, Color.WHITE, 0.5F);
		// this.drawTransparentImage(g, Color.BLACK, frontImg, 600, 200, 631, 617, 0, 207, 31, 224);
		// this.drawTransparentImage(g, new Color(0,0,0), frontImg, 600, 200, 631, 217, 0, 207, 31, 224);
		g.drawImage(frontImg, 600, 200, 631, 217, 0, 207, 31, 224, null);
		
		if (player.getPower() < 15) {
			g.fillRect(655, 245, 145 * player.getPower() / 15, 25);
            this.drawStringEx(g, "" + player.getPower(), 660, 250, Color.WHITE, 0.5F);
        } else {
        	g.fillRect(655, 245, 145, 25);
		    g.drawImage(frontImg, 660, 250, 719, 268, 64, 244, 108, 258, null);
        }
		g.drawImage(frontImg, 600, 250, 648, 267, 34, 207, 82, 224, null);
		this.drawStringEx(g, "" + player.getBoom(), 660, 300, Color.WHITE, 0.5F);
		g.drawImage(frontImg, 600, 300, 644, 317, 0, 160, 44, 177, null);
		
		g.drawImage(frontImg, 600, 350, 650, 367, 0, 176, 50, 193, null);

        // 标题字体
        ImageIcon imageicon = new ImageIcon(getClass().getResource("/images/front.png"));
        Image title = imageicon.getImage();
        int i = 60;
        g.drawImage(title, 590, 650, 791, 851, 125, 125, 255, 255, null); // 圈
        g.drawImage(title, 590, 650, 654, 714, 0, 0, 64, 64, null); // 东
        g.drawImage(title, 660, 650, 724, 714, 64, 0, 128, 64, null); // 方
        g.drawImage(title, 630, 690, 757, 807, 128, 0, 192, 64, null); // 红
        g.drawImage(title, 670, 780, 734, 844, 192, 0, 256, 64, null); // 魔
        g.drawImage(title, 740, 780, 804, 844, 0, 64, 64, 128, null); // 乡


		// g.drawStringEx("现在可以按键暂停：ESC", 580, 700);
		g.setFont(new Font("Kaiti", Font.BOLD, 20));
		this.drawStringEx(g, "现在可以按键暂停：ESC", 580, 500, Color.WHITE, 0.5F);
		this.drawStringEx(g, "现在可以按键无敌：SPACE", 580, 550, Color.WHITE, 0.5F);

		if (time4 == 20) {
			bgm[7].loop();
		}
		if (time4 >= 0) {
			g.setColor(new Color(0xFF0000));
			g.setFont(new Font("KaiTi", Font.BOLD, 40));
			this.drawStringEx(g, "倒计时: " + time4, 600, 400, Color.WHITE, 0.7F);
		}

		g.setPaint(gp);

	}

	/**
	 * 画窗口初始化
	 */
	private void paintScreen() {
		Graphics g = getGraphics();
		if (g != null && dbImage != null)
			g.drawImage(dbImage, 0, 0, null);
	}


	/**
	 * 时间线程控制，间隔1s
	 * @author 十七
	 *
	 */
	class TimeThread extends Thread {
		public void run() {
			while (true) {
//				time3 += time4;
//				if (time3 == 14) {
//					time4 = -1;
//				}
//				if (time3 == 0) {
//					time4 = 1;
//				}
				time3++;
				time4--;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 线程20毫秒
	 */
	public void run() {
//		System.out.println("执行");
//		System.out.println(menuMode);
		gameSet();
		do {
			gameUpdate();
			gameRender();
			paintScreen();


			// 屏幕睡眠
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// 玩家死亡检测
			if (!player.getExist()) {
				setMenuMode(MenuMode.DEFEAT.getMode());
			}
		} while (true);
	}

	// 按键监听事件
	public void keyTyped(KeyEvent e) {
	}
	public void keyPressed(KeyEvent e) {
		getKeys.keyPressed(e.getKeyCode());
	}
	public void keyReleased(KeyEvent e) {
		getKeys.keyReleased(e.getKeyCode());
	}

	// 获取鼠标焦点
	public void mouseDragged(MouseEvent e) {
	}
	public void mouseMoved(MouseEvent e) {
		moveP = e.getPoint();
	}

	// 鼠标监听事件
	public void mouseClicked(MouseEvent e) {
	}
	public void mouseReleased(MouseEvent e) {
	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}
	public void mousePressed(MouseEvent e) {
		checkP = e.getPoint();
		// 开始菜单
		if (menuMode == MenuMode.MAIN_MENU.getMode()) {
			// 开始游戏
			if ((moveP.getX() >= 600 && moveP.getX() <= 800) &&
					(moveP.getY() >= 400 && moveP.getY() <= 500)) {
				gameSet();
				setMenuMode(MenuMode.LOADING.getMode());
			}
			// 说明
			if ((moveP.getX() >= 600 && moveP.getX() <= 800) &&
					(moveP.getY() >= 530 && moveP.getY() <= 630)) {
				setMenuMode(MenuMode.HELP.getMode());
			}
			// 退出
			if ((moveP.getX() >= 600 && moveP.getX() <= 800) &&
					(moveP.getY() >= 650 && moveP.getY() <= 750)) {
				setMenuMode(MenuMode.EXIT.getMode());
			}
		}
		// 说明菜单
		if (menuMode == MenuMode.HELP.getMode()) {
			if ((moveP.getX() >= 705 && moveP.getX() <= 835) &&
					(moveP.getY() >= 920 && moveP.getY() <= 1000)) {
				setMenuMode(MenuMode.MAIN_MENU.getMode());
			}
		}
		// 失败页面
		if (menuMode == MenuMode.DEFEAT.getMode()) {
			// 继续
			if ((moveP.getX() >= 20 && moveP.getX() <= 310) &&
					(moveP.getY() >= 670 && moveP.getY() <= 850)) {
				System.out.println("继续");
				setMenuMode(MenuMode.CONTINUE.getMode());
			}
			// 回主菜单
			if ((moveP.getX() >= 350 && moveP.getX() <= 620) &&
					(moveP.getY() >= 670 && moveP.getY() <= 850)) {
				player.erase();
				gameSet();
				bgm[1].stop();
				bgm[2].stop();
				bgm[7].stop();
				time4 = -1;
				setMenuMode(MenuMode.MAIN_MENU.getMode());
			}
		}
		// 胜利页面
		if (menuMode == MenuMode.THE_END.getMode()) {
			if ((moveP.getX() >= 740 && moveP.getX() <= 815) &&
					(moveP.getY() >= 200 && moveP.getY() <= 720)) {
				setMenuMode(MenuMode.MAIN_MENU.getMode());
				bgm[6].stop();
				gameSet();
			}
		}
	}

	private void drawStringEx(Graphics2D g, String text, int x, int y, Color borderColor, float borderWidth) {
		Font f = g.getFont();
		Paint previousPaint = g.getPaint();
		GlyphVector v = f.createGlyphVector(getFontMetrics(f).getFontRenderContext(), text);
		Shape shape = v.getOutline();

		Rectangle bounds = shape.getBounds();
		g.translate(x - bounds.x, y - bounds.y);

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(g.getColor());
		g.fill(shape);
		g.setColor(borderColor);
		g.setStroke(new BasicStroke(borderWidth));
		g.draw(shape);
		g.setPaint(previousPaint);

		g.translate((x - bounds.x) * -1, (y - bounds.y) * -1);
	}
	
	private void drawTransparentImage(Graphics2D g, Color color, Image im, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2) {
		ImageFilter filter = new RGBImageFilter() {
			public int markerRGB = color.getRGB() | 0xFF000000;
	 
			@Override
			public final int filterRGB(int x, int y, int rgb) {
				if ((rgb | 0xFF000000) == markerRGB) {
					return 0x00FFFFFF & rgb;
				} else {
					return rgb;
				}
			}
		};
	 
		ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
		Image toolkitImage = Toolkit.getDefaultToolkit().createImage(ip);
	 
		BufferedImage bi = new BufferedImage(im.getWidth(null), im.getHeight(null), BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g2d = bi.createGraphics();
		g2d.drawImage(toolkitImage, 0, 0, im.getWidth(null), im.getHeight(null), null);
		g2d.dispose();
		
		g.drawImage(bi, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
	}

}
