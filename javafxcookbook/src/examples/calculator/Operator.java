package examples.calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;

public enum Operator implements BinaryOperator<Double>, Token, Expression {
    PLUS("+", 2, Associativity.LEFT),
    MINUS("-", 2, Associativity.LEFT),
    TIMES("*", 3, Associativity.LEFT),
    DIVIDE("/", 3, Associativity.LEFT);

    private final String strRep;
    private final int precedence;

    public enum Associativity { LEFT, RIGHT }
    private final Associativity associativity;

    private static final List<String> operatorReps;

    static {
        operatorReps = new ArrayList<>();
        for (final Operator operator : values()) {
            operatorReps.add(operator.strRep);
        }
    }

    Operator(final String strRep, final int precedence, final Associativity associativity) {
        this.strRep = strRep;
        this.precedence = precedence;
        this.associativity = associativity;
    }

    @Override
    public Double apply(Double n1, Double n2) {
        return switch (this) {
            case PLUS -> n1 + n2;
            case MINUS -> n1 - n2;
            case TIMES -> n1 * n2;
            case DIVIDE -> n1 / n2;
        };
    }

    public static Operator parseMathOperator(final String str) {
        for (final Operator operator : values()) {
            if (operator.strRep.equals(str)) {
                return operator;
            }
        }
        throw new IllegalArgumentException("String: " + str + " is not a valid math operator");
    }

    public static boolean isOperator(String str) {
        return operatorReps.contains(str);
    }

    public int getPrecedence() {
        return precedence;
    }

    public Associativity getAssociativity() {
        return associativity;
    }

    @Override
    public String toString() {
        return strRep;
    }
}
