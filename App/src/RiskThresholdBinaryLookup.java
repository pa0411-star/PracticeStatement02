import java.util.*;

public class RiskThresholdBinaryLookup {

    // -----------------------
    // Linear Search
    // -----------------------
    public static void linearSearch(int[] risks, int threshold) {
        int comparisons = 0;
        boolean found = false;
        for (int i = 0; i < risks.length; i++) {
            comparisons++;
            if (risks[i] == threshold) {
                System.out.println("Linear: threshold " + threshold + " found at index " + i + " (" + comparisons + " comparisons)");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Linear: threshold " + threshold + " not found (" + comparisons + " comparisons)");
        }
    }

    // -----------------------
    // Binary Search for exact/floor/ceiling
    // -----------------------
    public static void binarySearchFloorCeiling(int[] risks, int target) {
        int low = 0, high = risks.length - 1;
        int comparisons = 0;
        int floor = Integer.MIN_VALUE;
        int ceiling = Integer.MAX_VALUE;

        while (low <= high) {
            comparisons++;
            int mid = (low + high) / 2;
            if (risks[mid] == target) {
                floor = ceiling = risks[mid];
                System.out.println("Binary: threshold " + target + " found at index " + mid + " (" + comparisons + " comparisons)");
                return;
            } else if (risks[mid] < target) {
                floor = risks[mid];  // largest ≤ target
                low = mid + 1;
            } else {
                ceiling = risks[mid]; // smallest ≥ target
                high = mid - 1;
            }
        }

        System.out.println("Binary floor(" + target + "): " + (floor == Integer.MIN_VALUE ? "none" : floor) +
                ", ceiling: " + (ceiling == Integer.MAX_VALUE ? "none" : ceiling) +
                " (" + comparisons + " comparisons)");
    }

    // -----------------------
    // Main method
    // -----------------------
    public static void main(String[] args) {
        int[] risks = {10, 25, 50, 100};  // sorted risk bands
        int threshold = 30;

        // Linear search
        linearSearch(risks, threshold);

        // Binary search floor/ceiling
        binarySearchFloorCeiling(risks, threshold);
    }
}