package cloud.fogbow.auditingserver.core.datastore.repositories;

import cloud.fogbow.auditingserver.core.models.AssignedIp;
import cloud.fogbow.auditingserver.core.models.NetworkType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IpRepository extends JpaRepository<AssignedIp, Long> {
    AssignedIp findByAddress(String address);

    List<AssignedIp> findByComputeId(String computeId);

    List<AssignedIp> findByAddressAndNetworkIdAndComputeIdAndType(String address, String networkId, String computeId, NetworkType type);
}
