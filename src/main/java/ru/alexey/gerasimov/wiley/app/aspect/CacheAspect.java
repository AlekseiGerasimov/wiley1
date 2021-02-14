package ru.alexey.gerasimov.wiley.app.aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import ru.alexey.gerasimov.wiley.lib.annotation.Cacheable;

@Aspect
@Component
@RequiredArgsConstructor
public class CacheAspect {

    private final CacheStore cacheStore;

    @Around("@annotation(ru.alexey.gerasimov.wiley.lib.annotation.Cacheable)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        var myAnnotation = getAnnotation(joinPoint);
        var cache = cacheStore.getCache(myAnnotation.name(), myAnnotation.strategy());

        var id = joinPoint.getArgs()[0];
        var object = cache.get(id);
        if (object == null) {
            var proceed = joinPoint.proceed();
            cache.add(id, proceed);
            return proceed;
        } else {
            return object;
        }
    }

    private Cacheable getAnnotation(ProceedingJoinPoint joinPoint) {
        var signature = (MethodSignature) joinPoint.getSignature();
        var method = signature.getMethod();
        return method.getAnnotation(Cacheable.class);
    }
}
