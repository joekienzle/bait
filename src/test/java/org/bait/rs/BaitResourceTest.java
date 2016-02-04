package org.bait.rs;

import org.bait.model.BankAccountInformation;
import org.bait.rest.BaitResource;
import org.bait.service.BaiService;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class BaitResourceTest {
    @Test
    public void createBaiInfo() {
        BaitResource baitResource = new BaitResource();
        BaiService baiServiceMock = mock(BaiService.class);
        BankAccountInformation bankAccountInformationMock = mock(BankAccountInformation.class);
        when(baiServiceMock.createBankAccountInformation(bankAccountInformationMock)).thenReturn(bankAccountInformationMock);
        baitResource.setBaiService(baiServiceMock);
        Response response = baitResource.createBaiInfo(bankAccountInformationMock);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        verify(baiServiceMock, times(1)).createBankAccountInformation(bankAccountInformationMock);
    }

    @Test
    public void getBaiInfo() {
        BaitResource baitResource = new BaitResource();
        BaiService baiServiceMock = mock(BaiService.class);
        BankAccountInformation bankAccountInformationMock = mock(BankAccountInformation.class);
        baitResource.setBaiService(baiServiceMock);
        String someId = "someId";
        when(baiServiceMock.findBankAccountInformation(someId)).thenReturn(bankAccountInformationMock);

        Response response = baitResource.findBankAccountInformation(someId);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        verify(baiServiceMock, times(1)).findBankAccountInformation(someId);
    }
}
