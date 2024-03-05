package bank;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BankAccountTest {

    BankAccount bank;

    @BeforeEach
    void setup() {
        bank = new BankAccount(100) ;
    }


}
