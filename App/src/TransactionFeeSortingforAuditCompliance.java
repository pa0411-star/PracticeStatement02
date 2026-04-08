import java.util.*;

class Transaction {
    String id;
    double fee;
    String timestamp; // format HH:mm

    public Transaction(String id, double fee, String timestamp) {
        this.id = id;
        this.fee = fee;
        this.timestamp = timestamp;
    }

    public void display() {
        System.out.println(id + ": " + fee + " @ " + timestamp);
    }
}

public class TransactionFeeSortingforAuditCompliance {

    // -------------------------------
    // Bubble Sort (by fee)
    // -------------------------------
    public static void bubbleSort(List<Transaction> list) {
        int n = list.size();
        int swaps = 0;

        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;

            for (int j = 0; j < n - i - 1; j++) {
                if (list.get(j).fee > list.get(j + 1).fee) {
                    Collections.swap(list, j, j + 1);
                    swaps++;
                    swapped = true;
                }
            }

            if (!swapped) break; // early termination
        }

        System.out.println("Bubble Sort Completed. Swaps: " + swaps);
    }

    // -------------------------------
    // Insertion Sort (by fee + timestamp)
    // -------------------------------
    public static void insertionSort(List<Transaction> list) {
        for (int i = 1; i < list.size(); i++) {
            Transaction key = list.get(i);
            int j = i - 1;

            while (j >= 0 &&
                    (list.get(j).fee > key.fee ||
                            (list.get(j).fee == key.fee &&
                                    list.get(j).timestamp.compareTo(key.timestamp) > 0))) {

                list.set(j + 1, list.get(j)); // shift right
                j--;
            }

            list.set(j + 1, key);
        }

        System.out.println("Insertion Sort Completed.");
    }

    // -------------------------------
    // High Fee Detection
    // -------------------------------
    public static List<Transaction> findHighFee(List<Transaction> list) {
        List<Transaction> result = new ArrayList<>();
        for (Transaction t : list) {
            if (t.fee > 50) {
                result.add(t);
            }
        }
        return result;
    }

    // -------------------------------
    // Main Method
    // -------------------------------
    public static void main(String[] args) {

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("id1", 10.5, "10:00"));
        transactions.add(new Transaction("id2", 25.0, "09:30"));
        transactions.add(new Transaction("id3", 5.0, "10:15"));

        // Bubble Sort (Small batch)
        List<Transaction> bubbleList = new ArrayList<>(transactions);
        bubbleSort(bubbleList);

        System.out.println("\nBubble Sorted (by fee):");
        bubbleList.forEach(Transaction::display);

        // Insertion Sort (Medium batch)
        List<Transaction> insertionList = new ArrayList<>(transactions);
        insertionSort(insertionList);

        System.out.println("\nInsertion Sorted (fee + timestamp):");
        insertionList.forEach(Transaction::display);

        // High-fee outliers
        List<Transaction> outliers = findHighFee(transactions);

        System.out.println("\nHigh-fee Outliers (>50):");
        if (outliers.isEmpty()) {
            System.out.println("None");
        } else {
            outliers.forEach(Transaction::display);
        }
    }
}