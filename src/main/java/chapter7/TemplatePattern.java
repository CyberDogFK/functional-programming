package chapter7;

import java.awt.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class TemplatePattern {
    public static void main(String[] args) {
        FPSGame f = new FPSGame();
        StrategyGame s = new StrategyGame();

        // Functional
        TemplateDriver td = new TemplateDriver();
        FPSGameFunctional fps = new FPSGameFunctional(
                TemplateDriver::initializeGame,
                td::render,
                name -> {
                    System.out.println("Updating " + name);
                    return 0;
                }
        );
        StrategyGameFunctional sg = new StrategyGameFunctional(
                n -> System.out.println("Starting " + n),
                () -> {
                    System.out.println("Generating Strategy Image");
                    return "Strategy Image";
                },
                td::update
        );
    }
}

class TemplateDriver {
    public static void initializeGame(String name) {
        System.out.println("Starting " + name);
    }

    public String render() {
        System.out.println("Generating FPS Image");
        return "FPS Image";
    }

    int update(String name) {
        System.out.println("Updating " + name);
        return 0;
    }
}

class GameFunctional {
    Consumer<String> initialize;
    Supplier<String> render;
    Function<String, Integer> update;

    // Template method
    public final void run(String name) {
        initialize.accept(name);
        while (true) {
            String image = render.get();
            System.out.println("Rendering " + image);
            int status = update.apply(name);
            // Evaluate termination conditions
            System.out.println("...");
            break;
        }
    }
}

abstract class Game {
    abstract void initialize(String name);
    abstract String render();
    abstract int update(String name);

    // Template method
    public final void run(String name) {
        initialize(name);
        while (true) {
            String image = render();
            System.out.println("Rendering " + image);
            int status = update(name);
            // Evaluate termination conditions
            System.out.println("...");
            break;
        }
    }
}

class FPSGameFunctional extends GameFunctional {
    public FPSGameFunctional(
            Consumer<String> initialize,
            Supplier<String> render,
            Function<String, Integer> update
    ) {
        this.initialize = initialize;
        this.render = render;
        this.update = update;
        run("FPS Game");
    }
}

class StrategyGameFunctional extends GameFunctional {
    public StrategyGameFunctional(
            Consumer<String> initialize,
            Supplier<String> render,
            Function<String, Integer> update) {
        this.initialize = initialize;
        this.render = render;
        this.update = update;
        run("Strategy Game");
    }
}

class FPSGame extends Game {
    public FPSGame() {
        run("FPS Game");
    }

    @Override
    void initialize(String name) {
        System.out.println("Starting " + name);
    }

    @Override
    String render() {
        System.out.println("Generation FPS Image");
        return "FPS Image";
    }

    @Override
    int update(String name) {
        System.out.println("Updating " + name);
        return 0;
    }
}

class StrategyGame extends Game {
    public StrategyGame() {
        run("Strategy Game");
    }

    @Override
    void initialize(String name) {
        System.out.println("Starting " + name);
    }

    @Override
    String render() {
        System.out.println("Generating Strategy Image");
        return "Strategy Image";
    }

    @Override
    int update(String name) {
        System.out.println("Updating " + name);
        return 0;
    }
}


