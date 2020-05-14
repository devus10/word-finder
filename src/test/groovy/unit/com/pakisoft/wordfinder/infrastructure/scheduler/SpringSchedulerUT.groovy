package com.pakisoft.wordfinder.infrastructure.scheduler

import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.support.CronTrigger
import spock.lang.Specification

class SpringSchedulerUT extends Specification {

    private TaskScheduler taskScheduler = Mock()
    def scheduler = new SpringScheduler(taskScheduler)

    def "should schedule a task with given cron"() {
        given:
        def task = {}
        def cron = '* * * * * *'

        when:
        scheduler.schedule(task, cron)

        then:
        1 * taskScheduler.schedule(task, new CronTrigger(cron))
    }
}
