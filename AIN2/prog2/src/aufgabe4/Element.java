package aufgabe4;

public class Element<T> {
    final private T element;
    private int frequency;

    public Element(T element, int frequency) {
        this.element = element;
        this.frequency = frequency;
    }

    public T getElement() {
        return element;
    }

    public int getFrequency() {
        return frequency;
    }

    public void addFrequency(int frequency) {
        this.frequency += frequency;
    }

    @Override
    public String toString() {
        return element + ":" + frequency;
    }
}