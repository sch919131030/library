package com.sch.library.Service;

import com.sch.library.DTO.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sch.library.base.Result;
import com.sch.library.ResultCache;
import com.sch.library.Data;
import com.sch.library.Individual;
import com.sch.library.Utility;
import com.sch.library.DAO.QuestionDao;
import com.sch.library.DTO.QuestionDTO;
import com.sch.library.DAO.UserDao;
import com.sch.library.Service.QuestionService;

import javax.print.attribute.standard.MediaSize;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.List;


@Service
public class QuestionServiceImpl implements QuestionService{
    @Autowired
    QuestionDao questionDao;
    @Autowired
    UserDao userDao;

    @Override
    public Result setQuestionInfo(int category,int id,int isRight){
        if(category==1){
            if(questionDao.exists_life(id)==0) {
                return ResultCache.PERMISSION_DENIED;
            }
            QuestionDTO questionInfo = questionDao.selectQuestion_life_id(id);
//            questionInfo.setAnswer_number(questionInfo.getAnswer_number()+1);

            int rightNumber=questionInfo.getRight_number();
            if (isRight==1){
                rightNumber=rightNumber+1;
                questionInfo.setAnswer_number(questionInfo.getAnswer_number()+1);
            }
            double rightRate = ((double) rightNumber)/((double) questionInfo.getAnswer_number());

            int difficulty=1;
            if (rightRate>0&rightRate<=0.2)
                difficulty=1;
            else if (rightRate>0.2&rightRate<=0.4)
                difficulty=2;
            else if (rightRate>0.4&rightRate<=0.6)
                difficulty=3;
            else if (rightRate>0.6&rightRate<=0.8)
                difficulty=4;
            else if (rightRate>0.8&rightRate<=1)
                difficulty=5;

            int result = questionDao.setQuestionInfo_life(id,difficulty,rightNumber);

            return ResultCache.getCache(result);
        }
        else if(category==2){
            if(questionDao.exists_literature(id)==0) {
                return ResultCache.PERMISSION_DENIED;
            }
            QuestionDTO questionInfo = questionDao.selectQuestion_literature_id(id);

            int rightNumber=questionInfo.getRight_number();
            if (isRight==1){
                rightNumber=rightNumber+1;
                questionInfo.setAnswer_number(questionInfo.getAnswer_number()+1);
            }
            double rightRate = ((double) rightNumber)/((double) questionInfo.getAnswer_number());

            int difficulty=1;
            if (rightRate>0&rightRate<=0.2)
                difficulty=1;
            else if (rightRate>0.2&rightRate<=0.4)
                difficulty=2;
            else if (rightRate>0.4&rightRate<=0.6)
                difficulty=3;
            else if (rightRate>0.6&rightRate<=0.8)
                difficulty=4;
            else if (rightRate>0.8&rightRate<=1)
                difficulty=5;

            int result = questionDao.setQuestionInfo_literature(id,difficulty,rightNumber);

            return ResultCache.getCache(result);
        }
        else {
            if(questionDao.exists_video(id)==0) {
                return ResultCache.PERMISSION_DENIED;
            }
            QuestionDTO questionInfo = questionDao.selectQuestion_video_id(id);

            int rightNumber=questionInfo.getRight_number();
            if (isRight==1){
                rightNumber=rightNumber+1;
                questionInfo.setAnswer_number(questionInfo.getAnswer_number()+1);
            }
            double rightRate = ((double) rightNumber)/((double) questionInfo.getAnswer_number());

            int difficulty=1;
            if (rightRate>0&rightRate<=0.2)
                difficulty=1;
            else if (rightRate>0.2&rightRate<=0.4)
                difficulty=2;
            else if (rightRate>0.4&rightRate<=0.6)
                difficulty=3;
            else if (rightRate>0.6&rightRate<=0.8)
                difficulty=4;
            else if (rightRate>0.8&rightRate<=1)
                difficulty=5;

            int result = questionDao.setQuestionInfo_video(id,difficulty,rightNumber);

            return ResultCache.getCache(result);
        }
    }
    @Override
    public Result selectQuestion_category(String user_id,int question_category) {
        if(userDao.exists(user_id)==0) {
            return ResultCache.PERMISSION_DENIED;
        }
        UserDTO userinfo = userDao.selectUserInfo(user_id);
        double rightRate;
        int level = 0;
        if (userinfo.getScore()==0){
            //如果之前没有玩过,则给一个基础期望
            rightRate = 0.5;
            level = 3;

        }else{
            //获取用户该题型的正确率
            //rightRate = userDao.rightRate(user_id,question_category);
            if(question_category==1){
                rightRate = ((double)userinfo.getLife_right_number())/((double)userinfo.getLife_answer_number());
            }
            else if (question_category==2){
                rightRate = ((double)userinfo.getLiterature_right_number())/((double)userinfo.getLiterature_answer_number());
            }
            else {
                rightRate = ((double)userinfo.getVideo_right_number())/((double)userinfo.getVideo_answer_number());
            }
            if (rightRate>0&rightRate<=0.2)
                level = 1;
            else if (rightRate>0.2&rightRate<=0.4)
                level = 2;
            else if (rightRate>0.4&rightRate<=0.6)
                level = 3;
            else if (rightRate>0.6&rightRate<=0.8)
                level = 4;
            else if (rightRate>0.8&rightRate<=1)
                level = 5;
            }

        int userType = 10;
        if (rightRate-0.5>=0){
            //用户能力强,提高期望
            double deltaX = 10*(rightRate-0.5);
            double P1 = simpsonRule(-3+deltaX,-5+deltaX);
            double P2 = simpsonRule(-1+deltaX,-3+deltaX);
            double P3 = simpsonRule(1+deltaX,-1+deltaX);
            double P4 = simpsonRule(3+deltaX,1+deltaX);
            double P5 = simpsonRule(5+deltaX,3+deltaX);
            double I = simpsonRule(-5+deltaX,-5);
            double P = simpsonRule(5+deltaX,-5+deltaX);

            double PA = P1+I*P1/P;
            double PB = P2+I*P1/P;
            double PC = P3+I*P1/P;
            double PD = P4+I*P1/P;
            double PE = P5+I*P1/P;

            int NA = (int) (userType*PA);
            int NB = (int) (userType*PB);
            int NC = (int) (userType*PC);
            int ND = (int) (userType*PD);
            int NE = (int) (userType*PE);
            if (NA+NB+NC+ND+NE<userType)
                NA = NA+(userType-NA-NB-NC-ND-NE);

            Data.NA = NA;
            Data.NB = NB;
            Data.NC = NC;
            Data.ND = ND;
            Data.NE = NE;
        }

        try {
            return ResultCache.getDataOk(autoPaper(question_category,level));
        }catch (Exception e){
            return ResultCache.FAILURE;
        }


    }

    // apply simpson rule to approximately compute the integration.辛普森算法
    public double simpsonRule(double upper, double lower) {
        double result = 0;

        // splited into 200 subintervals.
        int n = 200;
        double unit = (upper-lower)/n;
        double factor1 = unit / 3;
        double[] x = new double[n+1];

        for (int i = 0; i < x.length; i++) {
            x[i] = lower + unit*i;
        }
        for (int i = 0; i < x.length; i++) {
            if(i==0 || i==x.length-1) {
                result += fun(x[i]);
            }else if(i%2 == 0) { // if i is even num.
                result += 2*fun(x[i]);
            }else { // if i is odd num.
                result += 4*fun(x[i]);
            }
        }

        result *= factor1;
        return result;
    }

    public double fun(double x) {
        if(x==0) {
            return 0;
        }
        return Math.pow(Math.E, -0.5*x*x) / (Math.pow(2*Math.PI, 0.5));
    }

    /**
     * 遗传算法进行组卷
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<QuestionDTO> autoPaper(int question_category,int level) throws Exception {
//        HttpServletRequest request = ServletActionContext.getRequest();
        Logger logger = Logger.getRootLogger();

        long start = System.currentTimeMillis();

        int userType = 10;// 用户选择的category 每次出10道题

        // 生成存放相应题型的List，存放从数据库中选择的数据
        //List<QuestionDTO> typeName = questionDao.selectQuestion_category(question_category);// typeName 中存从数据库中取出的category的所有试题
        List<QuestionDTO> typeName;
        if (question_category==1)
            typeName = questionDao.selectQuestion_life();
        else if (question_category==2)
            typeName = questionDao.selectQuestion_literature();
        else
            typeName = questionDao.selectQuestion_video();
        // 计算被选中的category共有多少道题(数据库中的)
        int questionCount = typeName.size();//数据库category对应的试题总数。

        //int level = Integer.parseInt(difficulty);
        int popNum = Data.POP_NUM;// 初始种群数
        Individual[] population = new Individual[popNum]; // 存入染色体
        double maxFitness = 0;
        for (int i = 0; i < popNum; i++) {// 初始化染色体组
            // 生成一个染色体；
            Individual ind = new Individual(typeName, questionCount, userType, level);
            population[i] = ind;
            if (maxFitness < ind.getFitness()) {
                maxFitness = ind.getFitness();
            }
        }

        // 计算相对适应度Pi
        // logger.info("maxFitness" + maxFitness + "-----++++++++++++----");
        Utility.calcPi(popNum, population, maxFitness);

        // 通过选择算子，选择出两个染色体进行交叉
        int finalPopNum = Data.FINAL_POP_NUM;
        Individual[] finalPopulation = new Individual[finalPopNum]; // 存放被选择出的染色体
        for (int i = 0; i < finalPopNum; i++) {
            finalPopulation[i] = Utility.selection(population);// 可能会有一样的染色体
        }

        int tt = 0;// 控制循环总次数
        double minFit = 20.0;
        Individual bestInd = new Individual();
        do {
            // 交叉操作
            Utility.crossover(finalPopulation);
            // 变异操作
            //	Utility.mutation(finalPopulation, questionCount, userType);

            // 调整被初始化的染色体，并计算Fitness
            for (int i = 0; i < finalPopNum; i++) {
                Individual ind = finalPopulation[i];
                ind.resetIndiWithChro(typeName, questionCount, userType,level);
                double tmpFit = ind.getFitness();

                if (tmpFit < minFit) {
                    minFit = tmpFit;
                    bestInd = (Individual)finalPopulation[i].deepClone();
                    System.out.println("这个执行了。。。。。。。。。。。。。");
                    logger.info("最优解：-----------："
                            + bestInd.calcualateFitness(level));
                    logger.info("和最优解得一样------------："
                            + finalPopulation[i].calcualateFitness(level));
                }
            }
            logger.info("aaaaaaaaaaaaaaaaaaaaa\n"
                    + bestInd.calcualateFitness(level));
            logger.info(tt + "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            tt++;
        } while (tt != 200);
        logger.info("以下是最优解：");
        bestInd.resetIndiWithChro(typeName, questionCount, userType,level);
        double f = bestInd.calcualateFitness(level);
        logger.info("fintess:" + f);
        logger.info("minFit:" + minFit + "$$");

        //把最优解持久化
        List<QuestionDTO> qList = bestInd.getList();//最终出的题


        long end = System.currentTimeMillis();


        return qList;
    }

}
