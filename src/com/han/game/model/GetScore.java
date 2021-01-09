package com.han.game.model;

import java.awt.Graphics;

public class GetScore extends GameObject {
	private int value;
	private double initY;
	private String stringVal;
	
	public GetScore() {
		this.setSize(-11);
	}
	
	public void move() {
        super.move();
    }

	@Override
    public void draw(Graphics g) {
		if (this.py < this.initY - 50) {
			this.erase();
		}
        super.draw(g);
    }
	
	@Override
	public void setData(double d, double d1, double d2, double d3,
			int i, int j, int k, int l, char m) {
		this.initY = d1;
		this.value = l;
		super.setData(d, d1, d2, d3, i, j, k, l, m);
	}
	
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public String getStringVal() {
		return stringVal;
	}
	public void setStringVal(String stringVal) {
		this.stringVal = stringVal;
	}
	@Override
	public String toString() {
		String number = Integer.toString(value);
		StringBuilder ret = new StringBuilder();
		for (byte x : number.getBytes()) {
			ret.append(x == '0' ? "○" : 
				x == '1' ? "一" :
				x == '2' ? "二" :
				x == '3' ? "三" :
				x == '4' ? "四" :
				x == '5' ? "五" :
				x == '6' ? "六" :
				x == '7' ? "七" :
				x == '8' ? "八" : 
				x == '9' ? "九" : "");
		}
		return ret.toString();
	}
    
     
}
