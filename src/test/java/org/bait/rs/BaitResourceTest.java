package org.bait.rs;

import org.bait.model.BankAccountInformation;
import org.bait.rest.BaitResource;
import org.bait.service.BaiService;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class BaitResourceTest {
    @Test
    public void serviceIsInvokedOnCreate() {
        BaitResource baitResource = new BaitResource();
        BaiService baiServiceMock = mock(BaiService.class);
        BankAccountInformation bankAccountInformationMock = mock(BankAccountInformation.class);
        baitResource.setBaiService(baiServiceMock);
        baitResource.createBaiInfo(bankAccountInformationMock);
        verify(baiServiceMock, times(1)).createBankAccountInformation(bankAccountInformationMock);
    }

    @Test
    public void serviceIsInvokedOnFind() {
        BaitResource baitResource = new BaitResource();
        BaiService baiServiceMock = mock(BaiService.class);
        BankAccountInformation bankAccountInformationMock = mock(BankAccountInformation.class);
        baitResource.setBaiService(baiServiceMock);
        String someId = "someId";
        when(baiServiceMock.findBankAccountInformation(someId)).thenReturn(bankAccountInformationMock);

        BankAccountInformation bankAccountInformation = baitResource.findBankAccountInformation(someId);
        assertNotNull(bankAccountInformation);
        verify(baiServiceMock, times(1)).findBankAccountInformation(someId);
    }
}
