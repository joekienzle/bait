package org.bait.rs;

import org.bait.model.Bai;
import org.bait.rest.BaitResource;
import org.bait.service.BaiService;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class BaitResourceTest {
    @Test
    public void createBaiInfo() {
        BaitResource baitResource = new BaitResource();
        BaiService baiServiceMock = mock(BaiService.class);
        Bai baiMock = mock(Bai.class);
        when(baiServiceMock.createBankAccountInformation(baiMock)).thenReturn(baiMock);
        baitResource.setBaiService(baiServiceMock);
        Response response = baitResource.createBaiInfo(baiMock);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        verify(baiServiceMock, times(1)).createBankAccountInformation(baiMock);
    }

    @Test
    public void getBaiInfo() {
        BaitResource baitResource = new BaitResource();
        BaiService baiServiceMock = mock(BaiService.class);
        Bai baiMock = mock(Bai.class);
        baitResource.setBaiService(baiServiceMock);
        String someId = "someId";
        when(baiServiceMock.findBankAccountInformation(someId)).thenReturn(baiMock);

        Response response = baitResource.findBankAccountInformation(someId);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        verify(baiServiceMock, times(1)).findBankAccountInformation(someId);
    }

    @Test
    public void getNoBaiInfo() {
        BaitResource baitResource = new BaitResource();
        BaiService baiServiceMock = mock(BaiService.class);
        baitResource.setBaiService(baiServiceMock);
        String someId = "someId";
        when(baiServiceMock.findBankAccountInformation(someId)).thenReturn(null);

        Response response = baitResource.findBankAccountInformation(someId);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        verify(baiServiceMock, times(1)).findBankAccountInformation(someId);
    }

    @Test
    public void deleteBaiInfo() {
        BaitResource baitResource = new BaitResource();
        BaiService baiServiceMock = mock(BaiService.class);
        String baiId = UUID.randomUUID().toString();
        baitResource.setBaiService(baiServiceMock);
        Response response = baitResource.deleteBaiInfo(baiId);
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
        verify(baiServiceMock, times(1)).deleteBankAccountInformation(baiId);
    }
}
