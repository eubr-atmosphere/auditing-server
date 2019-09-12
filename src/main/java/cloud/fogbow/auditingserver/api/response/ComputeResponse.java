package cloud.fogbow.auditingserver.api.response;

import cloud.fogbow.auditingserver.core.models.Ip;
import cloud.fogbow.auditingserver.core.models.IpGroup;

import java.util.List;
import java.util.Map;

public class ComputeResponse {
    private String username;
    private Map<String, IpGroup> ipAddresses;
    private List<Ip> federatedIpAddresses;

    public ComputeResponse(String username, Map<String, IpGroup> ipAddresses, List<Ip> federatedIpAddresses) {
        this.username = username;
        this.ipAddresses = ipAddresses;
        this.federatedIpAddresses = federatedIpAddresses;
    }
}
