package cloud.fogbow.auditingserver.core.models;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "ip_table")
public class Ip {
    @Column
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column
    private String address;

    @Column
    private Timestamp upTime;

    @Column
    private Timestamp downTime;

    public Ip(String address, Timestamp upTime) {
        this.address = address;
        this.upTime = upTime;
    }

    public Ip(String address, Timestamp upTime, Timestamp downTime) {
        this.address = address;
        this.upTime = upTime;
        this.downTime = downTime;
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

    public Timestamp getUpTime() {
        return upTime;
    }

    public void setUpTime(Timestamp upTime) {
        this.upTime = upTime;
    }

    public Timestamp getDownTime() {
        return downTime;
    }

    public void setDownTime(Timestamp downTime) {
        this.downTime = downTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ip ip = (Ip) o;
        return id.equals(ip.id) &&
                address.equals(ip.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, address);
    }


}
