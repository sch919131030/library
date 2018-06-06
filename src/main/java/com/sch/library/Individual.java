package com.sch.library;

/**
 * Created by macbook on 2018/5/9.
 */
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sch.library.DTO.QuestionDTO;
import com.sch.library.DTO.UserDTO;

public class Individual implements Serializable {
    private static final long serialVersionUID = 4162529888034588162L;
    public static Logger logger = Logger.getRootLogger();
    private static int geneCount; // 基因数量--试题总量
    private double bestrow_error; // 覆盖度误差
    private double exposal_error; // 曝光度误差
    private double diff_distribute_error; // 难度分布误差
    private double fitness; // 适应度
    private double pi; // 相对适应度，轮盘赌算法用

    private int length;
    private List<Integer> chromosome;// 染色体
    private List<QuestionDTO> list;// 存入被选中的试题，与染色体对应

    /**
     * 默认构造器
     */
    public Individual() {

    }

    /**
     * 带参构造器
     *
     * @param typeName
     *            存从数据库中取出的category的所有试题
     * @param questionCount
     *            数据库category对应的试题总数
     * @param userType
     *            10  要求category 每次取10道 (按实际情况再改)
     * @param level
     *            难度级别
     */
    @SuppressWarnings("unchecked")
    public Individual(List typeName, int questionCount, int userType, int level) {
        length = 1;
//		int chromosomeLength = 0; // 存放试卷共有多少道题
//		for (int i = 0; i < length; i++) {
//			chromosomeLength += userType.get(i).getNumber();
//		}
//		logger.info("chromosomeLength:chromosomeLength:chromosomeLength:"+chromosomeLength);
        chromosome = new ArrayList<Integer>();
        // 给染色体（chromosome）初始化
        for (int i = 0; i < length; i++) {
            initChromosome(chromosome, questionCount, userType);
        }
        geneCount = chromosome.size();

//logger.info("chromosomeLength:"+chromosomeLength+"%%%%%%%%%%%%%%%%%%%%%%%%%%%geneCount:"+geneCount);
        // 调整被初始化的染色体，并计算Fitness
        resetIndiWithChro(typeName, questionCount, userType, level);
    }

    /**
     * 调整被初始化的染色体，并计算Fitness
     *
     * @param typeName
     *            存从数据库中取出的category的所有试题
     * @param questionCount
     *            数据库category对应的试题总数
     * @param userType
     *            10  要求category 每次取10道 (按实际情况再改)
     * @param level
     */
    public void resetIndiWithChro(List typeName, int questionCount, int userType, int level) {
        // 去除染色体中的重复基因
        adjustChromosome(chromosome, questionCount, userType);
        list = this.chromosomeDecode(chromosome, typeName, userType);
        fitness = this.calcualateFitness(level);
    }

    /**
     * 使用简单的随机方法初始化染色体，染色体中有重复的基因
     *
     * @param chromosome
     *            存放代表试题的编号——基因
     * @param qNumber
     *            某种试题有多少道题
     * @param selectedNum
     *            用户要求选中的试题数量
     */
    @SuppressWarnings("unchecked")
    private void initChromosome(List chromosome, int qNumber, int selectedNum) {
        int tmp = selectedNum;
        logger.info(qNumber+"######################3"+selectedNum);
        while (tmp-- != 0) {
            logger.info(tmp+"!!!!!!!!!!!!!!!!!!!!!!!!");
            int select = Utility.randomIntNumber1(0, qNumber - 1);
            logger.info(select+"sssssssssssssssssssssssssssssssss");
            chromosome.add(select);
        }
    }

    /**
     * 去除染色体中的重复基因
     *
     * @param chromosome
     *            染色体
     * @param questionCount
     *            数据库category对应的试题总数
     * @param userType
     *            10  要求category 每次取10道 (按实际情况再改)
     */
    public void adjustChromosome(List<Integer> chromosome, int questionCount, int userType) {
        int start = 0;
        int end = 0;
        int qNumber = 0;// 题库中有多少道试题


        end = userType+ start;
        qNumber = questionCount;
//logger.info(start+"----------------- " +end);
        for (int j = start; j < end; j++) {
//logger.info(j+"$$$$$$$$$$$");
            int tmp = chromosome.get(j);
            for (int k = j + 1; k < end; k++) {
                if (tmp == chromosome.get(k)) {
                    tmp = Utility.getDifferentNumber(chromosome, 0,
                            qNumber - 1);
                    chromosome.set(j, tmp);
                }
            }
        }
    }

    /**
     * 把染色体解码成对应的试题，以便进行计算
     *
     * @param chromosome
     *            LIst<Integer> 染色体
     * @param typeName
     *            存从数据库中取出的category的所有试题
     * @param userType
     *            10  要求category 每次取10道 (按实际情况再改)
     * @return List<QuestionDTO>
     */
    @SuppressWarnings("unchecked")
    public List<QuestionDTO> chromosomeDecode(List<Integer> chromosome, List typeName, int userType) {
        List<QuestionDTO> list = new ArrayList<QuestionDTO>();
        List<QuestionDTO> questions;
        int start = 0;
        int end = 0;

        questions = typeName;

        end = userType+start ;
//	logger.info("start:"+ start+"--------------------end:"+end  +"染色体长度："+chromosome.size());
        for (int j = start; j < end; j++) {
//				logger.info(j+"@@@@@@@@@@@@@@");
            int tmp = chromosome.get(j);//存放试题的位置
            logger.info("questions.size:"+questions.size()+"存放试题的位置："+tmp);
            list.add(questions.get(tmp));
        }


        for (int i = 0; i < list.size(); i++) {
            QuestionDTO vo = list.get(i);

        }
        return list;
    }

    /**
     * 计算覆盖度误差
     *
     * @param list
     *            存放被选中的试题
     * @return 覆盖度误差
     */
    public double calculateBestrow(List<QuestionDTO> list) {
        int size = list.size();
        int charpterId;
        int count = 0;
        double result = 0;

        for (int j = 0; j < size; j++) {
            count++;
        }

        double d1 = Arith.div(count, geneCount);
        d1 = Math.abs(d1);

        result = Arith.add(result, d1);

        return result;
    }

    /**
     * 计算爆光度
     *
     * @param list
     *            存放被选中的试题
     * @return 所有题烛光度之和
     */
    public double calculateExposal(List<QuestionDTO> list) {
        int size = list.size();
        double result = 0;
        for (int i = 0; i < size; i++) {
            QuestionDTO vo = list.get(i);
            result += ((double) vo.getAnswer_number()) / 1000; //除以最大曝光度1000
        }

        return result;
    }

    /**
     * 计算难度分布误差
     *
     * @param list
     *            存放被选中试题
     * @param level
     *            试卷难度等级
     * @return 难度分布误差
     */
    public double calculateDifficult(List<QuestionDTO> list, int level) {
        int length = list.size();
        int size = list.size();
        // 试卷中某一难度试题量
        int diff1 = 0;
        int diff2 = 0;
        int diff3 = 0;
        int diff4 = 0;
        int diff5 = 0;
        for (int i = 0; i < size; i++) {
            if (Data.LEVEL1.equals(list.get(i).getDifficulty())) {
                diff1++;
            } else if (Data.LEVEL2.equals(list.get(i).getDifficulty())) {
                diff2++;
            } else if (Data.LEVEL3.equals(list.get(i).getDifficulty())) {
                diff3++;
            }else if (Data.LEVEL4.equals(list.get(i).getDifficulty())) {
                diff4++;
            }else if (Data.LEVEL5.equals(list.get(i).getDifficulty())) {
                diff5++;
            }
        }
        // 要求某一难度试题量
        int t1 = Data.NA;
        int t2 = Data.NB;
        int t3 = Data.NC;
        int t4 = Data.ND;
        int t5 = Data.NE;


        double result = Arith.add(Arith.add(Arith.add(Arith.add(Math.abs(Arith.div(Arith.sub(diff1, t1), length, 2)), Math.abs(Arith.div(Arith.sub(diff2, t2), length, 2))),
                Math.abs(Arith.div(Arith.sub(diff3, t3), length, 2))),Math.abs(Arith.div(Arith.sub(diff4, t4), length, 2))),Math.abs(Arith.div(Arith.sub(diff5, t5), length, 2)));


        return result;
    }

    /**
     * 计算适应度
     *
     * @param bestrow_error
     *            覆盖度误差
     * @param diff_distribute_error
     *            难度分布误差
     * @param exposal_error
     *            曝光度误差
     * @return 适应度
     */
    public double calcualateFitness(double bestrow_error, double diff_distribute_error, double exposal_error) {

        return Arith.add(Arith.add(bestrow_error * Data.W_BESTROW,
                diff_distribute_error * Data.W_DIFFERENT), exposal_error
                * Data.W_EXPOSAL);
    }

    /**
     * 计算适应度（这个比较好）
     *
     * @param level
     *            int
     * @return double
     */
    public double calcualateFitness( int level) {
        bestrow_error = this.calculateBestrow(list);
        diff_distribute_error = this.calculateDifficult(list, level);
        exposal_error = this.calculateExposal(list);
        double fit = Arith.add(Arith.add(Arith.mul(bestrow_error,
                Data.W_BESTROW), Arith.mul(diff_distribute_error,
                Data.W_DIFFERENT)), Arith.mul(exposal_error, Data.W_EXPOSAL));

//        logger.info("覆盖度：" + bestrow_error + "\t难度：" + diff_distribute_error
//                + "\t曝光度：" + exposal_error + "\t适应度" + fit);
        return fit;
    }

    public double getBestrow_error() {
        return bestrow_error;
    }

    public void setBestrow_error(double bestrowError) {
        bestrow_error = bestrowError;
    }

    public double getExposal_error() {
        return exposal_error;
    }

    public void setExposal_error(double exposalError) {
        exposal_error = exposalError;
    }

    public double getDiff_distribute_error() {
        return diff_distribute_error;
    }

    public void setDiff_distribute_error(double diffDistributeError) {
        diff_distribute_error = diffDistributeError;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public double getPi() {
        return pi;
    }

    public void setPi(double pi) {
        this.pi = pi;
    }

    public List<QuestionDTO> getList() {
        return list;
    }

    public void setList(List<QuestionDTO> list) {
        this.list = list;
    }

    public List<Integer> getChromosome() {
        return chromosome;
    }

    public void setChromosome(List<Integer> chromosome) {
        this.chromosome = chromosome;
    }

//    public static void main(String[] args) {
//        logger.info(Double.parseDouble("10"));
//        logger.info(Arith.div(10.0, 3.0, 7));
//    }
    /**
     * 利用Serializable实现深度克隆
     * @return 克隆出来的对象
     */
    public Object deepClone() {
        ObjectInputStream oi = null;
        Object obj = null;
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(this);
            // 从流里读出来
            ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
            oi = new ObjectInputStream(bi);
            obj = oi.readObject();
        } catch (Exception e) {
            logger.error("克隆出错：", e);
        }

        return (obj);
    }
}

