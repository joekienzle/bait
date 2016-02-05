package org.bait.db;

import org.bait.model.Bai;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BaiRepositoryInMemoryImplTest {

    @Test
    public void testSaveBankAccountInformation() {
        BaiRepository baiRepository = new BaiRepositoryInMemoryImpl();

        String accountNumber = "1234567890";
        String bankNumber = "0987654321";
        String bankName = "My Bank";
        Bai bai = mock(Bai.class);
        when(bai.getAccountNumber()).thenReturn(accountNumber);
        when(bai.getBankNumber()).thenReturn(bankNumber);
        when(bai.getBankName()).thenReturn(bankName);

        Bai savedBai = baiRepository.saveBankAccountInformation(bai);
        Bai loadedBai = baiRepository.findBankAccountInformationById(savedBai.getId());

        assertEquals(accountNumber, loadedBai.getAccountNumber());
        assertEquals(bankNumber, loadedBai.getBankNumber());
        assertEquals(bankName, loadedBai.getBankName());
    }
}