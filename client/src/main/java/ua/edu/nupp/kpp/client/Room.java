package ua.edu.nupp.kpp.client;

public class Room {
    private Long id;
    private String number;
    private int floor;
    private String type;
    private String status;

    public Room() {}
    public Room(Long id, String number, int floor, String type, String status) {
        this.id = id; this.number = number; this.floor = floor;
        this.type = type; this.status = status;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }
    public int getFloor() { return floor; }
    public void setFloor(int floor) { this.floor = floor; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}