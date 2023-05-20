package org.example.model;

import java.text.DateFormat;
import java.util.Date;

public class AccountingPeriod {
    private Date startDate;
    private Date endDate;

    public AccountingPeriod() {
        super();
    }

    public AccountingPeriod(Date startDate, Date endDate) {
        super();
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        StringBuffer buff = new StringBuffer();
        buff.append("-----model.AccountingPeriod-----)\n");
        if (this.startDate != null) {
            buff.append("StartDate "
                    + DateFormat.getDateInstance().format(this.startDate)
                    + "\n");
        } else {
            buff.append("No start date was set\n");
        }
        if (this.endDate != null) {
            buff.append("EndDate  "
                    + DateFormat.getDateInstance().format(this.endDate) + "\n");
        } else {
            buff.append("No ens date was set\n");
        }
        buff.append("-----End model.AccountingPeriod -)");
        return buff.toString();
    }
}
