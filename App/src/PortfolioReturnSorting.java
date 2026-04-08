import java.util.*;

class Asset {
    String symbol;
    double returnRate;  // in percentage
    double volatility;  // optional, for quick sort tie-breaker

    public Asset(String symbol, double returnRate, double volatility) {
        this.symbol = symbol;
        this.returnRate = returnRate;
        this.volatility = volatility;
    }

    @Override
    public String toString() {
        return symbol + ":" + returnRate + "%";
    }
}

public class PortfolioReturnSorting {

    // -------------------------------
    // Merge Sort by returnRate ASC (stable)
    // -------------------------------
    public static Asset[] mergeSortAsc(Asset[] assets) {
        if (assets.length <= 1) return assets;

        int mid = assets.length / 2;
        Asset[] left = Arrays.copyOfRange(assets, 0, mid);
        Asset[] right = Arrays.copyOfRange(assets, mid, assets.length);

        left = mergeSortAsc(left);
        right = mergeSortAsc(right);

        return merge(left, right);
    }

    private static Asset[] merge(Asset[] left, Asset[] right) {
        Asset[] result = new Asset[left.length + right.length];
        int i = 0, j = 0, k = 0;

        while (i < left.length && j < right.length) {
            if (left[i].returnRate <= right[j].returnRate) {  // stable
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
    // Quick Sort DESC by returnRate, tie-breaker volatility ASC
    // -------------------------------
    public static void quickSortDesc(Asset[] assets, int low, int high) {
        if (low < high) {
            int pi = partition(assets, low, high);
            quickSortDesc(assets, low, pi - 1);
            quickSortDesc(assets, pi + 1, high);
        }
    }

    private static int partition(Asset[] assets, int low, int high) {
        Asset pivot = assets[high];  // choose last element as pivot
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (assets[j].returnRate > pivot.returnRate ||
                    (assets[j].returnRate == pivot.returnRate && assets[j].volatility < pivot.volatility)) {
                i++;
                Asset temp = assets[i];
                assets[i] = assets[j];
                assets[j] = temp;
            }
        }

        Asset temp = assets[i + 1];
        assets[i + 1] = assets[high];
        assets[high] = temp;

        return i + 1;
    }

    // -------------------------------
    // Main Method
    // -------------------------------
    public static void main(String[] args) {
        Asset[] assets = {
                new Asset("AAPL", 12.0, 0.2),
                new Asset("TSLA", 8.0, 0.5),
                new Asset("GOOG", 15.0, 0.25),
                new Asset("MSFT", 12.0, 0.15)  // same return as AAPL
        };

        // --- Merge Sort ASC ---
        Asset[] mergeSorted = mergeSortAsc(assets);
        System.out.println("MergeSort ASC: " + Arrays.toString(mergeSorted));

        // --- Quick Sort DESC ---
        Asset[] quickSorted = Arrays.copyOf(assets, assets.length);
        quickSortDesc(quickSorted, 0, quickSorted.length - 1);
        System.out.println("QuickSort DESC: " + Arrays.toString(quickSorted));
    }
}