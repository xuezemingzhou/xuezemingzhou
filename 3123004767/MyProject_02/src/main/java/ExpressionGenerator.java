import java.util.*;

public class ExpressionGenerator {
    private final int range;
    private final Set<String> generated = new HashSet<>();
    private final Random random = new Random();
    private List<String> answers = new ArrayList<>();
    public ExpressionGenerator(int range) {
        this.range = range;
    }

    public List<String> generate(int count) {
        List<String> exercises = new ArrayList<>();

        while (exercises.size() < count) {
            int operators = 1 + random.nextInt(3);
            Node root = generateExpression(operators);
            if (root == null) continue;
            String expr = root.toString();
            String answer = root.value().toString();
            if (!generated.contains(expr)) {
                generated.add(expr);
                exercises.add(expr + " = ");
                answers.add(answer);
            }
        }
        return exercises;
    }
    public List<String> getAnswers() {
        return this.answers;
    }
    private Node generateExpression(int operators) {
        try {
            return buildNode(operators);
        } catch (IllegalStateException e) {
            return null;
        }
    }

    private Node buildNode(int operators) {
        if (operators == 0) {
            return new NumberNode(generateNumber());
        }

        char[] ops = {'+', '-', '×', '÷'};
        char op = ops[random.nextInt(4)];
        int leftOps = random.nextInt(operators);
        Node left = buildNode(leftOps);
        Node right = buildNode(operators - 1 - leftOps);

        if (op == '+' || op == '×') {
            if (left.value().compareTo(right.value()) > 0) {
                Node temp = left;
                left = right;
                right = temp;
            }
        }

        Fraction value;
        try {
            switch (op) {
                case '+': value = left.value().add(right.value()); break;
                case '-':
                    if (left.value().compareTo(right.value()) < 0) throw new IllegalStateException();
                    value = left.value().subtract(right.value());
                    break;
                case '×': value = left.value().multiply(right.value()); break;
                case '÷':
                    if (right.value().numerator == 0 || left.value().divide(right.value()).isInteger())
                        throw new IllegalStateException();
                    value = left.value().divide(right.value());
                    break;
                default: throw new IllegalStateException();
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            throw new IllegalStateException("Invalid operation");
        }

        return new OperatorNode(op, left, right, value);
    }

    private Fraction generateNumber() {
        if (random.nextBoolean()) {
            return new Fraction(random.nextInt(range), 1);
        } else {
            int denominator = 1 + random.nextInt(range - 1);
            int numerator = random.nextInt(denominator * range);
            return new Fraction(numerator, denominator);
        }
    }

    private interface Node {
        Fraction value();
        String toString();
    }

    private static class NumberNode implements Node {
        private final Fraction value;

        NumberNode(Fraction value) {
            this.value = value;
        }

        @Override
        public Fraction value() { return value; }

        @Override
        public String toString() { return value.toString(); }
    }

    private static class OperatorNode implements Node {
        private final char operator;
        private final Node left, right;
        private final Fraction value;

        OperatorNode(char operator, Node left, Node right, Fraction value) {
            this.operator = operator;
            this.left = left;
            this.right = right;
            this.value = value;
        }

        @Override
        public Fraction value() { return value; }

        @Override
        public String toString() {
            String l = left.toString();
            String r = right.toString();
            if (left instanceof OperatorNode) {
                OperatorNode lo = (OperatorNode) left;
                if (priority(lo.operator) < priority(operator)) {
                    l = "(" + l + ")";
                }
            }
            if (right instanceof OperatorNode) {
                OperatorNode ro = (OperatorNode) right;
                if (priority(ro.operator) < priority(operator) ||
                        (priority(ro.operator) == priority(operator) && (operator == '-' || operator == '÷')) ){
                    r = "(" + r + ")";
                }
            }
            return l + " " + operator + " " + r;
        }

        private int priority(char op) {
            return op == '+' || op == '-' ? 1 : 2;
        }
    }
}