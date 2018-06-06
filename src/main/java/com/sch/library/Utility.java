package com.sch.library;

/**
 * Created by macbook on 2018/5/9.
 */
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import com.sch.library.DTO.UserDTO;

public class Utility {
    static Logger logger = Logger.getRootLogger();

    /**
     * 生成从[from, to] 变量之间的随机整数
     *
     * @param from
     *            Integer
     * @param to
     *            Integer
     * @return Integer
     */
    public static int randomIntNumber1(int from, int to) {
        float a = from + (to - from) * (new Random().nextFloat());
        int b = (int) a;
        return ((a - b) > 0.5 ? 1 : 0) + b;
    }

    /**
     * 生成从[0,end)的随机整数
     *
     * @param end
     *            Integer
     * @return Integer
     */
    public static int randomIntNumber1(int end) {
        return new Random().nextInt(end);
    }

    /**
     * 生成0-1的随机数。
     *
     * @return 随机数
     */
    public static float randomFloatNumber() {
        return new Random().nextFloat();
    }

    /**
     * 求一个Integer类型 的长度
     *
     * @param num
     *            Integer
     * @return Integer
     */
    public static int length(int num) {
        return Integer.toString(num).length();
    }

    public static boolean findKey(List<Integer> list, int randomNumber) {
        int length = list.size();
        for (int i = 0; i < length; i++) {
            if (list.get(i) == randomNumber) {
                return true;
            }
        }

        return false;
    }

    /**
     * 得到一个[from, to]区间并且与list中不重复的Integer数
     *
     * @param list
     *            List<Integer>
     * @param from
     *            Integer
     * @param to
     *            Integer
     * @return Integer
     */
    public static int getDifferentNumber(List<Integer> list, int from, int to) {
        int number = randomIntNumber1(from, to);
        while (true) {
            if (!findKey(list, number)) {
                return number;
            } else {
                number = randomIntNumber1(from, to);
            }
        }
    }

    /**
     * 选择算子，使用轮盘赌算法选择出用来交叉的染色体
     *
     * @param population
     *            存放染色体的数组
     * @return 被选中的染色体
     */
    public static Individual selection(Individual[] population) {
        Float r = new Random().nextFloat();
        Individual ind;
        double sum = 0;
        int tmp = -1;
        for (int i = 0; i < Data.POP_NUM; i++) {
            sum = Arith.add(sum, population[i].getPi());
            if (new BigDecimal(Double.toString(sum)).compareTo(new BigDecimal(
                    Float.toString(r))) == 1) {//sum > r;轮盘赌的PI总合大于随机数，选择第I个数
                tmp = i;
                break;
            }
        }
        if (tmp == -1) {
            ind = population[Data.POP_NUM];
        } else {
            ind = population[tmp];
        }
        return ind;
    }

    /**
     * 计算每个染色体的适应值（Pi)
     *
     * @param popNum
     *            种群数
     * @param population
     *            存放染色体数组
     * @param maxFitness
     *            最大适应值
     */
    public static void calcPi(int popNum, Individual[] population,
                              double maxFitness) {
        double denominator = 0.0;
        for (int i = 0; i < popNum; i++) {
            denominator = Arith.add(denominator, Arith.div(maxFitness,
                    population[i].getFitness(), 4));
        }
        for (int i = 0; i < popNum; i++) {
            double numerator = Arith.div(maxFitness,
                    population[i].getFitness(), 4); // 分子
            double result = Arith.div(numerator, denominator, 4);
            population[i].setPi(result);
        }

    }

    public static double calcMaxFitness(int popNum) {

        return 0;
    }

    /**
     * 交叉操作，对每一条染色体生成一个随机数，如果该随机数小于交叉概率PC就进行交叉操作否则不操作，与之匹配的染色体也是随机选择的，交叉的位置也是随机的
     *
     * @param finalPopulation
     */
    public static void crossover(Individual[] finalPopulation) {
        if (finalPopulation != null) {
            int length = finalPopulation.length;
            int size = finalPopulation[0].getChromosome().size();
            for (int i = 0; i < length; i++) {

                float f = randomFloatNumber();
                if (f < Data.PC) {
                    Individual ind = finalPopulation[i];
                    int random = randomIntNumber1(length);
                    Individual ind2 = finalPopulation[random];
                    int crossoverLocation = randomIntNumber1(1, size - 1);// 交叉的位置
                    List<Integer> c1 = ind.getChromosome();
                    List<Integer> c2 = ind2.getChromosome();
                    for (int j = crossoverLocation; j < size; j++) {
                        Integer g1 = c1.get(j);
                        Integer g2 = c2.get(j);

                        c1.set(j, g2);
                        c2.set(j, g1);

                    }
                    //把重新得到的染色体保存回去
                    ind.setChromosome(c1);
                    ind.setChromosome(c2);
                }
            }
        }

    }

    /**
     * 变异操作，对每一条染色体的基因生成【0，1】的随机数，如果小于变异概率PM就有变异发生，
     * @param questionCount 计算被选中的每种题型有多少道题(数据库中的,被优化过的)
     * @param userType 用户选择的题型和相应题型的数量
     * @param finalPopulation 染色体群
     */
    public static void mutation(Individual[] finalPopulation,
                                int questionCount, int userType) {
        int typeLength = 1;//题型数量
        int to = 0;
        if (finalPopulation != null) {
            int length = finalPopulation.length;
            for (int i = 0; i < length; i++) {
                Individual ind = finalPopulation[i];
                List<Integer> chromosome = ind.getChromosome();
                int size = chromosome.size();
                for (int j = 0; j < size; j++) {//染色体长度
                    float f = randomFloatNumber();
                    if (f < Data.PM) {
                        int sum = 0 ;
                        int k = 0 ;//变异的是什么类型的试题
                        int typeCount = 0;
                        do {
                            typeCount = userType;
                            k++;
                            sum += typeCount;
                            if (j <= sum) {
                                break;
                            }
                        } while ((k-1) < typeLength );
                        logger.info((k-1)+"@@@@@@@@@@@kkkkkkkkkkkkkkkkkkkkkk@@@@@@@@@@@@@@@@@@"+"k++" +k);
                        to = questionCount -1;//这种类型的试题在数据库中共有多少道
                        int randomQuestion = randomIntNumber1(1,to);
                        logger.info("没有变异的染色体基因："+chromosome.get(j));
                        chromosome.set(j,randomQuestion );
                        logger.info("变异之后的染色体基因："+chromosome.get(j));

                    }
                }
                ind.setChromosome(chromosome);
            }
        }
    }

//	public static void main(String[] args) {
//		int[] t  = new int[10];
//		for (int i = 0; i < 10; i++) {
//			t[i] = i+4;
//			logger.info(t[i]);
//		}
//		int randomNum = 18;
//		int sum = 0 ;
//		int typeCount =  0  ;
//		int i = 0 ;
//		do {
//			typeCount = t[i++];
//			sum += typeCount;
//			if (randomNum <= sum) {
//				break;
//			}
//		} while (i < t.length);
//		logger.info(i);
//		logger.info(typeCount);
//	}

}

