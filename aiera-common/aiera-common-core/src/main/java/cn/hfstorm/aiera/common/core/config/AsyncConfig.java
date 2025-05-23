package cn.hfstorm.aiera.common.core.config;

import cn.hutool.core.util.ArrayUtil;
import cn.hfstorm.aiera.common.core.exception.ServiceException;
import cn.hfstorm.aiera.common.core.utils.SpringUtils;
import cn.hfstorm.aiera.common.core.utils.Threads;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Arrays;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 异步配置
 *
 * @author hmy
 */
@Slf4j
@EnableAsync(proxyTargetClass = true)
@AutoConfiguration
public class AsyncConfig implements AsyncConfigurer {

	private final int corePoolSize = Runtime.getRuntime().availableProcessors() + 1;
	private ScheduledExecutorService scheduledExecutorService;

	/**
	 * 执行周期性或定时任务
	 */
	@Bean(name = "scheduledExecutorService")
	public ScheduledExecutorService scheduledExecutorService() {
		ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(corePoolSize,
				new BasicThreadFactory.Builder().namingPattern("schedule-pool-%d").daemon(true).build(),
				new ThreadPoolExecutor.CallerRunsPolicy()) {
			@Override
			protected void afterExecute(Runnable r, Throwable t) {
				super.afterExecute(r, t);
				printException(r, t);
			}
		};
		this.scheduledExecutorService = scheduledThreadPoolExecutor;
		return scheduledThreadPoolExecutor;
	}

	/**
	 * 销毁事件
	 */
	@PreDestroy
	public void destroy() {
		try {
			log.info("====关闭后台任务任务线程池====");
			Threads.shutdownAndAwaitTermination(scheduledExecutorService);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 自定义 @Async 注解使用系统线程池
	 */
	@Override
	public Executor getAsyncExecutor() {
		return SpringUtils.getBean("scheduledExecutorService");
	}

	/**
	 * 异步执行异常处理
	 */
	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return (throwable, method, objects) -> {
			throwable.printStackTrace();
			StringBuilder sb = new StringBuilder();
			sb.append("Exception message - ").append(throwable.getMessage())
					.append(", Method name - ").append(method.getName());
			if (ArrayUtil.isNotEmpty(objects)) {
				sb.append(", Parameter value - ").append(Arrays.toString(objects));
			}
			throw new ServiceException(sb.toString());
		};
	}

	/**
	 * 打印线程异常信息
	 */
	public void printException(Runnable r, Throwable t) {
		if (t == null && r instanceof Future<?>) {
			try {
				Future<?> future = (Future<?>) r;
				if (future.isDone()) {
					future.get();
				}
			} catch (CancellationException ce) {
				t = ce;
			} catch (ExecutionException ee) {
				t = ee.getCause();
			} catch (InterruptedException ie) {
				Thread.currentThread().interrupt();
			}
		}
		if (t != null) {
			log.error(t.getMessage(), t);
		}
	}

}
