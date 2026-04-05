package org.example.task;


import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AsyncScheduledTasks {

    @Scheduled(cron = "0 0 10 * * ?")
    public void scheduleTask1() {
        executeTask1Async();
    }
    
    @Scheduled(cron = "0 0 10 * * ?")
    public void scheduleTask2() {
        executeTask2Async();
    }
    
    @Scheduled(cron = "0 0 10 * * ?")
    public void scheduleTask3() {
        executeTask3Async();
    }
    
    // ... 其他 10 个任务的调度方法
    
    @Async("asyncExecutor")
    public void executeTask1Async() {
        log.info("任务 1 执行 - 线程：{}", Thread.currentThread().getName());
        // 任务逻辑
    }
    
    @Async("asyncExecutor")
    public void executeTask2Async() {
        log.info("任务 2 执行 - 线程：{}", Thread.currentThread().getName());
        // 任务逻辑
    }
    
    @Async("asyncExecutor")
    public void executeTask3Async() {
        log.info("任务 3 执行 - 线程：{}", Thread.currentThread().getName());
        // 任务逻辑
    }
    // ... 其他任务
}
