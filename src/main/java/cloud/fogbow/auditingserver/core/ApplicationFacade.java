package cloud.fogbow.auditingserver.core;

import cloud.fogbow.auditingserver.api.response.ComputeResponse;
import cloud.fogbow.auditingserver.core.models.AuditingMessage;
import cloud.fogbow.auditingserver.exceptions.UnauthorizedAuditingMessageException;
import cloud.fogbow.common.exceptions.FogbowException;
import cloud.fogbow.common.exceptions.UnexpectedException;
import cloud.fogbow.common.util.CryptoUtil;
import cloud.fogbow.common.util.HomeDir;
import cloud.fogbow.common.util.ServiceAsymmetricKeysHolder;
import com.google.gson.Gson;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;

public class ApplicationFacade {
    private static ApplicationFacade instance;
    private AuditingController auditingController;
    private ComputeController computeController;

    private ApplicationFacade() {
        auditingController = new AuditingController();
        computeController = new ComputeController();
    }

    public static ApplicationFacade getInstance() {
        synchronized (ApplicationFacade.class) {
            if (instance == null) {
                instance = new ApplicationFacade();
            }
            return instance;
        }
    }

    public String getPublicKey() throws FogbowException{
        try {
            return CryptoUtil.toBase64(ServiceAsymmetricKeysHolder.getInstance().getPublicKey());
        } catch (IOException | GeneralSecurityException e) {
            throw new UnexpectedException(e.getMessage(), e);
        }
    }

    public void registerMessage(String message, String messageSignature, String clientId, String key) throws Exception {
        AuditingMessage auditingMessage = handleSecurityIssues(message, messageSignature, clientId, key);
        auditingController.processMessage(auditingMessage);
    }

    private AuditingMessage handleSecurityIssues(String message, String messageSignature, String clientId, String key) throws Exception{
        String privateKeyPath = HomeDir.getPath() + "private.key";
        RSAPrivateKey privateKey = CryptoUtil.getPrivateKey(privateKeyPath);

        String simKey = CryptoUtil.decrypt(key, privateKey);
        String body = CryptoUtil.decryptAES(simKey.getBytes(), message);

        String publicKeyPath = HomeDir.getPath() + "/clientkeys/" + clientId + ".pub";
        RSAPublicKey publicKey = CryptoUtil.getPublicKey(publicKeyPath);
        if(!CryptoUtil.verify(publicKey, body, messageSignature)) {
            throw new UnauthorizedAuditingMessageException("The signature doesn't match the specifications");
        }

        return new Gson().fromJson(body, AuditingMessage.class);
    }

    public List<ComputeResponse> getAllComputes() {
        return null;
    }
}
