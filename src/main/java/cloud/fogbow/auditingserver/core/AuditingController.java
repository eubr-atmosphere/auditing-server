package cloud.fogbow.auditingserver.core;

import cloud.fogbow.auditingserver.core.datastore.DatabaseManager;
import cloud.fogbow.auditingserver.core.models.*;
import cloud.fogbow.common.exceptions.UnexpectedException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AuditingController {

    private DatabaseManager databaseManager;

    public AuditingController() {
        databaseManager = DatabaseManager.getInstance();
    }

    public void processMessage(AuditingMessage message) throws UnexpectedException{
        List<Compute> activeComputes = message.getActiveComputes();
        List<FederatedNetwork> activeFednets = message.getActiveFederatedNetworks();
        Timestamp messageTimestamp = message.getCurrentTimestamp();
        String site = message.getFogbowSite();

        processComputes(activeComputes, site, messageTimestamp);
        processFednets(activeFednets, site, messageTimestamp);
    }

    private void processComputes(List<Compute> computes, String site, Timestamp messageTimestamp) {
        for(Compute compute: computes) {
            String id = compute.getId();
            Compute savedCompute = databaseManager.getCompute(id);
            for(String networkId: compute.getIpAddresses().keySet()) {
                for(Ip ip : compute.getIpAddresses().get(networkId).getIps()) {
                    if(savedCompute != null) {
                        if(!savedCompute.hasIp(networkId, ip.getAddress())) {
                            ip.setUpTime(messageTimestamp);
                            if(savedCompute.hasNetwork(networkId)) {
                                compute.getIpAddresses().get(networkId).getIps().add(ip);
                            } else {
                                List<Ip> ips = new ArrayList<>();
                                ips.add(ip);
                                compute.getIpAddresses().put(networkId, new IpGroup(ips));
                            }
                        }
                        databaseManager.saveIp(ip);
                    } else {
                        ip = databaseManager.getIpByAddress(ip.getAddress());
                        ip.setUpTime(messageTimestamp);
                        databaseManager.saveIp(ip);
                        databaseManager.saveCompute(compute);
                        return;
                    }
                }
            }

            if(savedCompute != null) {
                for(String networkId: savedCompute.getIpAddresses().keySet()) {
                    for(Ip ip: savedCompute.getIpAddresses().get(networkId).getIps()) {
                        if(!compute.hasIp(networkId, ip.getAddress())) {
                            ip.setDownTime(messageTimestamp);
                            databaseManager.saveIp(ip);
                        }
                    }
                }
                databaseManager.saveCompute(savedCompute);
            }
        }
    }

    private void processFednets(List<FederatedNetwork> federatedNetworks, String site, Timestamp messageTimestamp) throws UnexpectedException {
        for(FederatedNetwork federatedNetwork : federatedNetworks) {
            for(String computeId: federatedNetwork.getIpAddresses().keySet()) {
                String id = computeId + "@" + site;
                Compute savedCompute = databaseManager.getCompute(id);
                if(savedCompute == null) {
                    Compute compute = new Compute(computeId, federatedNetwork.getSerializedSystemUser());
                    compute.setFederatedIpAddresses(federatedNetwork.getIpAddresses().get(computeId));
                    for(Ip ip : compute.getFederatedIpAddresses()) {
                        ip.setUpTime(messageTimestamp);
                        databaseManager.saveIp(ip);
                    }
                    databaseManager.saveCompute(compute);
                } else {
                    for(Ip ip: federatedNetwork.getIpAddresses().get(computeId)) {
                        if(!savedCompute.hasIp(ip.getAddress())) {
                            ip.setUpTime(messageTimestamp);
                            databaseManager.saveIp(ip);
                            ip = databaseManager.getIpByAddress(ip.getAddress());
                            savedCompute.getFederatedIpAddresses().add(ip);
                        }
                    }
                    for(String networkId : savedCompute.getIpAddresses().keySet()) {
                        for(Ip ip: savedCompute.getIpAddresses().get(networkId).getIps()) {
                            if(!federatedNetwork.hasIp(computeId, ip.getAddress())) {
                                ip.setDownTime(messageTimestamp);
                                databaseManager.saveIp(ip);
                            }
                        }
                    }
                    for(Ip ip: savedCompute.getFederatedIpAddresses()) {
                        if(!federatedNetwork.hasIp(computeId, ip.getAddress())) {
                            ip.setDownTime(messageTimestamp);
                            databaseManager.saveIp(ip);
                        }
                    }
                    databaseManager.saveCompute(savedCompute);
                }
            }
        }
    }
}
