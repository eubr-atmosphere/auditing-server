package cloud.fogbow.auditingserver.api.response;

import cloud.fogbow.auditingserver.core.models.Ip;

import java.util.List;
import java.util.Map;

public class ComputeResponse {
    private String username;
    private Map<String, List<Ip>> ipAddresses;
    private List<Ip> federatedIpAddresses;

    public ComputeResponse(String username, Map<String, List<Ip>> ipAddresses, List<Ip> federatedIpAddresses) {
        this.username = username;
        this.ipAddresses = ipAddresses;
        this.federatedIpAddresses = federatedIpAddresses;
    }
}
