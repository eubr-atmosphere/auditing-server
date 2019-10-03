package cloud.fogbow.auditingserver.api.response;

import cloud.fogbow.auditingserver.core.models.AssignedIp;

import java.util.List;

public class ComputeResponse {
    private String username;
    private List<AssignedIp> federatedIpAddresses;

    public ComputeResponse(List<AssignedIp> federatedIpAddresses) {
        this.username = username;
        this.federatedIpAddresses = federatedIpAddresses;
    }
}
