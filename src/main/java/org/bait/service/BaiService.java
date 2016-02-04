package org.bait.service;

import org.bait.db.BaiRepository;
import org.bait.model.BankAccountInformation;

public interface BaiService {
    BankAccountInformation createBankAccountInformation(BankAccountInformation bankAccountInformation);

    void setBaiRepository(BaiRepository baiRepository);
}
