package org.example.model;

public class Account {
    private long accountno;
    private double balance;

    public Account() {
        super();
    }

    public Account(long accountno, double balance) {
        this.accountno = accountno;
        this.balance = balance;
    }

    public long getAccountno() {
        return accountno;
    }

    public void setAccountno(long accountno) {
        this.accountno = accountno;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        StringBuffer buff = new StringBuffer();
        buff.append("-----model.Account-----)\n");
        buff.append("model.Account no " + this.accountno + "\n");
        buff.append("Balance " + this.balance + "\n");
        buff.append("-----End model.Account-)");
        return buff.toString();
    }
}
