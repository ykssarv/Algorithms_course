package ee.ttu.algoritmid.fibonacci;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class AL01B {

    /**
     * Estimate or find the exact time required to compute the n-th Fibonacci number.
     * @param n The n-th number to compute.
     * @return The time estimate or exact time in YEARS.
     */
    public static String timeToComputeRecursiveFibonacci(int n) {
        BigDecimal factor = BigDecimal.valueOf(15673.0 / 1134903170.0) ;
        BigDecimal factorInYears1 = factor.divide(new BigDecimal(1000 * 365), RoundingMode.HALF_UP);
        BigDecimal factorInYears = factorInYears1.divide(new BigDecimal(3600 * 24), RoundingMode.HALF_UP);
        String fibonacciNumber = iterativeF(n);
        BigDecimal result = factorInYears.multiply(new BigDecimal(fibonacciNumber));
        return "" + result;
    }

    /**
     * Compute the Fibonacci sequence number recursively.
     * (You need this in the timeToComputeRecursiveFibonacci(int n) function!)
     * @param n The n-th number to compute.
     * @return The n-th Fibonacci number as a string.
     */
    public static BigInteger recursiveF(int n) {
        if (n <= 1)
            return BigInteger.valueOf(n);
        return recursiveF(n - 1).add(recursiveF(n - 2));
    }

    public static void main(String[] args) {
        timeToComputeRecursiveFibonacci(45);
    }

    public static String iterativeF(int n) {
        if(n < 2) {
            return "" + n;
        }
        BigInteger numbers[] = new BigInteger[n];
        numbers[0] = BigInteger.ONE;
        numbers[1] = BigInteger.ONE;
        for(int i = 2; i < n ; i++) {
            numbers[i] = numbers[i-1].add(numbers[i-2]);
        }
        return "" + numbers[n-1];
    }
}