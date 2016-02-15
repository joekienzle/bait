package org.bait.service;

import org.bait.db.BaiRepository;
import org.bait.db.model.BaiDbImpl;

public interface BaiService {
    BaiDbImpl createBankAccountInformation(BaiDbImpl baiDbImpl);

    BaiDbImpl findBankAccountInformation(String baiId);

    void setBaiRepository(BaiRepository baiRepository);

    void deleteBankAccountInformation(String baiId);
}
