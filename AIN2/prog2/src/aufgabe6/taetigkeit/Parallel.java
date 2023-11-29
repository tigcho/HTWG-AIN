package taetigkeit;

public class Parallel extends Zusammengesetzt {

    @Override
    public double getTime() {
        double totalTime = 0;

        for (Taetigkeit task : tasks) {
            double time = task.getTime();
            if (task.getTime() > totalTime) {
                totalTime = time;
            }
        }
        return totalTime;
    }
}
