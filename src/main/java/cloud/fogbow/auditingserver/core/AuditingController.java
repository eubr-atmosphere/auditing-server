package cloud.fogbow.auditingserver.core;

import cloud.fogbow.auditingserver.core.datastore.DatabaseManager;
import cloud.fogbow.auditingserver.core.models.AuditingMessage;
import cloud.fogbow.auditingserver.core.models.Compute;
import cloud.fogbow.auditingserver.core.models.FederatedNetwork;
import cloud.fogbow.auditingserver.core.models.Ip;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AuditingController {

    private DatabaseManager databaseManager;

    public AuditingController() {
        databaseManager = DatabaseManager.getInstance();
    }

    public void processMessage(AuditingMessage message) {
        List<Compute> activeComputes = message.getActiveComputes();
        List<FederatedNetwork> activeFednets = message.getActiveFederatedNetworks();
        Timestamp messageTimestamp = message.getCurrentTimestamp();
        String site = message.getFogbowSite();

        processComputes(activeComputes, site, messageTimestamp);
//        processFednets(activeFednets, );
    }

    private void processComputes(List<Compute> computes, String site, Timestamp messageTimestamp) {
        for(Compute compute: computes) {
            String id = compute.getId() + "@" + site;
            Compute savedCompute = databaseManager.getCompute(id);
            for(String networkId: compute.getIpAddresses().keySet()) {
                for(Ip ip : compute.getIpAddresses().get(networkId)) {
                    if(savedCompute != null) {
                        if(!savedCompute.hasIp(networkId, ip.getId())) {
                            ip.setUpTime(messageTimestamp);
                            if(savedCompute.hasNetwork(networkId)) {
                                compute.getIpAddresses().get(networkId).add(ip);
                            } else {
                                List<Ip> ips = new ArrayList<>();
                                ips.add(ip);
                                compute.getIpAddresses().put(networkId, ips);
                            }
                        }
                        databaseManager.saveIp(ip);
                    } else {
                        ip.setUpTime(messageTimestamp);
                        databaseManager.saveIp(ip);
                        databaseManager.saveCompute(compute);
                        return;
                    }
                }
            }

            if(savedCompute != null) {
                for(String networkId: savedCompute.getIpAddresses().keySet()) {
                    for(Ip ip: savedCompute.getIpAddresses().get(networkId)) {
                        if(!compute.hasIp(networkId, ip.getId())) {
                            ip.setDownTime(messageTimestamp);
                            databaseManager.saveIp(ip);
                        }
                    }
                }
                databaseManager.saveCompute(savedCompute);
            }
        }
    }
}
