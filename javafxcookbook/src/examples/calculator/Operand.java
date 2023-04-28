package examples.calculator;

public record Operand(double n) implements Token, Expression {
    @Override
    public String toString() {
        return Double.toString(n);
    }
}
