import java.text.DecimalFormat;

public class Main {

    public static void main(String[] args) {
        // 参数验证
        if (args.length != 3) {
            System.err.println("参数错误，请提供原文文件、抄袭版文件和输出文件的路径。");
            System.exit(1); // 退出程序
        }

        String originalFilePath = args[0]; // 原文文件路径
        String plagiarizedFilePath = args[1]; // 抄袭版文件路径
        String outputFilePath = args[2]; // 输出文件路径

        try {
            // 读取文件内容
            String originalText = FileUtils.readFile(originalFilePath);
            String plagiarizedText = FileUtils.readFile(plagiarizedFilePath);

            // 计算相似度
            double similarity = TextComparator.calculateSimilarity(originalText, plagiarizedText);

            // 格式化为两位小数
            DecimalFormat df = new DecimalFormat("0.00");
            String result = df.format(similarity);

            // 写入结果文件
            FileUtils.writeFile(outputFilePath, result);

            System.out.println("查重完成，结果已写入: " + outputFilePath);

        } catch (Exception e) {
            System.err.println("程序运行错误: " + e.getMessage());
            System.exit(1); // 退出程序
        }
    }
}