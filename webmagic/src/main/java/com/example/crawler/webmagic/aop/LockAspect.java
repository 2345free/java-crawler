package com.example.crawler.webmagic.aop;

import com.example.crawler.webmagic.annotation.Lockable;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@Aspect
public class LockAspect {
	@Autowired
	private StringRedisTemplate redisTemplate;

	@Around("@annotation(lockable)")
	public Object distributeLock(ProceedingJoinPoint pjp, Lockable lockable) {
		String key = lockable.key();
		String value = lockable.value();
		long expire = lockable.expire();
		log.info("开始redis锁,lockable:[{}]",key);
		Object resultObject = null;

		// 确认此注解是用在方法上
		Signature signature = pjp.getSignature();
		if (!(signature instanceof MethodSignature)) {
			log.error("Lockable is method annotation!");
			return resultObject;
		}

//		MethodSignature methodSignature = (MethodSignature) signature;
//		Method targetMethod = methodSignature.getMethod();

		// 获取注解信息
//		Lockable lockable = targetMethod.getAnnotation(Lockable.class);

		// 分布式锁，如果没有此key，设置此值并返回true；如果有此key，则返回false
		boolean result = redisTemplate.boundValueOps(key).setIfAbsent(value);
		if (!result) {
			log.info("已有其他机器获取该分布锁,不再继续执行");
			return resultObject;
		}

		// 设置过期时间，默认一分钟
		redisTemplate.boundValueOps(key).expire(expire, TimeUnit.SECONDS);

		try {
			resultObject = pjp.proceed(); // 调用对应方法执行
		} catch (Throwable throwable) {
			log.error("发生错误", throwable);
		}
		return resultObject;
	}
}