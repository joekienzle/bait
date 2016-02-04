package org.bait.service;

import org.bait.db.BaiRepository;
import org.bait.model.BankAccountInformation;

public interface BaiService {
    BankAccountInformation createBankAccountInformation(BankAccountInformation bankAccountInformation);

    BankAccountInformation findBankAccountInformation(String id);

    void setBaiRepository(BaiRepository baiRepository);
}
