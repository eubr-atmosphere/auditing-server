package cloud.fogbow.auditingserver.core.models;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "ip_table")
public class AssignedIp implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column
    private String address;

    @Column
    private String networkId;

    @Column
    private String computeId;

    @Column
    private NetworkType type;

    public AssignedIp() {
    }
    public AssignedIp(String address, Timestamp upTime) {
        this.address = address;
    }

    public AssignedIp(String address, Timestamp upTime, Timestamp downTime) {
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getNetworkId() {
        return networkId;
    }

    public void setNetworkId(String networkId) {
        this.networkId = networkId;
    }

    public String getComputeId() {
        return computeId;
    }

    public void setComputeId(String computeId) {
        this.computeId = computeId;
    }

    public NetworkType getType() {
        return type;
    }

    public void setType(NetworkType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssignedIp ip = (AssignedIp) o;
        return id.equals(ip.id) &&
                address.equals(ip.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, address);
    }

    @Override
    public String toString() {
        return this.address += this.computeId += this.networkId += this.type.getValue();
    }
}
