com.java.example.demo.aop;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;


import java.util.Arrays;

@Configuration
@Aspect
public class AopConfig {

    @Around("@within(org.springframework.stereotype.Controller) ")
    public Object simpleAop(final ProceedingJoinPoint pjp) throws Throwable{
         try{
             Object[] args = pjp.getArgs();
             System.out.println("Wen ke Aop | args:" + Arrays.asList(args));
             //调用原有方法
             Object o = pjp.proceed();
             System.out.println("Wen ke Aop | Return :" + o);
             return o;
         }catch ( Throwable e) {
             throw e;
         }
    }
}
