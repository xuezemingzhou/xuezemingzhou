import utils.MathUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Fraction {
    int numerator;
    private int denominator;

    public Fraction(int numerator, int denominator) {
        if (denominator == 0) throw new IllegalArgumentException("Denominator cannot be zero");
        int gcd = MathUtils.gcd(numerator, denominator);
        this.numerator = numerator / gcd;
        this.denominator = denominator / gcd;
        if (this.denominator < 0) {
            this.numerator *= -1;
            this.denominator *= -1;
        }
    }

    public Fraction(String s) {
        Pattern pattern = Pattern.compile("^(-?\\d+)(?:'(\\d+)/(\\d+)|/(\\d+)|)$");
        Matcher matcher = pattern.matcher(s);
        if (!matcher.find()) throw new IllegalArgumentException("Invalid fraction format");
        if (matcher.group(2) != null) { // mixed number
            int whole = Integer.parseInt(matcher.group(1));
            int num = Integer.parseInt(matcher.group(2));
            int den = Integer.parseInt(matcher.group(3));
            this.numerator = whole * den + num;
            this.denominator = den;
        } else if (matcher.group(4) != null) { // simple fraction
            this.numerator = Integer.parseInt(matcher.group(1));
            this.denominator = Integer.parseInt(matcher.group(4));
        } else { // whole number
            this.numerator = Integer.parseInt(matcher.group(1));
            this.denominator = 1;
        }
        // Normalization same as above constructor
    }

    public Fraction add(Fraction other) {
        int num = this.numerator * other.denominator + other.numerator * this.denominator;
        int den = this.denominator * other.denominator;
        return new Fraction(num, den);
    }

    public Fraction subtract(Fraction other) {
        return this.add(new Fraction(-other.numerator, other.denominator));
    }

    public Fraction multiply(Fraction other) {
        return new Fraction(this.numerator * other.numerator, this.denominator * other.denominator);
    }

    public Fraction divide(Fraction other) {
        return this.multiply(new Fraction(other.denominator, other.numerator));
    }

    public boolean isNegative() {
        return numerator < 0;
    }

    public boolean isInteger() {
        return denominator == 1;
    }

    public int compareTo(Fraction other) {
        long thisVal = (long) this.numerator * other.denominator;
        long otherVal = (long) other.numerator * this.denominator;
        return Long.compare(thisVal, otherVal);
    }

    @Override
    public String toString() {
        if (denominator == 1) return String.valueOf(numerator);
        int whole = numerator / denominator;
        int num = Math.abs(numerator % denominator);
        if (whole != 0) return whole + "'" + num + "/" + denominator;
        return numerator + "/" + denominator;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Fraction)) return false;
        Fraction other = (Fraction) obj;
        return this.numerator == other.numerator && this.denominator == other.denominator;
    }
}