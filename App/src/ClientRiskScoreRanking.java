import java.util.*;

class Client {
    String id;
    int riskScore;
    double accountBalance;

    public Client(String id, int riskScore, double accountBalance) {
        this.id = id;
        this.riskScore = riskScore;
        this.accountBalance = accountBalance;
    }

    public void display() {
        System.out.println(id + ": risk=" + riskScore + ", balance=" + accountBalance);
    }
}

public class ClientRiskScoreRanking {

    // -------------------------------
    // Bubble Sort by riskScore ASC
    // -------------------------------
    public static void bubbleSortAsc(Client[] clients) {
        int n = clients.length;
        int swaps = 0;

        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (clients[j].riskScore > clients[j + 1].riskScore) {
                    Client temp = clients[j];
                    clients[j] = clients[j + 1];
                    clients[j + 1] = temp;
                    swapped = true;
                    swaps++;
                    System.out.println("Swapped " + clients[j].id + " with " + clients[j + 1].id);
                }
            }
            if (!swapped) break; // early exit
        }

        System.out.println("Bubble Sort Completed. Total swaps: " + swaps);
    }

    // -------------------------------
    // Insertion Sort by riskScore DESC + accountBalance
    // -------------------------------
    public static void insertionSortDesc(Client[] clients) {
        int n = clients.length;
        for (int i = 1; i < n; i++) {
            Client key = clients[i];
            int j = i - 1;

            while (j >= 0 &&
                    (clients[j].riskScore < key.riskScore ||
                            (clients[j].riskScore == key.riskScore && clients[j].accountBalance < key.accountBalance))) {

                clients[j + 1] = clients[j]; // shift
                j--;
            }
            clients[j + 1] = key;
        }
        System.out.println("Insertion Sort Completed.");
    }

    // -------------------------------
    // Top N Risk Clients
    // -------------------------------
    public static List<Client> topNRiskClients(Client[] clients, int n) {
        List<Client> topClients = new ArrayList<>();
        for (int i = 0; i < Math.min(n, clients.length); i++) {
            topClients.add(clients[i]);
        }
        return topClients;
    }

    // -------------------------------
    // Main Method
    // -------------------------------
    public static void main(String[] args) {
        Client[] clients = {
                new Client("clientC", 80, 5000.0),
                new Client("clientA", 20, 15000.0),
                new Client("clientB", 50, 10000.0),
                new Client("clientD", 80, 7000.0)
        };

        // --- Bubble Sort ASC ---
        Client[] bubbleArray = Arrays.copyOf(clients, clients.length);
        bubbleSortAsc(bubbleArray);

        System.out.println("\nBubble Sorted (risk ASC):");
        for (Client c : bubbleArray) c.display();

        // --- Insertion Sort DESC + accountBalance ---
        Client[] insertionArray = Arrays.copyOf(clients, clients.length);
        insertionSortDesc(insertionArray);

        System.out.println("\nInsertion Sorted (risk DESC + balance):");
        for (Client c : insertionArray) c.display();

        // --- Top 3 Risk Clients ---
        List<Client> topClients = topNRiskClients(insertionArray, 3);
        System.out.println("\nTop 3 Risk Clients:");
        for (Client c : topClients) {
            System.out.println(c.id + "(" + c.riskScore + ")");
        }
    }
}