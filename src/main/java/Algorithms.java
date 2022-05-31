import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Algorithms {
    public static void main(String[] args) {
        final float initialAmount = 100000;
        List<Integer> arbitrage;
        List<ArrayList<Float>> quotesList = new ArrayList<ArrayList<Float>>();
        Scanner scanner = new Scanner(System.in);
        int numberOfQuotes = scanner.nextInt();
        if (numberOfQuotes > 1000 && numberOfQuotes < 1){
            System.out.println("Les devis doivent être de 1 à 10000");
            System.exit(1);
        }
        for (int i = 0; i <= numberOfQuotes - 1; i++) {
            List<Float> quotes = new ArrayList<Float>();
            for (int j = 0; j < 3; j++) {
                quotes.add(scanner.nextFloat());
            }
            quotesList.add((ArrayList<Float>) quotes);
        }

        arbitrage = Algorithms(initialAmount, quotesList);

        for (Integer profit : arbitrage) {
            System.out.println(profit);
        }
    }

    private static List<Integer> Algorithms(float amount, List<ArrayList<Float>> quotesList) {
        int exchangeCount = 0;
        float initialAmount = amount;
        List<Integer> arbitrage = new ArrayList<Integer>();
        for (ArrayList<Float> quote: quotesList) {
            for (int i = 0; i <= quote.size() - 1 && exchangeCount <= 3; i++) {
                amount = amount / quote.get(i);
                exchangeCount++;
            }


        float result = amount - initialAmount;
        if (result > 0)
            arbitrage.add((int) result);
        else
            arbitrage.add(0);
        }
        return arbitrage;
    }
}