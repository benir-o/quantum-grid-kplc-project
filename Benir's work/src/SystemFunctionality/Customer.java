package SystemFunctionality;

import javax.swing.*;
import java.sql.*;

public class Customer extends User {

    public int id;
    private final DatabaseConnection connection;
    private final PaymentService service;
    private Double Tokens;
    private static final Double TOKEN_UNIT=20.57;
    private String houseNo;

    public Customer(Integer customerId,String name, String email, String password,
                    String houseNo,Double initialTokens,
                    PaymentService serviceName,DatabaseConnection connection) {
        super(customerId,name, email, password);
        this.houseNo=houseNo;
        this.Tokens=initialTokens;
        this.service=serviceName;
        this.connection=connection;
    }

    public Customer(String name,
                    String email, String password,String houseNo,
                    Double initialTokens, PaymentService service,
                    DatabaseConnection connection) {
    super(name, email, password);
         this.Tokens=initialTokens;
         this.houseNo=houseNo;
         this.service=service;
         this.connection=connection;
}

    @Override
    void checkTokens() throws SQLException {
            Connection validateConnection=service.getConnection();
            connection.checkTokenColumn(validateConnection,this.id,Tokens);
    }
    @Override
    void makePayment(Double amount) throws SQLException {
        //Let 1 token represent 20.57 ksh and 1 KWH
        //20.57 shillings -> 1 Kwh/1 token
        VerifyPayments payment1=(payment_amount -> payment_amount<TOKEN_UNIT);
        if (payment1.verification(amount)){
            JOptionPane.showMessageDialog(null,"A token requires not less than 20.57");
        }else{
            Connection validateConnection= service.getConnection();
            TokenConversion conversion1=(payment_amount->{
                Tokens+=payment_amount/TOKEN_UNIT;
                return Tokens;
            });
            Tokens=conversion1.convertToTokens(amount);
            connection.updateTokens(validateConnection,Tokens,this.id);
        }
    }
    public Double getTokens(){
        return Tokens;
    }
    public int getId(){return id;}
    public String getHouseNo() {
        return houseNo;
    }
}
