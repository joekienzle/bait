package org.bait.integration;

import com.google.common.base.Strings;
import org.bait.config.SpringConfig;
import org.bait.db.model.BaiDbImpl;
import org.bait.db.model.TransferInfoDbImpl;
import org.bait.model.Bai;
import org.bait.model.TransferInfo;
import org.bait.rest.BaitResource;
import org.bait.rest.model.BaiJsonImpl;
import org.bait.rest.model.TransferInfoJsonImpl;
import org.bait.service.BaiService;
import org.bait.service.TransferInfoService;
import org.junit.Assert;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    public void writeAndDeleteBaiTest() {
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
    public void writeTransferTest() {
        BaiJsonImpl baiDbImpl = createBai("1234", "56789", "My Bank");
        String baiId = writeBai(baiDbImpl);
        TransferInfo transferInfo = createTransferInfo("142.43", "Bill number 08012312", baiId);

        String transferId = transferInfoService.createTransferInformation(transferInfo).getTransferId();
        assertNotNull(transferId);
    }

    @Test
    public void writeAndReadTransferTest() {
        BaiJsonImpl baiDbImpl = createBai("1234", "56789", "My Bank");
        String baiId = writeBai(baiDbImpl);

        TransferInfo transferInfoDbImplTransient = createTransferInfo("142.43", "Bill number 08012312", baiId);
        String transferId = transferInfoService.createTransferInformation(transferInfoDbImplTransient).getTransferId();
        assertNotNull(transferId);

        TransferInfo expected = createTransferInfo("142.43", "Bill number 08012312", baiId);
        compareTransferInfo(expected, transferInfoService.findTransferInfo(transferId));
    }

    private TransferInfo createTransferInfo(String amount, String subject, String baiId) {
        TransferInfoJsonImpl transferInfoJson = new TransferInfoJsonImpl();
        transferInfoJson.setAmount(amount);
        transferInfoJson.setSubject(subject);
        transferInfoJson.setBaiId(baiId);
        return transferInfoJson;
    }

    private void compareTransferInfo(TransferInfo expected, TransferInfo actual) {
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

    private String writeBai(BaiJsonImpl baiDbImpl) {
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
