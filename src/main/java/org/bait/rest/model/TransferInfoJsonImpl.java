package org.bait.rest.model;

import org.bait.model.TransferInfo;

public class TransferInfoJsonImpl implements TransferInfo {
    private String baiId;

    private String subject;

    private String transferId;

    private String amount;

    @Override
    public String getBaiId() {
        return baiId;
    }

    @Override
    public void setBaiId(final String baiId) {
        this.baiId = baiId;
    }

    @Override
    public String getTransferId() {
        return transferId;
    }

    @Override
    public void setTransferId(final String transferId) {
        this.transferId = transferId;
    }

    @Override
    public String getSubject() {
        return subject;
    }

    @Override
    public void setSubject(final String subject) {
        this.subject = subject;
    }

    @Override
    public String getAmount() {
        return amount;
    }

    @Override
    public void setAmount(final String amount) {
        this.amount = amount;
    }
}
