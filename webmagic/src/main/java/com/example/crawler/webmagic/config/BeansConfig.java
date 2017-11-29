package com.example.crawler.webmagic.config;

import com.example.crawler.webmagic.jmx.thread.ThreadPoolStatus;
import com.example.crawler.webmagic.jmx.thread.TrackingThreadPool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Configuration
public class BeansConfig {

    @Bean
    public TrackingThreadPool trackingThreadPool() {
        LinkedBlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
        System.err.format("剩余容量: %s \n", workQueue.remainingCapacity());
        return new TrackingThreadPool(5, 10, 5, TimeUnit.MINUTES, workQueue);
    }

}
