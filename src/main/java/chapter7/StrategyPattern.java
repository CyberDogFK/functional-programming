package chapter7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class StrategyPattern {
    public static void main(String[] args) {
        Task[] tasks = {
                new Task("Quick", 25),
                new Task("Longest", 200),
                new Task("Shortest", 2),
                new Task("Slow", 35)
        };

        Tasks taskList1 = new Tasks();
        taskList1.setTasks(Arrays.asList(tasks));
        taskList1.setStrategy(new STFStrategy());
        System.out.println(taskList1.getNextTask());

        Tasks taskList2 = new Tasks();
        taskList2.setTasks(Arrays.asList(tasks));
        taskList2.setStrategy(new FCFSSStrategy());
        System.out.println(taskList2.getNextTask());
        taskList2.setStrategy(new LTFStrategy());
        System.out.println(taskList2.getNextTask());

        // Functional solution
        SchedulingStrategy STF = t -> {
            Task shortest = t.get(0);
            for (Task task : t) {
                if (shortest.getDuration() > task.getDuration()) {
                    shortest = task;
                }
            }
            return shortest;
        };
        taskList1.setStrategy(STF);
        System.out.println(taskList1.getNextTask());

        Comparator<Task> comparator =
                (x, y) -> x.getDuration() - y.getDuration();
        SchedulingStrategy STFStrategy =
                t -> t.stream().min(comparator).get();

        SchedulingStrategy FCFStrategy = t -> t.get(0);
        SchedulingStrategy LTFStrategy =
                t -> t.stream().max(comparator).get();

        taskList1.setStrategy(STFStrategy);
        System.out.println(taskList1.getNextTask());

        Tasks taskList3 = new Tasks();
        taskList3.setTasks(Arrays.asList(tasks));
        taskList2.setStrategy(FCFStrategy);
        System.out.println(taskList2.getNextTask());

        taskList3.setStrategy(LTFStrategy);
        System.out.println(taskList2.getNextTask());

        Function<List<Task>, Task> FCFSStrategy = t -> t.get(0);
        STFStrategy = t -> t.stream().min(comparator).get();
        LTFStrategy = t -> t.stream().max(comparator).get();
    }
}

class Task {
    private String name;
    private int duration;

    Task(String name, int duration) {
        this.name = name;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Task{" + "name=" + name + ", duration="
                + duration + "}";
    }
}

class Tasks {
    private List<Task> tasks;
    private SchedulingStrategy strategy;
    private Function<List<Task>, Task> strategyFunction;

    Tasks() {
        tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void setStrategyFunction(Function<List<Task>, Task> strategy) {
        this.strategyFunction = strategy;
    }

    public void setStrategy(SchedulingStrategy strategy) {
        this.strategy = strategy;
    }

    public Task getNextTask() {
        return strategy.nextTask(tasks);
    }

    public Task getNextTaskFunction() {
        return strategyFunction.apply(tasks);
    }
}

@FunctionalInterface
interface SchedulingStrategy {
    Task nextTask(List<Task> tasks);
}

class FCFSSStrategy implements SchedulingStrategy {
    @Override
    public Task nextTask(List<Task> tasks) {
        return tasks.get(0);
    }
}

class STFStrategy implements SchedulingStrategy {
    @Override
    public Task nextTask(List<Task> tasks) {
        Task shortest = tasks.get(0);
        for (Task task : tasks) {
            if (shortest.getDuration() > task.getDuration()) {
                shortest = task;
            }
        }
        return shortest;
    }
}

class LTFStrategy implements SchedulingStrategy {
    @Override
    public Task nextTask(List<Task> tasks) {
        Task longest = tasks.get(0);
        for (Task task : tasks) {
            if (longest.getDuration() < task.getDuration()) {
                longest = task;
            }
        }
        return longest;
    }
}
