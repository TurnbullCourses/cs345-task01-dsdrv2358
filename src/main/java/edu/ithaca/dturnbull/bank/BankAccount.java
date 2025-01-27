package edu.ithaca.dturnbull.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email)){
            this.email = email;
            this.balance = startingBalance;
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     * 
     * If the amount is negative or 0, throw an IllegalArgumentException
     * If the amount is greater than the balance, throw an InsufficientFundsException
     * 
     * Equivalence partitions include negatives, zero (which would also be a border case), a legitimate
     * positive amount which the user has in their account, and a positive amount which the user does not have
     * 
     * Border cases would include the aforementioned zero, negative .01, positive .01, the balance minus .01,
     * the balance itself, and the balance plus .01
     */
    public void withdraw (double amount) throws InsufficientFundsException{
        if (amount <= balance){
            balance -= amount;
        }
        else {
            throw new InsufficientFundsException("Not enough money");
        }
    }


    public static boolean isEmailValid(String email){
        var emailRegex = "(?=.*\\w@.*)(?!.*\\.\\..*)^\\w[\\w-_.]*@[\\w-]+\\.\\w\\w+";
        
        if (email.matches(emailRegex)){
            return true;
        }
        else{
            return false;
        }
    }

    // returns true if the amount is positive and has two decimal points or less, and false otherwise
    public static boolean isAmountValid(double amount){
        String text = Double.toString(Math.abs(amount));
        int integerPlaces = text.indexOf('.');
        int decimalPlaces = text.length() - integerPlaces - 1;

        if (decimalPlaces > 2 || amount < 0){
            return false;
        }
        else{
            return true;
        }
    }
}