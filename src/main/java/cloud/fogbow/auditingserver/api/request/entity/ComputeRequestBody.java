package cloud.fogbow.auditingserver.api.request.entity;

import cloud.fogbow.auditingserver.core.models.Compute;
import cloud.fogbow.auditingserver.core.models.Ip;
import cloud.fogbow.auditingserver.core.models.IpGroup;
import cloud.fogbow.common.exceptions.UnexpectedException;

import java.util.*;

public class ComputeRequestBody {

    private Map<String, List<Ip>> ipAddresses;

    private List<Ip> federatedIpAddresses;

    private String serializedSystemUser;

    private String instanceId;

    public ComputeRequestBody() {
    }

    public Map<String, IpGroup> getIpAddressesAsIpGroups() {
        Map<String, IpGroup> addresses = new HashMap<>();
        for (Map.Entry<String, List<Ip>> entry : ipAddresses.entrySet()) {
            IpGroup group = new IpGroup();
            group.setIps(entry.getValue());
            addresses.put(entry.getKey(), group);
        }
        return addresses;
    }

    public Compute getCompute() {
        Compute compute = null;
        try {
            compute = new Compute(getIpAddressesAsIpGroups(), serializedSystemUser, instanceId);
            compute.setFederatedIpAddresses(federatedIpAddresses);

        } catch (UnexpectedException e) {
            e.printStackTrace();
        }
        return compute;
    }

    public Map<String, List<Ip>> getIpAddresses() {
        return ipAddresses;
    }

    public void setIpAddresses(Map<String, List<Ip>> ipAddresses) {
        this.ipAddresses = ipAddresses;
    }

    public List<Ip> getFederatedIpAddresses() {
        return federatedIpAddresses;
    }

    public void setFederatedIpAddresses(List<Ip> federatedIpAddresses) {
        this.federatedIpAddresses = federatedIpAddresses;
    }

    public String getSerializedSystemUser() {
        return serializedSystemUser;
    }

    public void setSerializedSystemUser(String serializedSystemUser) {
        this.serializedSystemUser = serializedSystemUser;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }
}
