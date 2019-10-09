package cloud.fogbow.auditingserver.core;

import cloud.fogbow.auditingserver.api.request.entities.ComputeRequest;
import cloud.fogbow.auditingserver.core.datastore.DatabaseManager;
import cloud.fogbow.auditingserver.core.models.*;
import cloud.fogbow.common.exceptions.FogbowException;
import org.apache.log4j.Logger;

import java.util.List;

public class AuditingController {
    private static final Logger LOGGER = Logger.getLogger(AuditingController.class);

    private DatabaseManager databaseManager;

    public AuditingController() {
        databaseManager = DatabaseManager.getInstance();
    }

    public void processMessage(AuditingMessage message) throws FogbowException{
        LOGGER.info("processing message with " + message.getActiveComputes().size() + "computes");
        for(ComputeRequest computeRequest: message.getActiveComputes()) {
            String computeId = computeRequest.getInstanceId() + '@' + message.getFogbowSite();
            Compute compute = databaseManager.getCompute(computeId);
            if(compute == null) {
                processNonExistentCompute(computeRequest, message.getFogbowSite(), message.getCurrentTimestamp());
            } else {
                processExistentCompute(compute, computeRequest.getAssignedIps(), message.getCurrentTimestamp());
            }
        }
    }

    private void processNonExistentCompute(ComputeRequest computeRequest, String site, Long messageTimestamp) throws FogbowException {
        Compute compute = new Compute(computeRequest.getSerializedSystemUser(), computeRequest.getInstanceId(), site);
        LOGGER.info("saving non existent compute in the database");
        databaseManager.saveCompute(compute);
        for(AssignedIp ip: computeRequest.getAssignedIps()) {
            saveNewIp(ip, compute.getId(), messageTimestamp);
        }
    }

    private void processExistentCompute(Compute compute, List<AssignedIp> ips, Long messageTimestamp) {
        for(AssignedIp ip : ips) {
            ip.setComputeId(compute.getId());
            AssignedIp savedIp = databaseManager.getIp(ip);
            if(savedIp == null) {
                saveNewIp(ip, compute.getId(), messageTimestamp);
            } else {
                AssignedIpEvent lastIpEventEntry = databaseManager.getLastEventIpEntry(savedIp.getId());
                if(!lastIpEventEntry.isUpEvent()) {
                    databaseManager.saveIpEvent(new AssignedIpEvent(savedIp.getId(), messageTimestamp, true));
                }
            }
        }

        List<AssignedIp> savedIps = databaseManager.getIpsByComputeId(compute.getId());
        for(AssignedIp ip: savedIps) {
            if(!isIncomingIp(ip, ips)) {
                AssignedIpEvent lastIpEventEntry = databaseManager.getLastEventIpEntry(ip.getId());
                if(lastIpEventEntry.isUpEvent()) {
                    databaseManager.saveIpEvent(new AssignedIpEvent(ip.getId(), messageTimestamp, false));
                }
            }
        }
    }

    private void saveNewIp(AssignedIp ip, String computeId, Long timestamp) {
        LOGGER.info("saving new ip with address" + ip.getAddress());
        ip.setComputeId(computeId);
        databaseManager.saveIp(ip);
        ip = databaseManager.getIp(ip);
        databaseManager.saveIpEvent(new AssignedIpEvent(ip.getId(), timestamp, true));
    }

    private boolean isIncomingIp(AssignedIp ip, List<AssignedIp> ips) {
        for(AssignedIp currentIp: ips) {
            if(ip.getAddress().equals(currentIp.getAddress()) &&
                ip.getComputeId().equals(currentIp.getComputeId()) &&
                ip.getNetworkId().equals(currentIp.getNetworkId()) &&
                ip.getType().equals(currentIp.getType())) {
                return true;
            }
        }

        return false;
    }
}
