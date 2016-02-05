package org.bait.db;

import org.bait.model.Bai;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BaiRepositoryInMemoryImpl implements BaiRepository {

    private Map<String, Bai> baiById = new HashMap<>();

    @Override
    public Bai saveBankAccountInformation(Bai bai) {
        // TODO: Using the same Object type for persistence is dangerous, I will deal with this later when including a proper persistence layer
        Bai persistedBai = new Bai();
        String id = generateUUIDString();
        persistedBai.setId(id);
        persistedBai.setAccountNumber(bai.getAccountNumber());
        persistedBai.setBankNumber(bai.getBankNumber());
        persistedBai.setBankName(bai.getBankName());
        baiById.put(id, persistedBai);
        return persistedBai;
    }

    @Override
    public Bai findBankAccountInformationById(String id) {
        return baiById.get(id);
    }

    private static String generateUUIDString() {
        return UUID.randomUUID().toString();
    }
}
