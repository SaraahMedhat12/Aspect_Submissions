package com.example.demo.aspect;

import com.example.demo.annotation.RateGate;
import com.example.demo.exception.RateLimitBlockedException;
import com.example.demo.service.RedisUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * Aspect that enforces rate-limiting for endpoints using the @RateGate annotation.
 */
@Aspect
@Component
@Order(1) // Ensures this runs before locking, if both are present
public class RateGateAspect {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private HttpServletRequest request;

    @Around("@annotation(rateGate)")
    public Object enforceRateLimit(ProceedingJoinPoint joinPoint, RateGate rateGate) throws Throwable {
        String clientIp = request.getRemoteAddr();
        String redisKey = "rategate:" + rateGate.keyBase() + ":" + clientIp;
        long ttlSeconds = rateGate.unit().toSeconds(rateGate.period());

        Long currentCount = redisUtil.incrementWithExpiry(redisKey, Duration.ofSeconds(ttlSeconds));
        if (currentCount != null && currentCount > rateGate.maxRequests()) {
            throw new RateLimitBlockedException("You have exceeded the allowed request limit. Please try again shortly.");
        }

        return joinPoint.proceed();
    }
}