package org.bait.service;

import org.bait.db.BaiRepository;
import org.bait.db.BaiImpl;

public interface BaiService {
    BaiImpl createBankAccountInformation(BaiImpl baiImpl);

    BaiImpl findBankAccountInformation(String baiId);

    void setBaiRepository(BaiRepository baiRepository);

    void deleteBankAccountInformation(String baiId);
}
