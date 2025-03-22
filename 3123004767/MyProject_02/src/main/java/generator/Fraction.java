package generator;

public class Fraction {
    private int numerator; // 分子
    private int denominator; // 分母

    public Fraction add(Fraction other) {
        int newDenominator = this.denominator * other.denominator;
        int newNumerator = this.numerator * other.denominator
                + other.numerator * this.denominator;
        return simplify(newNumerator, newDenominator);
    }

    private static Fraction simplify(int num, int den) {
        // 约分逻辑（使用欧几里得算法）
    }

    @Override
    public String toString() {
        // 处理带分数格式（如2’3/8）
    }
}
