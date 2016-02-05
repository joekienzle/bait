package org.bait.db;

import org.bait.model.Bai;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class BaiRepositoryInMemoryImpl implements BaiRepository {

    private Map<String, Bai> baiById = new HashMap<>();

    @Override
    public Bai saveBankAccountInformation(final Bai bai) {
        // TODO: Using the same Object type for persistence is dangerous, I will deal with this later when including a proper persistence layer
        Bai persistedBai = new Bai();
        String baiId = generateUUIDString();
        persistedBai.setBaiId(baiId);
        persistedBai.setAccountNumber(bai.getAccountNumber());
        persistedBai.setBankNumber(bai.getBankNumber());
        persistedBai.setBankName(bai.getBankName());
        baiById.put(baiId, persistedBai);
        return persistedBai;
    }

    @Override
    public Bai findBankAccountInformationById(final String baiId) {
        return baiById.get(baiId);
    }

    private static String generateUUIDString() {
        return UUID.randomUUID().toString();
    }
}
