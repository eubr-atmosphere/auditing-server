package cloud.fogbow.auditingserver.core.datastore.repositories;

import cloud.fogbow.auditingserver.core.models.Ip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IpRepository extends JpaRepository<Ip, Long> {
}
