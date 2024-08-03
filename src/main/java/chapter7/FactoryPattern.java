package chapter7;

import java.util.function.Supplier;

public class FactoryPattern {
    public static void main(String[] args) {
        VacuumCleaner dvc = VacuumCleanerFactory.getInstance("Dirt");
        dvc.vacuum();
        dvc.clean();
        VacuumCleaner wvc = VacuumCleanerFactory.getInstance("Water");
        wvc.vacuum();
        wvc.clean();

        // Functional solution
        Supplier<DirtVacuumCleaner> dvcSupplier =
                DirtVacuumCleaner::new;
        dvc = dvcSupplier.get();
        dvc.vacuum();
        dvc.clean();

        Supplier<WaterVacuumCleaner> wvcSupplier =
                WaterVacuumCleaner::new;
        wvc.vacuum();
        wvc.clean();
    }
}

interface VacuumCleaner {
    void vacuum();
    void clean();
}

class DirtVacuumCleaner implements VacuumCleaner {
    public DirtVacuumCleaner() {
        System.out.println("Creating DirtVacuumCleaner");
    }

    @Override
    public void vacuum() {
        System.out.println("Vacuuming dirt");
    }

    @Override
    public void clean() {
        System.out.println("Cleaning Dirt Vacuum Cleaner");
    }
}

class WaterVacuumCleaner implements VacuumCleaner {
    public WaterVacuumCleaner()  {
        System.out.println("Creating WaterVacuumCleaner");
    }

    @Override
    public void vacuum() {
        System.out.println("Vacuuming water");
    }

    @Override
    public void clean() {
        System.out.println("Cleaning Water Vacuum Cleaner");
    }
}

class VacuumCleanerFactory {
    public static VacuumCleaner getInstance(String type) {
        VacuumCleaner vacuumCleaner = null;
        if ("Dirt".equals(type)) {
            vacuumCleaner = new DirtVacuumCleaner();
        } else if ("Water".equals(type)) {
            vacuumCleaner = new WaterVacuumCleaner();
        } else {
            // Handle bad type
        }
        return vacuumCleaner;
    }
}
