package com.jt.aop;

import com.jt.anno.CatchFind;
import com.jt.util.ObjectMapperUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import java.util.Arrays;

@Component //交给spring容器管理
@Aspect //生命切面
public class CatchAop {

    //注入jedis
//    @Autowired
//    ShardedJedis shardedJedis;
//    @Autowired
//    JedisSentinelPool jedisSentinelPool;
    @Autowired
    JedisCluster jedis;

    @Pointcut("@annotation(com.jt.anno.CatchFind)")
    public void Catch(){

    }

    @Around("@annotation(catchFind)")
    public Object JedisCatch(ProceedingJoinPoint joinPoint, CatchFind catchFind){
        //获取jedis
//        Jedis jedis = jedisSentinelPool.getResource();

        //获取目标对象    获取方法参数
        Object target = joinPoint.getTarget();
        Object[] args = joinPoint.getArgs();

        String key = catchFind.key()+Arrays.toString(args);
        //存储返回结果
        Object resule;
        if (jedis.exists(key)){
            String json = jedis.get(key);
            MethodSignature methodSignature =
                    (MethodSignature) joinPoint.getSignature();
            Class targetClass = methodSignature.getReturnType();
            resule = ObjectMapperUtil.toObject(json, targetClass);
        }else {
            try {
                resule = joinPoint.proceed();
                String json = ObjectMapperUtil.toJson(resule);
                if(catchFind.seconds()>0){       //判断是否需要超时时间
                    jedis.setex(key, catchFind.seconds(), json);
                }else{
                    jedis.set(key,json);
                }
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                throw new RuntimeException(throwable);
            }
        }
//        jedis.close();
        return resule;
    }

    /**
     * bean切点
     */
    //@Pointcut(value = "bean(itemCatServiceImpl)")
    //@Pointcut("within(com.jt.service..*)")
    //拦截com.jt.service下的所有类的所有方法的任意参数类型
    //@Pointcut("execution(int com.jt.service..*.add*(String))")
    //@Pointcut("execution(int com.jt.service..*.add*(String))")
    @Pointcut("bean(itemCatServiceImpl)")
    public void Pointcut(){
    }

//    @Before("Pointcut()")
//    public void before(JoinPoint joinPoint){
//        System.out.println("我是前置通知");
//        Object target = joinPoint.getTarget();
//        System.out.println("目标方法为:"+target);
//        //获取路径名
//        String passName = joinPoint.getSignature().getDeclaringTypeName();
//        //获取方法名
//        String method = joinPoint.getSignature().getName();
//        System.out.println("全路径名为:"+passName+passName);
//        //输出目标参数
//        System.out.println(Arrays.toString(joinPoint.getArgs()));
//    }
}
