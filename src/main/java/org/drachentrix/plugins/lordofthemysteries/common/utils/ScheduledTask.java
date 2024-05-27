package org.drachentrix.plugins.lordofthemysteries.common.utils;

public class ScheduledTask {
    private final Runnable task;
    private int delay;

    public ScheduledTask(Runnable task, int delay) {
        this.task = task;
        this.delay = delay;
    }

    public void decrementDelay() {
        delay--;
    }

    public boolean isReady() {
        return delay <= 0;
    }

    public void run() {
        task.run();
    }
}

