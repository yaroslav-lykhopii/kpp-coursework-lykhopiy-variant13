package ua.edu.nupp.kpp.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class HotelClient {
    private static final String BASE = "http://localhost:8080/hotel-tickets-server";
    private final HttpClient http = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    // ================== ROOMS ==================
    public List<Room> getRooms() {
        try {
            HttpRequest req = HttpRequest.newBuilder(URI.create(BASE + "/rooms")).GET().build();
            HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            return Arrays.asList(mapper.readValue(resp.body(), Room[].class));
        } catch (Exception e) { throw new RuntimeException("HTTP Error", e); }
    }

    public void addRoom(Room r) {
        String form = "action=add&number=" + enc(r.getNumber()) +
                "&floor=" + r.getFloor() +
                "&type=" + enc(r.getType()) +
                "&status=" + enc(r.getStatus());
        post("/rooms", form);
    }

    public void updateRoom(Room r) {
        String form = "action=update&id=" + r.getId() +
                "&number=" + enc(r.getNumber()) +
                "&floor=" + r.getFloor() +
                "&type=" + enc(r.getType()) +
                "&status=" + enc(r.getStatus());
        post("/rooms", form);
    }

    public void deleteRoom(long id) {
        post("/rooms", "action=delete&id=" + id);
    }

    // ================== SPECIALISTS ==================
    public List<Specialist> getSpecialists() {
        try {
            HttpRequest req = HttpRequest.newBuilder(URI.create(BASE + "/specialists")).GET().build();
            HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            return Arrays.asList(mapper.readValue(resp.body(), Specialist[].class));
        } catch (Exception e) { throw new RuntimeException("HTTP Error", e); }
    }

    public void addSpecialist(Specialist s) {
        String form = "action=add&fullName=" + enc(s.getFullName()) +
                "&specialization=" + enc(s.getSpecialization()) +
                "&phone=" + enc(s.getPhone());
        post("/specialists", form);
    }

    public void updateSpecialist(Specialist s) {
        String form = "action=update&id=" + s.getId() +
                "&fullName=" + enc(s.getFullName()) +
                "&specialization=" + enc(s.getSpecialization()) +
                "&phone=" + enc(s.getPhone());
        post("/specialists", form);
    }

    public void deleteSpecialist(long id) {
        post("/specialists", "action=delete&id=" + id);
    }

    // ================== TICKETS ==================
    public List<Ticket> getTickets() {
        try {
            HttpRequest req = HttpRequest.newBuilder(URI.create(BASE + "/tickets")).GET().build();
            HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            return Arrays.asList(mapper.readValue(resp.body(), Ticket[].class));
        } catch (Exception e) { throw new RuntimeException("HTTP Error", e); }
    }

    public void addTicket(Ticket t) {
        String form = "action=add&roomId=" + t.getRoomId() +
                "&specialistId=" + (t.getSpecialistId() == null ? "" : t.getSpecialistId()) +
                "&title=" + enc(t.getTitle()) +
                "&description=" + enc(t.getDescription()) +
                "&f1=" + t.getF1() +
                "&f2=" + t.getF2() +
                "&f3=" + t.getF3();
        post("/tickets", form);
    }

    public void updateTicket(Ticket t) {
        String form = "action=update&id=" + t.getId() +
                "&roomId=" + t.getRoomId() +
                "&specialistId=" + (t.getSpecialistId() == null ? "" : t.getSpecialistId()) +
                "&title=" + enc(t.getTitle()) +
                "&description=" + enc(t.getDescription()) +
                "&f1=" + t.getF1() +
                "&f2=" + t.getF2() +
                "&f3=" + t.getF3();
        post("/tickets", form);
    }

    public void changeTicketState(long id, TicketState state) {
        post("/tickets", "action=transition&id=" + id + "&state=" + state.name());
    }

    public void deleteTicket(long id) {
        post("/tickets", "action=delete&id=" + id);
    }

    // ================== WORK RECORDS ==================
    public List<WorkRecord> getWorkRecords() {
        try {
            HttpRequest req = HttpRequest.newBuilder(URI.create(BASE + "/work-records")).GET().build();
            HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            return Arrays.asList(mapper.readValue(resp.body(), WorkRecord[].class));
        } catch (Exception e) { throw new RuntimeException("HTTP Error", e); }
    }

    public void addWorkRecord(WorkRecord w) {
        String form = "action=add&ticketId=" + w.getTicketId() +
                "&performedAt=" + enc(w.getPerformedAt()) +
                "&comment=" + enc(w.getComment()) +
                "&hoursSpent=" + w.getHoursSpent();
        post("/work-records", form);
    }

    public void updateWorkRecord(WorkRecord w) {
        String form = "action=update&id=" + w.getId() +
                "&ticketId=" + w.getTicketId() +
                "&performedAt=" + enc(w.getPerformedAt()) +
                "&comment=" + enc(w.getComment()) +
                "&hoursSpent=" + w.getHoursSpent();
        post("/work-records", form);
    }

    public void deleteWorkRecord(long id) {
        post("/work-records", "action=delete&id=" + id);
    }

    // ================== HELPERS ==================
    private void post(String path, String form) {
        try {
            HttpRequest req = HttpRequest.newBuilder(URI.create(BASE + path))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(form))
                    .build();
            HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());
            if (resp.statusCode() >= 400) throw new RuntimeException(resp.body());
        } catch (Exception e) {
            throw new RuntimeException("Помилка HTTP: " + e.getMessage(), e);
        }
    }

    private String enc(String s) {
        return URLEncoder.encode(s == null ? "" : s, StandardCharsets.UTF_8);
    }
}