package cloud.fogbow.auditingserver.core.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

public class AuditingMessage {
    private List<Compute> activeComputes;
    private List<FederatedNetwork> activeFederatedNetworks;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
    private Timestamp currentTimestamp;
    private String fogbowSite;

    public AuditingMessage(List<Compute> activeComputes, List<FederatedNetwork> federatedNetworks, String fogbowSite) {
        this.activeComputes = activeComputes;
        this.activeFederatedNetworks = federatedNetworks;
        this.fogbowSite = fogbowSite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuditingMessage that = (AuditingMessage) o;
        return Objects.equals(activeComputes, that.activeComputes) &&
                Objects.equals(activeFederatedNetworks, that.activeFederatedNetworks) &&
                Objects.equals(currentTimestamp, that.currentTimestamp) &&
                Objects.equals(fogbowSite, that.fogbowSite);
    }

    @Override
    public int hashCode() {
        return Objects.hash(activeComputes, activeFederatedNetworks, currentTimestamp, fogbowSite);
    }

    public void setActiveComputes(List<Compute> activeComputes) {
        this.activeComputes = activeComputes;
    }

    public void setActiveFederatedNetworks(List<FederatedNetwork> activeFederatedNetworks) {
        this.activeFederatedNetworks = activeFederatedNetworks;
    }

    public String getFogbowSite() {
        return fogbowSite;
    }

    public void setFogbowSite(String fogbowSite) {
        this.fogbowSite = fogbowSite;
    }

    public void setCurrentTimestamp(Timestamp currentTimestamp) {
        this.currentTimestamp = currentTimestamp;
    }

    public List<Compute> getActiveComputes() {
        return activeComputes;
    }

    public List<FederatedNetwork> getActiveFederatedNetworks() {
        return activeFederatedNetworks;
    }

    public Timestamp getCurrentTimestamp() {
        return currentTimestamp;
    }
}
