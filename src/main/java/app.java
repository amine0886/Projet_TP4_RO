import java.util.Scanner;

public class app {

    private static int length;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numbers = scanner.nextInt();
        int[] digits = new int[numbers];
        for (int i = 0; i < numbers; i++) {
            digits[i] = scanner.nextInt();
        }
        System.out.println("Answer: " + getLonelyInteger(digits));
    }

    private static int getLonelyInteger(int[] digits) {
        int culprit = -1;
        if (digits.length == 1)
            return digits[0];
        else if (digits.length % 2 == 1)
            culprit = findCulprit(digits);
        return culprit;
    }

    private static int findCulprit(int[] digits) {

        for (int i = 0; i < digits.length; i++) {
            int count = 0;
            for (int j = 0; j < digits.length; j++) {
                if (digits[i] == digits[j])
                    count++;
            }
            if (count != 2)
                return digits[i];
        }
        return 0;
    }
}
