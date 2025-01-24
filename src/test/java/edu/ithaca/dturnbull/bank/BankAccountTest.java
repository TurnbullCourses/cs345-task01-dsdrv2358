package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance(), 0.001);
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance(), 0.001);
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));
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
        assertFalse(BankAccount.isEmailValid("abc#def@mail.com")); // special character
        assertFalse(BankAccount.isEmailValid(".abc@mail.com")); // dot at the beginning of the local part
        assertFalse(BankAccount.isEmailValid("abc..def@mail.com")); // double dot
        assertFalse(BankAccount.isEmailValid("abc.def@mail.c")); //too short domain
        assertFalse(BankAccount.isEmailValid("abc.def@mail#archive.com")); // special character in domain
        assertFalse(BankAccount.isEmailValid("abc.def@mail")); // no domain
        assertFalse(BankAccount.isEmailValid("abc.def@mail..com")); // double dot in domain
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance(), 0.001);
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
    }

}