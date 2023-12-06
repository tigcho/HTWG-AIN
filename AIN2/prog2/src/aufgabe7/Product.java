package aufgabe7;

import java.util.Map;

public class Product extends CompoundExpression {

    public Product(Expression a, Expression b) {
        super(a, b);
    }

    @Override
    public double eval(Map<String, Double> map) {
        double var1 = e1.eval(map);
        double var2 = e2.eval(map);
        return var1 * var2;
    }

    @Override
    public String toString() {
        return "(" + this.e1.toString() + " * " + this.e2.toString() + ")";
    }
}
