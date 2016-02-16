
package org.bait.rs;

import org.bait.db.model.TransferInfoDbImpl;
import org.bait.model.TransferInfo;
import org.bait.rest.TransferResource;
import org.bait.rest.model.TransferInfoJsonImpl;
import org.bait.service.api.TransferInfoService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TransferResourceTest {

    private TransferResource transferResource;

    @Mock
    private TransferInfoService transferInfoServiceMock;

    @Mock
    private TransferInfo transferInfoMock;

    @Mock
    private TransferInfoJsonImpl transferInfoJsonMock;

    @Mock
    private TransferInfoDbImpl transferInfoDbImplMock;

    @Before
    public void setUp() {
        transferResource = new TransferResource();
        transferResource.setTransferInfoService(transferInfoServiceMock);
    }

    @Test
    public void createTransferInfo() {
        when(transferInfoServiceMock.createTransferInformation(transferInfoJsonMock)).thenReturn(transferInfoMock);
        Response response = transferResource.createTransferInfo(transferInfoJsonMock);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        verify(transferInfoServiceMock, times(1)).createTransferInformation(transferInfoJsonMock);
    }

    @Test
    public void getTransferInfo() {
        String transferInfoId = UUID.randomUUID().toString();
        when(transferInfoServiceMock.findTransferInfo(transferInfoId)).thenReturn(transferInfoDbImplMock);

        Response response = transferResource.getTransferInfo(transferInfoId);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        verify(transferInfoServiceMock, times(1)).findTransferInfo(transferInfoId);
    }

    @Test
    public void getNoTransferInfo() {
        String transferInfoId = UUID.randomUUID().toString();
        when(transferInfoServiceMock.findTransferInfo(transferInfoId)).thenReturn(null);

        Response response = transferResource.getTransferInfo(transferInfoId);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        verify(transferInfoServiceMock, times(1)).findTransferInfo(transferInfoId);
    }

    @Test
    public void deleteTransferInfo() {
        String transferInfoId = UUID.randomUUID().toString();
        Response response = transferResource.deleteTransferInfo(transferInfoId);
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
        verify(transferInfoServiceMock, times(1)).deleteTransferInformation(transferInfoId);
    }
}
