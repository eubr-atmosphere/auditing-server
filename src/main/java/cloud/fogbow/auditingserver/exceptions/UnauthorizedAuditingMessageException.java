package cloud.fogbow.auditingserver.exceptions;

import cloud.fogbow.common.exceptions.FogbowException;

public class UnauthorizedAuditingMessageException extends FogbowException {

    public UnauthorizedAuditingMessageException() {
        super();
    }

    public UnauthorizedAuditingMessageException(String msg) {
        super(msg);
    }
}
