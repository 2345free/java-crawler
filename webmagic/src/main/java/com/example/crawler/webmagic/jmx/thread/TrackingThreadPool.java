package com.example.crawler.webmagic.jmx.thread;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author tianyi
 */
@ManagedResource
public class TrackingThreadPool extends ThreadPoolExecutor {

    private final Map<Runnable, Boolean> inProgress = new ConcurrentHashMap<Runnable, Boolean>();
    private final ThreadLocal<Long> startTime = new ThreadLocal<>();
    private long totalTime;
    private int totalTasks;

    public TrackingThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                              TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        inProgress.put(r, Boolean.TRUE);
        startTime.set(new Long(System.currentTimeMillis()));
    }

    protected void afterExecute(Runnable r, Throwable t) {
        long time = System.currentTimeMillis() - startTime.get().longValue();
        synchronized (this) {
            totalTime += time;
            ++totalTasks;
        }
        inProgress.remove(r);
        super.afterExecute(r, t);
    }

    @ManagedAttribute
    public Set<Runnable> getInProgressTasks() {
        return Collections.unmodifiableSet(inProgress.keySet());
    }

    @ManagedAttribute
    public synchronized int getTotalTasks() {
        return totalTasks;
    }

    @ManagedAttribute
    public synchronized double getAverageTaskTime() {
        return (totalTasks == 0) ? 0 : totalTime / totalTasks;
    }

    @ManagedAttribute
    public int getActiveThreads() {
        return this.getPoolSize();
    }

    @ManagedAttribute
    public int getActiveTasks() {
        return this.getActiveCount();
    }

    @ManagedAttribute
    public int getQueuedTasks() {
        return this.getQueue().size();
    }

    @ManagedAttribute
    public String[] getActiveTaskNames() {
        return toStringArray(this.getInProgressTasks());
    }

    @ManagedAttribute
    public String[] getQueuedTaskNames() {
        return toStringArray(this.getQueue());
    }

    private String[] toStringArray(Collection<Runnable> collection) {
        ArrayList<String> list = new ArrayList<String>();
        for (Runnable r : collection)
            list.add(r.toString());
        return list.toArray(new String[0]);
    }

}
