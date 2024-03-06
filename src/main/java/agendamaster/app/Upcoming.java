/*
 * This is an agenda app where users can sign up / log in to their account
 * and view their calendar, upcoming events, and profile. This app incorporates
 * the use of txt flat files in order to store user information and a result, all
 * of the user's data is preserved locally everytime the program runs. Please use
 * username: ayushb, password: 12345 to log in and view a pre-made calendar. Or
 * sign up to create a fresh account and experiment with the app.
 */



package agendamaster.app;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Upcoming extends Scene {

    App app;

    Label errorMessage = new Label();

    ScrollPane scrollPane = new ScrollPane();


    VBox mainVbox = new VBox();

    Font montserratBold = Font.loadFont(getClass().getResourceAsStream("/Montserrat-Bold.otf"), 30);
    Font montserratBoldSmall = Font.loadFont(getClass().getResourceAsStream("/Montserrat-Bold.otf"), 16);
    Font montserratRegular = Font.loadFont(getClass().getResourceAsStream("/MontserratAlternates-Regular.otf"), 15);
    Font montserratLight = Font.loadFont(getClass().getResourceAsStream("/MontserratAlternates-Light.otf"), 15);
    Font montserratSemibold = Font.loadFont(getClass().getResourceAsStream("/MontserratAlternates-SemiBold.otf"), 15);

    public Upcoming(App app) throws FileNotFoundException {
        super(new Group(), 900, 600);

        this.app = app;



        Group root = (Group) (getRoot());

        int width = 900;
        int height = 600;

        VBox mainVbox = new VBox();
        mainVbox.setPrefWidth(700);
        mainVbox.setPrefHeight(400);
        mainVbox.setLayoutX(100);
        mainVbox.setLayoutY(30);

        Image image = new Image(new FileInputStream("src/main/resources/images/background-upcoming.jpg"));  ;
        ImageView imageView = new ImageView(image);
        imageView.setX(0);
        imageView.setY(0);
        imageView.setFitHeight(600);
        imageView.setFitWidth(900);



        EventHandler handler = new MyEventHandler();

        StackPane navbar = new StackPane();

        Rectangle navbarBackground = new Rectangle();
        navbarBackground.setWidth(350);
        navbarBackground.setHeight(35);
        navbarBackground.setArcWidth(25);
        navbarBackground.setArcHeight(25);
        navbarBackground.setFill(Color.WHITE);


        HBox navbarItems = new HBox();
        navbarItems.setPrefWidth(350);
        navbarItems.setPrefHeight(35);




        Label calendarLabel = new Label("Calendar");
        calendarLabel.setId("calendar");
        Label upcomingLabel = new Label("Upcoming");
        upcomingLabel.setId("upcoming");
        Label profileLabel = new Label("Profile");
        profileLabel.setId("profile");

        calendarLabel.setOnMouseEntered(handler);
        calendarLabel.setOnMouseClicked(handler);
        calendarLabel.setOnMouseExited(handler);

        upcomingLabel.setOnMouseEntered(handler);
        upcomingLabel.setOnMouseClicked(handler);
        upcomingLabel.setOnMouseExited(handler);

        profileLabel.setOnMouseEntered(handler);
        profileLabel.setOnMouseClicked(handler);
        profileLabel.setOnMouseExited(handler);

        calendarLabel.setFont(montserratBoldSmall);
        upcomingLabel.setFont(montserratBoldSmall);
        profileLabel.setFont(montserratBoldSmall);

        navbarItems.getChildren().addAll(calendarLabel, upcomingLabel, profileLabel);

        navbarItems.setSpacing(20);
        navbarItems.setAlignment(Pos.CENTER);
        navbar.setAlignment(Pos.CENTER);

        navbar.getChildren().addAll(navbarBackground, navbarItems);


        errorMessage = new Label();
        errorMessage.setFont(montserratBoldSmall);

        mainVbox.setSpacing(20);
        mainVbox.getChildren().addAll(navbar, scrollPane);


        updateUpcoming();



        root.getChildren().addAll(imageView, mainVbox);

    }

    private class MyEventHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent mouseEvent) {
            Object item = mouseEvent.getSource();

            if (item instanceof Label) {
                Label label = (Label)(item);
                if (mouseEvent.getEventType() == MouseEvent.MOUSE_ENTERED) {


                    label.setTextFill(Color.PURPLE);

                }
                else if (mouseEvent.getEventType() == MouseEvent.MOUSE_CLICKED) {

                    if (label.getId().equals("profile")) {
                        try {
                            app.profileScene.updateProfile();
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        app.showScene(app.profileScene);
                    }
                    else if (label.getId().equals("calendar")){
                        app.calendarScene.updateCalendar();
                        app.showScene(app.calendarScene);
                    }


                }
                else if (mouseEvent.getEventType() == MouseEvent.MOUSE_EXITED) {
                    label.setTextFill(Color.BLACK);
                }
            }


        }

        // Insert methods required by the EventHandler interface here
    }

    public static String getMonth(int x) {
        if (x == 1) {
            return "January";
        }
        else if (x == 2) {
            return "February";
        }
        else if (x == 3) {
            return "March";
        }
        else if (x == 4) {
            return "April";
        }
        else if (x == 5) {
            return "May";
        }
        else if (x == 6) {
            return "June";
        }
        else if (x == 7) {
            return "July";
        }
        else if (x == 8) {
            return "August";
        }
        else if (x == 9) {
            return "October";
        }
        else if (x == 10) {
            return "September";
        }
        else if (x == 11) {
            return "November";
        }
        else  {
            return "December";
        }

    }


    public void updateUpcoming() {

        VBox items = new VBox();
        items.setAlignment(Pos.CENTER);
        items.setSpacing(10);


        try {
            Scanner scEvents = new Scanner(new File(App.username + ".txt"));

            ArrayList<Integer> monthArrayList = new ArrayList<>();
            ArrayList<Integer> dateArrayList = new ArrayList<>();
            ArrayList<String> eventArrayList = new ArrayList<>();

            while (scEvents.hasNextLine()) {
                int monthNum = scEvents.nextInt();
                int dateNum = scEvents.nextInt();
                String eventsName = scEvents.nextLine();

                monthArrayList.add(monthNum);
                dateArrayList.add(dateNum);
                eventArrayList.add(eventsName);
            }

            sort(monthArrayList, dateArrayList, eventArrayList);


            for (int i = 0; i < monthArrayList.size(); i++) {
                Label eventLabel = new Label((i+1) + ". " + getMonth(monthArrayList.get(i)) + " " + dateArrayList.get(i) + ", 2024: " + eventArrayList.get(i));
                eventLabel.setFont(montserratBoldSmall);
                eventLabel.setPadding(new Insets(10));
                eventLabel.setMinWidth(680);
                eventLabel.setMaxWidth(680);
                eventLabel.setAlignment(Pos.CENTER);


                items.getChildren().add(eventLabel);

            }

            StackPane stackPane = new StackPane();
            stackPane.getChildren().add(items);
            stackPane.setAlignment(Pos.CENTER);

            scrollPane.setContent(items);
            scrollPane.setPrefViewportWidth(400);
            scrollPane.setLayoutX((getWidth() - scrollPane.getPrefViewportWidth()) / 2);



        } catch (IOException io) {
            errorMessage = new Label("You have no upcoming events on your calendar");
        }


        mainVbox.setAlignment(Pos.CENTER);
        mainVbox.setSpacing(30);

    }



    public void sort(ArrayList<Integer> months, ArrayList<Integer> dates, ArrayList<String> eventNames) {
        int n = months.size();
        for (int i = 1; i < n; ++i) {
            int keyMonth = months.get(i);
            int keyDate = dates.get(i);
            String keyEvent = eventNames.get(i);
            int j = i - 1;

            while (j >= 0 && (months.get(j) > keyMonth || (months.get(j).equals(keyMonth) && dates.get(j) > keyDate))) {
                months.set(j + 1, months.get(j));
                dates.set(j + 1, dates.get(j));
                eventNames.set(j + 1, eventNames.get(j));
                j = j - 1;
            }

            months.set(j + 1, keyMonth);
            dates.set(j + 1, keyDate);
            eventNames.set(j + 1, keyEvent);
        }
    }

}
