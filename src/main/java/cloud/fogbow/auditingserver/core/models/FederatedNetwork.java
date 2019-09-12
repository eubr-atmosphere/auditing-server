package cloud.fogbow.auditingserver.core.models;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FederatedNetwork {
    private String serializedSystemUser;
    private Map<String, List<Ip>> ipAddresses;

    public FederatedNetwork(String serializedSystemUser, Map<String, List<Ip>> ipAddresses) {
        this.serializedSystemUser = serializedSystemUser;
        this.ipAddresses = ipAddresses;
    }

    public String getSerializedSystemUser() {
        return serializedSystemUser;
    }

    public void setSerializedSystemUser(String serializedSystemUser) {
        this.serializedSystemUser = serializedSystemUser;
    }

    public Map<String, List<Ip>> getIpAddresses() {
        return ipAddresses;
    }

    public void setIpAddresses(Map<String, List<Ip>> ipAddresses) {
        this.ipAddresses = ipAddresses;
    }

    public boolean hasIp(String computeId, String address) {
        for(Ip ip: this.getIpAddresses().get(computeId)) {
            if(ip.getAddress().equals(address)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FederatedNetwork that = (FederatedNetwork) o;
        return Objects.equals(serializedSystemUser, that.serializedSystemUser) &&
                Objects.equals(ipAddresses, that.ipAddresses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serializedSystemUser, ipAddresses);
    }
}
