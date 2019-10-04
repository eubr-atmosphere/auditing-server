package cloud.fogbow.auditingserver;

import cloud.fogbow.auditingserver.core.datastore.DatabaseManager;
import cloud.fogbow.auditingserver.core.datastore.repositories.AssignedIpEventRepository;
import cloud.fogbow.auditingserver.core.datastore.repositories.ComputeRepository;
import cloud.fogbow.auditingserver.core.datastore.repositories.IpRepository;
import cloud.fogbow.common.constants.FogbowConstants;
import cloud.fogbow.common.util.HomeDir;
import cloud.fogbow.common.util.ServiceAsymmetricKeysHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class Main implements ApplicationRunner {

    @Autowired
    private IpRepository ipRepository;
    @Autowired
    private ComputeRepository computeRepository;
    @Autowired
    private AssignedIpEventRepository assignedIpEventRepository;

    @Override
    public void run(ApplicationArguments args) {
        DatabaseManager.getInstance().setComputeRepository(computeRepository);
        DatabaseManager.getInstance().setIpRepository(ipRepository);
        DatabaseManager.getInstance().setIpEventReposityory(assignedIpEventRepository);

        String publicKeyFilePath = HomeDir.getPath() + "public.key";
        String privateKeyFilePath = HomeDir.getPath() + "private.key";
        ServiceAsymmetricKeysHolder.getInstance().setPublicKeyFilePath(publicKeyFilePath);
        ServiceAsymmetricKeysHolder.getInstance().setPrivateKeyFilePath(privateKeyFilePath);
    }
}
