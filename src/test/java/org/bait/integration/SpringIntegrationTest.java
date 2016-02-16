package org.bait.integration;

import org.bait.config.SpringConfig;
import org.bait.model.Bai;
import org.bait.model.TransferInfo;
import org.bait.rest.BaitResource;
import org.bait.rest.model.BaiJsonImpl;
import org.bait.rest.model.TransferInfoJsonImpl;
import org.bait.service.PreconditionFailedException;
import org.bait.service.api.BaiService;
import org.bait.service.api.TransferInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.ws.rs.core.Response;
import java.util.UUID;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfig.class})
public class SpringIntegrationTest {

    @Autowired
    private BaiService baiService;

    @Autowired
    private TransferInfoService transferInfoService;

    @Autowired
    private BaitResource baitResource;

    @Test
    public void writeAndReadBaiTest() {
        BaiJsonImpl baiDbImpl = createBai("accountNumber" + createUuid(), "bankNumber" + createUuid(), "bankName" + createUuid());
        String baiId = writeBai(baiDbImpl);
        compareBai(baiDbImpl, baiService.findBankAccountInformation(baiId));
    }

    @Test
    public void multipleWriteAndReadBaiTest() {
        BaiJsonImpl baiDbImplOne = createBai("accountNumberOne" + createUuid(), "bankNumberOne" + createUuid(), "bankNameOne" + createUuid());
        String baiOneId = writeBai(baiDbImplOne);

        BaiJsonImpl baiDbImplTwo = createBai("accountNumberTwo" + createUuid(), "bankNumberTwo" + createUuid(), "bankNameTwo" + createUuid());
        String baiTwoId = writeBai(baiDbImplTwo);

        compareBai(baiDbImplOne, baiService.findBankAccountInformation(baiOneId));
        compareBai(baiDbImplTwo, baiService.findBankAccountInformation(baiTwoId));
    }

    @Test
    public void writeAndDeleteBaiTest() throws PreconditionFailedException {
        BaiJsonImpl baiDbImpl = createBai("accountNumber" + createUuid(), "bankNumber"  + createUuid(), "bankName" +  createUuid());
        String baiId = writeBai(baiDbImpl);
        compareBai(baiDbImpl, baiService.findBankAccountInformation(baiId));

        baiService.deleteBankAccountInformation(baiId);
        assertNull(baiService.findBankAccountInformation(baiId));
    }

    @Test
    public void findNoBai() {
        Bai loadedBaiDbImpl = baiService.findBankAccountInformation(createUuid());
        assertNull(loadedBaiDbImpl);
    }

    @Test
    public void writeBaiTest() {
        BaiJsonImpl baiDbImpl = createBai("123456", "7890", "my bank name");

        Response response = baitResource.createBaiInfo(baiDbImpl);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    public void writeTransferInfoTest() {
        BaiJsonImpl baiDbImpl = createBai("1234", "56789", "My Bank");
        String baiId = writeBai(baiDbImpl);
        TransferInfo transferInfo = createTransferInfo(baiId);

        String transferId = transferInfoService.createTransferInformation(transferInfo).getTransferId();
        assertNotNull(transferId);
    }

    @Test
    public void writeAndReadTransferInfoTest() {
        BaiJsonImpl baiDbImpl = createBai("1234", "56789", "My Bank");
        String baiId = writeBai(baiDbImpl);

        TransferInfo transferInfoDbImplTransient = createTransferInfo(baiId);
        String transferId = transferInfoService.createTransferInformation(transferInfoDbImplTransient).getTransferId();
        assertNotNull(transferId);

        TransferInfo expected = createTransferInfo(baiId);
        compareTransferInfo(expected, transferInfoService.findTransferInfo(transferId));
    }

    @Test
    public void writeAndDeleteTransferInfoTest() {
        BaiJsonImpl baiDbImpl = createBai("accountNumber" + createUuid(), "bankNumber"  + createUuid(), "bankName" +  createUuid());
        String baiId = writeBai(baiDbImpl);

        TransferInfo transferInfoDbImplTransient = createTransferInfo(baiId);
        String transferId = transferInfoService.createTransferInformation(transferInfoDbImplTransient).getTransferId();
        assertNotNull(transferId);

        assertNotNull(transferInfoService.findTransferInfo(transferId));

        transferInfoService.deleteTransferInformation(transferId);

        assertNull(transferInfoService.findTransferInfo(transferId));
    }

    @Test
    public void baiNotDeletableWhenTransferExists() {
        BaiJsonImpl baiDbImpl = createBai("accountNumber" + createUuid(), "bankNumber"  + createUuid(), "bankName" +  createUuid());
        String baiId = writeBai(baiDbImpl);

        TransferInfo transferInfoTransient1 = createTransferInfo(baiId);
        String transferId1 = transferInfoService.createTransferInformation(transferInfoTransient1).getTransferId();
        assertNotNull(transferId1);

        TransferInfo transferInfoTransient2 = createTransferInfo(baiId);
        String transferId2 = transferInfoService.createTransferInformation(transferInfoTransient2).getTransferId();
        assertNotNull(transferId2);
        assertNotEquals(transferId1, transferId2);

        assertNotNull(baiService.findBankAccountInformation(baiId));
        boolean exceptionRaised = false;
        try {
            baiService.deleteBankAccountInformation(baiId);
        } catch (PreconditionFailedException e) {
            String message = e.getMessage();
            assertTrue(message.contains(transferId1));
            assertTrue(message.contains(transferId2));
            exceptionRaised = true;
        }
        assertTrue("There should have been an exception raised for violating the foreign key constraint. Bai object still has dependencies.", exceptionRaised);
        assertNotNull(baiService.findBankAccountInformation(baiId));
    }

    private TransferInfo createTransferInfo(final String baiId) {
        return createTransferInfo("142.43", "Bill number 08012312", baiId);
    }

    private TransferInfo createTransferInfo(final String amount, final String subject, final String baiId) {
        TransferInfoJsonImpl transferInfoJson = new TransferInfoJsonImpl();
        transferInfoJson.setAmount(amount);
        transferInfoJson.setSubject(subject);
        transferInfoJson.setBaiId(baiId);
        return transferInfoJson;
    }

    private void compareTransferInfo(final TransferInfo expected, final TransferInfo actual) {
        assertEquals(expected.getAmount(), actual.getAmount());
        assertEquals(expected.getSubject(), actual.getSubject());
        assertEquals(expected.getBaiId(), actual.getBaiId());
    }

    private String createUuid() {
        return UUID.randomUUID().toString();
    }

    private BaiJsonImpl createBai(final String accountNumber, final  String bankNumber, final  String bankName) {
        BaiJsonImpl baiJsonImpl = new BaiJsonImpl();
        baiJsonImpl.setAccountNumber(accountNumber);
        baiJsonImpl.setBankNumber(bankNumber);
        baiJsonImpl.setBankName(bankName);
        return baiJsonImpl;
    }

    private String writeBai(final BaiJsonImpl baiDbImpl) {
        Bai persistedBaiDbImpl = baiService.createBankAccountInformation(baiDbImpl);
        assertNotNull(persistedBaiDbImpl);
        String baiId = persistedBaiDbImpl.getBaiId();
        assertNotNull(baiId);
        return baiId;
    }

    private void compareBai(final Bai expected, final Bai actual) {
        assertEquals(expected.getAccountNumber(), actual.getAccountNumber());
        assertEquals(expected.getBankNumber(), actual.getBankNumber());
        assertEquals(expected.getBankName(), actual.getBankName());
    }
}
