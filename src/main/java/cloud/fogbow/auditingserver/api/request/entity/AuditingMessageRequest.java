package cloud.fogbow.auditingserver.api.request.entity;

import cloud.fogbow.auditingserver.core.models.AuditingMessage;
import cloud.fogbow.auditingserver.core.models.Compute;
import cloud.fogbow.auditingserver.core.models.FederatedNetwork;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AuditingMessageRequest {
    private List<ComputeRequestBody> activeComputes;
    private List<FederatedNetwork> activeFederatedNetworks;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
    private Timestamp currentTimestamp;
    private String fogbowSite;

    public AuditingMessageRequest(List<ComputeRequestBody> activeComputes, List<FederatedNetwork> federatedNetworks, String fogbowSite) {
        this.activeComputes = activeComputes;
        this.activeFederatedNetworks = federatedNetworks;
        this.fogbowSite = fogbowSite;
    }

    public AuditingMessage getAuditingMessage() {
        AuditingMessage message = new AuditingMessage(getActiveComputesList(), activeFederatedNetworks, fogbowSite);
        message.setCurrentTimestamp(currentTimestamp);
        return message;
    }

    private List<Compute> getActiveComputesList() {
        List<Compute> computes = new ArrayList<>();
        for (ComputeRequestBody computeRequestBody : this.activeComputes) {
            computes.add(computeRequestBody.getCompute());
        }
        return computes;
    }
}
