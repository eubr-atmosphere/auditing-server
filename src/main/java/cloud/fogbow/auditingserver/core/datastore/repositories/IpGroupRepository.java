package cloud.fogbow.auditingserver.core.datastore.repositories;

import cloud.fogbow.auditingserver.core.models.IpGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IpGroupRepository extends JpaRepository<IpGroup, Long> {
}
