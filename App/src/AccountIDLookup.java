import java.util.*;

class Transaction {
    String accountId;
    double amount;
    String timestamp;

    public Transaction(String accountId, double amount, String timestamp) {
        this.accountId = accountId;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return accountId + ":" + amount + "@" + timestamp;
    }
}

public class AccountIDLookup {

    // -----------------------
    // Linear Search
    // -----------------------
    public static int linearFirstOccurrence(List<Transaction> logs, String target) {
        int comparisons = 0;
        for (int i = 0; i < logs.size(); i++) {
            comparisons++;
            if (logs.get(i).accountId.equals(target)) {
                System.out.println("Linear first '" + target + "' at index " + i + " (" + comparisons + " comparisons)");
                return i;
            }
        }
        System.out.println("Linear: '" + target + "' not found (" + comparisons + " comparisons)");
        return -1;
    }

    public static int linearLastOccurrence(List<Transaction> logs, String target) {
        int comparisons = 0;
        int lastIndex = -1;
        for (int i = 0; i < logs.size(); i++) {
            comparisons++;
            if (logs.get(i).accountId.equals(target)) {
                lastIndex = i;
            }
        }
        if (lastIndex != -1) {
            System.out.println("Linear last '" + target + "' at index " + lastIndex + " (" + comparisons + " comparisons)");
        } else {
            System.out.println("Linear: '" + target + "' not found (" + comparisons + " comparisons)");
        }
        return lastIndex;
    }

    // -----------------------
    // Binary Search (logs must be sorted by accountId)
    // Returns first occurrence index and count of duplicates
    // -----------------------
    public static void binarySearch(List<Transaction> logs, String target) {
        logs.sort(Comparator.comparing(t -> t.accountId));  // Ensure sorted
        int low = 0, high = logs.size() - 1;
        int comparisons = 0;
        int foundIndex = -1;

        while (low <= high) {
            comparisons++;
            int mid = (low + high) / 2;
            int cmp = logs.get(mid).accountId.compareTo(target);
            if (cmp == 0) {
                foundIndex = mid;
                break;
            } else if (cmp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        if (foundIndex == -1) {
            System.out.println("Binary: '" + target + "' not found (" + comparisons + " comparisons)");
            return;
        }

        // Count duplicates
        int count = 1;
        int left = foundIndex - 1;
        while (left >= 0 && logs.get(left).accountId.equals(target)) {
            count++;
            left--;
        }
        int right = foundIndex + 1;
        while (right < logs.size() && logs.get(right).accountId.equals(target)) {
            count++;
            right++;
        }

        System.out.println("Binary '" + target + "' at index " + foundIndex + " (" + comparisons + " comparisons), count=" + count);
    }

    // -----------------------
    // Main method
    // -----------------------
    public static void main(String[] args) {
        List<Transaction> logs = new ArrayList<>();
        logs.add(new Transaction("accB", 100.0, "10:00"));
        logs.add(new Transaction("accA", 50.0, "09:30"));
        logs.add(new Transaction("accB", 75.0, "10:15"));
        logs.add(new Transaction("accC", 200.0, "11:00"));

        // Linear search examples
        linearFirstOccurrence(logs, "accB");
        linearLastOccurrence(logs, "accB");

        // Binary search example
        binarySearch(logs, "accB");
    }
}