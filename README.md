# th06_2.0
东方project自制小demo，殴打蕾咪

## 基本操作
*  方向键移动
*  shift减速聚焦火力
*  Z攻击
*  X大招（二妹）

## 内容
*  有完整的游戏流程
*  敌人会掉落各种奖励，拾取可以获得对应的加成
*  有BGM，可以流程的切换
*  有终符，坚持20秒
---

* 2021-01-09
  - 击败BOSS符卡时所有BOSS弹幕改为变成清弹道具，拾取后获得相当于得点道具分数的1/10。
  - 添加了回收线的设定，自机位置高于回收线时，屏幕内的所有道具将会自动飞到自机所在位置并回收。目前暂定回收线高度距离顶端为3/10。
  - 小怪被毁的音效改成风神录的
  - HUD面板的数据名称改为东方原作贴图，数字字体改为Sylfaen
  - HUD布局微调
  - 火力达到满级时改为显示MAX
  - 修复了通关之后重新开始时保险数没有重置的BUG
* 2021-01-07
  - 得点道具计分方式变更，捡到得点道具时自机位置越高得分越高，最低为100分，位置在距离屏幕顶部3/10以上时得点最高，为800分。
  - 每颗子弹打中敌人改为给11分
  - 所有掉落道具移动方式改为上抛然后逐渐加速下降（BOSS符卡击败时直接加速下降）
  - 击败BOSS符卡时所有BOSS弹幕变成得点道具
  - 添加了文字抗锯齿
  - 添加了FPS显示
  - 调整了通关画面显示文字的大小
  - 代码小重构，小怪出现区间和BOSS出现位置改为读专门的地图常量类
  - 尚未解决的BUG：叠在一起的道具可能只能捡到一个
* 2020-12-28
  - 添加了打单个EXE执行文件的配置
* 2020-12-24
  - 重制了得点方式，打中就有分，得点道具改为100分
  - 通关界面顶部添加了最终得点的显示
  - 添加了更多音效，拾取道具，玩家攻击，小怪击爆
  - 修复了放保险会将火力、得点等道具也会清除的BUG
* 2020-5-21
  -  增加人物动画效果，包括移动动画。
  -  增加全键盘操作，现在可以不需要鼠标了！
* 2020-5-18
  -  添加了手动无敌功能，space，可以方便观赏弹幕
* 2020-5-13
  -  添加了暂停功能 ESC，可以方便截图弹幕



![image](https://github.com/No5972/th06_2.0/blob/master/pictures/1.png)
![image](https://github.com/No5972/th06_2.0/blob/master/pictures/2.png)
![image](https://github.com/No5972/th06_2.0/blob/master/pictures/3.png)
![image](https://github.com/No5972/th06_2.0/blob/master/pictures/4.png)
![image](https://github.com/No5972/th06_2.0/blob/master/pictures/5.png)
![image](https://github.com/No5972/th06_2.0/blob/master/pictures/6.png)
![image](https://github.com/No5972/th06_2.0/blob/master/pictures/7.png)
![image](https://github.com/No5972/th06_2.0/blob/master/pictures/7a.png)
![image](https://github.com/No5972/th06_2.0/blob/master/pictures/8.png)
![image](https://github.com/No5972/th06_2.0/blob/master/pictures/9.png)
