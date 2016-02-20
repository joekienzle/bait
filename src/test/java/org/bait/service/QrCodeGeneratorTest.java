package org.bait.service;

import org.bait.model.TransferInfo;
import org.junit.Test;

import java.io.OutputStream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class QrCodeGeneratorTest {

    @Test
    public void generateQrCodeImage() {
        QrCodeGenerator qrCodeGenerator = new QrCodeGenerator();
        TransferInfo transferInfo = mock(TransferInfo.class);
        // TODO Read generated reference image to compare with generation
        OutputStream referenceQrCodeImageStream = mock (OutputStream.class);
        OutputStream generatedQrCodeImageStream = qrCodeGenerator.generateImage(transferInfo);
        assertNotNull(generatedQrCodeImageStream);
    }

}