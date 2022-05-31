import java.text.DecimalFormat;
import java.util.*;

import static java.lang.System.out;

public class BetterSpendingRates {
    private List<List<Integer>> betterSpending = new ArrayList<List<Integer>>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int portfolio = scanner.nextInt();
        if (portfolio > 100000 || portfolio <1)
            callError();
        float interestRate = scanner.nextFloat() / 100;
        if (interestRate >= 100 || interestRate <= 0)
            callError();
        int time = scanner.nextInt();
        if (time >= 20 || time < 1)
            callError();
        List<Integer> spendingRate = new ArrayList<Integer>();
        int threshold = scanner.nextInt();
        if (threshold > 15 || threshold < 1)
            callError();

        for (int i = 0; i < time; i++) {
            spendingRate.add(scanner.nextInt());
        }

        BetterSpendingRates betterSpendingRates = new BetterSpendingRates();
        System.out.println(betterSpendingRates.findCurrentTotalIncome(portfolio, interestRate, time, spendingRate));
        betterSpendingRates.findBetterSpendingCombinations(threshold, spendingRate);
        betterSpendingRates.findBetterSpendingRates(portfolio, interestRate, time, spendingRate, threshold);
    }

    private void findBetterSpendingRates(int portfolio, float interestRate, int time, List<Integer> spendingRate, int threshold) {
        int k = 0;
        List<Integer> illeagls = new ArrayList<Integer>();
        for (List<Integer> spending : this.betterSpending){
            boolean[] sameCombo = new boolean[spendingRate.size()];
            Arrays.fill(sameCombo, false);
            for (int i = 0; i < spending.size(); i++) {
                if (!((spending.get(i) >= (spendingRate.get(i) - threshold)) && (spending.get(i) <= (spendingRate.get(i) + threshold)))){
                    illeagls.add(k);
                    break;
                }
            }
            k++;
        }

        for (int remove : illeagls) {
            this.betterSpending.remove(remove);
        }

        HashMap<String, Double> map = new HashMap<String, Double>();
        for (List<Integer> combo : this.betterSpending) {
            String key =  " - ";
            for (int part : combo) {
                key += part + " ";
            }
            map.put(key, findCurrentTotalIncome(portfolio, interestRate, time, combo));
        }

        Set<Map.Entry<String, Double>> set = map.entrySet();
        List<Map.Entry<String, Double>> list = new ArrayList<Map.Entry<String, Double>>(set);
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        for (int i = 0; i < 3; i++) {
            String[] elements = list.get(i).toString().split("=");
            System.out.println(elements[1] + elements[0]);
        }
    }

    private void findBetterSpendingCombinations(int threshold, List<Integer> spendingRate) {
        int totalSpent = 0;
        for (int s : spendingRate) {
            totalSpent += s;
        }
        List<Integer> possibles = new ArrayList<Integer>();
        List<Integer> partial = new ArrayList<Integer>();
        for (int i = 0; i < spendingRate.size(); i++) {
            for (int j = spendingRate.get(i) - threshold; j <= spendingRate.get(i) + threshold; j++) {
                possibles.add(j);
            }
        }
        findCombinations(possibles, totalSpent, partial);
    }

    private void findCombinations(List<Integer> possibles, int totalSpent, List<Integer> partial) {
        int s = 0;
        for (int x : partial) {
            s += x;
        }
        if (s == totalSpent)
            this.betterSpending.add(partial);
        if (s >= totalSpent)
            return;
        for(int i=0;i<possibles.size();i++) {
            ArrayList<Integer> remaining = new ArrayList<Integer>();
            int n = possibles.get(i);
            for (int j=i+1; j<possibles.size();j++) remaining.add(possibles.get(j));
            ArrayList<Integer> partial_rec = new ArrayList<Integer>(partial);
            partial_rec.add(n);
            findCombinations(remaining,totalSpent,partial_rec);
        }
    }

    private double findCurrentTotalIncome(int portfolio, float interestRate, int time, List<Integer> spendingRate) {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(3);
        float incomeGenerated = 0.0f;
        for (int i = 0; i < time; i++) {
            int spent = 1;
            for (int j = i; !(j == 0) ; j--)
                spent = spent * (100 - spendingRate.get(j - 1));
            incomeGenerated += ((portfolio * (Math.pow((1 + interestRate), i + 1)) * spendingRate.get(i) * spent) / Math.pow(100, i + 1));
        }
        return Math.round(incomeGenerated * 1000d) / 1000d;
    }

    private static void callError() {
        out.println("Constraints error.....");
        System.exit(1);
    }
}
