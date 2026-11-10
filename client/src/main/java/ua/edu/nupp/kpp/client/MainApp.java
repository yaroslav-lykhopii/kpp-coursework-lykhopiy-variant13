package ua.edu.nupp.kpp.client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) {
        TabPane tabPane = new TabPane();

        Tab roomsTab = new Tab("Номери", new RoomsTab().getContent());
        roomsTab.setClosable(false);

        Tab specialistsTab = new Tab("Фахівці", new SpecialistsTab().getContent());
        specialistsTab.setClosable(false);

        Tab ticketsTab = new Tab("Заявки", new TicketsTab().getContent());
        ticketsTab.setClosable(false);

        Tab workRecordsTab = new Tab("Записи виконання", new WorkRecordsTab().getContent());
        workRecordsTab.setClosable(false);

        tabPane.getTabs().addAll(roomsTab, specialistsTab, ticketsTab, workRecordsTab);

        Scene scene = new Scene(tabPane, 1000, 600);
        stage.setTitle("Готельний комплекс - Управління");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}