package com.han.game.main;

import java.awt.Graphics;

import com.han.game.enums.HitObject;
import com.han.game.model.Boss;
import com.han.game.model.Bullet;
import com.han.game.model.Enemy;
import com.han.game.model.GameObject;
import com.han.game.model.Shoot;

/**
 * ������
 * 
 * @author ʮ��
 *
 */
public class ObjectsArray {
	// �������
	private GameObject gameObject[];
	private int emptySearch;
	// ��������
	private int arrayMax;
	private int i;

	/**
	 * ���������Ŷ���
	 * 
	 * @param s
	 * @param j
	 */
	public ObjectsArray(String s, int j) {
		emptySearch = 0;
		arrayMax = j;
		gameObject = new GameObject[j];
		for (i = 0; i < j; i++) {
			if (s.equals("Bullet")) {
				gameObject[i] = new Bullet();
			}
			if (s.equals("Shoot")) {
				gameObject[i] = new Shoot();
			}
			if (s.equals("Enemy")) {
				gameObject[i] = new Enemy();
			}
			if (s.equals("Boss")) {
				gameObject[i] = new Boss();
			}
		}
	}

	public void allMove() {
		for (i = 0; i < arrayMax; i++) {
			if (gameObject[i].getExist()) {
				gameObject[i].move();
			}
		}
	}

	public void allDraw(Graphics g) {
		for (i = 0; i < arrayMax; i++) {
			if (gameObject[i].getExist()) {
				gameObject[i].draw(g);
			}
		}
	}

	/**
	 * �Ƴ�����
	 */
	public void allErase() {
		for (i = 0; i < arrayMax; i++)
			gameObject[i].erase();
	}
	
	/**
	 * ��ʱ - ֻ�Ƴ��ӵ�
	 */
	public void eraseBullet() {
		for (i = 0; i < arrayMax; i++)
			if (gameObject[i].getSize() != HitObject.LIFE.getSize() &&
					gameObject[i].getSize() != HitObject.BOMB.getSize() &&
					gameObject[i].getSize() != HitObject.POINT.getSize() &&
					gameObject[i].getSize() != HitObject.POWER.getSize())
				gameObject[i].erase();
	}
	
	// ���ؿ�������λ
	public GameObject getEmpty() {
		for (i = 0; i < arrayMax; i++) {
			// ���������λδ��ռ��
			if (!gameObject[emptySearch].getExist()) {
				// ���ظ�����
				return gameObject[emptySearch];
			}
			// �±�+1
			emptySearch++;
			// ����±곬���涨����
			if (emptySearch >= arrayMax) {
				// ���ص���λ����
				emptySearch = 0;
			}
		}
		return null;
	}

	public GameObject getObject(int j) {
		return gameObject[j];
	}

	public int getArrayMax() {
		return arrayMax;
	}
}
