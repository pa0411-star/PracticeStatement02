import java.util.*;

class Trade {
    String id;
    int volume;

    public Trade(String id, int volume) {
        this.id = id;
        this.volume = volume;
    }

    @Override
    public String toString() {
        return id + ":" + volume;
    }
}

public class HistoricalTradeVolumeAnalysis {

    // -------------------------------
    // Merge Sort ASC (stable)
    // -------------------------------
    public static Trade[] mergeSortAsc(Trade[] trades) {
        if (trades.length <= 1) return trades;

        int mid = trades.length / 2;
        Trade[] left = Arrays.copyOfRange(trades, 0, mid);
        Trade[] right = Arrays.copyOfRange(trades, mid, trades.length);

        left = mergeSortAsc(left);
        right = mergeSortAsc(right);

        return merge(left, right);
    }

    private static Trade[] merge(Trade[] left, Trade[] right) {
        Trade[] result = new Trade[left.length + right.length];
        int i = 0, j = 0, k = 0;

        while (i < left.length && j < right.length) {
            if (left[i].volume <= right[j].volume) {
                result[k++] = left[i++];
            } else {
                result[k++] = right[j++];
            }
        }

        while (i < left.length) result[k++] = left[i++];
        while (j < right.length) result[k++] = right[j++];

        return result;
    }

    // -------------------------------
    // Quick Sort DESC (in-place)
    // -------------------------------
    public static void quickSortDesc(Trade[] trades, int low, int high) {
        if (low < high) {
            int pi = partition(trades, low, high);
            quickSortDesc(trades, low, pi - 1);
            quickSortDesc(trades, pi + 1, high);
        }
    }

    private static int partition(Trade[] trades, int low, int high) {
        Trade pivot = trades[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (trades[j].volume >= pivot.volume) { // DESC order
                i++;
                Trade temp = trades[i];
                trades[i] = trades[j];
                trades[j] = temp;
            }
        }

        Trade temp = trades[i + 1];
        trades[i + 1] = trades[high];
        trades[high] = temp;

        return i + 1;
    }

    // -------------------------------
    // Merge two sorted Trade arrays
    // -------------------------------
    public static Trade[] mergeTwoSorted(Trade[] a, Trade[] b) {
        Trade[] result = new Trade[a.length + b.length];
        int i = 0, j = 0, k = 0;

        while (i < a.length && j < b.length) {
            if (a[i].volume <= b[j].volume) {
                result[k++] = a[i++];
            } else {
                result[k++] = b[j++];
            }
        }
        while (i < a.length) result[k++] = a[i++];
        while (j < b.length) result[k++] = b[j++];
        return result;
    }

    // -------------------------------
    // Compute total volume
    // -------------------------------
    public static int totalVolume(Trade[] trades) {
        int total = 0;
        for (Trade t : trades) total += t.volume;
        return total;
    }

    // -------------------------------
    // Main Method
    // -------------------------------
    public static void main(String[] args) {
        Trade[] trades = {
                new Trade("trade3", 500),
                new Trade("trade1", 100),
                new Trade("trade2", 300)
        };

        // --- Merge Sort ASC ---
        Trade[] mergeSorted = mergeSortAsc(trades);
        System.out.println("MergeSort ASC: " + Arrays.toString(mergeSorted));

        // --- Quick Sort DESC ---
        Trade[] quickSorted = Arrays.copyOf(trades, trades.length);
        quickSortDesc(quickSorted, 0, quickSorted.length - 1);
        System.out.println("QuickSort DESC: " + Arrays.toString(quickSorted));

        // --- Merge two sorted lists ---
        Trade[] morning = { new Trade("t1", 200), new Trade("t2", 400) };
        Trade[] afternoon = { new Trade("t3", 150), new Trade("t4", 350) };
        Trade[] mergedSession = mergeTwoSorted(mergeSortAsc(morning), mergeSortAsc(afternoon));
        System.out.println("Merged morning+afternoon: " + Arrays.toString(mergedSession));
        System.out.println("Total Volume: " + totalVolume(mergedSession));
    }
}