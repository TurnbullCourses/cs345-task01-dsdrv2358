package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.IllegalArgumentException;


class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance(), 0.001);

        assertEquals(0, (new BankAccount("a@b.com",0)).getBalance(), 0.001); // zero balance

        assertThrows(IllegalArgumentException.class, () -> (new BankAccount("a@b.com", -100)).getBalance()); // negative balance
        assertThrows(IllegalArgumentException.class, () -> (new BankAccount("a@b.com", 100.011)).getBalance()); // too many decimal places
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance(), 0.001); // Just a standard withdrawal

        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));  // too much money
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-100)); // negative amount
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(0)); // zero amount
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(100.01)); // border case too much money
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-.01)); // border case just barely negative
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(5.001)); // Too many decimal places

        bankAccount.withdraw(100); // border case, exactly all the money
        assertEquals(0, bankAccount.getBalance(), 0.001);

        BankAccount bankAccount2 = new BankAccount("a@c.com", 200);
        bankAccount2.withdraw(.01); //border case, just enough money for a withdrawal
    }



    @Test
    void isEmailValidTest(){
        assertTrue(BankAccount.isEmailValid( "a@b.com"));   // valid email address
        assertTrue(BankAccount.isEmailValid( "abc-d@mail.com")); //hyphen in the local part
        assertTrue(BankAccount.isEmailValid( "abc.def@mail.com"));  //dot in the local part
        assertTrue(BankAccount.isEmailValid( "abc@mail.com")); //no special characters, minimum length
        assertTrue(BankAccount.isEmailValid( "abc_def@mail.com")); //hyphen
        assertTrue(BankAccount.isEmailValid( "abc.def@mail.cc")); // minimmum domain length
        assertTrue(BankAccount.isEmailValid( "abc.def@mail-archive.com")); // hyphen in domain
        assertTrue(BankAccount.isEmailValid( "abc.def@mail.org")); // just a standard

        assertFalse(BankAccount.isEmailValid(""));         // empty string
        assertFalse(BankAccount.isEmailValid("abc-@mail.com")); // hyphen at the end of the local part
        assertFalse(BankAccount.isEmailValid("abc#def@mail.com")); // unallowed special character
        assertFalse(BankAccount.isEmailValid(".abc@mail.com")); // dot at the beginning of the local part
        assertFalse(BankAccount.isEmailValid("abc..def@mail.com")); // double dot
        assertFalse(BankAccount.isEmailValid("abc.def@mail.c")); //too short domain
        assertFalse(BankAccount.isEmailValid("abc.def@mail#archive.com")); // special character in domain
        assertFalse(BankAccount.isEmailValid("abc.def@mail")); // no domain
        assertFalse(BankAccount.isEmailValid("abc.def@mail..com")); // double dot in domain
        assertFalse( BankAccount.isEmailValid("aaa"));   // no @ sign
        assertFalse( BankAccount.isEmailValid("@a"));    // no character before @
        assertFalse( BankAccount.isEmailValid("a@"));    // no character after @
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance(), 0.001);
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
    
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("a@b.com",5.001)); // too many decimals
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("a@b.com",-.01)); // negative border case
        
        BankAccount bankAccount2 = new BankAccount("a@b.com", 0); // border case of zero balance
        assertEquals("a@b.com", bankAccount2.getEmail());
        assertEquals(0, bankAccount2.getBalance(), 0.001);

    }

    @Test
    void isAmountValidTest(){
        assertTrue(BankAccount.isAmountValid(100.1)); // Postive balance with one decimal place
        assertTrue(BankAccount.isAmountValid(100.01)); // Postive balance with two decimal places. Border case
        assertTrue(BankAccount.isAmountValid(0)); // Not negative border case. I took the liberty to say that zero is a valid balance
        assertFalse(BankAccount.isAmountValid(-100)); // Negative balance
        assertFalse(BankAccount.isAmountValid(100.001)); // Too many decimal places. Could be considered a border case
        assertFalse(BankAccount.isAmountValid(-.01)); // Negative border case
    }

}