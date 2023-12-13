package edu.whu.demo.aspect;

//import org.aspectj.lang.JoinPoint;
import edu.whu.demo.exception.ProductAdminException;
import lombok.Data;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@Aspect
@Component
@Data
public class ApiAspect {

    //统计各API调用次数
    //key=函数名 val=调用次数
    Map<String,Long> callNum= Collections.synchronizedMap(new HashMap<>());

    //各API最长、最短、平均响应时间
    //key=函数名 val=各个API的不同响应时间
    Map<String, List<Integer>>time=Collections.synchronizedMap(new HashMap<>());

    //各API发生异常次数
    //key=函数名 val=异常次数
    Map<String,Long>exNum=Collections.synchronizedMap(new HashMap<>());

    Logger logger= LoggerFactory.getLogger(getClass());

    @Pointcut("execution(* edu.whu.demo.controller.*.*(..))")
    public void controllerPointCut(){}

    @AfterThrowing(pointcut="controllerPointCut()",throwing="ex")
    public void CalculateExNum(JoinPoint jp, Throwable ex)
    {
        String name=String.valueOf(jp.getSignature());
        long base =exNum.containsKey(name)?exNum.get(name):0;
        exNum.put(name,++base);
    }

    //统计调用次数
    @Before("controllerPointCut()")
    public void getNum(JoinPoint jp){
        String name= String.valueOf(jp.getSignature());//函数名称
        long base=callNum.containsKey(name)?callNum.get(name):0;
        callNum.put(name,++base);
    }

    //各API最长、最短、平均响应时间

    //获得各API所有调用时间
    @Around("controllerPointCut()")
    public Object getTime(ProceedingJoinPoint jp) throws Throwable {
        String name=String.valueOf(jp.getSignature());
        Long t1= Calendar.getInstance().getTimeInMillis();
        Object retValue=jp.proceed();
        Long t2=Calendar.getInstance().getTimeInMillis();
        List<Integer> base=time.containsKey(name)?time.get(name):new ArrayList<Integer>();
        base.add((int) (t2-t1));
        time.put(name,base);
        return retValue;
    }


    //获取各API最长、最短、平均响应时间
    public Map<String,List<Integer>> getTime()
    {
        Map<String,List<Integer>> result=Collections.synchronizedMap(new HashMap<>());

        for(String name:time.keySet())
        {
            List<Integer> reTime=new ArrayList<>();
            List<Integer> time1=time.get(name);//API每次调用的时间
            if(time1.size()!=0)
            {
                Integer max=time1.get(0);
                Integer min=time1.get(0);
                Integer sum=time1.get(0);
                for(int i=1;i<time1.size();i++)
                {
                    sum+=time1.get(i);
                    if(time1.get(i)>max)
                    {
                        max=time1.get(i);
                    }
                    if(time1.get(i)<min)
                    {
                        min=time1.get(i);
                    }
                }
                reTime.add(max);
                reTime.add(min);
                reTime.add(sum/time1.size());
            }
            //没有调用
            else {
                reTime.add(0);
                reTime.add(0);
                reTime.add(0);
            }
            result.put(name,reTime);


        }
        return result;

    }

    //各API发生异常次数




}
