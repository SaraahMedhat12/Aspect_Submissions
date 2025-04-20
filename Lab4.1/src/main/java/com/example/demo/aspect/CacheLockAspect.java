package com.example.demo.aspect;

import com.example.demo.annotation.CacheLock;
import com.example.demo.exception.ResourceLockedException;
import com.example.demo.service.RedisUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.*;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.UUID;

/**
 * Aspect to enforce distributed locking using Redis on methods annotated with @CacheLock.
 */
@Aspect
@Component
public class CacheLockAspect {

    @Autowired
    private RedisUtil redisUtil;

    private final ExpressionParser parser = new SpelExpressionParser();

    @Around("@annotation(cacheLock)")
    public Object lockMethod(ProceedingJoinPoint joinPoint, CacheLock cacheLock) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        EvaluationContext context = new StandardEvaluationContext();
        Object[] args = joinPoint.getArgs();
        String[] paramNames = signature.getParameterNames();
        for (int i = 0; i < args.length; i++) {
            context.setVariable(paramNames[i], args[i]);
        }

        String dynamicKey = parser.parseExpression(cacheLock.keyExpression()).getValue(context, String.class);
        String lockKey = "lock:" + cacheLock.prefix() + ":" + dynamicKey;
        String token = UUID.randomUUID().toString();
        Duration ttl = Duration.of(cacheLock.timeout(), cacheLock.timeUnit().toChronoUnit());

        boolean acquired = Boolean.TRUE.equals(redisUtil.tryLock(lockKey, token, ttl));

        if (!acquired) {
            throw new ResourceLockedException("This resource is currently locked. Please try again later.");
        }

        try {
            return joinPoint.proceed();
        } finally {
            redisUtil.delete(lockKey);
        }
    }
}