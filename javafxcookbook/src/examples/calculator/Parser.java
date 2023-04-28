package examples.calculator;

import java.util.*;

public class Parser {
    private static Queue<String> shuntingYardParse(Scanner input) {
        Queue<String> outputQueue = new ArrayDeque<>();
        Deque<String> operatorStack = new ArrayDeque<>();

        while (input.hasNext()) {
            String token = input.next();
            switch (token) {
                case "1", "2", "3", "4", "5",
                        "6", "7", "8", "9", "0" -> outputQueue.add(token);
                case "+", "*", "-", "/" -> {
                    String topOfStack;
                    // Don't check for isEmpty at first
                    while (!operatorStack.isEmpty() &&
                            !(topOfStack = operatorStack.peek()).equals("(") &&
                            getPrecedence(topOfStack) > getPrecedence(token)) {
                        outputQueue.add(operatorStack.pop());
                    }
                    operatorStack.push(token);
                }
                case "(" -> operatorStack.push(token);
                case ")" -> {
                    while (!operatorStack.peek().equals("(")) {
                        assert !operatorStack.isEmpty();
                        outputQueue.add(operatorStack.pop());
                    }
                    operatorStack.pop();
                }
                default -> throw new IllegalArgumentException("Character: " + token + " not recognized");
            }
        }

        while (!operatorStack.isEmpty()) {
            outputQueue.add(operatorStack.pop());
        }

        return outputQueue;
    }

    private static int getPrecedence(String str) {
        return switch (str) {
            case "+", "-" -> 2;
            case "*", "/" -> 3;
            default -> -1;
        };
    }

    private static double interpretRPN(Queue<String> queue) {
        if (queue.isEmpty()) return 0;

        Deque<Double> answer = new ArrayDeque<>();

        while (!queue.isEmpty()) {
            String element = queue.poll();
            switch (element) {
                case "+" -> {
                    Double n2 = answer.pop();
                    Double n1 = answer.pop();
                    answer.push(n1 + n2);
                }
                case "*" -> {
                    Double n2 = answer.pop();
                    Double n1 = answer.pop();
                    answer.push(n1 * n2);
                }
                case "-" -> {
                    Double n2 = answer.pop();
                    Double n1 = answer.pop();
                    answer.push(n1 - n2);
                }
                case "/" -> {
                    Double n2 = answer.pop();
                    Double n1 = answer.pop();
                    answer.push(n1 / n2);
                }
                default -> answer.push(Double.parseDouble(element));
            }
        }

        assert !answer.isEmpty();

        return answer.pop();
    }

    // Version 2
    private static List<Token> tokenizeInfix(String input) {
        String delimiter = "";
        String inputDelimited = input.replaceAll(" ", delimiter);

        List<Token> tokens = new ArrayList<>();

        try (Scanner s = new Scanner(inputDelimited)) {
            s.useDelimiter(delimiter);

            while (s.hasNext()) {
                String character = s.next();
                if (character.matches("\\d")) {
                    StringBuilder token = new StringBuilder();
                    token.append(character);
                    while (s.hasNext("\\d|\\.")) {
                        token.append(s.next());
                    }
                    tokens.add(new Operand(Double.parseDouble(token.toString())));
                } else if (Operator.isOperator(character)) {
                    tokens.add(Operator.parseMathOperator(character));
                } else if (character.equals("(")) {
                    tokens.add(new LeftParen());
                } else if (character.equals(")")) {
                    tokens.add(new RightParen());
                } else {
                    throw new IllegalArgumentException("Character: " + character + " not recognized");
                }
            }
        }

        return tokens;
    }

    private static List<Expression> shuntingYardParseV2(List<Token> input) {
        List<Expression> output = new ArrayList<>();
        Deque<Token> operatorStack = new ArrayDeque<>();

        for (Token token : input) {
            if (token instanceof Operand n) {
                output.add(n);
            }
            else if (token instanceof Operator op) {
                Token topOfStack;
                while (!operatorStack.isEmpty() &&
                        !((topOfStack = operatorStack.peek()) instanceof LeftParen) &&
                        (topOfStack instanceof Operator nextOp &&
                                (nextOp.getPrecedence() > op.getPrecedence() ||
                                        (nextOp.getPrecedence() == op.getPrecedence() &&
                                        op.getAssociativity() == Operator.Associativity.LEFT)))) {
                    output.add(((Operator)operatorStack.pop()));
                }
                operatorStack.push(op);
            }
            else if (token instanceof LeftParen lp) {
                operatorStack.push(lp);
            }
            else {
                    while (!(operatorStack.peek() instanceof RightParen) &&
                            operatorStack.peek() instanceof Operator op) {
                        output.add(op);
                    }
                    operatorStack.pop();
            }
        }

//        switch (token) {
//            case Operand n -> output.add(n);
//            case Operator op -> {
//                Token topOfStack;
//                while (!operatorStack.isEmpty() &&
//                        !((topOfStack = operatorStack.peek()) instanceof LeftParen) &&
//                        (topOfStack instanceof Operator nextOp &&
//                                (nextOp.getPrecedence() > op.getPrecedence() ||
//                                        (nextOp.getPrecedence() == op.getPrecedence() &&
//                                                op.getAssociativity() == Operator.Associativity.LEFT)))) {
//                    output.add(((Operator)operatorStack.pop()));
//                }
//                operatorStack.push(op);
//            }
//            case LeftParen lp -> operatorStack.push(lp);
//            case RightParen rp -> {
//                while (!(operatorStack.peek() instanceof RightParen) &&
//                        operatorStack.peek() instanceof Operator op) {
//                    output.add(op);
//                }
//                operatorStack.pop();
//            }
//        }

        while (!operatorStack.isEmpty() &&
                operatorStack.peek() instanceof Operator op) {
            output.add(((Operator) operatorStack.pop()));
        }

        return output;
    }

    private static double interpretPostFixExprV2(List<Expression> postFixExprs) {
        if (postFixExprs.isEmpty()) return 0;

        Deque<Double> stack = new ArrayDeque<>();

        for (Expression expr : postFixExprs) {
            if (expr instanceof Operand n) stack.push(n.n());
            else if (expr instanceof Operator op) {
                double n2 = stack.pop();
                double n1 = stack.pop();
                stack.push(op.apply(n1, n2));
            }
        }

        return stack.pop();
    }

    public static double interpretInfix(String infixExpr) {
        return interpretPostFixExprV2(shuntingYardParseV2(tokenizeInfix(infixExpr)));
    }

    public static void main(String[] args) {
        String expr = "9-4-4";
        List<Token> tokens = tokenizeInfix(expr);
        System.out.println(tokens);
        List<Expression> expressions = shuntingYardParseV2(tokens);
        System.out.println(expressions);
        double answer = interpretPostFixExprV2(expressions);
        System.out.println(answer);
    }
}
