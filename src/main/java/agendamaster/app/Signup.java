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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;

public class Signup extends Scene {

    App app;


    Rectangle signUpButton;
    Label loginButton;
    TextField firstNameTextField;
    TextField lastNameTextField;
    TextField usernameTextField;
    TextField passwordTextField;
    Font montserratRegular = Font.loadFont(getClass().getResourceAsStream("/MontserratAlternates-Regular.otf"), 15);
    public Signup(App app) throws FileNotFoundException {
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
        int login_height = 450;

        Rectangle login = new Rectangle();
        login.setFill(Color.WHITE);
        login.setX(width/2 - login_width * 0.5);
        login.setY(height/2 - login_height * 0.5 + 40);
        login.setWidth(login_width);
        login.setHeight(login_height);
        login.setArcHeight(25);
        login.setArcWidth(25);


        Pane pane = new Pane();



        VBox loginUI = new VBox();

        loginUI.setLayoutX(300);
        loginUI.setLayoutY(10);
        loginUI.setAlignment(Pos.CENTER);
        loginUI.setSpacing(10);
        loginUI.setPrefWidth(login_width);
        loginUI.setPrefHeight(login_height + 150);



        //BackgroundFill fill = new BackgroundFill(Color.AQUA,  new CornerRadii(2), new Insets(0));
        //loginUI.setBackground(new Background(fill));

        Font montserratBold = Font.loadFont(getClass().getResourceAsStream("/Montserrat-Bold.otf"), 30);
        Font montserratBoldSmall = Font.loadFont(getClass().getResourceAsStream("/Montserrat-Bold.otf"), 20);
        montserratRegular = Font.loadFont(getClass().getResourceAsStream("/MontserratAlternates-Regular.otf"), 15);
        Font montserratLight = Font.loadFont(getClass().getResourceAsStream("/MontserratAlternates-Light.otf"), 15);
        Font montserratSemibold = Font.loadFont(getClass().getResourceAsStream("/MontserratAlternates-SemiBold.otf"), 15);


        EventHandler handler = new MyEventHandler();


        Label title = new Label("Agenda Master");
        title.setFont(montserratBold);
        title.setPadding(new Insets(0, 0, 45, 0));


        Label signupText = new Label("Signup");
        signupText.setFont(montserratBoldSmall);
        signupText.setPadding(new Insets(0, 0, 15, 0));

        Label firstName = new Label("First Name");
        firstNameTextField = new TextField();
        firstName.setFont(montserratSemibold);
        firstNameTextField.setFont(montserratLight);
        firstNameTextField.setMaxWidth(login_width * 4 / 5);

        Label lastName = new Label("Last Name");
        lastNameTextField = new TextField();
        lastName.setFont(montserratSemibold);
        lastNameTextField.setFont(montserratLight);
        lastNameTextField.setMaxWidth(login_width * 4 / 5);


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


        StackPane signUpNodes = new StackPane();
        signUpNodes.setOnMouseEntered(handler);
        signUpNodes.setOnMouseExited(handler);
        signUpNodes.setOnMouseClicked(handler);

        signUpButton = new Rectangle();



        signUpButton.setWidth(login_width * 3/4);
        signUpButton.setHeight(30);
        signUpButton.setArcWidth(25);
        signUpButton.setArcHeight(25);
        signUpButton.setFill(Color.PURPLE);


        Label signUpButtonText = new Label("Sign Up");
        signUpButtonText.setFont(montserratSemibold);




        signUpNodes.getChildren().addAll(signUpButton, signUpButtonText);

        loginButton = new Label("Or Login");
        loginButton.setFont(montserratLight);
        loginButton.setPadding(new Insets(0, 0, 20, 0));
        loginButton.setId("login");

        loginButton.setOnMouseEntered(handler);
        loginButton.setOnMouseClicked(handler);
        loginButton.setOnMouseExited(handler);








        loginUI.getChildren().addAll(title, signupText, firstName, firstNameTextField, lastName, lastNameTextField, username, usernameTextField, password, passwordTextField, space, signUpNodes, loginButton);
        root.getChildren().addAll(imageView, login, loginUI);






    }

    private class MyEventHandler implements EventHandler<MouseEvent> {


        @Override
        public void handle(MouseEvent mouseEvent) {
            Object item = mouseEvent.getSource();

            if (item instanceof StackPane) {
                if (mouseEvent.getEventType() == MouseEvent.MOUSE_ENTERED) {

                    signUpButton.setFill(Color.LIGHTBLUE);

                }
                else if (mouseEvent.getEventType() == MouseEvent.MOUSE_CLICKED) {
                    try {
                        validateSignUpCredentials(firstNameTextField, lastNameTextField, usernameTextField, passwordTextField);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }


                }
                else if (mouseEvent.getEventType() == MouseEvent.MOUSE_EXITED) {
                    signUpButton.setFill(Color.PURPLE);
                }
            }
            else if (item instanceof Label) {

                if (mouseEvent.getEventType() == MouseEvent.MOUSE_ENTERED) {

                    loginButton.setTextFill(Color.PURPLE);

                }
                else if (mouseEvent.getEventType() == MouseEvent.MOUSE_CLICKED) {
                    app.showScene(app.loginScene);
                }
                else if (mouseEvent.getEventType() == MouseEvent.MOUSE_EXITED) {

                    loginButton.setTextFill(Color.BLACK);
                }


            }

        }

        // Insert methods required by the EventHandler interface here
    }

    public void validateSignUpCredentials(TextField first, TextField last, TextField username, TextField password) throws IOException {
        String firstText = first.getText().trim();
        String lastText = last.getText().trim();
        String usernameText = username.getText().trim();
        String passwordText = password.getText().trim();

        if (firstText.length() > 0 && lastText.length() > 0 && usernameText.length() >= 5 && passwordText.length() >= 5){

            BufferedWriter bwNames = new BufferedWriter(new FileWriter("names.txt", true) );
            BufferedWriter bwPasswords = new BufferedWriter(new FileWriter("passwords.txt", true));
            BufferedWriter bwUsernames = new BufferedWriter(new FileWriter("usernames.txt", true));

            bwNames.write(firstText + " " + lastText + "\n");
            bwPasswords.write(passwordText + "\n");
            bwUsernames.write(usernameText + "\n");

            bwNames.flush();
            bwPasswords.flush();
            bwUsernames.flush();

            app.calendarScene.updateCalendar();
            app.showScene(app.calendarScene);

            App.username = usernameText;

            Stage alert = new Stage();
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Success");

            VBox root = new VBox();
            Scene alertScene = new Scene(root, 300, 100);


            Label errorText = new Label("Congratulations, you have made your account!");
            errorText.setFont(montserratRegular);
            errorText.setWrapText(true);
            errorText.setPadding(new Insets(10));
            errorText.setAlignment(Pos.CENTER);

            root.setAlignment(Pos.CENTER);
            root.getChildren().add(errorText);
            alert.setScene(alertScene);
            alert.showAndWait();




        } else {
            Stage alert = new Stage();
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Error");

            VBox root = new VBox();
            Scene alertScene = new Scene(root, 300, 100);


            Label errorText = new Label("Please ensure your names are at least one character and your credentials are at least five characters");
            errorText.setFont(montserratRegular);
            errorText.setWrapText(true);
            errorText.setPadding(new Insets(10));
            errorText.setAlignment(Pos.CENTER);

            root.setAlignment(Pos.CENTER);
            root.getChildren().add(errorText);
            alert.setScene(alertScene);
            alert.showAndWait();

        }



    }

}
