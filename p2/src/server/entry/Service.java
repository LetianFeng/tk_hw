package server.entry;

public class Service {

    private final int serviceId;
    private final String serviceName;
    private final double price;
    private final boolean isRoom;
    private final String description;
    private final int availableAmount;

    public Service(int serviceId, String serviceName, double price, boolean isRoom, String description, int availableAmount) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.price = price;
        this.isRoom = isRoom;
        this.description = description;
        this.availableAmount = availableAmount;
    }

}
