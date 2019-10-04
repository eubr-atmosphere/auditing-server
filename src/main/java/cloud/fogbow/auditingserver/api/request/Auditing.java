package cloud.fogbow.auditingserver.api.request;

import cloud.fogbow.auditingserver.core.ApplicationFacade;
import cloud.fogbow.auditingserver.util.Constants;
import cloud.fogbow.auditingserver.util.Messages;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping(value = Auditing.AUDITING_ENDPOINT)
public class Auditing {
    public static final String AUDITING_ENDPOINT = Constants.AUDITING_SERVER_BASE_ENDPOINT + "auditing";

    private final Logger LOGGER = Logger.getLogger(Auditing.class);

    @RequestMapping(method = RequestMethod.POST)
    public void registerMessage(
            @RequestBody String message,
            @RequestHeader(value = Constants.SYSTEM_SIGNATURE_HEADER_KEY) String messageSignature,
            @RequestHeader(value = Constants.SYSTEM_SIGNATURE_HEADER_KEY) String clientId,
            @RequestHeader(value = Constants.SYSTEM_SIGNATURE_HEADER_KEY) String key)
            throws Exception {

        try {
            LOGGER.info(Messages.Api.REGISTERING_AUDITING_MESSAGE);
            ApplicationFacade.getInstance().registerMessage(message, messageSignature, clientId, key);
        } catch (Exception e) {
            LOGGER.debug(e);
            throw e;
        }
    }
}
