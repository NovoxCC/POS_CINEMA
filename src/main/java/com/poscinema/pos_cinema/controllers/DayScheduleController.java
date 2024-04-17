package com.poscinema.pos_cinema.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.LocalDate;

public class DayScheduleController {

    @FXML
    private TableView<Schedule> dayScheduleTable;

    @FXML
    private TableColumn<Schedule, String> titleColumn;

    @FXML
    private TableColumn<Schedule, String> genreColumn;

    @FXML
    private TableColumn<Schedule, String> startTimeColumn;

    @FXML
    private TableColumn<Schedule, String> endTimeColumn;

    public void initialize() {
        // Configurar las columnas de la tabla
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        genreColumn.setCellValueFactory(cellData -> cellData.getValue().genreProperty());
        startTimeColumn.setCellValueFactory(cellData -> cellData.getValue().startTimeProperty());
        endTimeColumn.setCellValueFactory(cellData -> cellData.getValue().endTimeProperty());

        // Ejecutar la consulta y cargar los datos en la tabla
        try (Connection connection = new DatabaseConnection().getConnection()) {
            String query = "SELECT m.title, m.genre, s.start_time, s.end_time " +
                    "FROM Movies m " +
                    "JOIN Schedules s ON m.id = s.movie_id " +
                    "WHERE s.date = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, LocalDate.now().toString()); // Pasar la fecha actual como parámetro
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String genre = resultSet.getString("genre");
                String startTime = resultSet.getString("start_time");
                String endTime = resultSet.getString("end_time");

                Schedule schedule = new Schedule(title, genre, startTime, endTime);
                dayScheduleTable.getItems().add(schedule);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de errores
        }
    }

    public static class Schedule {
        private final StringProperty title;
        private final StringProperty genre;
        private final StringProperty startTime;
        private final StringProperty endTime;

        public Schedule(String title, String genre, String startTime, String endTime) {
            this.title = new SimpleStringProperty(title);
            this.genre = new SimpleStringProperty(genre);
            this.startTime = new SimpleStringProperty(startTime);
            this.endTime = new SimpleStringProperty(endTime);
        }

        public String getTitle() {
            return title.get();
        }

        public StringProperty titleProperty() {
            return title;
        }

        public void setTitle(String title) {
            this.title.set(title);
        }

        public String getGenre() {
            return genre.get();
        }

        public StringProperty genreProperty() {
            return genre;
        }

        public void setGenre(String genre) {
            this.genre.set(genre);
        }

        public String getStartTime() {
            return startTime.get();
        }

        public StringProperty startTimeProperty() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime.set(startTime);
        }

        public String getEndTime() {
            return endTime.get();
        }

        public StringProperty endTimeProperty() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime.set(endTime);
        }
    }
}
