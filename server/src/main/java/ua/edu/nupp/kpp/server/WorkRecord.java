package ua.edu.nupp.kpp.server;

public class WorkRecord {
    private Long id;
    private long ticketId;
    private String performedAt;
    private String comment;
    private double hoursSpent;

    public WorkRecord() {}
    public WorkRecord(Long id, long ticketId, String performedAt, String comment, double hoursSpent) {
        this.id = id; this.ticketId = ticketId; this.performedAt = performedAt;
        this.comment = comment; this.hoursSpent = hoursSpent;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public long getTicketId() { return ticketId; }
    public void setTicketId(long ticketId) { this.ticketId = ticketId; }
    public String getPerformedAt() { return performedAt; }
    public void setPerformedAt(String performedAt) { this.performedAt = performedAt; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
    public double getHoursSpent() { return hoursSpent; }
    public void setHoursSpent(double hoursSpent) { this.hoursSpent = hoursSpent; }
}