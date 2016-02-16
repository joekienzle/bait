package org.bait.db.model;

import org.bait.model.Bai;
import org.bait.model.TransferInfo;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name="Bai")
public class BaiDbImpl implements Bai {

    @Id
    @Column(name="baiId")
    @GeneratedValue(generator="hibernate-uuid")
    @GenericGenerator(name="hibernate-uuid", strategy = "uuid2")
    private String baiId;

    private String accountNumber;

    private String bankNumber;

    private String bankName;

    @OneToMany(targetEntity = TransferInfoDbImpl.class, mappedBy = "bai")
    private Collection<TransferInfo> transferInfo;

    @Override
    public String getBaiId() {
        return baiId;
    }

    @Override
    public void setBaiId(final String baiId) {
        this.baiId = baiId;
    }

    @Override
    public String getAccountNumber() {
        return accountNumber;
    }

    @Override
    public void setAccountNumber(final String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public String getBankNumber() {
        return bankNumber;
    }

    @Override
    public void setBankNumber(final String bankNumber) {
        this.bankNumber = bankNumber;
    }

    @Override
    public String getBankName() {
        return bankName;
    }

    @Override
    public void setBankName(final String bankName) {
        this.bankName = bankName;
    }

    public Collection<TransferInfo> getTransferInfo() {
        return transferInfo;
    }

    public void setTransferInfo(final Collection<TransferInfo> transferInfo) {
        this.transferInfo = transferInfo;
    }
}
