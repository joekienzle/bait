package org.bait.db.model;

import org.bait.model.Bai;
import org.bait.model.TransferInfo;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="TransferInfo")
public class TransferInfoDbImpl implements TransferInfo {

    @ManyToOne(targetEntity = BaiDbImpl.class)
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

    public void setBai(final Bai bai) {
        this.bai = bai;
    }

    @Override
    public String getBaiId() {
        return getBai().getBaiId();
    }

    @Override
    public void setBaiId(final String baiId) {
        this.bai.setBaiId(baiId);
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
