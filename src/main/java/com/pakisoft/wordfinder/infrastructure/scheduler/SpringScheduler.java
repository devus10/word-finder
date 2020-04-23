package com.pakisoft.wordfinder.infrastructure.scheduler;

import com.pakisoft.wordfinder.domain.port.secondary.Scheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

@RequiredArgsConstructor
public class SpringScheduler implements Scheduler {

    private final TaskScheduler taskScheduler;

    @Override
    public void schedule(Runnable runnable, String cron) {
        var trigger = new CronTrigger(cron);
        taskScheduler.schedule(runnable, trigger);
    }
}
