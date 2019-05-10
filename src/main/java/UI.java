import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UI extends Application{
    private WebDriver loggedDriver;
    private Boolean loggedIn = false;

    @Override
    public void start(Stage stage) {
        final Methods methods = new Methods();
        final WebDriver driver = methods.createDriver();

        //Gets groups from DB and displays
        final ListView<String> groupListView = new ListView<String>();
        final ObservableList<String> groupList = FXCollections.observableArrayList(methods.getGroupListFromDatabase());
        groupListView.setItems(groupList.sorted());

        //selected groups list
        final ListView<String> groupsSelectedView = new ListView<String>();
        final ObservableList<String> groupsSelected = FXCollections.observableArrayList();
        //groupListView.setPlaceholder(new Label("No Groups Selected"));

        //keywords list
        final ListView<String> groupKeywordsView = new ListView<String>();
        final ObservableList<String> groupKeywords = FXCollections.observableArrayList();
        //groupKeywordsView.setPlaceholder(new Label("No Keywords"));

        //labels
        final Label startLoginWarning = new Label("");
        Label addKeywordLabel = new Label("Enter keyword to add:");
        Label emailLabel = new Label("Email");
        Label passwordLabel = new Label("Password");
        final Label loggedInConfirmation = new Label("Not Logged In");

        //text fields
        final TextField emailField = new TextField();
        final TextField passwordField = new PasswordField();
        final TextField keywordField = new TextField();

        //Creating buttons
        Button addGroup = new Button(">");
        Button removeGroup = new Button("<");
        Button refreshGroups = new Button("Refresh Groups");
        Button addKeyword = new Button("Add Keyword");
        Button removeKeyword = new Button("Remove Keyword");
        final Button loginButton = new Button("Login");
        Button startButton = new Button("Start Watching Groups");
        startButton.setDisable(false);
        startButton.setId("startButton");

        //Background
        GridPane gridPane = new GridPane();
        gridPane.setMinSize(500, 500);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.CENTER);

        //Adding items to Vboxes
        VBox addRemove = new VBox(addGroup, removeGroup);
        final HBox loginContainer = new HBox(loginButton, loggedInConfirmation);
        loginContainer.setSpacing(10);
        loginContainer.setAlignment(Pos.CENTER_LEFT);

        final VBox login = new VBox(emailLabel, emailField, passwordLabel, passwordField, loginContainer);
        login.setSpacing(2);

        VBox keywords = new VBox(addKeywordLabel,keywordField, addKeyword, groupKeywordsView, removeKeyword);
        keywords.setSpacing(5);

        VBox start = new VBox(startButton, startLoginWarning);


        //Adding VBoxes
        gridPane.add(login, 0, 0);
        gridPane.add(addRemove,2, 1);


        gridPane.add(start,4,0);
        gridPane.add(refreshGroups,0,4);

        gridPane.add(groupListView,0, 1);
        gridPane.add(groupsSelectedView,3, 1);
        gridPane.add(keywords,4, 1);

        Scene scene = new Scene(gridPane, 200, 500);
        scene.getStylesheets().add(getClass().getResource("/test.css").toExternalForm());
        stage.setScene(scene);

        stage.show();


        // Event listeners

        startButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                if (!loggedIn) {
                    startLoginWarning.setText("You need to be logged in before starting.");
                }
                List<String> selections = groupsSelectedView.getItems();
                for (String selection : selections) {
                    List<Post> posts = methods.getGroupPosts(loggedDriver, methods.getGroupUrlFromName(selection));
                    for (Post post : posts) {
                        if (methods.containsKeyword(post, methods.getGroupKeywordsFromName(selection))) {
                            if (!methods.isPostInDatabase(post)) {
                                System.out.println("Group " + selection + " found a post that contains the word youre after.");
                                methods.addPostToDatabase(post);
                            }

                        }
                    }
                }
            }
        });

        addGroup.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent ActionEvent) {
                String selection = groupListView.getSelectionModel().getSelectedItem();
                if (selection != null) {
                    //selectedNotEmpty = true;
                    groupListView.getSelectionModel().clearSelection();
                    groupList.remove(selection);
                    groupsSelected.add(selection);
                    groupsSelectedView.setItems(groupsSelected.sorted()); //need this here for some reason on the add
                    Object[] sortedSelectedGroupArray = groupsSelected.sorted().toArray();
                    List<Object> tempSelectedGroups = new ArrayList<Object>();
                    Collections.addAll(tempSelectedGroups, sortedSelectedGroupArray);
                    int index = tempSelectedGroups.indexOf(selection);
                    ObservableList<String> keywords;
                    groupsSelectedView.getSelectionModel().select(index);
                    groupsSelectedView.getFocusModel().focus(index);

                    keywords = FXCollections.observableArrayList(methods.getGroupKeywordsFromName(selection));
                    groupKeywordsView.setItems(keywords);

                }
             }
         });

        removeGroup.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                String selection = groupsSelectedView.getSelectionModel().getSelectedItem();
                if (selection != null ) {
                    groupsSelectedView.getSelectionModel().clearSelection();
                    groupsSelected.remove(selection);
                    groupList.add(selection);
                    groupListView.setItems(groupList.sorted());
                    List test = groupListView.getItems();
                    if (test.get(0).equals("")) {
                        System.out.println("nothing in list");
                    }
                }
            }
        });

        groupsSelectedView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                ObservableList<String> keywords;
                String selection = groupsSelectedView.getSelectionModel().getSelectedItem();
                if (selection != null) {
                    keywords = FXCollections.observableArrayList(methods.getGroupKeywordsFromName(selection));
                    groupKeywordsView.setItems(keywords);
                }
            }
        });

        addKeyword.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                String newKeyword = keywordField.getText();
                ObservableList<String> keywords;
                if (newKeyword.trim().length() > 0) {
                    String selectedGroup = groupsSelectedView.getSelectionModel().getSelectedItem();
                    methods.addKeywordToGroupDatabase(selectedGroup, newKeyword.trim());
                    keywords = FXCollections.observableArrayList(methods.getGroupKeywordsFromName(selectedGroup));
                    groupKeywordsView.setItems(keywords.sorted());
                    keywordField.clear();
                }
            }
        });

        removeKeyword.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                String selection = groupKeywordsView.getSelectionModel().getSelectedItem();
                ObservableList<String> keywords;
                if (selection != null) {
                    String selectedGroup = groupsSelectedView.getSelectionModel().getSelectedItem();
                    methods.removeKeywordFromGroupDatabase(selectedGroup, selection);
                    keywords = FXCollections.observableArrayList(methods.getGroupKeywordsFromName(selectedGroup));
                    groupKeywordsView.setItems(keywords.sorted());
                }
            }
        });

        loginButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                String emailString = emailField.getText();
                String passwordString = passwordField.getText();
                if (emailString.equals("") || passwordString.equals("")) {
                    loggedInConfirmation.setText("Missing username or password");
                    passwordField.clear();
                } else {
                    Login login = methods.login(driver, emailString, passwordString);

                    if (!login.getLoginSuccessful()) {
                        loggedInConfirmation.setText("Login failed, try again.");
                        passwordField.clear();
                    } else {
                        emailField.clear();
                        emailField.setDisable(true);

                        passwordField.clear();
                        passwordField.setDisable(true);

                        loginButton.setDisable(true);
                        loggedInConfirmation.setText("Logged In");

                        startLoginWarning.setText("");

                        loggedIn = true;
                        loggedDriver = login.getDriver();
                    }
                }
            }
        });

    }
}
