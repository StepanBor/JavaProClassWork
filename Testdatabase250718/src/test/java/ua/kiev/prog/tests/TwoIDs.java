package ua.kiev.prog.tests;

public class TwoIDs {
    private final long clientId;
    private final long addressId;

    public TwoIDs(long clientId, long addressId) {
        this.clientId = clientId;
        this.addressId = addressId;
    }

    public long getClientId() {
        return clientId;
    }

    public long getAddressId() {
        return addressId;
    }
}
