package cloud.fogbow.auditingserver.api.request;

import cloud.fogbow.auditingserver.core.ApplicationFacade;
import cloud.fogbow.auditingserver.core.models.AuditingMessage;
import cloud.fogbow.auditingserver.exceptions.UnauthorizedAuditingMessageException;
import cloud.fogbow.auditingserver.util.Constants;
import cloud.fogbow.auditingserver.util.Messages;
import cloud.fogbow.common.util.CryptoUtil;
import cloud.fogbow.common.util.HomeDir;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.security.interfaces.RSAPublicKey;

@CrossOrigin
@RestController
@RequestMapping(value = Auditing.AUDITING_ENDPOINT)
public class Auditing {
    public static final String AUDITING_ENDPOINT = Constants.AUDITING_SERVER_BASE_ENDPOINT + "auditing";

    private final Logger LOGGER = Logger.getLogger(Auditing.class);

    @RequestMapping(method = RequestMethod.POST)
    public void registerMessage(
            @RequestBody AuditingMessage message,
            @RequestHeader(required = false, value = Constants.SYSTEM_SIGNATURE_HEADER_KEY) String messageSignature)
            throws Exception {

        try {
            LOGGER.info(Messages.Api.REGISTERING_AUDITING_MESSAGE);
            String publicKeyPath = HomeDir.getPath() + "/clientkeys/" + message.getClientId() + ".pub'";
            RSAPublicKey publicKey = CryptoUtil.getPublicKey(publicKeyPath);
            if(!CryptoUtil.verify(publicKey, message.toString(), messageSignature)) {
                throw new UnauthorizedAuditingMessageException("The signature doesn't match the specifications");
            }
            ApplicationFacade.getInstance().registerMessage(message);
        } catch (Exception e) {
            LOGGER.debug(e);
            throw e;
        }
    }
}
