package ua.edu.nupp.kpp.client;

public class Ticket {
    private Long id;
    private long roomId;
    private Long specialistId;
    private String title;
    private String description;
    private TicketState state;
    private double f1;
    private double f2;
    private double f3;
    private double impactIndex;

    public Ticket() {}
    public Ticket(Long id, long roomId, Long specialistId, String title,
                  String description, TicketState state,
                  double f1, double f2, double f3, double impactIndex) {
        this.id = id; this.roomId = roomId; this.specialistId = specialistId;
        this.title = title; this.description = description; this.state = state;
        this.f1 = f1; this.f2 = f2; this.f3 = f3; this.impactIndex = impactIndex;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public long getRoomId() { return roomId; }
    public void setRoomId(long roomId) { this.roomId = roomId; }
    public Long getSpecialistId() { return specialistId; }
    public void setSpecialistId(Long specialistId) { this.specialistId = specialistId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public TicketState getState() { return state; }
    public void setState(TicketState state) { this.state = state; }
    public double getF1() { return f1; }
    public void setF1(double f1) { this.f1 = f1; }
    public double getF2() { return f2; }
    public void setF2(double f2) { this.f2 = f2; }
    public double getF3() { return f3; }
    public void setF3(double f3) { this.f3 = f3; }
    public double getImpactIndex() { return impactIndex; }
    public void setImpactIndex(double impactIndex) { this.impactIndex = impactIndex; }
}