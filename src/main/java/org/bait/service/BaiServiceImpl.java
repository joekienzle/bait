package org.bait.service;

import org.bait.db.BaiRepository;
import org.bait.model.BankAccountInformation;

public class BaiServiceImpl implements BaiService {

    BaiRepository baiRepository;

    @Override
    public BankAccountInformation createBankAccountInformation(BankAccountInformation bankAccountInformation) {
        return baiRepository.saveBankAccountInformation(bankAccountInformation);
    }

    @Override
    public BankAccountInformation findBankAccountInformation(String id) {
        return baiRepository.findBankAccountInformationById(id);
    }

    @Override
    public void setBaiRepository(BaiRepository baiRepository) {
        this.baiRepository = baiRepository;
    }
}
