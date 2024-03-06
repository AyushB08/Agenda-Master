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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.Scanner;

public class Login extends Scene {

    Rectangle loginButton;
    Label signUpButton;
    TextField usernameTextField;
    TextField passwordTextField;
    Font montserratRegular = Font.loadFont(getClass().getResourceAsStream("/MontserratAlternates-Regular.otf"), 15);

    App app;

    public Login(App app) throws FileNotFoundException {
        super(new Group(), 900, 600);

        this.app = app;


        Group root = (Group)(getRoot());

        int width = 900;
        int height = 600;

        Image image = new Image(new FileInputStream("src/main/resources/images/background-login.jpg"));  ;
        ImageView imageView = new ImageView(image);
        imageView.setX(0);
        imageView.setY(0);
        imageView.setFitHeight(600);
        imageView.setFitWidth(900);

        int login_width = 300;
        int login_height = 300;

        Rectangle login = new Rectangle();
        login.setFill(Color.WHITE);
        login.setX(width/2 - login_width * 0.5);
        login.setY(height/2 - login_height * 0.5 + 70);
        login.setWidth(login_width);
        login.setHeight(login_height);
        login.setArcHeight(25);
        login.setArcWidth(25);


        Pane pane = new Pane();



        VBox loginUI = new VBox();

        loginUI.setLayoutX(300);
        loginUI.setLayoutY(100);
        loginUI.setAlignment(Pos.CENTER);
        loginUI.setSpacing(10);
        loginUI.setPrefWidth(login_width);
        loginUI.setPrefHeight(login_height + 150);



        //BackgroundFill fill = new BackgroundFill(Color.AQUA,  new CornerRadii(2), new Insets(0));
        //loginUI.setBackground(new Background(fill));

        Font montserratBold = Font.loadFont(getClass().getResourceAsStream("/Montserrat-Bold.otf"), 30);
        Font montserratBoldSmall = Font.loadFont(getClass().getResourceAsStream("/Montserrat-Bold.otf"), 20);

        Font montserratLight = Font.loadFont(getClass().getResourceAsStream("/MontserratAlternates-Light.otf"), 15);
        Font montserratSemibold = Font.loadFont(getClass().getResourceAsStream("/MontserratAlternates-SemiBold.otf"), 15);


        EventHandler handler = new MyEventHandler();


        Label title = new Label("Agenda Master");
        title.setFont(montserratBold);
        title.setPadding(new Insets(0, 0, 70, 0));


        Label loginText = new Label("Login");
        loginText.setFont(montserratBoldSmall);
        loginText.setPadding(new Insets(0, 0, 15, 0));


        Label username = new Label("Username");
        usernameTextField = new TextField();
        username.setFont(montserratSemibold);
        usernameTextField.setFont(montserratLight);
        usernameTextField.setMaxWidth(login_width * 4 / 5);


        Label password = new Label("Password");
        password.setFont(montserratSemibold);

        passwordTextField = new PasswordField();
        passwordTextField.setMaxWidth(login_width * 4 / 5);

        Label space = new Label();
        space.setMinHeight(0);
        space.setMaxHeight(0);


        StackPane loginNodes = new StackPane();

        loginNodes.setOnMouseEntered(handler);
        loginNodes.setOnMouseClicked(handler);
        loginNodes.setOnMouseExited(handler);

        loginButton = new Rectangle();

        loginButton.setWidth(login_width * 3/4);
        loginButton.setHeight(30);
        loginButton.setArcWidth(25);
        loginButton.setArcHeight(25);
        loginButton.setFill(Color.PURPLE);

        Label loginButtonText = new Label("Login");
        loginButtonText.setFont(montserratSemibold);


        loginNodes.getChildren().addAll(loginButton, loginButtonText);

        signUpButton = new Label("Or Sign Up");
        signUpButton.setFont(montserratLight);
        signUpButton.setPadding(new Insets(0, 0, 20, 0));

        signUpButton.setOnMouseEntered(handler);
        signUpButton.setOnMouseClicked(handler);
        signUpButton.setOnMouseExited(handler);







        loginUI.getChildren().addAll(title, loginText, username, usernameTextField, password, passwordTextField, space, loginNodes, signUpButton);
        root.getChildren().addAll(imageView, login, loginUI);




    }

    private class MyEventHandler implements EventHandler<MouseEvent> {


        @Override
        public void handle(MouseEvent mouseEvent) {
            Object item = mouseEvent.getSource();

            if (item instanceof StackPane) {
                if (mouseEvent.getEventType() == MouseEvent.MOUSE_ENTERED) {

                    loginButton.setFill(Color.LIGHTBLUE);
                }
                else if (mouseEvent.getEventType() == MouseEvent.MOUSE_CLICKED) {
                    try {
                        validateLogInCredentials(usernameTextField, passwordTextField);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else if (mouseEvent.getEventType() == MouseEvent.MOUSE_EXITED) {
                    loginButton.setFill(Color.PURPLE);
                }
            }
            else if (item instanceof Label) {

                if (mouseEvent.getEventType() == MouseEvent.MOUSE_ENTERED) {

                    signUpButton.setTextFill(Color.PURPLE);

                }
                else if (mouseEvent.getEventType() == MouseEvent.MOUSE_CLICKED) {

                    app.showScene(app.signupScene);


                }
                else if (mouseEvent.getEventType() == MouseEvent.MOUSE_EXITED) {

                    signUpButton.setTextFill(Color.BLACK);
                }


            }

        }

        // Insert methods required by the EventHandler interface here
    }

    public boolean validateLogInCredentials(TextField username, TextField password) throws IOException {


        Scanner scUsername = new Scanner(new File("usernames.txt"));
        Scanner scPassword = new Scanner(new File("passwords.txt"));

        String usernameInput = username.getText();
        String passwordInput = password.getText();

        int counter = 0;
        while (scUsername.hasNext()) {
            String s = scUsername.nextLine();
            if (s.equals(usernameInput)) {
                for (int i = 0; i <= counter; i++) {
                    String pass = scPassword.nextLine();
                    if (i == counter) {
                        if (passwordInput.equals(pass)) {
                            App.username = usernameInput;
                            app.calendarScene.updateCalendar();
                            app.showScene(app.calendarScene);
                            return true;
                        } else {
                            break;
                        }
                    }
                }
            }
            counter += 1;
        }

        Stage alert = new Stage();
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle("Error");

        VBox root = new VBox();
        Scene alertScene = new Scene(root, 300, 100);


        Label errorText = new Label("Credentials could not be found.");
        errorText.setFont(montserratRegular);
        errorText.setWrapText(true);
        errorText.setPadding(new Insets(10));
        errorText.setAlignment(Pos.CENTER);

        root.setAlignment(Pos.CENTER);
        root.getChildren().add(errorText);
        alert.setScene(alertScene);
        alert.showAndWait();
        return false;
    }


}

