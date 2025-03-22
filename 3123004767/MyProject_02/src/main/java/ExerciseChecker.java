import utils.FileUtils;
import java.util.*;
import java.util.stream.Collectors;

public class ExerciseChecker {
    public static void check(String exerciseFile, String answerFile) {
        List<String> exercises = FileUtils.readLines(exerciseFile);
        List<String> userAnswers = FileUtils.readLines(answerFile);
        List<Integer> correctIndices = new ArrayList<>();
        List<Integer> wrongIndices = new ArrayList<>();

        for (int i = 0; i < exercises.size(); i++) {
            String expr = exercises.get(i).replace(" =", "").trim();
            try {
                Fraction correct = evaluateExpression(expr);
                String userAns = userAnswers.get(i).trim();
                boolean isCorrect = correct.equals(new Fraction(userAns));

                if (isCorrect) {
                    correctIndices.add(i + 1);
                } else {
                    wrongIndices.add(i + 1);
                }
            } catch (Exception e) {
                wrongIndices.add(i + 1);
            }
        }

        String result = buildResultString(correctIndices, wrongIndices);
        FileUtils.write("Grade.txt", Collections.singletonList(result));
    }

    private static Fraction evaluateExpression(String expr) {
        List<String> tokens = Tokenizer.tokenize(expr);
        List<String> postfix = InfixToPostfix.convert(tokens);
        return PostfixEvaluator.evaluate(postfix);
    }

    private static String buildResultString(List<Integer> correct, List<Integer> wrong) {
        return "Correct: " + correct.size() + formatIndices(correct) + "\n" +
                "Wrong: " + wrong.size() + formatIndices(wrong);
    }

    private static String formatIndices(List<Integer> indices) {
        if (indices.isEmpty()) return " ()";
        return " (" + indices.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", ")) + ")";
    }

    private static class Tokenizer {
        static List<String> tokenize(String expr) {
            List<String> tokens = new ArrayList<>();
            StringBuilder buffer = new StringBuilder();

            for (char c : expr.replace(" ", "").toCharArray()) {
                if (Character.isDigit(c) || c == '/' || c == '\'') {
                    buffer.append(c);
                } else if ("+-×÷()".indexOf(c) != -1) {
                    if (buffer.length() > 0) {
                        tokens.add(buffer.toString());
                        buffer.setLength(0);
                    }
                    tokens.add(String.valueOf(c));
                }
            }
            if (buffer.length() > 0) tokens.add(buffer.toString());
            return tokens;
        }
    }

    private static class InfixToPostfix {
        static List<String> convert(List<String> infix) {
            List<String> postfix = new ArrayList<>();
            Deque<String> stack = new ArrayDeque<>();
            Map<String, Integer> prec = new HashMap<>() {{
                put("+", 1); put("-", 1); put("×", 2); put("÷", 2); put("(", 0);
            }};

            for (String token : infix) {
                if (token.matches(".*[\\d'/].*")) {
                    postfix.add(token);
                } else if (token.equals("(")) {
                    stack.push(token);
                } else if (token.equals(")")) {
                    while (!stack.peek().equals("("))
                        postfix.add(stack.pop());
                    stack.pop();
                } else {
                    while (!stack.isEmpty() &&
                            prec.getOrDefault(stack.peek(), 0) >= prec.get(token)) {
                        postfix.add(stack.pop());
                    }
                    stack.push(token);
                }
            }
            while (!stack.isEmpty()) postfix.add(stack.pop());
            return postfix;
        }
    }
    class PostfixEvaluator {
        static Fraction evaluate(List<String> postfix) {
            Deque<Fraction> stack = new ArrayDeque<>();
            for (String token : postfix) {
                if (token.matches(".*[\\d'/].*")) {
                    stack.push(new Fraction(token));
                } else {
                    Fraction b = stack.pop();
                    Fraction a = stack.pop();
                    switch (token) {
                        case "+": stack.push(a.add(b)); break;
                        case "-":
                            if (a.compareTo(b) < 0) throw new ArithmeticException();
                            stack.push(a.subtract(b)); break;
                        case "×": stack.push(a.multiply(b)); break;
                        case "÷":
                            Fraction result = a.divide(b);
                            if (result.isInteger()) throw new ArithmeticException();
                            stack.push(result); break;
                    }
                }
            }
            return stack.pop();
        }
    }
}