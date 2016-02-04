package org.bait.db;

import org.bait.model.BankAccountInformation;

public interface BaiRepository {
    BankAccountInformation saveBankAccountInformation(BankAccountInformation bankAccountInformation);

    BankAccountInformation findBankAccountInformationById(String id);
}
