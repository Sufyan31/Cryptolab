import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class ElGamalSignature {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the prime number (p): ");
        BigInteger p = scanner.nextBigInteger();
        System.out.print("Enter the primitive root (g): ");
        BigInteger g = scanner.nextBigInteger();
        System.out.print("Enter Alice's private key: ");
        BigInteger aPrivate = scanner.nextBigInteger();
        System.out.print("Enter the message to be signed: ");
        BigInteger message = scanner.nextBigInteger();
        BigInteger k = generateRandomK(p);
        BigInteger r = sq_mul(g, k, p);
        while (!k.gcd(p.subtract(BigInteger.ONE)).equals(BigInteger.ONE)) {
            k = generateRandomK(p);
            r = sq_mul(g, k, p);
        }
        BigInteger kInverse = inverse(k, p.subtract(BigInteger.ONE));
        BigInteger s = aPrivate.multiply(r).add(message).mod(p.subtract(BigInteger.ONE)).multiply(kInverse).mod(p.subtract(BigInteger.ONE));
        System.out.println("Random private key (k): " + k);
        System.out.println("Signature (r, s): (" + r + ", " + s + ")");
        BigInteger v1 = sq_mul(g, message, p);
        BigInteger v2 = sq_mul(r, s, p);
        BigInteger v = v1.multiply(v2).mod(p);
        boolean isValid = v.equals(r);
        System.out.println("Verification result: " + isValid);
        scanner.close();
    }

    private static BigInteger generateRandomK(BigInteger p) {
        Random random = new Random();
        BigInteger max = p.subtract(BigInteger.ONE);
        BigInteger k;
        do {
            k = new BigInteger(max.bitLength(), random);
        } while (k.compareTo(max) >= 0 || k.compareTo(BigInteger.ONE) <= 0);
        return k;
    }

    public static BigInteger inverse(BigInteger a, BigInteger n) {
        BigInteger m0 = n;
        BigInteger x0 = BigInteger.ZERO;
        BigInteger x1 = BigInteger.ONE;

        if (n.equals(BigInteger.ONE)) {
            return BigInteger.ZERO;
        }

        while (a.compareTo(BigInteger.ONE) > 0) {
            BigInteger[] divAndRemainder = a.divideAndRemainder(n);
            BigInteger q = divAndRemainder[0];
            BigInteger t = n;

            n = divAndRemainder[1];
            a = t;
            t = x0;
            x0 = x1.subtract(q.multiply(x0));
            x1 = t;
        }

        if (x1.compareTo(BigInteger.ZERO) < 0) {
            x1 = x1.add(m0);
        }

        return x1;
    }

    public static BigInteger sq_mul(BigInteger a, BigInteger x, BigInteger n) {
        BigInteger y = BigInteger.ONE;
        while (x.compareTo(BigInteger.ZERO) > 0) {
            BigInteger[] divAndRemainder = x.divideAndRemainder(BigInteger.valueOf(2));
            BigInteger rem = divAndRemainder[1];

            if (divAndRemainder[0].compareTo(BigInteger.ZERO) > 0) {
                if (rem.equals(BigInteger.ONE)) {
                    y = y.multiply(a).mod(n);
                }
                a = a.multiply(a).mod(n);
            } else {
                y = y.multiply(a).mod(n);
            }
            x = x.divide(BigInteger.valueOf(2));
        }
        return y;
    }
}
