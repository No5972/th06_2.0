package com.han.game.model;

import java.awt.Graphics;
import java.util.List;
import java.util.Map;

import com.han.game.consts.Constants;
import com.han.game.control.GetKeys;
import com.han.game.enums.HitObject;
import com.han.game.enums.MenuMode;

/**
 * 自机主类
 * 
 * @author 十七
 *
 */
public class Player extends GameObject {
	private int shotInterval;
	private int boom = 3;
	private int power;
	private int score;

	public Player() {
	}

	public void setData(double d, double d1, double d2, double d3,
			int i, int j, int k, int l, char m) {
		super.setData(d, d1, d2, d3, i, j, k, l, 'n');
		shotInterval = 0;
		power = 0;
		score = 0;
	}

	public void draw(Graphics g) {
		super.draw(g);
	}
	

	public void move(GetKeys getkeys) {
		super.move();
		// 移动增量
		vx = 0;
		vy = 0;
		if (getkeys.up) {
			vy -= 8;
		}
		if (getkeys.down)
			vy += 8;
		if (getkeys.left) {
			vx = -8;
			anim = 'l';
		}
		if (getkeys.right) {
			vx = 8;
			anim = 'r';
		}
		if (getkeys.shift) {
			vx *= 0.5;
			vy *= 0.5;
		}
		// 动画控制
		if (getkeys.left == false && getkeys.right == false) {
			anim = 'n';
		}
		
		if (getkeys.x) {
			if (boom > 0 && life > 0) {
				if ((tmp = p.shoots.getEmpty()) != null) {
					tmp.setData(280, 900, 0, -20, 101, 0, 0, 0, 'n');
					p.bgm[5].play();
					boom--;
					p.bullets.eraseBullet();
					getkeys.x = false;
				}
			}
		}
		// 是否无敌
		if (p.isM == false) {
			if (getkeys.space) {
				p.isM = true;
				p.setTime(300);
				getkeys.space = false;
			}
		}
		if (p.isM == true) {
			if (getkeys.space) {
				p.isM = false;
				p.setTime(0);
				getkeys.space = false;
			}
		}
		
		

		// 自机子弹模块
		if (shotInterval < 1) {
			if (getkeys.z) {
				shotInterval = 1;
				for (int i = -8; i <= 8; i += 16) {
					// 发射子弹
					if ((tmp = p.shoots.getEmpty()) != null)
						tmp.setData(px + (double) i, py, 0, -24, 16, 0, 0, 0, 'n');
				}
//					if ((tmp = p.shoots.getEmpty()) != null)
//						tmp.setData(px + (double) i, py,
//								Math.cos((Math.PI*frame/36)) * 6,
//								Math.sin((Math.PI*frame/36)) * 6
//								, 16, 0, 0, 0);
//				}

				int i = 21;
				if (power < 12)
					i = 15;
				if (power < 9)
					i = 9;
				if (power < 6)
					i = 3;
				if (power < 3)
					i = 0;
				byte byte0;
				// 按住shift，聚集子弹
				if (getkeys.shift) {
					byte0 = 6;
				} else {
					i *= 2;
					byte0 = 12;
				}
				// 根据火力调节子弹弹幕
				for (int j = 90 - i; j <= 90 + i; j += byte0)
					if ((tmp = p.shoots.getEmpty()) != null) {
						tmp.setData(px, py, -Math.cos(Math.toRadians(j)) * 24,
								-Math.sin(Math.toRadians(j)) * 24, 20, 0,
								0, 0, 'n');
					}
				
				p.bgm[8].play(); // 攻击音效
			}

			// 限制移动范围
			if (px < 10) {
				px = 10;
			}
			if (py < 150) {
				py = 150;
			}
			if (px > 540) {
				px = 540;
			}
			if (py > 925) {
				py = 925;
			}

		} else {
			shotInterval--;
		}
		
		if (this.py < 300) {
			p.bullets.setAllApproaching();
		}

		int i = 0;
		do {
			if (i >= p.bullets.getArrayMax()) {
				break;
			}
			// 玩家被击中事件
			if ((tmp = p.bullets.getObject(i)).getExist()) {
				// 奖励事件
				if (tmp.size == HitObject.POWER.getSize() || tmp.size == HitObject.POINT.getSize() || tmp.size == HitObject.SMALL_POINT.getSize()) {
					if (25 > Math.hypot((px + 5) - (tmp.getPx() + tmp.getSize() / 2),
							(py + 15) - (tmp.getPy() + tmp.getSize() / 2))) {
						if (tmp.size == HitObject.POWER.getSize()) { // 灵力
							p.bgm[10].play();
							tmp.erase();
							power++;
							break;
						} else if (tmp.size == HitObject.POINT.getSize()) { // 得点
							p.bgm[10].play();
							tmp.erase();
							score += 100 + ( this.py > 300 ? 1000 - this.py : 700 );
							break;
						} else { // 消弹
							p.bgm[10].play();
							tmp.erase();
							score += (100 + ( this.py > 300 ? 1000 - this.py : 700 )) / 10;
							break;
						}
					}
				} else if (tmp.size == HitObject.BOMB.getSize()) { // 投弹
					if (25 > Math.hypot((px + 5) - (tmp.getPx() + tmp.getSize() / 2),
							(py + 15) - (tmp.getPy() + tmp.getSize() / 2))) {
						tmp.erase();
						p.bgm[10].play();
						if (boom < 10) {
							boom++;
						}
						break;
					}
				} else if (tmp.size == HitObject.LIFE.getSize()) { // 残机奖励
					if (25 > Math.hypot((px + 5) - (tmp.getPx() + tmp.getSize() / 2),
							(py + 15) - (tmp.getPy() + tmp.getSize() / 2))) {
						tmp.erase();
						p.bgm[10].play();
						if (life < 10) {
							life++;
						}
						break;
					}
				// 碰子弹
				} else if (tmp.size == HitObject.BOSS_SHOT_C.getSize()) { // 首领子弹C
					if (32 > Math.hypot((px + 5) - (tmp.getPx() + tmp.getSize() / 2),
							(py + 15) - (tmp.getPy() + tmp.getSize() / 2)) && p.isM == false) {
						tmp.erase();
						life--;
						p.bgm[3].play(); // 中弹音效
						break;
					}
				} else { // 敌机子弹B
					if (10 > Math.hypot((px + 5) - (tmp.getPx() + tmp.getSize() / 2),
							(py + 15) - (tmp.getPy() + tmp.getSize() / 2)) && p.isM == false) {
						tmp.erase();
						life--;
						p.bgm[3].play(); // 中弹音效
						break;
					}
				}

				if (life < 0) {
					p.setMenuMode(MenuMode.DEFEAT.getMode());
					px = 312;
					py = 539;
					p.shoots.allErase();
				}
			}
			i++;
		} while (true);

		Constants constants = new Constants();

		for (Map.Entry<Integer, Map<String, List<int[]>>> k : constants.enemies.entrySet()) {// 遍历关卡敌人
			switch (k.getKey()) {
				case 1: // 第一关的敌人
					if (1 == k.getKey()) { // TODO: 前面改成当前关卡
						for (Map.Entry<String, List<int[]>> k1 : k.getValue().entrySet()) { // 遍历敌人的出现位置范围
							switch (k1.getKey()) { // 下标0 - 开始位置 下表1 - 结束位置
								case "stg1AL":
									for (int[] e : k1.getValue()) if (frame >= e[0] && frame < e[1]) stg1A_l();
									break;
								case "stg1AR":
									for (int[] e : k1.getValue()) if (frame >= e[0] && frame < e[1]) stg1A_r();
									break;
								case "stg1ALX":
									for (int[] e : k1.getValue()) if (frame >= e[0] && frame < e[1]) stg1A_lx();
									break;
								case "stg1ARX":
									for (int[] e : k1.getValue()) if (frame >= e[0] && frame < e[1]) stg1A_rx();
									break;
								case "stg1B":
									for (int[] e : k1.getValue()) if (frame >= e[0] && frame < e[1]) stg1B();
									break;
								case "stg1Boss":
									for (int[] e : k1.getValue())
										if (frame >= e[0] && frame < e[1]) {
											p.setTime2(0);
											p.bGM(2, 1);
											System.out.println("boss");
											p.enemys.allErase();
											tmp = p.enemys.getObject(0);
											tmp.setData(280, 200, 0, 0, 4, 0, 100, 1500, 'n');
										}
									break;
								default:
									break;
							}
						}
					}
					break;
				default:
					break;
			}
		}

		/*
		// 左边
		if (frame < 360) {
			stg1A_l();
			// 右边
		} else if (frame < 660) {
			stg1A_r();
		} else if (frame >= 700) {
			if (frame < 1000) {
				stg1A_lx();
				stg1A_rx();
			} else if (frame < 1300) {
				stg1B();
			} else if (frame < 1500) {
				stg1A_l();
			} else if (frame < 1800) {
				stg1A_r();
			} else if (frame < 2100) {
				stg1A_lx();
				stg1A_rx();
			} else if (frame < 2500) {
				stg1A_lx();
				stg1A_rx();
				stg1B();
			} else if (frame == 3300) {
				p.setTime2(0);
				p.bGM(2, 1);
				System.out.println("boss");
				p.enemys.allErase();
				tmp = p.enemys.getObject(0);
				tmp.setData(280, 200, 0, 0, 4, 0, 100, 1500, 'n');
			}
		}

		 */
		if (p.enemys.getObject(0).count == 5) {
			System.out.println("stage1");
			p.bullets.convertToPowerups();
			p.bgm[4].play();
			if ((tmp = p.boss.getEmpty()) != null) {
				tmp.setData(p.enemys.getObject(0).px - 80, p.enemys.getObject(0).py + 10,
						0, 0, 0, 0, 2, 1000, 'n');
			}
		}
		if (p.enemys.getObject(0).count == 50) {
			System.out.println("stage2");
			p.bullets.convertToPowerups();
			p.bgm[4].play();
			p.boss.allErase();
			p.enemys.getObject(0).vx = 1;
			p.enemys.getObject(0).vy = 1;
		}
		if (p.enemys.getObject(0).count == 105) {
			System.out.println("stage3");
			p.bullets.convertToPowerups();
			p.bgm[4].play();
			p.boss.allErase();
			p.enemys.getObject(0).px = 280;
			p.enemys.getObject(0).py = 200;
		}

//		System.out.println(frame);
//        System.out.println(p.enemys.getObject(0).count);
//		System.out.println(p.enemys.getObject(0).life);

	}

	/**
	 * 小妖精-左
	 */
	private void stg1A_l() {
		if (frame % 40 == 0 && (tmp = p.enemys.getEmpty()) != null) {
			tmp.setData(0, Math.random() * 96 + 48, 2, 0, 2, 0, 2, 25, 'n');
		}
	}

	/**
	 * 小妖精-右
	 */
	private void stg1A_r() {
		if (frame % 40 == 15 && (tmp = p.enemys.getEmpty()) != null) {
			tmp.setData(560, Math.random() * 96 + 48, -2, 0, 2, 0, 2, 25, 'n');
		}
	}
	/**
	 * 小妖精-左斜
	 */
	private void stg1A_lx() {
		if (frame % 40 == 0 && (tmp = p.enemys.getEmpty()) != null) {
			tmp.setData(0, Math.random() * 96 + 48, 2, 1, 2, 0, 2, 30, 'n');
		}
	}

	/**
	 * 小妖精-右斜
	 */
	private void stg1A_rx() {
		if (frame % 40 == 15 && (tmp = p.enemys.getEmpty()) != null) {
			tmp.setData(560, Math.random() * 96 + 48, -2, 1, 2, 0, 2, 30, 'n');
		}
	}

	/**
	 * 大妖精
	 */
	private void stg1B() {
		if (frame % 30 == 7 && (tmp = p.enemys.getEmpty()) != null) {
			tmp.setData(Math.random() * 540, 0, 0, 1, 3, 0, 3, 60, 'n');
		}
	}

	public void setBoom(int boom) {
		this.boom += boom;
	}

	public int getBoom() {
		return boom;
	}

	public int getPower() {
		return power;
	}

	public int getScore() {
		return score;
	}

	public void setPower(int power) {
		this.power += power;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public void setScore(int score) {
		this.score += score;
	}

}
