package site.teamo.mall.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SlowMethodStatistics {

    private static final Logger LOGGER = LoggerFactory.getLogger(SlowMethodStatistics.class);

    @Around("execution(* site.teamo.mall.service.impl..*.*(..))")
    public Object slowMethodLog(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        // 执行目标 service
        Object result = joinPoint.proceed();
        // 记录结束时间
        long cost = System.currentTimeMillis() - start;
        if (cost > 100) {
            LOGGER.info("{}.{} 执行耗时:{}ms",
                    joinPoint.getTarget().getClass(),
                    joinPoint.getSignature().getName(),
                    cost);
        }
        return result;
    }
}
