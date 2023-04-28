package examples.calculator;

public sealed interface Token permits Operand, Operator, LeftParen, RightParen {}
record LeftParen() implements Token {}
record RightParen() implements Token {}