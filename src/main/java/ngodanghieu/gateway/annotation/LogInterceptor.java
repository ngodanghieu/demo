package ngodanghieu.gateway.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;


@Service
@Aspect
public class LogInterceptor {
    @Before(value = "ngodanghieu.gateway.annotation.LogManager.auditLog()"
            + "&& target(bean) "
            + "&& @annotation(ngodanghieu.gateway.annotation.LogsActivityAnnotation)"
            + "&& @annotation(logme)", argNames = "bean,logme")
    public void log(JoinPoint jp, Object bean, LogsActivityAnnotation logme) {

        System.out.println(String.format("Log Message: %s", logme.message()));
        System.out.println(String.format("Bean Called: %s", bean.getClass()
                .getName()));
        System.out.println(String.format("Method Called: %s", jp.getSignature()
                .getName()));

    }

    @Around(value = "ngodanghieu.gateway.annotation.LogsActivityAnnotation"
            + "&& target(bean) "
            + "&& @annotation(ngodanghieu.gateway.annotation.LogsActivityAnnotation)"
            + "&& @annotation(logme)", argNames = "bean,logme")
    public void performanceLog(ProceedingJoinPoint joinPoint, Object bean, LogsActivityAnnotation logme) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        joinPoint.proceed();


        stopWatch.stop();

        StringBuffer logMessage = new StringBuffer();
        logMessage.append(joinPoint.getTarget().getClass().getName());
        logMessage.append(".");
        logMessage.append(joinPoint.getSignature().getName());
        logMessage.append("(");
        // append args
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            logMessage.append(args[i]).append(",");
        }
        if (args.length > 0) {
            logMessage.deleteCharAt(logMessage.length() - 1);
        }

        logMessage.append(")");
        logMessage.append(" execution time: ");
        logMessage.append(stopWatch.getTotalTimeMillis());
        logMessage.append(" ms");
        System.out.println(logMessage.toString());


    }
}