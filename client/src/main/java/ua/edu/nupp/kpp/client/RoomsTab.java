package ua.edu.nupp.kpp.client;

import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class RoomsTab {
    private final HotelClient api = new HotelClient();
    private final TableView<Room> table = new TableView<>();

    public VBox getContent() {
        // Колонки
        TableColumn<Room, Long> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Room, String> numCol = new TableColumn<>("Номер");
        numCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        TableColumn<Room, Integer> floorCol = new TableColumn<>("Поверх");
        floorCol.setCellValueFactory(new PropertyValueFactory<>("floor"));
        TableColumn<Room, String> typeCol = new TableColumn<>("Тип");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        TableColumn<Room, String> statusCol = new TableColumn<>("Статус");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        table.getColumns().addAll(idCol, numCol, floorCol, typeCol, statusCol);
        VBox.setVgrow(table, Priority.ALWAYS);

        // Форма
        TextField numField = new TextField(); numField.setPromptText("Номер");
        TextField floorField = new TextField(); floorField.setPromptText("Поверх");
        TextField typeField = new TextField(); typeField.setPromptText("Тип");
        TextField statusField = new TextField(); statusField.setPromptText("Статус");

        Button addBtn = new Button("Додати");
        Button delBtn = new Button("Видалити");
        Button refBtn = new Button("Оновити");

        addBtn.setOnAction(e -> {
            try {
                api.addRoom(new Room(null, numField.getText(), Integer.parseInt(floorField.getText()), typeField.getText(), statusField.getText()));
                refresh();
            } catch (Exception ex) { showErr(ex.getMessage()); }
        });

        delBtn.setOnAction(e -> {
            Room selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) { api.deleteRoom(selected.getId()); refresh(); }
            else showErr("Оберіть запис");
        });

        refBtn.setOnAction(e -> refresh());

        // При выборе строки заполняем поля
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                numField.setText(newVal.getNumber());
                floorField.setText(String.valueOf(newVal.getFloor()));
                typeField.setText(newVal.getType());
                statusField.setText(newVal.getStatus());
            }
        });

        VBox form = new VBox(10, new Label("Додати / редагувати:"), numField, floorField, typeField, statusField, new HBox(10, addBtn, delBtn, refBtn));
        form.setPrefWidth(250);

        HBox root = new HBox(10, table, form);
        HBox.setHgrow(table, Priority.ALWAYS);

        refresh();
        return new VBox(root);
    }

    private void refresh() {
        try { table.setItems(FXCollections.observableArrayList(api.getRooms())); }
        catch (Exception e) { showErr("Помилка завантаження: " + e.getMessage()); }
    }

    private void showErr(String msg) {
        new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK).showAndWait();
    }
}