import org.testng.AssertJUnit;

public class Test2 {
    public static void main(String[] args) {

            double similarity = TextComparator.calculateSimilarityWithCache("hello", "hola");
            System.out.println("测试结果为："+similarity);
            AssertJUnit.assertEquals(similarity, TextComparator.calculateSimilarityWithCache("hello", "hola"));


    }

}
