
package org.bait.rs;

import org.bait.db.TransferInfoImpl;
import org.bait.rest.TransferResource;
import org.bait.service.TransferInfoService;
import org.junit.Test;

import javax.ws.rs.core.Response;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class TransferResourceTest {
    @Test
    public void createTransferInfo() {
        TransferResource transferResource = new TransferResource();
        TransferInfoService transferInfoServiceMock = mock(TransferInfoService.class);
        TransferInfoImpl transferInfoImplMock = mock(TransferInfoImpl.class);
        when(transferInfoServiceMock.createTransferInformation(transferInfoImplMock)).thenReturn(transferInfoImplMock);
        transferResource.setTransferInfoService(transferInfoServiceMock);
        Response response = transferResource.createTransferInfo(transferInfoImplMock);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        verify(transferInfoServiceMock, times(1)).createTransferInformation(transferInfoImplMock);
    }

    @Test
    public void getTransferInfo() {
        TransferResource transferResource = new TransferResource();
        TransferInfoService transferInfoServiceMock = mock(TransferInfoService.class);
        String transferInfoId = UUID.randomUUID().toString();
        TransferInfoImpl transferInfoImplMock = mock(TransferInfoImpl.class);
        when(transferInfoServiceMock.findTransferInfo(transferInfoId)).thenReturn(transferInfoImplMock);
        transferResource.setTransferInfoService(transferInfoServiceMock);

        Response response = transferResource.getTransferInfo(transferInfoId);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        verify(transferInfoServiceMock, times(1)).findTransferInfo(transferInfoId);
    }

    @Test
    public void getNoTransferInfo() {
        TransferResource transferResource = new TransferResource();
        TransferInfoService transferInfoServiceMock = mock(TransferInfoService.class);
        String transferInfoId = UUID.randomUUID().toString();
        when(transferInfoServiceMock.findTransferInfo(transferInfoId)).thenReturn(null);
        transferResource.setTransferInfoService(transferInfoServiceMock);

        Response response = transferResource.getTransferInfo(transferInfoId);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        verify(transferInfoServiceMock, times(1)).findTransferInfo(transferInfoId);
    }
}
