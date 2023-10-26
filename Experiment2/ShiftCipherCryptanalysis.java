import static java.lang.Character.isUpperCase;
import java.util.Scanner;

public class ShiftCipherCryptanalysis {
    public static void main(String[] args) {
        ShiftCipherCryptanalysis ob = new ShiftCipherCryptanalysis();
        Scanner sc = new Scanner(System.in);
        String s;
        System.out.println("Enter the encrypted string:");
        s = sc.next();
        ob.decrypt(s);
    }

    public void decrypt(String s) {
        for (int j = 0; j < 26; j++) {
            String ans = "";
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == ' ') {
                    ans = ans + s.charAt(i);
                    continue;
                }
                int n;
                if (isUpperCase(s.charAt(i))) {
                    n = s.charAt(i) - 65;
                } else {
                    n = s.charAt(i) - 97;
                }
                int en = (n - j) % 26;
                if (en < 0) {
                    en = 26 + en;
                }
                char q;
                if (isUpperCase(s.charAt(i))) {
                    q = (char) (en + 65);
                } else {
                    q = (char) (en + 97);
                }
                ans = ans + q;
            }
            System.out.println("Shift " + j + ": " + ans + "\n");
        }
    }
}
