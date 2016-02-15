package org.bait.service.api;

import org.bait.model.Bai;

public interface BaiService {
    Bai createBankAccountInformation(Bai bai);

    Bai findBankAccountInformation(String baiId);

    void deleteBankAccountInformation(String baiId);
}
