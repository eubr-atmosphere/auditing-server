package cloud.fogbow.auditingserver.core.models;

public enum NetworkType {
    CLOUD("cloud"),
    FEDNET("fednet");

    private String value;

    private NetworkType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
