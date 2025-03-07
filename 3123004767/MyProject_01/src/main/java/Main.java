import java.io.IOException;
import java.text.DecimalFormat;
import java.util.concurrent.CompletableFuture;

public class Main {

    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("参数错误，请提供原文文件、抄袭版文件和输出文件的路径。");
            System.exit(1);
        }

        String originalFilePath = args[0];
        String plagiarizedFilePath = args[1];
        String outputFilePath = args[2];

        try {
            // 异步读取文件内容
            CompletableFuture<String> originalFuture = FileHandler.readFileAsync(originalFilePath);
            CompletableFuture<String> plagiarizedFuture = FileHandler.readFileAsync(plagiarizedFilePath);

            String originalText = originalFuture.join();
            String plagiarizedText = plagiarizedFuture.join();

            // 计算相似度（带缓存）
            double similarity = TextComparator.calculateSimilarityWithCache(originalText, plagiarizedText);

            // 格式化为两位小数
            DecimalFormat df = new DecimalFormat("0.00");
            String result = df.format(similarity);

            // 写入结果文件
            FileHandler.writeFile(outputFilePath, result);

            System.out.println("查重完成，结果已写入: " + outputFilePath);

        } catch (IOException e) {
            System.err.println("文件处理错误: " + e.getMessage());
            System.exit(1);
        }
    }
}