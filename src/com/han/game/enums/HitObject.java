package com.han.game.enums;

/**
 * 道具、自机子弹或敌人子弹
 */
public enum HitObject {
    /**
     * 灵力
     */
    POWER(5),
    /**
     * 得点
     */
    POINT(6),
    /**
     * 投弹
     */
    BOMB(8),
    /**
     * 残机奖励
     */
    LIFE(9),
    /**
     * 消弹道具
     */
    SMALL_POINT(10),
    /**
     * 敌机子弹A
     */
    EMENY_SHOT_A(15),
    /**
     * 敌机子弹B
     */
    EMENY_SHOT_B(23),
    /**
     * 首领子弹A
     */
    BOSS_SHOT_A(24),
    /**
     * 首领子弹B
     */
    BOSS_SHOT_B(25),
    /**
     * 首领子弹C
     */
    BOSS_SHOT_C(32),
    /**
     * 首领子弹D
     */
    BOSS_SHOT_D(17),
    /**
     * 首领子弹E
     */
    BOSS_SHOT_E(26),
    /**
     * 首领子弹F
     */
    BOSS_SHOT_F(18),
    /**
     * 自机子弹A
     */
    PLAYER_SHOT_A(16),
    /**
     * 自机子弹B
     */
    PLAYER_SHOT_B(20),
    /**
     * 自机投弹
     */
    PLAYER_BOMB(101)
    ;

    private int size;

    HitObject(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
