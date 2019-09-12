package cloud.fogbow.auditingserver.core;

import cloud.fogbow.auditingserver.api.response.ComputeResponse;
import cloud.fogbow.auditingserver.core.models.AuditingMessage;
import cloud.fogbow.auditingserver.core.models.Compute;
import cloud.fogbow.common.exceptions.UnexpectedException;

import java.util.ArrayList;
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

    public void registerMessage(AuditingMessage message) throws UnexpectedException {
        auditingController.processMessage(message);
    }

    public List<ComputeResponse> getAllComputes() {
        List<Compute> computes = computeController.getAll();
        List<ComputeResponse> computeResponses = new ArrayList<>();
        for(Compute compute: computes) {
            computeResponses.add(
                new ComputeResponse(compute.getSystemUser().getName(), compute.getIpAddresses(), compute.getFederatedIpAddresses())
            );
        }
        return computeResponses;
    }
}
