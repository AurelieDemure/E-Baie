package org.codingweek.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.codingweek.ApplicationContext;
import org.codingweek.db.entity.Chat;
import org.codingweek.db.entity.Offer;
import org.codingweek.db.entity.User;
import org.codingweek.model.ChatModel;
import org.codingweek.model.DatabaseHandler;
import org.codingweek.model.ImageHandler;
import org.codingweek.model.MarketModel;

import java.net.URL;
import java.util.*;

import java.util.Calendar;
import java.util.Date;

public class TchatController extends Controller implements Observeur {
    @FXML
    public VBox contact_list;
    @FXML
    public VBox receiver_messages;
    @FXML
    public VBox sender_messages;
    @FXML
    public TextField message;

    @FXML
    public Label sender;

    @FXML
    public Label receiver;

    @FXML
    public Label destinataire;

    public List<User> contacts = new ArrayList<User>();

    public List<String> senderMessages = new ArrayList<String>();
    public List<String> receiverMessages = new ArrayList<String>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.receiver.setText("example@example.com");

        this.contacts.clear();
        this.senderMessages.clear();
        this.receiverMessages.clear();

        String current_user = ApplicationContext.getInstance().getUser_authentified().getEmail();

        this.contacts = ChatModel.getContacts(current_user);

        this.senderMessages = ChatModel.getSenderMessages(current_user, this.receiver.getText());
        this.receiverMessages = ChatModel.getReceiverMessages(current_user, this.receiver.getText());

        refresh();
    }


    public Date createDate(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public void testInitialise() {
        User user1 = DatabaseHandler.getInstance().getDbManager().getEntity(User.class, "p");

        /*User user2 = new User("example@example.com", "Jane", "Doe", "password123", "0701234567", "Paris", "Another description", 50, new Date());
        User user3 = new User("test@test2.com","Test2", "User2", "test123","0601528495", "Nancy", "description", 100, new Date());

        Chat chat1 = new Chat(user1, user2, "Hello", createDate(11, 0)); // 11:00
        Chat chat2 = new Chat(user2, user1, "Hi", createDate(11, 20)); // 11:20

        Chat chat7 = new Chat(user2, user1, "How are you?", createDate(11, 40)); // 11:40
        Chat chat8 = new Chat(user1, user2, "I'm good, thanks!", createDate(12, 0)); // 12:00

        Chat chat5 = new Chat(user2, user3, "Hey!", createDate(13, 0)); // 13:00
        Chat chat6 = new Chat(user3, user2, "What's up?", createDate(13, 20)); // 13:20

        DatabaseHandler.getInstance().getDbManager().saveEntity(user1);
        DatabaseHandler.getInstance().getDbManager().saveEntity(user2);
        DatabaseHandler.getInstance().getDbManager().saveEntity(user3);
        DatabaseHandler.getInstance().getDbManager().saveEntity(chat1);
        DatabaseHandler.getInstance().getDbManager().saveEntity(chat2);
        DatabaseHandler.getInstance().getDbManager().saveEntity(chat5);
        DatabaseHandler.getInstance().getDbManager().saveEntity(chat6);
        DatabaseHandler.getInstance().getDbManager().saveEntity(chat7);
        DatabaseHandler.getInstance().getDbManager().saveEntity(chat8);*/

        /*User user1 = new User("e@example.com", "ggg", "Doe", "password123", "0701234567", "Paris", "Another description", 50, new Date());
        User user2 = new User("f@test2.com","hhh", "User2", "test123","0601528495", "Nancy", "description", 100, new Date());*/

        User user2 = DatabaseHandler.getInstance().getDbManager().getEntity(User.class, "e@example.com");
        User user3 = DatabaseHandler.getInstance().getDbManager().getEntity(User.class, "f@test2.com");

    }

    @Override
    public void refresh() {
        this.contact_list.getChildren().clear();
        this.receiver_messages.getChildren().clear();
        this.sender_messages.getChildren().clear();

        for(User user : this.contacts){
            Button button = makeContactButton(user);
            this.contact_list.getChildren().add(button);
        }

        for(String message : this.senderMessages){
            Pane pane = makeSenderMessagePane(message);
            this.sender_messages.getChildren().add(pane);
        }

        for(String message : this.receiverMessages){
            Pane pane = makeReceiverMessagePane(message);
            this.receiver_messages.getChildren().add(pane);
        }
    }

    /** ces containers sont des bouttons qui change la conversation affichée en fonction du contact cliqué
     *
     * @param user
     * @return
     */
    public Button makeContactButton(User user){
        Button button = new Button();
        button.getStyleClass().add("contact-button");
        button.setPrefWidth(150);
        button.setPrefHeight(50);
        button.setText(user.getEmail());
        button.setOnAction((event) -> {
            this.receiver.setText(user.getEmail());
            this.senderMessages = ChatModel.getSenderMessages(ApplicationContext.getInstance().getUser_authentified().getEmail(), this.receiver.getText());
            this.receiverMessages = ChatModel.getReceiverMessages(ApplicationContext.getInstance().getUser_authentified().getEmail(), this.receiver.getText());
            refresh();
        });
        return button;
    }

    public Pane makeSenderMessagePane(String message){
        Label messageLabel = new Label(message);
        messageLabel.getStyleClass().add("message");
        messageLabel.setMaxWidth(150);

        Pane pane = new Pane();
        pane.getChildren().add(messageLabel);
        return pane;
    }

    public Pane makeReceiverMessagePane(String message){
        Label messageLabel = new Label(message);
        messageLabel.getStyleClass().add("message");
        messageLabel.setMaxWidth(150);

        Pane pane = new Pane();
        pane.getChildren().add(messageLabel);
        return pane;
    }

    @Override
    public void update() {

    }

    @FXML
    public void sendMessage(ActionEvent actionEvent) {
        String current_user = ApplicationContext.getInstance().getUser_authentified().getEmail();
        String message = this.message.getText();
        String receiver = this.receiver.getText();

        User sender = DatabaseHandler.getInstance().getDbManager().getEntity(User.class, current_user);
        User receiver_user = DatabaseHandler.getInstance().getDbManager().getEntity(User.class, receiver);

        Chat chat = new Chat(sender, receiver_user, message, new Date());
        DatabaseHandler.getInstance().getDbManager().saveEntity(chat);

        this.senderMessages = ChatModel.getSenderMessages(current_user, this.receiver.getText());
        this.receiverMessages = ChatModel.getReceiverMessages(current_user, this.receiver.getText());
        refresh();
    }
}
