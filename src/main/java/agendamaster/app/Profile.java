/*
 * This is an agenda app where users can sign up / log in to their account
 * and view their calendar, upcoming events, and profile. This app incorporates
 * the use of txt flat files in order to store user information and a result, all
 * of the user's data is preserved locally everytime the program runs. Please use
 * username: ayushb, password: 12345 to log in and view a pre-made calendar. Or
 * sign up to create a fresh account and experiment with the app.
 */


package agendamaster.app;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
import java.util.Scanner;


public class Profile extends Scene {

    EventHandler handler = new MyEventHandler();
    StackPane navbar = new StackPane();
    Font montserratBold = Font.loadFont(getClass().getResourceAsStream("/Montserrat-Bold.otf"), 30);
    Font montserratBoldSmall = Font.loadFont(getClass().getResourceAsStream("/Montserrat-Bold.otf"), 16);
    Font montserratRegular = Font.loadFont(getClass().getResourceAsStream("/MontserratAlternates-Regular.otf"), 15);
    Font montserratLight = Font.loadFont(getClass().getResourceAsStream("/MontserratAlternates-Light.otf"), 15);
    Font montserratSemibold = Font.loadFont(getClass().getResourceAsStream("/MontserratAlternates-SemiBold.otf"), 15);

    App app;
    Group root = new Group();
    ImageView imageView = new ImageView();
    VBox mainVbox = new VBox();

    public Profile(App app) throws FileNotFoundException {
        super(new Group(), 900, 600);

        this.app = app;


        root = (Group) (getRoot());



        int width = 900;
        int height = 600;
        mainVbox = new VBox();
        mainVbox.setPrefWidth(700);
        mainVbox.setPrefHeight(400);
        mainVbox.setLayoutX(100);
        mainVbox.setLayoutY(30);

        BackgroundFill fill = new BackgroundFill(Color.LIGHTGREEN, new CornerRadii(20), new Insets(0));

        mainVbox.setBackground(new Background(fill));



        Image image = new Image(new FileInputStream("src/main/resources/images/background-calendar.jpg"));  ;
        imageView = new ImageView(image);
        imageView.setX(0);
        imageView.setY(0);
        imageView.setFitHeight(600);
        imageView.setFitWidth(900);



        handler = new MyEventHandler();

        navbar = new StackPane();

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


        mainVbox.setAlignment(Pos.CENTER);
        mainVbox.setSpacing(30);
        mainVbox.getChildren().addAll(navbar);





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

                    if (label.getId().equals("upcoming")) {
                        app.upcomingScene.updateUpcoming();
                        app.showScene(app.upcomingScene);
                    }
                    else if (label.getId().equals("calendar")){
                        app.calendarScene.updateCalendar();
                        app.showScene(app.calendarScene);
                    }


                }
                else if (mouseEvent.getEventType() == MouseEvent.MOUSE_EXITED) {
                    label.setTextFill(Color.BLACK);
                }
            } else if (item instanceof  StackPane){
                StackPane pane = (StackPane) (item);
                if (mouseEvent.getEventType() == MouseEvent.MOUSE_ENTERED) {


                    ((Rectangle)(pane.getChildren().get(0))).setFill(Color.WHITE);

                }
                else if (mouseEvent.getEventType() == MouseEvent.MOUSE_CLICKED) {

                    app.showScene(app.loginScene);

                }
                else if (mouseEvent.getEventType() == MouseEvent.MOUSE_EXITED) {
                    ((Rectangle)(pane.getChildren().get(0))).setFill(Color.PURPLE);
                }
            }


        }

        // Insert methods required by the EventHandler interface here
    }

    public void updateProfile() throws FileNotFoundException {
        String firstName = "";
        String lastName = "";
        String username = App.username;
        String password = "";
        Scanner sc = new Scanner(new File("usernames.txt"));
        int counter = 0;

        while (sc.hasNextLine()) {

            String str = sc.nextLine();
            if (App.username.equals(str)) {

                Scanner namesScanner = new Scanner(new File("names.txt"));

                for (int i = 0; i <= counter; i++) {

                    if (i == counter) {
                        firstName = namesScanner.next();
                        lastName = namesScanner.next();
                        break;
                    } else {
                        String namesStr = namesScanner.nextLine();
                    }

                }

                Scanner passwordScanner = new Scanner(new File("passwords.txt"));


                for (int i = 0; i <= counter; i++) {
                    if (i == counter) {
                        password = passwordScanner.next();
                        break;
                    } else {
                        String namesStr = passwordScanner.nextLine();
                    }

                }

                break;
            }
            counter += 1;

        }
        Scanner eventsSc = new Scanner(new File(App.username + ".txt"));

        int[] arrayCount = new int[12];

        int counter2 = 0;
        while (eventsSc.hasNextLine()) {
            int monthNumber = eventsSc.nextInt();
            arrayCount[monthNumber - 1] = arrayCount[monthNumber - 1] + 1;
            eventsSc.nextLine();
            counter2 += 1;
        }
        Label nameProfileLabel = new Label(firstName + " " + lastName + "'s Profile");
        nameProfileLabel.setFont(montserratBold);

        Label firstNameLabel = new Label("First Name - " + firstName);
        firstNameLabel.setFont(montserratBoldSmall);
        Label lastNameLabel = new Label("Last Name - " + lastName);
        lastNameLabel.setFont(montserratBoldSmall);
        Label usernameLabel = new Label("Username - " + username);
        usernameLabel.setFont(montserratBoldSmall);
        Label passwordLabel = new Label("Password - " + password);
        passwordLabel.setFont(montserratBoldSmall);

        Label totalEventsCreatedLabel = new Label("Events Created: " + counter2);
        totalEventsCreatedLabel.setFont(montserratBold);

        VBox daysTotalEvents = new VBox();
        daysTotalEvents.setAlignment(Pos.CENTER);

        BackgroundFill fill = new BackgroundFill(Color.ORANGERED, new CornerRadii(20), new Insets(0));
        daysTotalEvents.setBackground(new Background(fill));
        daysTotalEvents.setMaxWidth(650);
        daysTotalEvents.setMaxWidth(650);
        daysTotalEvents.setMinHeight(50);
        daysTotalEvents.setMaxHeight(50);



        HBox monthLabels = new HBox();
        monthLabels.setAlignment(Pos.CENTER);
        monthLabels.setSpacing(15);

        Label jan = new Label("JAN");
        Label feb = new Label("FEB");
        Label mar = new Label("MAR");
        Label apr = new Label("APR");
        Label may = new Label("MAY");
        Label jun = new Label("JUN");
        Label jul = new Label("JUL");
        Label aug = new Label("AUG");
        Label sep = new Label("SEP");
        Label oct = new Label("OCT");
        Label nov = new Label("NOV");
        Label dec = new Label("DEC");

        monthLabels.getChildren().addAll(jan, feb, mar, apr, may, jun, jul, aug, sep , oct, nov , dec);

        HBox monthValues = new HBox();
        monthValues.setAlignment(Pos.CENTER);
        monthValues.setSpacing(40);

        Label janV = new Label(arrayCount[0] + "");
        Label febV = new Label(arrayCount[1] + "");
        Label marV = new Label(arrayCount[2] + "");
        Label aprV = new Label(arrayCount[3] + "");
        Label mayV = new Label(arrayCount[4] + "");
        Label junV = new Label(arrayCount[5] + "");
        Label julV = new Label(arrayCount[6] + "");
        Label augV = new Label(arrayCount[7] + "");
        Label sepV = new Label(arrayCount[8] + "");
        Label octV = new Label(arrayCount[9] + "");
        Label novV = new Label(arrayCount[10] + "");
        Label decV = new Label(arrayCount[11] + "");

        monthValues.getChildren().addAll(janV, febV, marV, aprV, mayV, junV, julV, augV, sepV , octV, novV, decV);

        ObservableList<Node> children = monthLabels.getChildren();
        for (int i = 0; i < children.size(); i++) {
            Label text = (Label)(children.get(i));
            text.setFont(montserratBoldSmall);

        }

        children = monthValues.getChildren();
        for (int i = 0; i < children.size(); i++) {
            Label text = (Label)(children.get(i));
            text.setFont(montserratLight);

        }

        daysTotalEvents.getChildren().addAll(monthLabels, monthValues);


        StackPane signOutButton = new StackPane();
        signOutButton.setId("button");
        signOutButton.setAlignment(Pos.CENTER);
        signOutButton.setOnMouseClicked(handler);
        signOutButton.setOnMouseEntered(handler);
        signOutButton.setOnMouseExited(handler);


        Rectangle signOutRectangle = new Rectangle();
        signOutRectangle.setWidth(100);
        signOutRectangle.setHeight(30);
        signOutRectangle.setArcHeight(20);
        signOutRectangle.setArcWidth(20);
        signOutRectangle.setFill(Color.PURPLE);

        Label signOut = new Label("Sign Out");
        signOut.setFont(montserratSemibold);

        signOutButton.getChildren().addAll(signOutRectangle, signOut);

        mainVbox.getChildren().clear();
        mainVbox.getChildren().addAll(navbar, firstNameLabel, lastNameLabel, usernameLabel, passwordLabel, totalEventsCreatedLabel, daysTotalEvents, signOutButton);
        mainVbox.setAlignment(Pos.CENTER);
        mainVbox.setSpacing(20);

        mainVbox.setLayoutY(100);
        root.getChildren().clear();
        root.getChildren().addAll(imageView, mainVbox);
    }
}
