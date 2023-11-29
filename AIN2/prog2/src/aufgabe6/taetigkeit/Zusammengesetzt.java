package taetigkeit;

import java.util.ArrayList;
import java.util.List;

public abstract class Zusammengesetzt implements Taetigkeit {
    protected List<Taetigkeit> tasks;

    public Zusammengesetzt() {
        tasks = new ArrayList<>();
    }

    public void add(Taetigkeit task) {
        tasks.add(task);
    }

    public void remove(Taetigkeit task) {
        tasks.remove(task);
    }

    public int getAnzahl() {
        int count = 0;
        for (Taetigkeit task : tasks) {
            count += task.getAnzahl();
        }
        return count;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (Taetigkeit task : tasks) {
            stringBuilder.append(task.toString()).append("\n");
        }
        return stringBuilder.toString();
    }
}
