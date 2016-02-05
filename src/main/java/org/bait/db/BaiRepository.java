package org.bait.db;

import org.bait.model.Bai;

public interface BaiRepository {
    Bai saveBankAccountInformation(Bai bai);

    Bai findBankAccountInformationById(String id);
}
