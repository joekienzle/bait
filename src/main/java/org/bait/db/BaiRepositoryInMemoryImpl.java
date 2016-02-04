package org.bait.db;

import org.bait.model.BankAccountInformation;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BaiRepositoryInMemoryImpl implements BaiRepository {

    private Map<String, BankAccountInformation> baiById = new HashMap<>();

    @Override
    public BankAccountInformation saveBankAccountInformation(BankAccountInformation bankAccountInformation) {
        BankAccountInformation persistedBankAccountInformation = new BankAccountInformation();
        String id = generateUUIDString();
        persistedBankAccountInformation.setId(id);
        persistedBankAccountInformation.setAccountNumber(bankAccountInformation.getAccountNumber());
        persistedBankAccountInformation.setBankNumber(bankAccountInformation.getBankNumber());
        persistedBankAccountInformation.setBankName(bankAccountInformation.getBankName());
        baiById.put(id, persistedBankAccountInformation);
        return persistedBankAccountInformation;
    }

    @Override
    public BankAccountInformation findBankAccountInformationById(String id) {
        return baiById.get(id);
    }

    private static String generateUUIDString() {
        return UUID.randomUUID().toString();
    }
}
