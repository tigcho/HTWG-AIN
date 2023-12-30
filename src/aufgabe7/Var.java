package aufgabe7;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Var implements Expression {
    private final String var;
    public Var(String var) {
        this.var = var;
    }

    public double eval(Map<String, Double> map) {
        return map.get(var);
    }

    public Set<String> getVars() {
        Set<String> var = new TreeSet<>();
        var.add(this.var);
        return var;
    }

    @Override
    public String toString() {
        return var;
    }
}
