package market;

import java.util.ArrayList;
import edu.rutgers.cs112.LL.LLNode; 


public class StockMarket {
 
    private Stock[] stockData; // All stock data for the market
    private LLNode<Holding> portfolio; // User's current stock holdings
    private LLNode<Transaction> transactions; // History of all transactions
     
    private double balance; 
    private int currentDay; 
    private int transactionCount; 
    private int numHoldings;

   
    public StockMarket(double initialBalance) {
        this.stockData = null;
        this.portfolio = null;
        this.transactions = null;
        this.balance = initialBalance;
        this.currentDay = 0;
        this.transactionCount = 0;
        this.numHoldings = 0;
    }
   
   
    public void readStockData(String inputFile) {
        // WRITE YOUR CODE HERE 
        StdIn.setFile(inputFile);
        int num = Integer.parseInt(StdIn.readLine());
        stockData = new Stock[num];
        int stockIndex = 0;

        for(int i = 0; i < num; i++){
            String line = StdIn.readLine();
            String[] parts = line.split(",");
            String symbol = parts[0];

            double[] prices = new double[parts.length - 1];
            for(int j = 1; j < parts.length; j++){
                prices[j - 1] = Double.parseDouble(parts[j]);
            }

            Stock stock = new Stock(symbol, prices);
            stockData[stockIndex] = stock;
            stockIndex++;
        }
        
    }
 
  
    public boolean nextDay() { 
        // WRITE YOUR CODE HERE 
        currentDay++;
        if(currentDay >= stockData[0].getPriceHistory().length){
            return false;
        }
        return true;
    }

   
    public void updateBalance(int quantity, double pricePerShare, String type) {
        // WRITE YOUR CODE HERE
        double totalValue  = quantity * pricePerShare;
        if(type.equals("BUY")){
            balance = balance - totalValue ;
        }
        else if(type.equals("SELL")){
            balance = balance + totalValue;
        }
    }

    
    public boolean buyStock(String stockID, int qty) {
        // WRITE YOUR CODE HERE
        if(qty <= 0){
            return false;
        }
        for(int i = 0; i < stockData.length; i++){
           if(stockData[i].getStockID() == stockID){ 
               double pricePerShare = stockData[i].getPriceHistory()[currentDay];
               double totalCost = pricePerShare * qty;
               if(balance < totalCost){
                   return false;
               }
               Holding newHolding = new Holding(getNextHoldingID(), stockData[i], qty, totalCost, currentDay);
               LLNode<Holding> newNode = new LLNode<>(newHolding);
               newNode.setNext(portfolio);
               portfolio = newNode;
               numHoldings++;
               updateBalance(qty, pricePerShare, Transaction.BUY);
               addTransaction(newHolding, qty, currentDay, Transaction.BUY);
               transactionCount++;
               return true;
           }
        }
        return false;
    }

   
    public boolean sellStock(String stockID, int qty) {
        // if they want a negative or zero quantity, return false
        if(qty <= 0){
            return false;
        }

        //adding all the matching stock IDs to a list
        LLNode<Holding> current = portfolio;
        ArrayList<LLNode<Holding>> relevantHoldings = new ArrayList<>();
        while(current != null){
            if(current.getData().getStock().getStockID().equals(stockID)){
                relevantHoldings.add(current);
            }
            current = current.getNext();
        }

        //if there was not matching holding return false
        if(relevantHoldings.isEmpty()){
            return false;
        }

        //stores the highest value holding in the Holding called high
        Holding high = relevantHoldings.get(0).getData();
        for(int i = 0; i < relevantHoldings.size(); i++){
            if(relevantHoldings.get(i).getData().getStock().getPriceHistory()[currentDay] > high.getStock().getPriceHistory()[currentDay]){
                high = relevantHoldings.get(i).getData();
            }
        }


        //if the holding has more quantity than the disire to sell, updates the quantity and balance
        //also update the balance and the transaction history 
        if(high.getQuantity() > qty){
            double pricePerShare = high.getStock().getPriceHistory()[currentDay];
            high.setQuantity(high.getQuantity() - qty);
            updateBalance(qty, pricePerShare, Transaction.SELL);
            addTransaction(high, qty, currentDay, Transaction.SELL);
            transactionCount++;
        }

        //if the holding has less than or equal to, remove the holding, update the transaction and the balance
        //only if there was less than, recursively call sellStock to sell the remaining quantity
        else if(high.getQuantity() <= qty){
            if(high == portfolio.getData()){ 
                portfolio = portfolio.getNext(); 
            }
            else{
                //removes the holding fromt the list
                while( portfolio.getNext() != null){
                    if(portfolio.getNext().getData().equals(high)){
                        portfolio.setNext(portfolio.getNext().getNext());
                        break;
                    }
                    portfolio = portfolio.getNext();
                }
            }

            //update the balance and add the transaction
            Holding temp = high;
            double pricePerShare = temp.getStock().getPriceHistory()[currentDay];
            updateBalance(temp.getQuantity(), pricePerShare, Transaction.SELL);
            addTransaction(temp, temp.getQuantity(), currentDay, Transaction.SELL);
            transactionCount++;
            qty -= temp.getQuantity(); 
            numHoldings--;

            //recursively call sellStock to sell the remaining quantity
            sellStock(stockID, qty);
        }
        
        //Return true at the end, if we successful in selling ANY stock
        return true;
    }


   
    public void addTransaction(Holding s, int quantity, int transactionDay, String type) {
        // WRITE YOUR CODE HERE
        Transaction current = new Transaction(transactionCount, transactionDay, s.getStock(), quantity, s.getStock().getPriceHistory()[transactionDay], quantity * s.getStock().getPriceHistory()[transactionDay], type);
        LLNode<Transaction> newHead  = new LLNode<>(current);
        newHead.setNext(transactions);
        transactions = newHead;
        transactionCount ++;
    }
    
   
    public double calculateROI(String stockID) {
        // WRITE YOUR CODE HERE
        LLNode<Holding> current = portfolio;
        double totalCost =0.0;
        double totalValue = 0.0;
        while(current != null){
            if(current.getData().getStock().getStockID().equals(stockID)){
                totalCost += current.getData().getQuantity() * current.getData().getStock().getPriceHistory()[current.getData().getPurchaseDay()];
                totalValue += current.getData().getStock().getPriceHistory()[currentDay] * current.getData().getQuantity();
                if(totalCost == 0.0){
                    return 0.0;
                }
                return ((totalValue - totalCost) / totalCost) * 100;
            }
            current = current.getNext();
        }
        return 0;
    }

    
    public String[] findExtrema() { 
        // WRITE YOUR CODE HERE
        Holding min;
        Holding max;
        if(portfolio == null){
            return new String[]{"", ""};
        }
        else{
            LLNode<Holding> current = portfolio;
            min = current.getData();
            max = current.getData();
            while(current != null){
                //((quantity * currentPrice) – cost)
                double currentProfit = ((current.getData().getStock().getPriceHistory()[currentDay] * current.getData().getQuantity()) - current.getData().getStock().getPriceHistory()[current.getData().getPurchaseDay()]);
                double minProfit = ((min.getStock().getPriceHistory()[currentDay] * min.getQuantity()) - min.getStock().getPriceHistory()[min.getPurchaseDay()]) * min.getQuantity();
                double maxProfit = ((max.getStock().getPriceHistory()[currentDay] * max.getQuantity()) - max.getStock().getPriceHistory()[max.getPurchaseDay()]) * max.getQuantity();
                if(currentProfit < minProfit){
                    min = current.getData();
                }
                if(currentProfit > maxProfit){
                    max = current.getData();
                }
                 current = current.getNext();
            }
            return new String[]{max.getStock().getStockID(), min.getStock().getStockID()};
        }
    }

    // Getter Methods
    public Stock[] getStockData() {  return stockData; } 
    public LLNode<Holding> getPortfolio() {  return portfolio; } 
    public LLNode<Transaction> getTransactions() {  return transactions; } 
    public double getBalance() {  return balance; } 
    public int getCurrentDay() {  return currentDay; } 
    public int getTransactionCount() {  return transactionCount; } 
    public int getHoldings() {  return numHoldings; }

    public int getNextHoldingID() {
        LLNode<Transaction> current = transactions;
        int ID = 1;
        while (current != null) {
            if (current.getData().getType() == Transaction.BUY) {
                ID++;
            }
            current = current.getNext();
        }
        return ID;
    }
}