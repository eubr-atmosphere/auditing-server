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
import java.util.Properties;

@Entity
@Table(name = "compute_table")
public class Compute {
    private final String ID_SEPARATOR = "@";

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

    public Compute() {
    }

    public Compute(SystemUser systemUser, String instanceId, String site) {
        this.systemUser = systemUser;
        this.instanceId = instanceId;
        this.id = instanceId + ID_SEPARATOR + site;
    }

    public Compute(String systemUser, String instanceId, String site) throws UnexpectedException{
        Properties properties = PropertiesUtil.readProperties(HomeDir.getPath() + Constants.CONF_FILE_KEY);
        this.serializedSystemUser = systemUser;
        this.instanceId = instanceId;
        this.id = instanceId + ID_SEPARATOR + site;
        deserializeSystemUser();
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
}
