package ee.ttu.algoritmid.fibonacci;

import java.math.BigInteger;

public class AL01A {

    /**
     * Compute the Fibonacci sequence number.
     * @param n The number of the sequence to compute.
     * @return The n-th number in Fibonacci series.
     */
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

    public static void main(String[] args) {
        System.out.println(iterativeF(45));
    }
}