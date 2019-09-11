package cloud.fogbow.auditingserver.core;

import cloud.fogbow.auditingserver.core.models.AuditingMessage;

public class ApplicationFacade {
    private static ApplicationFacade instance;
    private AuditingController auditingController;

    private ApplicationFacade() {
        auditingController = new AuditingController();
    }

    public static ApplicationFacade getInstance() {
        synchronized (ApplicationFacade.class) {
            if (instance == null) {
                instance = new ApplicationFacade();
            }
            return instance;
        }
    }

    public void registerMessage(AuditingMessage message) {
        auditingController.processMessage(message);
    }
}
