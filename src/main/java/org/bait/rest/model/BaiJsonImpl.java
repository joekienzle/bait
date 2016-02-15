package org.bait.rest.model;

import org.bait.model.Bai;

public class BaiJsonImpl implements Bai {
    private String baiId;

    private String accountNumber;

    private String bankNumber;

    private String bankName;

    @Override
    public String getBaiId() {
        return baiId;
    }

    @Override
    public void setBaiId(String baiId) {
        this.baiId = baiId;
    }

    @Override
    public String getAccountNumber() {
        return accountNumber;
    }

    @Override
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public String getBankNumber() {
        return bankNumber;
    }

    @Override
    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    @Override
    public String getBankName() {
        return bankName;
    }

    @Override
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
