import generator.ExpressionGenerator;
import picocli.CommandLine;
import validator.AnswerChecker;

@CommandLine.Command(name = "MathGenerator", mixinStandardHelpOptions = true)
public class Main implements Runnable {
    @CommandLine.Option(names = {"-n"}, description = "题目数量")
    private int questionCount;

    @CommandLine.Option(names = {"-r"}, required = true, description = "数值范围")
    private int range;

    @CommandLine.Option(names = {"-e"}, description = "题目文件路径")
    private String exerciseFile;

    @CommandLine.Option(names = {"-a"}, description = "答案文件路径")
    private String answerFile;

    @Override
    public void run() {
        if (exerciseFile != null && answerFile != null) {
            AnswerChecker.check(exerciseFile, answerFile);
        } else {
            ExpressionGenerator.generate(questionCount, range);
        }
    }

    public static void main(String[] args) {
        new CommandLine(new Main()).execute(args);
    }
}