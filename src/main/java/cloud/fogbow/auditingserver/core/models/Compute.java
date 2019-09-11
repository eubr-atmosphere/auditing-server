package cloud.fogbow.auditingserver.core.models;

import cloud.fogbow.auditingserver.util.Constants;
import cloud.fogbow.auditingserver.util.Messages;
import cloud.fogbow.common.exceptions.UnexpectedException;
import cloud.fogbow.common.models.SystemUser;
import cloud.fogbow.common.util.GsonHolder;
import cloud.fogbow.common.util.HomeDir;
import cloud.fogbow.common.util.PropertiesUtil;
import cloud.fogbow.common.util.SerializedEntityHolder;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

@Entity
@Table(name = "compute_table")
public class Compute {
    private final String ID_SEPARATOR = "@";

    @OneToMany
    private Map<String, List<Ip>> ipAddresses;

    @Column
    @Size(max = SystemUser.SERIALIZED_SYSTEM_USER_MAX_SIZE)
    private String serializedSystemUser;

    @Column
    private String instanceId;

    @Transient
    private SystemUser systemUser;

    @Column
    @Id
    private String id;

    public Compute(Map<String, List<Ip>>ipAddresses, SystemUser systemUser, String instanceId) {
        Properties properties = PropertiesUtil.readProperties(HomeDir.getPath() + Constants.CONF_FILE_KEY);
        this.ipAddresses = ipAddresses;
        this.systemUser = systemUser;
        this.instanceId = instanceId;
        this.id = instanceId + ID_SEPARATOR + properties.getProperty(Constants.SITE_KEY);
    }

    public Compute(Map<String, List<Ip>> ipAddresses, String systemUser, String instanceId) {
        Properties properties = PropertiesUtil.readProperties(HomeDir.getPath() + Constants.CONF_FILE_KEY);
        this.ipAddresses = ipAddresses;
        this.serializedSystemUser = systemUser;
        this.instanceId = instanceId;
        this.id = instanceId + ID_SEPARATOR + properties.getProperty(Constants.SITE_KEY);
    }

    public boolean hasNetwork(String networkId) {
        return this.getIpAddresses().containsKey(networkId);
    }

    public boolean hasIp(String networkId, Long id) {
        if(!this.getIpAddresses().containsKey(networkId)) {
            return false;
        }
        for(Ip ip: this.getIpAddresses().get(networkId)) {
            if(ip.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public Map<String, List<Ip>> getIpAddresses() {
        return ipAddresses;
    }

    public void setIpAddresses(Map<String, List<Ip>> ipAddresses) {
        this.ipAddresses = ipAddresses;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SystemUser getSystemUser() {
        return systemUser;
    }

    public void setSystemUser(SystemUser systemUser) {
        this.systemUser = systemUser;
    }

    // Cannot be called at @PrePersist because the transient field systemUser is set to null at this stage
    // Instead, the systemUser is explicitly serialized before being save by RecoveryService.save().
    public void serializeSystemUser(SystemUser systemUser) {
        SerializedEntityHolder<SystemUser> serializedSystemUserHolder = new SerializedEntityHolder<>(systemUser);
        this.setSerializedSystemUser(GsonHolder.getInstance().toJson(serializedSystemUserHolder));
    }

    @PostLoad
    private void deserializeSystemUser() throws UnexpectedException {
        try {
            SerializedEntityHolder serializedSystemUserHolder = GsonHolder.getInstance().fromJson(
                    this.getSerializedSystemUser(), SerializedEntityHolder.class);
            this.setSystemUser((SystemUser) serializedSystemUserHolder.getSerializedEntity());
        } catch(ClassNotFoundException exception) {
            throw new UnexpectedException(Messages.Exception.UNABLE_TO_DESERIALIZE_SYSTEM_USER);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Compute compute = (Compute) o;
        return Objects.equals(ipAddresses, compute.ipAddresses) &&
                Objects.equals(serializedSystemUser, compute.serializedSystemUser) &&
                Objects.equals(instanceId, compute.instanceId) &&
                Objects.equals(id, compute.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ipAddresses, serializedSystemUser, instanceId, id);
    }
}
