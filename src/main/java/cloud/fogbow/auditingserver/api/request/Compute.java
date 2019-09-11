package cloud.fogbow.auditingserver.api.request;

import cloud.fogbow.auditingserver.api.response.ComputeResponse;
import cloud.fogbow.auditingserver.util.Constants;
import cloud.fogbow.auditingserver.util.Messages;
import cloud.fogbow.common.exceptions.FogbowException;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = Compute.COMPUTE_ENDPOINT)
public class Compute {
    public static final String COMPUTE_ENDPOINT = Constants.AUDITING_SERVER_BASE_ENDPOINT + "compute";

    private final Logger LOGGER = Logger.getLogger(Auditing.class);

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ComputeResponse>> getAllComputes()
            throws FogbowException {
        try {
            LOGGER.debug(Messages.Api.);
            List<InstanceStatus> computeInstanceStatus =
                    ApplicationFacade.getInstance().getAllInstancesStatus(systemUserToken, ResourceType.COMPUTE);
            return new ResponseEntity<>(computeInstanceStatus, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.debug(String.format(Messages.Exception.GENERIC_EXCEPTION, e.getMessage()), e);
            throw e;
        }
    }
}
