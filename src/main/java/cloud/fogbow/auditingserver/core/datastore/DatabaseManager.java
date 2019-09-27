package cloud.fogbow.auditingserver.core.datastore;

import cloud.fogbow.auditingserver.core.datastore.repositories.ComputeRepository;
import cloud.fogbow.auditingserver.core.datastore.repositories.IpRepository;
import cloud.fogbow.auditingserver.core.models.Compute;
import cloud.fogbow.auditingserver.core.models.Ip;;

import java.util.List;

public class DatabaseManager {

    private static DatabaseManager instance;

    private IpRepository ipRepository;
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

    public IpRepository getIpRepository() {
        return ipRepository;
    }

    public void setIpRepository(IpRepository ipRepository) {
        this.ipRepository = ipRepository;
    }

    public ComputeRepository getComputeRepository() {
        return computeRepository;
    }

    public void setComputeRepository(ComputeRepository computeRepository) {
        this.computeRepository = computeRepository;
    }

    public Compute getCompute(String id) {
        return computeRepository.findById(id).orElse(null);
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

    public Ip getIpByAddress(String address) {
        return ipRepository.findByAddress(address);
    }
}
