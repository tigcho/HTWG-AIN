package taetigkeit;

public class Seriell extends Zusammengesetzt {

    @Override
    public double getTime() {
        double totalTime = 0;

        for (Taetigkeit task : tasks) {
            totalTime += task.getTime();
        }
        return totalTime;
    }
}
