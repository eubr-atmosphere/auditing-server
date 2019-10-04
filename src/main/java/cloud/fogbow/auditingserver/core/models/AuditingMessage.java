package cloud.fogbow.auditingserver.core.models;

import cloud.fogbow.auditingserver.api.request.entities.ComputeRequest;
import java.util.List;
import java.util.Objects;

public class AuditingMessage {
    private List<ComputeRequest> computes;

    private Long currentTimestamp;

    private String fogbowSite;

    private String clientId;

    public AuditingMessage(List<ComputeRequest> computes, Long currentTimestamp, String fogbowSite) {
        this.computes = computes;
        this.currentTimestamp = currentTimestamp;
        this.fogbowSite = fogbowSite;
    }

    public List<ComputeRequest> getComputes() {
        return computes;
    }

    public void setComputes(List<ComputeRequest> computes) {
        this.computes = computes;
    }

    public Long getCurrentTimestamp() {
        return currentTimestamp;
    }

    public void setCurrentTimestamp(Long currentTimestamp) {
        this.currentTimestamp = currentTimestamp;
    }

    public String getFogbowSite() {
        return fogbowSite;
    }

    public void setFogbowSite(String fogbowSite) {
        this.fogbowSite = fogbowSite;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuditingMessage that = (AuditingMessage) o;
        return computes.equals(that.computes) &&
                currentTimestamp.equals(that.currentTimestamp) &&
                fogbowSite.equals(that.fogbowSite);
    }

    @Override
    public int hashCode() {
        return Objects.hash(computes, currentTimestamp, fogbowSite);
    }

    @Override
    public String toString() {
        String value = "";
        value += this.clientId + this.getCurrentTimestamp() + this.getFogbowSite();
        for(ComputeRequest computeRequest : this.computes) {
            value += computeRequest.toString();
        }
        return value;
    }
}
