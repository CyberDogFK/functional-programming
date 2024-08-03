package chapter7;

public class VisitorPattern {
    public static void main(String[] args) {
        ISceneElement scene = new Scene("Primary Scene");
        scene.accept(new SceneElementPrintVisitor());
        scene.accept(new SceneElementRefreshVisitor());

        // Functional
        ISceneElement sceneF = new SceneFunctional("Primary Scene");
        sceneF.accept(new SceneElementPrintVisitor());
        sceneF.accept(new SceneElementRefreshVisitor());
        scene.accept(t -> System.out.println(
                "Another visitor operation on " + t.getName()
        ));
    }
}

@FunctionalInterface
interface ISceneElement {
    String getName();
    default void accept(ISceneElementVisitor visitor) {
        visitor.visit(this);
    }
}

@FunctionalInterface
interface ISceneElementVisitor {
    void visit(ISceneElement element);
}

class SceneFunctional implements ISceneElement {
    ISceneElement[] elements;
    private String name;

    SceneFunctional(String name) {
        this.name = name;
        BuildingElementFunctional building = () -> "Tool Shed";
        this.elements = new ISceneElement[] {
                building,
                () -> "Brick House",
                new PlantElement("Oak Tree"),
                new PlantElement("Lawn")
        };
    }

    @Override
    public void accept(ISceneElementVisitor visitor) {
        for (ISceneElement elem : elements) {
            elem.accept(visitor);
        }
        visitor.visit(this);
    }

    @Override
    public String getName() {
        return this.name;
    }
}

class Scene implements ISceneElement {
    ISceneElement[] elements;
    private String name;

    Scene(String name) {
        this.name = name;
        this.elements = new ISceneElement[] {
                new BuildingElement("Tool Shed"),
                new BuildingElement("Brick House"),
                new PlantElement("Oak Tree"),
                new PlantElement("Lawn")
        };
    }

    @Override
    public void accept(ISceneElementVisitor visitor) {
        for (ISceneElement elem : elements) {
            elem.accept(visitor);
        }
        visitor.visit(this);
    }

    @Override
    public String getName() {
        return this.name;
    }
}

class BuildingElement implements ISceneElement {
    private String name;

    BuildingElement(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

}

@FunctionalInterface
interface BuildingElementFunctional extends ISceneElement { }

class PlantElement implements ISceneElement {
    private String name;

    PlantElement(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}

class SceneElementRefreshVisitor implements
        ISceneElementVisitor {
    @Override
    public void visit(ISceneElement element) {
        System.out.println("Refreshing " + element.getName());
    }
}

class SceneElementPrintVisitor implements ISceneElementVisitor {
    @Override
    public void visit(ISceneElement element) {
        System.out.println("Printing " + element.getName());
    }
}
