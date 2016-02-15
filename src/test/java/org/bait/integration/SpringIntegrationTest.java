package org.bait.integration;

import org.bait.config.SpringConfig;
import org.bait.model.Bai;
import org.bait.model.TransferInfo;
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
        Bai bai = createBai("accountNumber" + createUuid(), "bankNumber" + createUuid(), "bankName" + createUuid());
        String baiId = writeBai(bai);
        compareBai(bai, baiService.findBankAccountInformation(baiId));
    }

    @Test
    public void baiServiceMultipleWriteAndReadBaiTest() {
        Bai baiOne = createBai("accountNumberOne" + createUuid(), "bankNumberOne" + createUuid(), "bankNameOne" + createUuid());
        String baiOneId = writeBai(baiOne);

        Bai baiTwo = createBai("accountNumberTwo" + createUuid(), "bankNumberTwo" + createUuid(), "bankNameTwo" + createUuid());
        String baiTwoId = writeBai(baiTwo);

        compareBai(baiOne, baiService.findBankAccountInformation(baiOneId));
        compareBai(baiTwo, baiService.findBankAccountInformation(baiTwoId));
    }

    @Test
    public void baiServiceWriteAndDeleteBaiTest() {
        Bai bai = createBai("accountNumber" + createUuid(), "bankNumber"  + createUuid(), "bankName" +  createUuid());
        String baiId = writeBai(bai);
        compareBai(bai, baiService.findBankAccountInformation(baiId));

        baiService.deleteBankAccountInformation(baiId);
        assertNull(baiService.findBankAccountInformation(baiId));
    }

    @Test
    public void baiServiceFindNoBai() {
        Bai loadedBai = baiService.findBankAccountInformation(createUuid());
        assertNull(loadedBai);
    }

    @Test
    public void baiResourceWriteBaiTest() {
        Bai bai = createBai("123456", "7890", "my bank name");

        Response response = baitResource.createBaiInfo(bai);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    public void transferInfoServiceWriteTransferTest() {
        Bai bai = createBai("1234", "56789", "My Bank");
        String baiId = writeBai(bai);

        TransferInfo transferInfo = createTransferInfo("142.43", "Bill number 08012312");
        String transferId = transferInfoService.createTransferInformation(transferInfo).getTransferId();
        assertNotNull(transferId);
    }

    @Test
    public void transferInfoServiceWriteAndReadTransferTest() {
        Bai bai = createBai("1234", "56789", "My Bank");
        String baiId = writeBai(bai);

        TransferInfo transferInfo = createTransferInfo("142.43", "Bill number 08012312");
        String transferId = transferInfoService.createTransferInformation(transferInfo).getTransferId();
        assertNotNull(transferId);

        compareTransferInfo(transferInfo, transferInfoService.findTransferInfo(transferId));
    }

    private TransferInfo createTransferInfo(String amount, String subject) {
        TransferInfo transferInfo = new TransferInfo();
        transferInfo.setAmount(amount);
        transferInfo.setSubject(subject);
        return transferInfo;
    }

    private void compareTransferInfo(TransferInfo expected, TransferInfo actual) {
        assertEquals(expected.getAmount(), actual.getAmount());
        assertEquals(expected.getSubject(), actual.getSubject());
        compareBai(expected.getBai(), actual.getBai());
    }

    private String createUuid() {
        return UUID.randomUUID().toString();
    }

    private Bai createBai(final String accountNumber,final  String bankNumber,final  String bankName) {
        Bai bai = new Bai();
        bai.setAccountNumber(accountNumber);
        bai.setBankNumber(bankNumber);
        bai.setBankName(bankName);
        return bai;
    }

    private String writeBai(Bai bai) {
        Bai persistedBai = baiService.createBankAccountInformation(bai);
        assertNotNull(persistedBai);
        String baiId = persistedBai.getBaiId();
        assertNotNull(baiId);
        return baiId;
    }

    private void compareBai(final Bai expected, final Bai actual) {
        assertEquals(expected.getAccountNumber(), actual.getAccountNumber());
        assertEquals(expected.getBankNumber(), actual.getBankNumber());
        assertEquals(expected.getBankName(), actual.getBankName());
    }
}
