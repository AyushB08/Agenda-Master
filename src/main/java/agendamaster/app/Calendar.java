/*
 * This is an agenda app where users can sign up / log in to their account
 * and view their calendar, upcoming events, and profile. This app incorporates
 * the use of txt flat files in order to store user information and a result, all
 * of the user's data is preserved locally everytime the program runs. Please use
 * username: ayushb, password: 12345 to log in and view a pre-made calendar. Or
 * sign up to create a fresh account and experiment with the app.
 */



package agendamaster.app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Calendar extends Scene {

    App app;
    EventHandler handler = new MyEventHandler();
    String username;
    int currMonth = 3;
    VBox calendarVbox = new VBox();
    ComboBox<String>  dropdown = new ComboBox<String>();
    TextField addEventField = new TextField();
    Label addItemLabel;
    StackPane calendarPane = new StackPane();

    VBox mainVbox = new VBox();
    Font montserratBold = Font.loadFont(getClass().getResourceAsStream("/Montserrat-Bold.otf"), 30);
    Font montserratBoldSmall = Font.loadFont(getClass().getResourceAsStream("/Montserrat-Bold.otf"), 16);
    Font montserratRegular = Font.loadFont(getClass().getResourceAsStream("/MontserratAlternates-Regular.otf"), 15);
    Font montserratLight = Font.loadFont(getClass().getResourceAsStream("/MontserratAlternates-Light.otf"), 15);
    Font montserratSemibold = Font.loadFont(getClass().getResourceAsStream("/MontserratAlternates-SemiBold.otf"), 15);
    public Calendar(App app) throws FileNotFoundException {
        super(new Group(), 900, 600);

        this.app = app;
        this.username = username;


        Group root = (Group)(getRoot());

        int width = 900;
        int height = 600;



        mainVbox = new VBox();
        mainVbox.setPrefWidth(700);
        mainVbox.setPrefHeight(400);
        mainVbox.setLayoutX(100);
        mainVbox.setLayoutY(30);

        Image image = new Image(new FileInputStream("src/main/resources/images/background-calendar.jpg"));  ;
        ImageView imageView = new ImageView(image);
        imageView.setX(0);
        imageView.setY(0);
        imageView.setFitHeight(600);
        imageView.setFitWidth(900);



        handler = new MyEventHandler();

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

        updateCalendar();

        HBox addControls = new HBox();
        addControls.setSpacing(20);
        addControls.setAlignment(Pos.CENTER);


        VBox dateBox = new VBox();
        Label dayLabel = new Label("Day #");
        dayLabel.setFont(montserratBoldSmall);
        dateBox.setSpacing(10);
        dateBox.setAlignment(Pos.CENTER);

        ObservableList<String> daysList = FXCollections.observableArrayList();

        int[] arr = getStartEndMonth(currMonth);

        for (int i = 1; i <= arr[1]; i++) {
            daysList.add("" + i);
        }
        dropdown = new ComboBox<>(daysList);

        dropdown.setValue("1");

        dateBox.getChildren().addAll(dayLabel, dropdown);

        StackPane addItemPane = new StackPane();
        addItemPane.setId("add");

        addItemPane.setOnMouseEntered(handler);
        addItemPane.setOnMouseClicked(handler);
        addItemPane.setOnMouseExited(handler);

        Rectangle addItemRectangle = new Rectangle();
        addItemRectangle.setWidth(100);
        addItemRectangle.setHeight(40);
        addItemRectangle.setArcWidth(25);
        addItemRectangle.setArcHeight(25);
        addItemRectangle.setFill(Color.WHITE);

        addItemLabel = new Label("ADD");
        addItemLabel.setFont(montserratBoldSmall);

        addItemPane.getChildren().addAll(addItemRectangle, addItemLabel);

        VBox addEvent = new VBox();
        addEvent.setSpacing(10);
        addEvent.setAlignment(Pos.CENTER);
        Label addEventLabel = new Label("Event Name");
        addEventLabel.setFont(montserratBoldSmall);

        addEventField = new TextField();
        addEventField.setMaxHeight(20);
        addEventField.setMaxWidth(100);

        addEvent.getChildren().addAll(addEventLabel, addEventField);
        addControls.getChildren().addAll(dateBox, addItemPane, addEvent);




        mainVbox.setAlignment(Pos.CENTER);
        mainVbox.setSpacing(30);
        mainVbox.getChildren().addAll(navbar, calendarPane, addControls);





        root.getChildren().addAll(imageView, mainVbox);

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

    public void updateCalendar() {
        int x = mainVbox.getChildren().indexOf(calendarPane);

        calendarPane = new StackPane();

        Rectangle backgroundCalendar = new Rectangle();
        backgroundCalendar.setWidth(400);
        backgroundCalendar.setHeight(400);
        backgroundCalendar.setFill(Color.WHITE);
        backgroundCalendar.setArcHeight(25);
        backgroundCalendar.setArcWidth(25);

        calendarVbox = new VBox();
        calendarVbox.setAlignment(Pos.CENTER);
        calendarVbox.setSpacing(10);



        HBox month = new HBox();
        month.setAlignment(Pos.CENTER);
        month.setSpacing(10);

        StackPane leftArrowPane = new StackPane();
        leftArrowPane.setId("leftarrow");


        leftArrowPane.setOnMouseEntered(handler);
        leftArrowPane.setOnMouseClicked(handler);
        leftArrowPane.setOnMouseExited(handler);

        Rectangle leftArrowPaneBox = new Rectangle();
        leftArrowPaneBox.setWidth(20);
        leftArrowPaneBox.setHeight(20);
        leftArrowPaneBox.setFill(Color.WHITE);


        Label leftArrowText = new Label("<");
        leftArrowText.setFont(montserratRegular);

        leftArrowPane.getChildren().addAll(leftArrowPaneBox, leftArrowText);

        Label monthLabel = new Label(getMonth(currMonth) + ", 2024");

        monthLabel.setFont(montserratBoldSmall);

        StackPane rightArrowPane = new StackPane();
        rightArrowPane.setId("rightarrow");

        rightArrowPane.setOnMouseEntered(handler);
        rightArrowPane.setOnMouseClicked(handler);
        rightArrowPane.setOnMouseExited(handler);

        Rectangle rightArrowPaneBox = new Rectangle();
        rightArrowPaneBox.setWidth(20);
        rightArrowPaneBox.setHeight(20);
        rightArrowPaneBox.setFill(Color.WHITE);


        Label rightArrowText = new Label(">");

        rightArrowText.setFont(montserratRegular);

        rightArrowPane.getChildren().addAll(rightArrowPaneBox, rightArrowText);

        month.getChildren().addAll(leftArrowPane, monthLabel, rightArrowPane);
        calendarVbox.getChildren().add(month);



        HBox weekLabels = new HBox();
        weekLabels.setSpacing(10);
        weekLabels.setAlignment(Pos.CENTER);


        Label sundayLabel = new Label("SUN");
        Label mondayLabel = new Label("MON");
        Label tuesdayLabel = new Label("TUE");
        Label wednesdayLabel = new Label("WED");
        Label thursdayLabel = new Label("THU");
        Label fridayLabel = new Label("FRI");
        Label saturdayLabel = new Label("SAT");

        weekLabels.getChildren().addAll(sundayLabel, mondayLabel, tuesdayLabel, wednesdayLabel, thursdayLabel, fridayLabel, saturdayLabel);
        ObservableList<Node> children = weekLabels.getChildren();
        for (int i = 0; i < children.size(); i++) {
            Label text = (Label)(children.get(i));
            text.setFont(montserratBoldSmall);

        }


        calendarVbox.getChildren().add(weekLabels);

        int[] arr = getStartEndMonth(currMonth);
        //6, 31
        for (int i = 0; i < 6; i++) {

            HBox week = new HBox();
            week.setAlignment(Pos.CENTER);
            week.setSpacing(10);


            for (int a = 0; a < 7; a++) {
                StackPane day = new StackPane();

                day.setAlignment(Pos.CENTER);

                Rectangle dayRectangle = new Rectangle();
                dayRectangle.setWidth(40);
                dayRectangle.setHeight(40);
                dayRectangle.setArcWidth(20);
                dayRectangle.setArcHeight(20);
                dayRectangle.setFill(Color.LAWNGREEN);




                day.getChildren().addAll(dayRectangle);

                try {

                    Scanner scCalendar = new Scanner(new File(App.username + ".txt"));
                    int target = (7 * i) + (a + 1) - arr[0] + 1;
                    while (scCalendar.hasNextLine()) {
                        String eventStr = scCalendar.nextLine();
                        Scanner scStr = new Scanner(eventStr);

                        int monthData = scStr.nextInt();
                        int eventDay = scStr.nextInt();

                        if (monthData  == currMonth && eventDay == target) {
                            dayRectangle.setFill(Color.RED);

                            day.setOnMouseEntered(handler);
                            day.setOnMouseClicked(handler);
                            day.setOnMouseExited(handler);
                            day.setId("" + target);
                        }

                    }
                } catch (IOException b) {

                }

                if (((7 * i) + (a + 1) - arr[0] < arr[1]) && !((i == 0  && ((a + 1) < arr[0])))) {

                    Label dayNum = new Label("" + ((7 * i) + (a + 1) - arr[0] + 1));
                    dayNum.setFont(montserratLight);
                    day.getChildren().add(dayNum);
                }
                week.getChildren().add(day);

            }

            calendarVbox.getChildren().add(week);



        }



        calendarPane.setAlignment(Pos.CENTER);
        calendarPane.getChildren().addAll(backgroundCalendar, calendarVbox);

        if (x != -1) {
            mainVbox.getChildren().set(x, calendarPane);
        }


    }
    public static  int[] getStartEndMonth(int x) {

        if (x == 1) {
            int[] arr = {1, 31};
            return arr;
        }
        else if (x == 2) {
            int[] arr = {5, 29};
            return arr;
        }
        else if (x == 3) {
            int[] arr = {6, 31};
            return arr;
        }
        else if (x == 4) {
            int[] arr = {1, 30};
            return arr;
        }
        else if (x == 5) {
            int[] arr = {3, 31};
            return arr;
        }
        else if (x == 6) {
            int[] arr = {6, 30};
            return arr;
        }
        else if (x == 7) {
            int[] arr = {1, 31};
            return arr;
        }
        else if (x == 8) {
            int[] arr = {4, 31};
            return arr;
        }
        else if (x == 9) {
            int[] arr = {0, 30};
            return arr;
        }
        else if (x == 10) {
            int[] arr = {2, 31};
            return arr;
        }
        else if (x == 11) {
            int[] arr = {5, 30};
            return arr;

        }
        else  {
            int[] arr = {0, 31};
            return arr;
        }

    }

    private class MyEventHandler implements EventHandler<MouseEvent> {


        @Override
        public void handle(MouseEvent mouseEvent) {
            Object item = mouseEvent.getSource();
            if (item instanceof StackPane) {
                StackPane tempPane = (StackPane)(item);
                if (tempPane.getId().equals("add")) {


                    if (mouseEvent.getEventType() == MouseEvent.MOUSE_ENTERED) {


                        addItemLabel.setTextFill(Color.PURPLE);

                    } else if (mouseEvent.getEventType() == MouseEvent.MOUSE_CLICKED) {

                        int number = Integer.parseInt(dropdown.getValue());
                        String eventName = addEventField.getText();
                        if (eventName.trim().length() > 0) {
                            try {
                                BufferedWriter bw = new BufferedWriter(new FileWriter(App.username + ".txt", true));
                                bw.write(currMonth + " " + number + " " + eventName.trim() + "\n");
                                bw.flush();
                                updateCalendar();


                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            Stage alert = new Stage();
                            alert.initModality(Modality.APPLICATION_MODAL);
                            alert.setTitle("Error");

                            VBox root = new VBox();
                            Scene alertScene = new Scene(root, 300, 100);


                            Label errorText = new Label("Please enter an event name.");
                            errorText.setFont(montserratRegular);
                            errorText.setWrapText(true);
                            errorText.setPadding(new Insets(10));
                            errorText.setAlignment(Pos.CENTER);

                            root.setAlignment(Pos.CENTER);
                            root.getChildren().add(errorText);
                            alert.setScene(alertScene);
                            alert.showAndWait();
                        }


                    } else if (mouseEvent.getEventType() == MouseEvent.MOUSE_EXITED) {
                        addItemLabel.setTextFill(Color.BLACK);
                    }

                }
                else {
                    if (mouseEvent.getEventType() == MouseEvent.MOUSE_ENTERED) {
                        ((Rectangle)(tempPane.getChildren().get(0))).setFill(Color.LIGHTBLUE);
                    }

                    else if (mouseEvent.getEventType() == MouseEvent.MOUSE_CLICKED) {
                        if (tempPane.getId().equals("rightarrow")) {
                            if (currMonth < 12) {
                                currMonth += 1;
                                updateCalendar();
                            }
                        } else if (tempPane.getId().equals("leftarrow")) {
                            if (currMonth > 0) {
                                currMonth -= 1;
                                updateCalendar();
                            }
                        } else {


                            ArrayList<String> events = new ArrayList<>();
                            int numDay = Integer.parseInt(tempPane.getId());
                            try {

                                Scanner scCalendar = new Scanner(new File(App.username + ".txt"));
                                int target = numDay;
                                while (scCalendar.hasNextLine()) {
                                    String eventStr = scCalendar.nextLine();
                                    Scanner scStr = new Scanner(eventStr);

                                    int monthData = scStr.nextInt();
                                    int eventDay = scStr.nextInt();

                                    if (monthData == currMonth && eventDay == target) {
                                        events.add(scStr.nextLine());
                                    }
                                }

                                Stage notification = new Stage();
                                notification.initModality(Modality.APPLICATION_MODAL);
                                notification.setTitle("Notification");

                                VBox root = new VBox();
                                Scene notificationScene = new Scene(root, 300, 150);


                                Label titleText = new Label(getMonth(currMonth) + " " + target + ", 2024");
                                titleText.setFont(montserratBoldSmall);
                                titleText.setAlignment(Pos.CENTER);

                                root.setAlignment(Pos.CENTER);
                                root.getChildren().add(titleText);

                                for (int i = 0; i < events.size(); i++) {
                                    Label eventText = new Label(events.get(i));
                                    eventText.setFont(montserratLight);
                                    root.getChildren().add(eventText);
                                }

                                notification.setScene(notificationScene);
                                notification.showAndWait();

                            } catch (IOException b) {

                            }
                        }
                    }

                    else if (mouseEvent.getEventType() == MouseEvent.MOUSE_EXITED) {
                        if (tempPane.getId().equals("rightarrow") || tempPane.getId().equals("leftarrow")) {
                            ((Rectangle)(tempPane.getChildren().get(0))).setFill(Color.WHITE);
                        } else {
                            ((Rectangle)(tempPane.getChildren().get(0))).setFill(Color.RED);
                        }

                    }


                }
            }
            if (item instanceof Label) {
                Label label = (Label)(item);
                if (mouseEvent.getEventType() == MouseEvent.MOUSE_ENTERED) {


                    label.setTextFill(Color.PURPLE);

                }
                else if (mouseEvent.getEventType() == MouseEvent.MOUSE_CLICKED) {

                    if (label.getId().equals("upcoming")) {
                        app.upcomingScene.updateUpcoming();
                        app.showScene(app.upcomingScene);
                    }
                    else if (label.getId().equals("profile")) {
                        try {
                            app.profileScene.updateProfile();

                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        app.showScene(app.profileScene);
                    }


                }
                else if (mouseEvent.getEventType() == MouseEvent.MOUSE_EXITED) {
                    label.setTextFill(Color.BLACK);
                }
            }


        }

        // Insert methods required by the EventHandler interface here
    }
}
