package com.alliander.osgp.oslp;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

import org.apache.commons.lang3.ArrayUtils;
import org.joda.time.Instant;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import com.alliander.osgp.oslp.Oslp;
import com.alliander.osgp.oslp.OslpEnvelope;
import com.alliander.osgp.oslp.Oslp.Message;
import com.alliander.osgp.oslp.Oslp.RegisterDeviceResponse;
import com.alliander.osgp.shared.security.CertificateHelper;

/**
 * Unittests for OSLP envelope with RSA security.
 */
public class OslpEnvelopeRsaTest {

    private static final String PRIVATE_KEY_BASE_64 = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMZLix1u8mSOk8LrMW7wZHgskw0J"
            + "VY/EECl5BaxZKtJxIwB3W/9zaceYMgbBSE+7RcjAheRGncmF3DvuD0j40937PyV3OE48YsfPVecP"
            + "Xa+NIfVpYWwGa2QEYYvjBZ5FjD6zeubCe80fKhB2bXLM1SDXiuvoemSt161rC4m8hUvXAgMBAAEC"
            + "gYEAvOZ6QC/Q+bpZSPaEwQqAq3rLG0ApIivEub1wih7njFH65hbOrStlOZ7jCUxXdp0QfY3p/uzG"
            + "o5PBmdXO+dUQ/lcpZSJzvmlf1gfEZAL7088pe9fyvwLRuiCOzw6b+j5AoQLfXrFv3fDZlWf1z82q"
            + "Dc8cGNspYrvCSnjSRG4izQECQQD51biejn12Qan57c7nbF++xgaRmBQcURDFhFltb7vGajoYwSvp"
            + "9w42pTNL29yAPlFqx9X+FsLCu58g4TJUlDRtAkEAyzA7ET8Az3PqXcL6VwykZQE37HVnLZwb7d4u"
            + "y7+TIqXCuGDkHAjQ4bsHrGzheJI8fgqyOmvGxMY3P658aCyu0wJABJQPExDHadBgPg1GmmUZCBT2"
            + "79oanD48EXKQdPn0NfmiYOvBU0NMxmGWpBA+ZTc/JLbOzB48qXbovqCB3JzurQJBAJ/iufgeLZMQ"
            + "0ZEqRjeNeScJyGnHEIOxXcDVntkxTKRs70aK57Svsz6NH8KsgtePqw47eHfEK0rX9s2jjb2ju4UC"
            + "QQDUw3UoM9nLSmPBqPhWpNiTThOISZNmTMXEmEvb0D3A0Tpmbu6ciTr1sJRUFQi4WRTaxaqM3sdj" + "cPjXvilnNkk6";

    private static final String PUBLIC_KEY_BASE_64 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDGS4sdbvJkjpPC6zFu8GR4LJMNCVWPxBApeQWs"
            + "WSrScSMAd1v/c2nHmDIGwUhPu0XIwIXkRp3Jhdw77g9I+NPd+z8ldzhOPGLHz1XnD12vjSH1aWFs"
            + "BmtkBGGL4wWeRYw+s3rmwnvNHyoQdm1yzNUg14rr6HpkrdetawuJvIVL1wIDAQAB";

    private static final String DEVIATING_PUBLIC_KEY_BASE_64 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCbrSPQRtnu96QjsLDS0PEqZHFzoafIXOtBT+Gf"
            + "u00MKCReFgXlFRSi/Ed3q4a4+dckaf7Dn+UmF3IEV/GweU830SNAEkkE7kiSzk9Y8GrltH7rkkf+"
            + "MW+EfkS7L+J4RSteRn92Sw7auReiqT551dbtD7kd/p2NcADfHnfir0b9pwIDAQAB";

    private static final String KEY_TYPE = "RSA";
    private static final String SIGNATURE = "SHA512withRsa";
    private static final String FALLBACK_SIGNATURE = "SHA512encryptedwithRSA";
    private static final String PROVIDER = "SunRsaSign";

    private static final DateTimeFormatter FORMAT = DateTimeFormat.forPattern("yyyyMMddHHmmss");

    /**
     * Valid must pass when decryption succeeds using correct keys
     * 
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchProviderException
     */
    @Test
    public void buildOslpMessageSuccessHashWithRsa() throws IOException, NoSuchAlgorithmException,
            InvalidKeySpecException, NoSuchProviderException, Exception {
        this.buildOslpMessageSuccess(SIGNATURE);
    }

    /**
     * Valid must pass when decryption succeeds using correct keys
     * 
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchProviderException
     */
    @Test
    public void buildOslpMessageSuccessRsaEncryptedHash() throws IOException, NoSuchAlgorithmException,
            InvalidKeySpecException, NoSuchProviderException, Exception {
        this.buildOslpMessageSuccess(FALLBACK_SIGNATURE);
    }

    /**
     * 
     * @param signature
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchProviderException
     * @throws Exception
     */
    private void buildOslpMessageSuccess(final String signature) throws IOException, NoSuchAlgorithmException,
            InvalidKeySpecException, NoSuchProviderException, Exception {
        final OslpEnvelope request = this.buildMessage(signature);

        // Validate security key is set in request
        final byte[] securityKey = request.getSecurityKey();
        assertTrue(securityKey.length == OslpEnvelope.SECURITY_KEY_LENGTH);
        assertFalse(ArrayUtils.isEmpty(securityKey));

        // Verify the message using public certificate
        final OslpEnvelope response = new OslpEnvelope.Builder().withSignature(signature).withProvider(PROVIDER)
                .withSecurityKey(request.getSecurityKey()).withDeviceId(request.getDeviceId())
                .withSequenceNumber(request.getSequenceNumber()).withPayloadMessage(request.getPayloadMessage())
                .build();

        assertTrue(response.validate(CertificateHelper
                .createPublicKeyFromBase64(PUBLIC_KEY_BASE_64, KEY_TYPE, PROVIDER)));
    }

    /**
     * Valid must fail when decryption fails using incorrect keys
     * 
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchProviderException
     */
    @Test
    public void buildOslpMessageDecryptFailureHashWithRsa() throws IOException, NoSuchAlgorithmException,
            InvalidKeySpecException, NoSuchProviderException, Exception {
        this.buildOslpMessageDecryptFailure(SIGNATURE);
    }

    /**
     * Valid must fail when decryption fails using incorrect keys
     * 
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchProviderException
     */
    @Test
    public void buildOslpMessageDecryptFailureRsaEncryptedHash() throws IOException, NoSuchAlgorithmException,
            InvalidKeySpecException, NoSuchProviderException, Exception {
        this.buildOslpMessageDecryptFailure(FALLBACK_SIGNATURE);
    }

    private void buildOslpMessageDecryptFailure(final String provider) throws IOException, NoSuchAlgorithmException,
            InvalidKeySpecException, NoSuchProviderException, Exception {
        final OslpEnvelope request = this.buildMessage();

        // Verify the message using wrong public certificate
        final OslpEnvelope response = new OslpEnvelope.Builder().withSignature(SIGNATURE).withProvider(PROVIDER)
                .withSecurityKey(request.getSecurityKey()).withDeviceId(request.getDeviceId())
                .withSequenceNumber(request.getSequenceNumber()).withPayloadMessage(request.getPayloadMessage())
                .build();

        assertFalse(response.validate(CertificateHelper.createPublicKeyFromBase64(DEVIATING_PUBLIC_KEY_BASE_64,
                KEY_TYPE, PROVIDER)));
    }

    /**
     * Valid must fail when message is changed and hash does not match
     * 
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchProviderException
     */
    @Test
    public void buildOslpMessageSignatureFailure() throws IOException, NoSuchAlgorithmException,
            InvalidKeySpecException, NoSuchProviderException {
        final OslpEnvelope request = this.buildMessage();

        final byte[] fakeDeviceId = new byte[] { 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9 };

        // Validate security key is set in request
        final byte[] securityKey = request.getSecurityKey();
        assertTrue(securityKey.length == OslpEnvelope.SECURITY_KEY_LENGTH);
        assertFalse(ArrayUtils.isEmpty(securityKey));

        // Verify the message using public certificate
        final OslpEnvelope response = new OslpEnvelope.Builder().withSignature(SIGNATURE).withProvider(PROVIDER)
                .withSecurityKey(request.getSecurityKey()).withDeviceId(fakeDeviceId)
                .withSequenceNumber(request.getSequenceNumber()).withPayloadMessage(request.getPayloadMessage())
                .build();

        assertFalse(response.validate(CertificateHelper.createPublicKeyFromBase64(PUBLIC_KEY_BASE_64, KEY_TYPE,
                PROVIDER)));
    }

    /**
     * Valid must fail when decryption fails using incorrect keys
     * 
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchProviderException
     */
    @Test(expected = IllegalArgumentException.class)
    public void buildOslpMessageIncorrectSignature() throws IOException, NoSuchAlgorithmException,
            InvalidKeySpecException, NoSuchProviderException {
        final byte[] deviceId = new byte[] { 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 };
        final byte[] sequenceNumber = new byte[] { 0, 1 };

        final Message message = this.buildRegisterResponse();

        new OslpEnvelope.Builder().withSignature("Incorrect").withProvider(PROVIDER)
                .withPrimaryKey(CertificateHelper.createPrivateKeyFromBase64(PRIVATE_KEY_BASE_64, KEY_TYPE, PROVIDER))
                .withDeviceId(deviceId).withSequenceNumber(sequenceNumber).withPayloadMessage(message).build();
    }

    /**
     * Valid must fail when decryption fails using incorrect keys
     * 
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchProviderException
     */
    @Test(expected = IllegalArgumentException.class)
    public void buildOslpMessageIncorrectProvider() throws IOException, NoSuchAlgorithmException,
            InvalidKeySpecException, NoSuchProviderException {
        final byte[] deviceId = new byte[] { 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 };
        final byte[] sequenceNumber = new byte[] { 0, 1 };

        final Message message = this.buildRegisterResponse();

        new OslpEnvelope.Builder().withSignature(SIGNATURE).withProvider("Incorrect")
                .withPrimaryKey(CertificateHelper.createPrivateKeyFromBase64(PRIVATE_KEY_BASE_64, KEY_TYPE, PROVIDER))
                .withDeviceId(deviceId).withSequenceNumber(sequenceNumber).withPayloadMessage(message).build();
    }

    private OslpEnvelope buildMessage() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException,
            NoSuchProviderException {
        return this.buildMessage(SIGNATURE);
    }

    private OslpEnvelope buildMessage(final String signature) throws NoSuchAlgorithmException, InvalidKeySpecException,
            IOException, NoSuchProviderException {
        final byte[] deviceId = new byte[] { 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 };
        final byte[] sequenceNumber = new byte[] { 0, 1 };

        final OslpEnvelope message = new OslpEnvelope.Builder().withSignature(signature).withProvider(PROVIDER)
                .withPrimaryKey(CertificateHelper.createPrivateKeyFromBase64(PRIVATE_KEY_BASE_64, KEY_TYPE, PROVIDER))
                .withDeviceId(deviceId).withSequenceNumber(sequenceNumber)
                .withPayloadMessage(this.buildRegisterResponse()).build();

        return message;
    }

    private Message buildRegisterResponse() {
        // Both random numbers should be between 0 and 65535 (16 bit value).
        final int randomDevice = 53568;
        final int randomPlatform = 17643;

        return Message
                .newBuilder()
                .setRegisterDeviceResponse(
                        RegisterDeviceResponse.newBuilder().setStatus(Oslp.Status.OK)
                                .setCurrentTime(Instant.now().toString(FORMAT)).setRandomDevice(randomDevice)
                                .setRandomPlatform(randomPlatform)).build();
    }
}
