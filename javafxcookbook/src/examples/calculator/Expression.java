package examples.calculator;

public sealed interface Expression permits Operator, Operand {}
