package org.bait.db.model;

import org.bait.model.Bai;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="TransferInfo")
public class TransferInfoDbImpl implements org.bait.model.TransferInfo {

    @ManyToOne(targetEntity = BaiDbImpl.class)
    private Bai bai;

    @Id
    @Column(name="transferId")
    @GeneratedValue(generator="hibernate-uuid")
    @GenericGenerator(name="hibernate-uuid", strategy = "uuid2")
    private String transferId;

    private String subject;

    private String amount;

    @Override
    public Bai getBai() {
        return bai;
    }

    @Override
    public void setBai(Bai bai) {
        this.bai = bai;
    }

    @Override
    public String getTransferId() {
        return transferId;
    }

    @Override
    public String getSubject() {
        return subject;
    }

    @Override
    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String getAmount() {
        return amount;
    }

    @Override
    public void setAmount(String amount) {
        this.amount = amount;
    }
}
