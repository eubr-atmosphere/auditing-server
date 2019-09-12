package cloud.fogbow.auditingserver.core;

import cloud.fogbow.auditingserver.core.datastore.DatabaseManager;
import cloud.fogbow.auditingserver.core.models.Compute;

import java.util.List;

public class ComputeController {

    private DatabaseManager databaseManager;

    public ComputeController() {
        databaseManager = DatabaseManager.getInstance();
    }

    public List<Compute> getAll() {
        return databaseManager.getAllComputes();
    }
}
