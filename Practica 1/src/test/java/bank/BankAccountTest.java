package bank;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.lang.invoke.LambdaMetafactory;

import static org.junit.jupiter.api.Assertions.*;

public class BankAccountTest {

    BankAccount bank;

    @BeforeEach
    void setup() {
        bank = new BankAccount(100) ;
    }

    @Test
    @DisplayName("Retira dinero que posee")
    public void Withdraw_EnoughMoney_ReturnTrue(){
        int amount = 50;

        boolean res = this.bank.withdraw(amount);

        assertTrue(res);
    }

    @Test
    @DisplayName("Retira más dinero que posee")
    public void Withdraw_MoreMoney_ReturnFalse(){
        int amount = 150;

        boolean res = this.bank.withdraw(amount);

        assertFalse(res);
    }

    @Test
    @DisplayName("Deposita dinero positivo")
    public void Deposit_PositiveMoney_ReturnNum(){
        int amount = 150;
        int money = this.bank.getBalance();

        int res = this.bank.deposit(amount);

        assertEquals(money + amount,this.bank.getBalance());
    }

    @Test
    @DisplayName("Deposita dinero negativo")
    public void Deposit_NegativeMoney_ReturnException (){
        int amount = -150;

        assertThrows(IllegalArgumentException.class,() -> this.bank.deposit(amount));
    }

    @Test
    @DisplayName("Paga dinero")
    public void Payment_Money_ReturnEqual (){
        double total_amount = 150;
        double interest = 0.001;
        int npayments = 2;
        double expected_value = total_amount*(interest*Math.pow((1+interest), npayments)/(Math.pow((1+interest), npayments)-1));

        double final_value = this.bank.payment(total_amount,interest,npayments);

        assertEquals(expected_value,final_value);
    }

    @Test
    @DisplayName("Calcula pago pendiente")
    public void Pending_Money_ReturnNum (){
        double amount = 10000;
        double interest = 0.001;
        int months = 12;
        int month = 2;

        double pending = this.bank.pending(amount,interest,months,month);

        assertEquals(8342, Math.round(pending));
    }

    @ParameterizedTest
    @DisplayName("Calcula pago pendiente datos negativo")
    @CsvSource({
            "-1000.0,0.001,12,2",
            "1000.0,-0.001,12,2",
            "1000.0,0.001,-12,2",
            "1000.0,0.001,12,-2",
    })
    public void Pending_NegativeData_ReturnException (double amount,double interest, int payments, int month){
        assertThrows(IllegalArgumentException.class,() -> this.bank.pending(amount,interest,payments,month));
    }

    @ParameterizedTest
    @DisplayName("Calcula pago datos negativo")
    @CsvSource({
            "-1000.0,0.001,12",
            "1000.0,-0.001,12",
            "1000.0,0.001,-12",
            "0.0,0.001,12",
    })
    public void Payment_NegativeData_ReturnException (double amount,double interest, int payments){
        assertThrows(IllegalArgumentException.class,() -> this.bank.payment(amount,interest,payments));
    }

}
