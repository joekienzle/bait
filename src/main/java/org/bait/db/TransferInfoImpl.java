package org.bait.db;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="TransferInfo")
public class TransferInfoImpl {

    @ManyToOne(targetEntity = BaiImpl.class)
    private BaiImpl baiImpl;

    @Id
    @Column(name="transferId")
    @GeneratedValue(generator="hibernate-uuid")
    @GenericGenerator(name="hibernate-uuid", strategy = "uuid2")
    private String transferId;

    private String subject;

    private String amount;

    public BaiImpl getBaiImpl() {
        return baiImpl;
    }

    public void setBaiImpl(BaiImpl baiImpl) {
        this.baiImpl = baiImpl;
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
