package cloud.fogbow.auditingserver.api.request;

import cloud.fogbow.auditingserver.core.ApplicationFacade;
import cloud.fogbow.auditingserver.core.models.AuditingMessage;
import cloud.fogbow.auditingserver.util.Constants;
import cloud.fogbow.auditingserver.util.Messages;
import cloud.fogbow.common.exceptions.FogbowException;
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
            @RequestBody AuditingMessage message)
            throws FogbowException {

        try {
            LOGGER.info(Messages.Api.REGISTERING_AUDITING_MESSAGE);
            ApplicationFacade.getInstance().registerMessage(message);
        } catch (Exception e) {
            LOGGER.debug(e);
            throw e;
        }
    }
}
