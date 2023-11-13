package aufgabe1;

/**
 *
 * @author oliverbittel
 */
public abstract class AbstractFrequencyTable implements FrequencyTable {
	@Override
	public boolean isEmpty() {
		return this.size() == 0;
	}
	
	@Override
    public void add(String w) {
		add(w, 1);
    }

	@Override
	public void addAll(FrequencyTable fq) {
		// iteriert durch die Elemente im fq Array und sammelt von jedem Wort die Haeufigkeit
		for (int i = 0; i < fq.size(); i++) {
			Word w = fq.get(i); // Wort-Objekt wird fq-Wort an Position i zugewiesen
			add(w.getWord(), w.getFrequency());
		}
	}

	@Override
	public void collectNMostFrequent(int n, FrequencyTable fq) {
		fq.clear(); // damit nur die aktuellen haeufigsten n Woerter enthalten sind

		/*
		solange i < n und Groesse der Tabelle ist, gibt es keine IOB-Exception
		 */
		for (int i = 0; i < n && i < size(); i++) {
			Word w = get(i);
			fq.add(w.getWord(), w.getFrequency());
		}
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder("{");
		for (int i = 0; i < size(); i++) { // bis size - 1
			s.append(get(i).toString()).append(", "); // ZK-Repräsentation an Stelle i
		}
		s.append("} size = ").append(size());
		return s.toString(); // return alles als eine Zeichenkette
	}
}
