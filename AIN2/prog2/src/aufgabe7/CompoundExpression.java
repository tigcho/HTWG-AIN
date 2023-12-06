package aufgabe7;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class CompoundExpression implements Expression {
    Expression e1;
    Expression e2;

    public CompoundExpression(Expression a, Expression b) {
        this.e1 = a;
        this.e2 = b;
    }

    @Override
    public double eval(Map<String, Double> map) {
        return 0;
    }

    @Override
    public Set<String> getVars() {
        Set<String> vars = this.e1.getVars();
        vars.addAll(this.e2.getVars());
        return vars;
    }
}
