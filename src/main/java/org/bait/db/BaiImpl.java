package org.bait.db;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="Bai")
public class BaiImpl {

    @Id
    @Column(name="baiId")
    @GeneratedValue(generator="hibernate-uuid")
    @GenericGenerator(name="hibernate-uuid", strategy = "uuid2")
    private String baiId;

    private String accountNumber;

    private String bankNumber;

    private String bankName;

    public String getBaiId() {
        return baiId;
    }

    public void setBaiId(final String baiId) {
        this.baiId = baiId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(final String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(final String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(final String bankName) {
        this.bankName = bankName;
    }
}
