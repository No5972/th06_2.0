package com.han.game.main;

import java.awt.Toolkit;

import javax.swing.JFrame;

/**
 * 主框架
 * @author 十七
 *
 */
public class Welcome {

	static JFrame jf;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		jf=new JFrame();
		jf.setTitle("東方赤魔鄉 v2.1");
		jf.setDefaultCloseOperation(3);
		jf.setResizable(false);
		jf.setBounds((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()-640)/2,
				(int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()-640)/2,850,1000);
		GamePanel gamepanel = new GamePanel();
		jf.add(gamepanel);
		jf.setLocationRelativeTo(null);
		jf.setVisible(true);
		jf.setIconImage(gamepanel.iconImg);
	}

}
