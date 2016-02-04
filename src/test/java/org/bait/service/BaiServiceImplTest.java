package org.bait.service;

import org.bait.db.BaiRepository;
import org.bait.model.BankAccountInformation;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class BaiServiceImplTest {

    @Test
    public void repositoryIsInvokedOnCreate() {
        BaiService baiService = new BaiServiceImpl();
        BankAccountInformation bankAccountInformationMock = mock(BankAccountInformation.class);
        BaiRepository baiRepository = mock(BaiRepository.class);
        baiService.setBaiRepository(baiRepository);

        baiService.createBankAccountInformation(bankAccountInformationMock);
        verify(baiRepository, times(1)).saveBankAccountInformation(bankAccountInformationMock);
    }

    @Test
    public void repositoryIsInvokedOnFind() {
        BaiService baiService = new BaiServiceImpl();
        BankAccountInformation bankAccountInformationMock = mock(BankAccountInformation.class);
        BaiRepository baiRepository = mock(BaiRepository.class);
        baiService.setBaiRepository(baiRepository);
        String accountId = "someId";

        baiService.findBankAccountInformation(accountId);
        verify(baiRepository, times(1)).findBankAccountInformationById(accountId);
    }
}