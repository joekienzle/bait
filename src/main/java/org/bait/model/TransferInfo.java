package org.bait.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="Transfer")
public class TransferInfo {

    @ManyToOne(targetEntity = Bai.class)
    private Bai bai;

    @Id
    @Column(name="transferId")
    @GeneratedValue(generator="hibernate-uuid")
    @GenericGenerator(name="hibernate-uuid", strategy = "uuid2")
    private String transferId;

    private String subject;

    private String amount;

    public Bai getBai() {
        return bai;
    }

    public void setBai(Bai bai) {
        this.bai = bai;
    }

    public String getTransferId() {
        return transferId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
