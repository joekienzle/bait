package org.bait.db;

import org.bait.model.BankAccountInformation;
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
        BankAccountInformation bankAccountInformation = mock(BankAccountInformation.class);
        when(bankAccountInformation.getAccountNumber()).thenReturn(accountNumber);
        when(bankAccountInformation.getBankNumber()).thenReturn(bankNumber);
        when(bankAccountInformation.getBankName()).thenReturn(bankName);

        BankAccountInformation savedBankAccountInformation = baiRepository.saveBankAccountInformation(bankAccountInformation);
        BankAccountInformation loadedBankAccountInformation = baiRepository.findBankAccountInformationById(savedBankAccountInformation.getId());

        assertEquals(accountNumber, loadedBankAccountInformation.getAccountNumber());
        assertEquals(bankNumber, loadedBankAccountInformation.getBankNumber());
        assertEquals(bankName, loadedBankAccountInformation.getBankName());
    }
}