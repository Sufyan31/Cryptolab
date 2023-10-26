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
        BigInteger r = g.modPow(k, p);
        while (!k.gcd(p.subtract(BigInteger.ONE)).equals(BigInteger.ONE)) {
            k = generateRandomK(p);
            r = g.modPow(k, p);
        }
        BigInteger kInverse = k.modInverse(p.subtract(BigInteger.ONE));
        BigInteger s = aPrivate.multiply(r).add(message).mod(p.subtract(BigInteger.ONE)).multiply(kInverse).mod(p.subtract(BigInteger.ONE));
        System.out.println("Random private key (k): " + k);
        System.out.println("Signature (r, s): (" + r + ", " + s + ")");
        BigInteger v1 = g.modPow(message, p);
        BigInteger v2 = r.modPow(s, p);
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
}
