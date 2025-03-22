import utils.FileUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        try {
            Map<String, String> params = parseArgs(args);
            if (params.containsKey("-e") && params.containsKey("-a")) {
                ExerciseChecker.check(params.get("-e"), params.get("-a"));
            } else if (params.containsKey("-n") && params.containsKey("-r")) {
                int n = Integer.parseInt(params.get("-n"));
                int r = Integer.parseInt(params.get("-r"));

                ExpressionGenerator generator = new ExpressionGenerator(r);
                List<String> exercises = generator.generate(n);
                List<String> answers = generator.getAnswers();

                FileUtils.write("Exercises.txt", exercises);
                FileUtils.write("Answers.txt", answers);
            } else {
                showHelp();
            }
        } catch (Exception e) {
            showHelp();
        }
    }

    private static Map<String, String> parseArgs(String[] args) {
        Map<String, String> params = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("-")) {
                params.put(args[i], i < args.length-1 ? args[++i] : "");
            }
        }
        return params;
    }

    private static void showHelp() {
        System.out.println("Usage:");
        System.out.println("Generate mode: Myapp.exe -n <number> -r <range>");
        System.out.println("Check mode: Myapp.exe -e <exercisefile> -a <answerfile>");
        System.exit(1);
    }
}