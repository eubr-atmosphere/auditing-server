package cloud.fogbow.auditingserver.core.datastore.repositories;

import cloud.fogbow.auditingserver.core.models.AssignedIpEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignedIpEventRepository extends JpaRepository<AssignedIpEvent, Long> {
    AssignedIpEvent findTopByIpIdOrderByEventTimestampAsc(Long ipId);
}
