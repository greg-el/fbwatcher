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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UI extends Application{
    WebDriver loggedDriver;

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

        //keywords list
        final ListView<String> groupKeywordsView = new ListView<String>();
        final ObservableList<String> groupKeywords = FXCollections.observableArrayList();

        //labels
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
        Button loginButton = new Button("Login");

        //Background
        GridPane gridPane = new GridPane();
        gridPane.setMinSize(500, 500);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.CENTER);

        //Adding items to Vboxes
        VBox addRemove = new VBox(addGroup, removeGroup);
        final VBox login = new VBox(emailLabel, emailField, passwordLabel, passwordField, loginButton, loggedInConfirmation);
        VBox keywords = new VBox(addKeywordLabel,keywordField, addKeyword, removeKeyword, groupKeywordsView);

        //Adding VBoxes
        gridPane.add(login, 0, 1);
        gridPane.add(addRemove,1, 3);
        gridPane.add(keywords, 3, 3);

        //Adding lists
        gridPane.add(groupListView, 0, 3);
        gridPane.add(groupsSelectedView,2,3);


        stage.setScene(new Scene(gridPane, 200, 500));
        stage.show();


        // Event listeners
        addGroup.setOnAction(new EventHandler<ActionEvent>() {
             public void handle(ActionEvent ActionEvent) {
                String selection = groupListView.getSelectionModel().getSelectedItem();
                if (selection != null) {
                    groupListView.getSelectionModel().clearSelection();
                    groupList.remove(selection);
                    groupsSelected.add(selection);
                    groupsSelectedView.setItems(groupsSelected.sorted()); //need this here for some reason on the add
                    Object[] test = groupsSelected.toArray();
                    List<String> sortedTest = new ArrayList<String>();
                    Collections.addAll(Arrays.asList(test));
                    for (Object test01 : test) {
                        sortedTest.add(test01);

                    }

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
                loggedDriver = methods.login(driver, emailString, passwordString);

                emailField.clear();
                emailField.setDisable(true);

                passwordField.clear();
                passwordField.setDisable(true);

                login.setDisable(true);
                loggedInConfirmation.setText("Logged In");
            }
        });

    }
}
