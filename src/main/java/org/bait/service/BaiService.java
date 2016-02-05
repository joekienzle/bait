package org.bait.service;

import org.bait.db.BaiRepository;
import org.bait.model.Bai;

public interface BaiService {
    Bai createBankAccountInformation(Bai bai);

    Bai findBankAccountInformation(String baiId);

    void setBaiRepository(BaiRepository baiRepository);
}
