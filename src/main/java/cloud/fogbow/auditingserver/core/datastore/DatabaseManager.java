package cloud.fogbow.auditingserver.core.datastore;

import cloud.fogbow.auditingserver.core.datastore.repositories.AssignedIpEventRepository;
import cloud.fogbow.auditingserver.core.datastore.repositories.ComputeRepository;
import cloud.fogbow.auditingserver.core.datastore.repositories.IpRepository;
import cloud.fogbow.auditingserver.core.models.AssignedIpEvent;
import cloud.fogbow.auditingserver.core.models.Compute;
import cloud.fogbow.auditingserver.core.models.AssignedIp;

import java.util.List;

public class DatabaseManager {

    private static DatabaseManager instance;

    private IpRepository ipRepository;
    private ComputeRepository computeRepository;
    private AssignedIpEventRepository ipEventRepository;

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

    public AssignedIpEventRepository getIpEventReposityory() {
        return ipEventRepository;
    }

    public void setIpEventReposityory(AssignedIpEventRepository ipEventReposityory) {
        this.ipEventRepository = ipEventReposityory;
    }

    public Compute getCompute(String id) {
        return computeRepository.findById(id).orElse(null);
    }

    public void saveCompute(Compute compute) {
        computeRepository.save(compute);
    }

    public void saveIp(AssignedIp ip) {
        ipRepository.save(ip);
    }

    public AssignedIp getIp(AssignedIp ip) {
        List<AssignedIp> assignedIps = ipRepository.findByAddressAndNetworkIdAndComputeIdAndType(ip.getAddress(), ip.getNetworkId(), ip.getComputeId(), ip.getType());
        return assignedIps.isEmpty() ? null : assignedIps.iterator().next();
    }

    public List<Compute> getAllComputes() {
        return computeRepository.findAll();
    }

    public AssignedIp getIpByAddress(String address) {
        return ipRepository.findByAddress(address);
    }

    public void saveIpEvent(AssignedIpEvent ipEvent) {
        ipEventRepository.save(ipEvent);
    }

    public AssignedIpEvent getLastEventIpEntry(Long id) {
        return ipEventRepository.findTopByIpIdOrderByEventTimestampAsc(id);
    }

    public List<AssignedIp> getIpsByComputeId(String computeId) {
        return ipRepository.findByComputeId(computeId);
    }


}
