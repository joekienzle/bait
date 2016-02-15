package org.bait.integration;

import org.bait.config.SpringConfig;
import org.bait.db.BaiImpl;
import org.bait.db.TransferInfoImpl;
import org.bait.rest.BaitResource;
import org.bait.service.BaiService;
import org.bait.service.TransferInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.ws.rs.core.Response;

import java.util.UUID;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;
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
    public void baiServiceWriteAndReadBaiTest() {
        BaiImpl baiImpl = createBai("accountNumber" + createUuid(), "bankNumber" + createUuid(), "bankName" + createUuid());
        String baiId = writeBai(baiImpl);
        compareBai(baiImpl, baiService.findBankAccountInformation(baiId));
    }

    @Test
    public void baiServiceMultipleWriteAndReadBaiTest() {
        BaiImpl baiImplOne = createBai("accountNumberOne" + createUuid(), "bankNumberOne" + createUuid(), "bankNameOne" + createUuid());
        String baiOneId = writeBai(baiImplOne);

        BaiImpl baiImplTwo = createBai("accountNumberTwo" + createUuid(), "bankNumberTwo" + createUuid(), "bankNameTwo" + createUuid());
        String baiTwoId = writeBai(baiImplTwo);

        compareBai(baiImplOne, baiService.findBankAccountInformation(baiOneId));
        compareBai(baiImplTwo, baiService.findBankAccountInformation(baiTwoId));
    }

    @Test
    public void baiServiceWriteAndDeleteBaiTest() {
        BaiImpl baiImpl = createBai("accountNumber" + createUuid(), "bankNumber"  + createUuid(), "bankName" +  createUuid());
        String baiId = writeBai(baiImpl);
        compareBai(baiImpl, baiService.findBankAccountInformation(baiId));

        baiService.deleteBankAccountInformation(baiId);
        assertNull(baiService.findBankAccountInformation(baiId));
    }

    @Test
    public void baiServiceFindNoBai() {
        BaiImpl loadedBaiImpl = baiService.findBankAccountInformation(createUuid());
        assertNull(loadedBaiImpl);
    }

    @Test
    public void baiResourceWriteBaiTest() {
        BaiImpl baiImpl = createBai("123456", "7890", "my bank name");

        Response response = baitResource.createBaiInfo(baiImpl);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    public void transferInfoServiceWriteTransferTest() {
        BaiImpl baiImpl = createBai("1234", "56789", "My Bank");
        String baiId = writeBai(baiImpl);

        TransferInfoImpl transferInfoImpl = createTransferInfo("142.43", "Bill number 08012312");
        String transferId = transferInfoService.createTransferInformation(transferInfoImpl).getTransferId();
        assertNotNull(transferId);
    }

    @Test
    public void transferInfoServiceWriteAndReadTransferTest() {
        BaiImpl baiImpl = createBai("1234", "56789", "My Bank");
        String baiId = writeBai(baiImpl);

        BaiImpl baiImplTransient = new BaiImpl();
        baiImplTransient.setBaiId(baiId);

        TransferInfoImpl transferInfoImplTransient = createTransferInfo("142.43", "Bill number 08012312");
        transferInfoImplTransient.setBaiImpl(baiImplTransient);
        String transferId = transferInfoService.createTransferInformation(transferInfoImplTransient).getTransferId();
        assertNotNull(transferId);

        TransferInfoImpl expected = createTransferInfo("142.43", "Bill number 08012312");
        expected.setBaiImpl(baiImpl);
        compareTransferInfo(expected, transferInfoService.findTransferInfo(transferId));
    }

    private TransferInfoImpl createTransferInfo(String amount, String subject) {
        TransferInfoImpl transferInfoImpl = new TransferInfoImpl();
        transferInfoImpl.setAmount(amount);
        transferInfoImpl.setSubject(subject);
        return transferInfoImpl;
    }

    private void compareTransferInfo(TransferInfoImpl expected, TransferInfoImpl actual) {
        assertEquals(expected.getAmount(), actual.getAmount());
        assertEquals(expected.getSubject(), actual.getSubject());
        compareBai(expected.getBaiImpl(), actual.getBaiImpl());
    }

    private String createUuid() {
        return UUID.randomUUID().toString();
    }

    private BaiImpl createBai(final String accountNumber, final  String bankNumber, final  String bankName) {
        BaiImpl baiImpl = new BaiImpl();
        baiImpl.setAccountNumber(accountNumber);
        baiImpl.setBankNumber(bankNumber);
        baiImpl.setBankName(bankName);
        return baiImpl;
    }

    private String writeBai(BaiImpl baiImpl) {
        BaiImpl persistedBaiImpl = baiService.createBankAccountInformation(baiImpl);
        assertNotNull(persistedBaiImpl);
        String baiId = persistedBaiImpl.getBaiId();
        assertNotNull(baiId);
        return baiId;
    }

    private void compareBai(final BaiImpl expected, final BaiImpl actual) {
        assertEquals(expected.getAccountNumber(), actual.getAccountNumber());
        assertEquals(expected.getBankNumber(), actual.getBankNumber());
        assertEquals(expected.getBankName(), actual.getBankName());
    }
}
