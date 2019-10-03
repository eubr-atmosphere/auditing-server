package cloud.fogbow.auditingserver.api.request.entities;

import cloud.fogbow.auditingserver.core.models.AssignedIp;

import java.util.List;
import java.util.Objects;

public class ComputeRequest {

    private String instanceId;

    private List<AssignedIp> assignedIps;

    private String serializedSystemUser;

    public ComputeRequest(String instanceId, String site, List<AssignedIp> ips, String serializedSystemUser) {
        this.instanceId = instanceId;
        this.assignedIps = ips;
        this.serializedSystemUser = serializedSystemUser;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public List<AssignedIp> getAssignedIps() {
        return assignedIps;
    }

    public void setAssignedIps(List<AssignedIp> assignedIps) {
        this.assignedIps = assignedIps;
    }

    public String getSerializedSystemUser() {
        return serializedSystemUser;
    }

    public void setSerializedSystemUser(String serializedSystemUser) {
        this.serializedSystemUser = serializedSystemUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComputeRequest that = (ComputeRequest) o;
        return instanceId.equals(that.instanceId) &&
                assignedIps.equals(that.assignedIps) &&
                serializedSystemUser.equals(that.serializedSystemUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instanceId, assignedIps, serializedSystemUser);
    }
}
