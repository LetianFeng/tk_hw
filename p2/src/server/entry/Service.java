package server.entry;

public class Service {

    public final int serviceId;
    public final String serviceName;
    public final double price;
    public final boolean isRoom;
    public final String description;
    public final int availableAmount;

    public Service(int serviceId, String serviceName, double price, boolean isRoom, String description, int availableAmount) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.price = price;
        this.isRoom = isRoom;
        this.description = description;
        this.availableAmount = availableAmount;
    }

}
