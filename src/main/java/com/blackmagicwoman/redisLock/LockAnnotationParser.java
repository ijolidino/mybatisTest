package com.blackmagicwoman.redisLock;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class LockAnnotationParser {

    @Autowired
    private RedisLockUtils redisLockUtils;
    /**
     * 定义切点
     */
    @Pointcut(value = "@annotation(com.blackmagicwoman.redisLock.RedisLock)")
    private void cutMethod() {
    }
    /**
     * 切点逻辑具体实现
     */
    @Around(value = "cutMethod() && @annotation(redisLock)")
    public Object parser(ProceedingJoinPoint point, RedisLock redisLock) throws Throwable {
        String value = redisLock.value();
        if (this.isEl(value)) {
            value = this.getByEl(value, point);
        }
        String key =this.getRealLockKey(value);
        if(StringUtils.isNotEmpty(redisLock.prefix())){
            key = redisLock.prefix()+key;
        }
        redisLockUtils.tryLock(key,redisLock.loop(),redisLock.interval(),redisLock.expireTime());
        try {
            return point.proceed();
        } finally {
            redisLockUtils.unLock(value);
        }
    }
    /**
     * 解析 SpEL 表达式并返回其值
     */
    private String getByEl(String el, ProceedingJoinPoint point) {
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        String[] paramNames = getParameterNames(method);
        Object[] arguments = point.getArgs();
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(el);
        EvaluationContext context = new StandardEvaluationContext();
        for (int i = 0; i < arguments.length; i++) {
            context.setVariable(paramNames[i], arguments[i]);
        }
        return expression.getValue(context, String.class);
    }

    /**
     * 获取方法参数名列表
     */
    private String[] getParameterNames(Method method) {
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        return u.getParameterNames(method);
    }

    private boolean isEl(String str) {
        return str.contains("#");
    }

    /**
     * 锁键值
     */
    private String getRealLockKey(String value) {
        return String.format("lock:%s", value);
    }

}
