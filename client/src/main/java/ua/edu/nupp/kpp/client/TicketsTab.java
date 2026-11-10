package ua.edu.nupp.kpp.client;

import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class TicketsTab {
    private final HotelClient api = new HotelClient();
    private final TableView<Ticket> table = new TableView<>();
    private Long currentId = null;

    public VBox getContent() {
        TableColumn<Ticket, Long> idCol = new TableColumn<>("ID"); idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Ticket, Long> roomIdCol = new TableColumn<>("ID Номера"); roomIdCol.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        TableColumn<Ticket, Long> specIdCol = new TableColumn<>("ID Фахівця"); specIdCol.setCellValueFactory(new PropertyValueFactory<>("specialistId"));
        TableColumn<Ticket, String> titleCol = new TableColumn<>("Заголовок"); titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn<Ticket, TicketState> stateCol = new TableColumn<>("Стан"); stateCol.setCellValueFactory(new PropertyValueFactory<>("state"));
        TableColumn<Ticket, Double> f1Col = new TableColumn<>("F1"); f1Col.setCellValueFactory(new PropertyValueFactory<>("f1"));
        TableColumn<Ticket, Double> f2Col = new TableColumn<>("F2"); f2Col.setCellValueFactory(new PropertyValueFactory<>("f2"));
        TableColumn<Ticket, Double> f3Col = new TableColumn<>("F3"); f3Col.setCellValueFactory(new PropertyValueFactory<>("f3"));
        TableColumn<Ticket, Double> indexCol = new TableColumn<>("Індекс I"); indexCol.setCellValueFactory(new PropertyValueFactory<>("impactIndex"));

        table.getColumns().addAll(idCol, roomIdCol, specIdCol, titleCol, stateCol, f1Col, f2Col, f3Col, indexCol);
        VBox.setVgrow(table, Priority.ALWAYS);

        TextField roomIdField = new TextField(); roomIdField.setPromptText("ID Номера");
        TextField specIdField = new TextField(); specIdField.setPromptText("ID Фахівця (не обов.)");
        TextField titleField = new TextField(); titleField.setPromptText("Заголовок");
        TextField descField = new TextField(); descField.setPromptText("Опис");
        TextField f1Field = new TextField(); f1Field.setPromptText("F1 (0-100)");
        TextField f2Field = new TextField(); f2Field.setPromptText("F2 (0-100)");
        TextField f3Field = new TextField(); f3Field.setPromptText("F3 (0-100)");

        ComboBox<TicketState> stateBox = new ComboBox<>(FXCollections.observableArrayList(TicketState.values()));
        stateBox.setPromptText("Новий стан");

        Button addBtn = new Button("Додати");
        Button updBtn = new Button("Змінити");
        Button delBtn = new Button("Видалити");
        Button stateBtn = new Button("Змінити стан");
        Button refBtn = new Button("Оновити");

        addBtn.setOnAction(e -> {
            try {
                Ticket t = new Ticket(null, Long.parseLong(roomIdField.getText()),
                        specIdField.getText().isEmpty() ? null : Long.parseLong(specIdField.getText()),
                        titleField.getText(), descField.getText(), TicketState.NEW,
                        Double.parseDouble(f1Field.getText()), Double.parseDouble(f2Field.getText()), Double.parseDouble(f3Field.getText()), 0.0);
                api.addTicket(t); refresh(); clearForm(roomIdField, specIdField, titleField, descField, f1Field, f2Field, f3Field);
            } catch (Exception ex) { showErr("Перевірте правильність введення (числа, ID): " + ex.getMessage()); }
        });

        updBtn.setOnAction(e -> {
            if (currentId == null) { showErr("Оберіть запис"); return; }
            try {
                Ticket t = new Ticket(currentId, Long.parseLong(roomIdField.getText()),
                        specIdField.getText().isEmpty() ? null : Long.parseLong(specIdField.getText()),
                        titleField.getText(), descField.getText(), TicketState.NEW,
                        Double.parseDouble(f1Field.getText()), Double.parseDouble(f2Field.getText()), Double.parseDouble(f3Field.getText()), 0.0);
                api.updateTicket(t); refresh(); clearForm(roomIdField, specIdField, titleField, descField, f1Field, f2Field, f3Field);
            } catch (Exception ex) { showErr("Помилка: " + ex.getMessage()); }
        });

        delBtn.setOnAction(e -> {
            Ticket selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) { api.deleteTicket(selected.getId()); refresh(); clearForm(roomIdField, specIdField, titleField, descField, f1Field, f2Field, f3Field); }
            else showErr("Оберіть запис");
        });

        stateBtn.setOnAction(e -> {
            Ticket selected = table.getSelectionModel().getSelectedItem();
            if (selected == null || stateBox.getValue() == null) { showErr("Оберіть заявку та новий стан"); return; }
            try { api.changeTicketState(selected.getId(), stateBox.getValue()); refresh(); }
            catch (Exception ex) { showErr("Недопустимий перехід стану: " + ex.getMessage()); }
        });

        refBtn.setOnAction(e -> refresh());

        table.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                currentId = newVal.getId();
                roomIdField.setText(String.valueOf(newVal.getRoomId()));
                specIdField.setText(newVal.getSpecialistId() == null ? "" : String.valueOf(newVal.getSpecialistId()));
                titleField.setText(newVal.getTitle());
                descField.setText(newVal.getDescription());
                f1Field.setText(String.valueOf(newVal.getF1()));
                f2Field.setText(String.valueOf(newVal.getF2()));
                f3Field.setText(String.valueOf(newVal.getF3()));
                stateBox.setValue(newVal.getState());
            }
        });

        VBox form = new VBox(5, new Label("Додати / редагувати:"), roomIdField, specIdField, titleField, descField, f1Field, f2Field, f3Field,
                new HBox(10, addBtn, updBtn, delBtn, refBtn),
                new Separator(), new Label("Зміна стану:"), stateBox, stateBtn);
        form.setPrefWidth(300);

        ScrollPane scrollForm = new ScrollPane(form);
        scrollForm.setFitToWidth(true);
        scrollForm.setPrefWidth(300);

        HBox root = new HBox(10, table, scrollForm);
        HBox.setHgrow(table, Priority.ALWAYS);
        refresh();
        return new VBox(root);
    }

    private void refresh() {
        try { table.setItems(FXCollections.observableArrayList(api.getTickets())); }
        catch (Exception e) { showErr("Помилка: " + e.getMessage()); }
    }
    private void clearForm(TextField... fields) { for (TextField f : fields) f.clear(); currentId = null; table.getSelectionModel().clearSelection(); }
    private void showErr(String msg) { new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK).showAndWait(); }
}