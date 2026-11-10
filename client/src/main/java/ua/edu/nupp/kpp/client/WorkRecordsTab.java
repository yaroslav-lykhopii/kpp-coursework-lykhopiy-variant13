package ua.edu.nupp.kpp.client;

import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class WorkRecordsTab {
    private final HotelClient api = new HotelClient();
    private final TableView<WorkRecord> table = new TableView<>();
    private Long currentId = null;

    public VBox getContent() {
        TableColumn<WorkRecord, Long> idCol = new TableColumn<>("ID"); idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<WorkRecord, Long> ticketIdCol = new TableColumn<>("ID Заявки"); ticketIdCol.setCellValueFactory(new PropertyValueFactory<>("ticketId"));
        TableColumn<WorkRecord, String> dateCol = new TableColumn<>("Дата"); dateCol.setCellValueFactory(new PropertyValueFactory<>("performedAt"));
        TableColumn<WorkRecord, String> commentCol = new TableColumn<>("Коментар"); commentCol.setCellValueFactory(new PropertyValueFactory<>("comment"));
        TableColumn<WorkRecord, Double> hoursCol = new TableColumn<>("Години"); hoursCol.setCellValueFactory(new PropertyValueFactory<>("hoursSpent"));
        table.getColumns().addAll(idCol, ticketIdCol, dateCol, commentCol, hoursCol);
        VBox.setVgrow(table, Priority.ALWAYS);

        TextField ticketIdField = new TextField(); ticketIdField.setPromptText("ID Заявки");
        TextField dateField = new TextField(); dateField.setPromptText("Дата (YYYY-MM-DD)");
        TextField commentField = new TextField(); commentField.setPromptText("Коментар");
        TextField hoursField = new TextField(); hoursField.setPromptText("Витрачено годин");

        Button addBtn = new Button("Додати");
        Button updBtn = new Button("Змінити");
        Button delBtn = new Button("Видалити");
        Button refBtn = new Button("Оновити");

        addBtn.setOnAction(e -> {
            try {
                api.addWorkRecord(new WorkRecord(null, Long.parseLong(ticketIdField.getText()), dateField.getText(), commentField.getText(), Double.parseDouble(hoursField.getText())));
                refresh(); clearForm(ticketIdField, dateField, commentField, hoursField);
            } catch (Exception ex) { showErr(ex.getMessage()); }
        });

        updBtn.setOnAction(e -> {
            if (currentId == null) { showErr("Оберіть запис"); return; }
            try {
                api.updateWorkRecord(new WorkRecord(currentId, Long.parseLong(ticketIdField.getText()), dateField.getText(), commentField.getText(), Double.parseDouble(hoursField.getText())));
                refresh(); clearForm(ticketIdField, dateField, commentField, hoursField);
            } catch (Exception ex) { showErr(ex.getMessage()); }
        });

        delBtn.setOnAction(e -> {
            WorkRecord selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) { api.deleteWorkRecord(selected.getId()); refresh(); clearForm(ticketIdField, dateField, commentField, hoursField); }
            else showErr("Оберіть запис");
        });

        refBtn.setOnAction(e -> refresh());

        table.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                currentId = newVal.getId();
                ticketIdField.setText(String.valueOf(newVal.getTicketId()));
                dateField.setText(newVal.getPerformedAt());
                commentField.setText(newVal.getComment());
                hoursField.setText(String.valueOf(newVal.getHoursSpent()));
            }
        });

        VBox form = new VBox(10, new Label("Додати / редагувати:"), ticketIdField, dateField, commentField, hoursField, new HBox(10, addBtn, updBtn, delBtn, refBtn));
        form.setPrefWidth(250);
        HBox root = new HBox(10, table, form);
        HBox.setHgrow(table, Priority.ALWAYS);
        refresh();
        return new VBox(root);
    }

    private void refresh() {
        try { table.setItems(FXCollections.observableArrayList(api.getWorkRecords())); }
        catch (Exception e) { showErr("Помилка: " + e.getMessage()); }
    }
    private void clearForm(TextField... fields) { for (TextField f : fields) f.clear(); currentId = null; table.getSelectionModel().clearSelection(); }
    private void showErr(String msg) { new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK).showAndWait(); }
}