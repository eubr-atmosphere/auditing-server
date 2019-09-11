package cloud.fogbow.auditingserver.core.datastore;

import cloud.fogbow.auditingserver.core.datastore.repositories.ComputeRepository;
import cloud.fogbow.auditingserver.core.datastore.repositories.IpRepository;
import cloud.fogbow.auditingserver.core.models.Compute;
import cloud.fogbow.auditingserver.core.models.Ip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DatabaseManager {

    private static DatabaseManager instance;

    @Autowired
    private IpRepository ipRepository;

    @Autowired
    private ComputeRepository computeRepository;

    private DatabaseManager() {

    }

    public static DatabaseManager getInstance() {
        synchronized (DatabaseManager.class) {
            if(instance == null) {
                instance = new DatabaseManager();
            }
            return instance;
        }
    }

    public Compute getCompute(String id) {
        return computeRepository.getOne(id);
    }

    public void saveCompute(Compute compute) {
        computeRepository.save(compute);
    }

    public void saveIp(Ip ip) {
        ipRepository.save(ip);
    }

    public List<Compute> getAllComputes() {
        return computeRepository.findAll();
    }
}
