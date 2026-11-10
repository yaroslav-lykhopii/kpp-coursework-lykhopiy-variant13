package ua.edu.nupp.kpp.client;

import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class SpecialistsTab {
    private final HotelClient api = new HotelClient();
    private final TableView<Specialist> table = new TableView<>();

    public VBox getContent() {
        TableColumn<Specialist, Long> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Specialist, String> nameCol = new TableColumn<>("ПІБ");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        TableColumn<Specialist, String> specCol = new TableColumn<>("Спеціалізація");
        specCol.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        TableColumn<Specialist, String> phoneCol = new TableColumn<>("Телефон");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));

        table.getColumns().addAll(idCol, nameCol, specCol, phoneCol);
        VBox.setVgrow(table, Priority.ALWAYS);

        TextField nameField = new TextField(); nameField.setPromptText("ПІБ");
        TextField specField = new TextField(); specField.setPromptText("Спеціалізація");
        TextField phoneField = new TextField(); phoneField.setPromptText("Телефон");

        Button addBtn = new Button("Додати");
        Button delBtn = new Button("Видалити");
        Button refBtn = new Button("Оновити");

        addBtn.setOnAction(e -> {
            try {
                api.addSpecialist(new Specialist(null, nameField.getText(), specField.getText(), phoneField.getText()));
                refresh();
            } catch (Exception ex) { showErr(ex.getMessage()); }
        });

        delBtn.setOnAction(e -> {
            Specialist selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) { api.deleteSpecialist(selected.getId()); refresh(); }
            else showErr("Оберіть запис");
        });

        refBtn.setOnAction(e -> refresh());

        table.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                nameField.setText(newVal.getFullName());
                specField.setText(newVal.getSpecialization());
                phoneField.setText(newVal.getPhone());
            }
        });

        VBox form = new VBox(10, new Label("Додати / редагувати:"), nameField, specField, phoneField, new HBox(10, addBtn, delBtn, refBtn));
        form.setPrefWidth(250);

        HBox root = new HBox(10, table, form);
        HBox.setHgrow(table, Priority.ALWAYS);

        refresh();
        return new VBox(root);
    }

    private void refresh() {
        try { table.setItems(FXCollections.observableArrayList(api.getSpecialists())); }
        catch (Exception e) { showErr("Помилка завантаження: " + e.getMessage()); }
    }

    private void showErr(String msg) {
        new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK).showAndWait();
    }
}