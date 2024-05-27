package org.drachentrix.plugins.lordofthemysteries.common.utils;

import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Mod.EventBusSubscriber
public class Scheduler {

    private static final List<ScheduledTask> tasks = new ArrayList<>();

    public static void schedule(Runnable task, int delay) {
        tasks.add(new ScheduledTask(task, delay));
    }

    public static void tick() {
        Iterator<ScheduledTask> iterator = tasks.iterator();
        while (iterator.hasNext()) {
            ScheduledTask task = iterator.next();
            task.decrementDelay();
            if (task.isReady()) {
                task.run();
                iterator.remove();
            }
        }
    }
}
