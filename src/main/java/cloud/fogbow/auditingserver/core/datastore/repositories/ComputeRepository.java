package cloud.fogbow.auditingserver.core.datastore.repositories;

import cloud.fogbow.auditingserver.core.models.Compute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComputeRepository extends JpaRepository<Compute, String> {
}
