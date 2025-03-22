package generator;

import java.util.Random;

public class ExpressionGenerator {
    private static final String[] OPERATORS = {"+", "-", "×", "÷"};

    public static String generateExpression(int maxDepth, int range) {
        if (maxDepth == 0 || (maxDepth > 1 && new Random().nextBoolean())) {
            String left = generateExpression(maxDepth - 1, range);
            String right = generateExpression(maxDepth - 1, range);
            String op = OPERATORS[new Random().nextInt(4)];
            return wrapWithParentheses(left + " " + op + " " + right);
        }
        return generateOperand(range);
    }

    private static String generateOperand(int range) {
        // 生成自然数或真分数（参考网页[6]的分数处理）
        return null;
    }
}
