package com.sch.library;

/**
 * Created by macbook on 2018/5/9.
 */

//????????????????????????
public class Data {
    public static final int POP_NUM = 50; // 种群初始数量为50个
    public static final int FINAL_POP_NUM = 30;
    public static final float PC = 0.75f;
    public static final float PM =  0.1f;
    //定义难度等级，五个难度的试题所占试卷的比例(level2和level3的比例)
    public static final double[][] DIFFICULT_LEVEL = new double[][] {
            {0.2, 0.1 }, { 0.3, 0.2 }, {0.4, 0.3 } };
    //试卷难度5个等级
    public static final String LEVEL1 = "1";
    public static final String LEVEL2 = "2";
    public static final String LEVEL3 = "3";
    public static final String LEVEL4 = "4";
    public static final String LEVEL5 = "5";

    public static final int W_BESTROW = 10;	//难度覆盖度权重
    public static final int W_DIFFERENT = 6;//难度权重
    public static final int W_EXPOSAL = 4;	//曝光度权重

    //五种题目需要的个数
    public static int NA = 0;
    public static int NB = 0;
    public static int NC = 0;
    public static int ND = 0;
    public static int NE = 0;

}
