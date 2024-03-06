/*
 * This is an agenda app where users can sign up / log in to their account
 * and view their calendar, upcoming events, and profile. This app incorporates
 * the use of txt flat files in order to store user information and a result, all
 * of the user's data is preserved locally everytime the program runs. Please use
 * username: ayushb, password: 12345 to log in and view a pre-made calendar. Or
 * sign up to create a fresh account and experiment with the app.
 */



package agendamaster.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;

public class App extends Application {
    public static String username = "";
    Stage stage = new Stage();
    Login loginScene = new Login(this);
    Signup signupScene = new Signup(this);
    Profile profileScene = new Profile(this);
    Calendar calendarScene = new Calendar(this);
    Upcoming upcomingScene = new Upcoming(this);

    public App() throws FileNotFoundException {
        stage.setTitle("Agenda Master");
    }

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("To-Do List");
        stage.setResizable(false);
        stage.sizeToScene();



        showScene(loginScene);




    }

    public void showScene(Scene x) {
        stage.setScene(x);
        stage.show();
    }








}


