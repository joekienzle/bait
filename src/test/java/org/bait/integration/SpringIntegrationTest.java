package org.bait.integration;

import org.bait.config.SpringConfig;
import org.bait.db.model.BaiDbImpl;
import org.bait.db.model.TransferInfoDbImpl;
import org.bait.model.Bai;
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
        BaiDbImpl baiDbImpl = createBai("accountNumber" + createUuid(), "bankNumber" + createUuid(), "bankName" + createUuid());
        String baiId = writeBai(baiDbImpl);
        compareBai(baiDbImpl, baiService.findBankAccountInformation(baiId));
    }

    @Test
    public void baiServiceMultipleWriteAndReadBaiTest() {
        BaiDbImpl baiDbImplOne = createBai("accountNumberOne" + createUuid(), "bankNumberOne" + createUuid(), "bankNameOne" + createUuid());
        String baiOneId = writeBai(baiDbImplOne);

        BaiDbImpl baiDbImplTwo = createBai("accountNumberTwo" + createUuid(), "bankNumberTwo" + createUuid(), "bankNameTwo" + createUuid());
        String baiTwoId = writeBai(baiDbImplTwo);

        compareBai(baiDbImplOne, baiService.findBankAccountInformation(baiOneId));
        compareBai(baiDbImplTwo, baiService.findBankAccountInformation(baiTwoId));
    }

    @Test
    public void baiServiceWriteAndDeleteBaiTest() {
        BaiDbImpl baiDbImpl = createBai("accountNumber" + createUuid(), "bankNumber"  + createUuid(), "bankName" +  createUuid());
        String baiId = writeBai(baiDbImpl);
        compareBai(baiDbImpl, baiService.findBankAccountInformation(baiId));

        baiService.deleteBankAccountInformation(baiId);
        assertNull(baiService.findBankAccountInformation(baiId));
    }

    @Test
    public void baiServiceFindNoBai() {
        BaiDbImpl loadedBaiDbImpl = baiService.findBankAccountInformation(createUuid());
        assertNull(loadedBaiDbImpl);
    }

    @Test
    public void baiResourceWriteBaiTest() {
        BaiDbImpl baiDbImpl = createBai("123456", "7890", "my bank name");

        Response response = baitResource.createBaiInfo(baiDbImpl);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    public void transferInfoServiceWriteTransferTest() {
        BaiDbImpl baiDbImpl = createBai("1234", "56789", "My Bank");
        String baiId = writeBai(baiDbImpl);

        TransferInfoDbImpl transferInfoDbImpl = createTransferInfo("142.43", "Bill number 08012312");
        String transferId = transferInfoService.createTransferInformation(transferInfoDbImpl).getTransferId();
        assertNotNull(transferId);
    }

    @Test
    public void transferInfoServiceWriteAndReadTransferTest() {
        BaiDbImpl baiDbImpl = createBai("1234", "56789", "My Bank");
        String baiId = writeBai(baiDbImpl);

        BaiDbImpl baiDbImplTransient = new BaiDbImpl();
        baiDbImplTransient.setBaiId(baiId);

        TransferInfoDbImpl transferInfoDbImplTransient = createTransferInfo("142.43", "Bill number 08012312");
        transferInfoDbImplTransient.setBai(baiDbImplTransient);
        String transferId = transferInfoService.createTransferInformation(transferInfoDbImplTransient).getTransferId();
        assertNotNull(transferId);

        TransferInfoDbImpl expected = createTransferInfo("142.43", "Bill number 08012312");
        expected.setBai(baiDbImpl);
        compareTransferInfo(expected, transferInfoService.findTransferInfo(transferId));
    }

    private TransferInfoDbImpl createTransferInfo(String amount, String subject) {
        TransferInfoDbImpl transferInfoDbImpl = new TransferInfoDbImpl();
        transferInfoDbImpl.setAmount(amount);
        transferInfoDbImpl.setSubject(subject);
        return transferInfoDbImpl;
    }

    private void compareTransferInfo(TransferInfoDbImpl expected, TransferInfoDbImpl actual) {
        assertEquals(expected.getAmount(), actual.getAmount());
        assertEquals(expected.getSubject(), actual.getSubject());
        compareBai(expected.getBai(), actual.getBai());
    }

    private String createUuid() {
        return UUID.randomUUID().toString();
    }

    private BaiDbImpl createBai(final String accountNumber, final  String bankNumber, final  String bankName) {
        BaiDbImpl baiDbImpl = new BaiDbImpl();
        baiDbImpl.setAccountNumber(accountNumber);
        baiDbImpl.setBankNumber(bankNumber);
        baiDbImpl.setBankName(bankName);
        return baiDbImpl;
    }

    private String writeBai(BaiDbImpl baiDbImpl) {
        BaiDbImpl persistedBaiDbImpl = baiService.createBankAccountInformation(baiDbImpl);
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
