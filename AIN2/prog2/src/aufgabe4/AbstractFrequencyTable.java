package aufgabe4;

/**
 *
 * @author oliverbittel
 */
public abstract class AbstractFrequencyTable<T> implements FrequencyTable<T> {
    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public void add(T s) {
        add(s, 1);
    }

    @Override
    public void addAll(FrequencyTable<? extends T> fq) {
        for (Element<? extends T> element : fq) {
            add(element.getElement(), element.getFrequency());
        }
    }

    @Override
    public void collectNMostFrequent(int n, FrequencyTable<? super T> fq) {
        fq.clear(); // damit nur die aktuellen haeufigsten n Woerter enthalten sind

		/*
		solange i < n und Groesse der Tabelle ist, gibt es keine IOB-Exception
		 */
        for (int i = 0; i < n && i < size(); i++) {
            Element<T> s = get(i);
            fq.add(s.getElement(), s.getFrequency());
        }
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("{");

        for (Element<T> element : this) {
            s.append(element.getElement())
                    .append(":")
                    .append(element.getFrequency())
                    .append(", ");
        }

        s.append("} size = ").append(size());
        return s.toString();
    }

}
