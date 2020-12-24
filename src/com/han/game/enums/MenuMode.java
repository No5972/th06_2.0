package com.han.game.enums;

/**
 * 游戏界面
 */
public enum MenuMode {
    /**
     * 主菜单
     */
    MAIN_MENU(0),
    /**
     * 说明
     */
    HELP(1),
    /**
     * 退出
     */
    EXIT(2),
    /**
     * 加载游戏
     */
    LOADING(3),
    /**
     * 失败
     */
    DEFEAT(4),
    /**
     * 继续
     */
    CONTINUE(5),
    /**
     * 本作终
     */
    THE_END(6),
    /**
     * 暂停
     */
    PAUSE(7);


    private int mode;

    MenuMode(int mode) {
        this.mode = mode;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
}
