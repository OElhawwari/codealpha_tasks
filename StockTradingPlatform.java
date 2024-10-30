import java.util.HashMap;
import java.util.Scanner;

class Stock {
    String symbol;
    String name;
    double price;

    Stock(String symbol, String name, double price) {
        this.symbol = symbol;
        this.name = name;
        this.price = price;
    }
}

class Portfolio {
    HashMap<String, Integer> holdings = new HashMap<>();
    double balance;

    Portfolio(double initialBalance) {
        this.balance = initialBalance;
    }

    void buyStock(String symbol, int quantity, double price) {
        double totalCost = price * quantity;
        if (totalCost > balance) {
            System.out.println("Insufficient balance to complete the purchase.");
        } else {
            balance -= totalCost;
            holdings.put(symbol, holdings.getOrDefault(symbol, 0) + quantity);
            System.out.println("Purchased " + quantity + " shares of " + symbol);
        }
    }

    void sellStock(String symbol, int quantity, double price) {
        int availableShares = holdings.getOrDefault(symbol, 0);
        if (quantity > availableShares) {
            System.out.println("You don't own enough shares to sell.");
        } else {
            balance += price * quantity;
            holdings.put(symbol, availableShares - quantity);
            if (holdings.get(symbol) == 0) {
                holdings.remove(symbol);
            }
            System.out.println("Sold " + quantity + " shares of " + symbol);
        }
    }

    void viewPortfolio(HashMap<String, Stock> market) {
        System.out.println("\nYour Portfolio:");
        if (holdings.isEmpty()) {
            System.out.println("You don't own any stocks yet.");
        } else {
            double totalValue = 0;
            for (String symbol : holdings.keySet()) {
                int quantity = holdings.get(symbol);
                double stockPrice = market.get(symbol).price;
                double stockValue = stockPrice * quantity;
                totalValue += stockValue;
                System.out.println(symbol + ": " + quantity + " shares, Current Price: $" + stockPrice + ", Total Value: $" + stockValue);
            }
            System.out.println("Total Portfolio Value: $" + totalValue);
        }
        System.out.println("Cash Balance: $" + balance);
    }
}

public class StockTradingPlatform {
    static HashMap<String, Stock> market = new HashMap<>();
    static Portfolio portfolio;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeMarket();
        portfolio = new Portfolio(10000.00);

        while (true) {
            System.out.println("\nWelcome to the Simulated Stock Trading Platform");
            System.out.println("1. View Market Data");
            System.out.println("2. Buy Stocks");
            System.out.println("3. Sell Stocks");
            System.out.println("4. View Portfolio");
            System.out.println("5. Exit");
            System.out.print("Please select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewMarketData();
                    break;
                case 2:
                    buyStock();
                    break;
                case 3:
                    sellStock();
                    break;
                case 4:
                    portfolio.viewPortfolio(market);
                    break;
                case 5:
                    System.out.println("Exiting the platform. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public static void initializeMarket() {
        market.put("AAPL", new Stock("AAPL", "Apple Inc.", 150.00));
        market.put("GOOGL", new Stock("GOOGL", "Alphabet Inc.", 2800.00));
        market.put("TSLA", new Stock("TSLA", "Tesla Inc.", 700.00));
        market.put("AMZN", new Stock("AMZN", "Amazon.com Inc.", 3500.00));
        market.put("MSFT", new Stock("MSFT", "Microsoft Corp.", 290.00));
    }

    public static void viewMarketData() {
        System.out.println("\nMarket Data:");
        for (Stock stock : market.values()) {
            System.out.println(stock.symbol + ": " + stock.name + " - $" + stock.price);
        }
    }

    public static void buyStock() {
        System.out.print("\nEnter stock symbol to buy: ");
        String symbol = scanner.nextLine().toUpperCase();

        if (market.containsKey(symbol)) {
            System.out.print("Enter quantity to buy: ");
            int quantity = scanner.nextInt();
            scanner.nextLine();
            Stock stock = market.get(symbol);
            portfolio.buyStock(symbol, quantity, stock.price);
        } else {
            System.out.println("Invalid stock symbol.");
        }
    }

    public static void sellStock() {
        System.out.print("\nEnter stock symbol to sell: ");
        String symbol = scanner.nextLine().toUpperCase();

        if (portfolio.holdings.containsKey(symbol)) {
            System.out.print("Enter quantity to sell: ");
            int quantity = scanner.nextInt();
            scanner.nextLine();
            Stock stock = market.get(symbol);
            portfolio.sellStock(symbol, quantity, stock.price);
        } else {
            System.out.println("You don't own any shares of " + symbol);
        }
    }
}