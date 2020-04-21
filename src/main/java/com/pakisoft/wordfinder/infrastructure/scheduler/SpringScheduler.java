package com.pakisoft.wordfinder.infrastructure.scheduler;

import com.pakisoft.wordfinder.domain.port.secondary.Scheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;

@RequiredArgsConstructor
public class SpringScheduler implements Scheduler {

    private final TaskScheduler taskScheduler;

    @Override
    public void schedule(Runnable runnable) {
        var trigger = new PeriodicTrigger(1000000);
        taskScheduler.schedule(runnable, trigger);
    }
}
