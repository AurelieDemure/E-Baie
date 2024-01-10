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

    public List<Chat> chats = new ArrayList<Chat>();

    public String current_user;
    public String current_receiver;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.contacts.clear();
        this.chats.clear();
        this.current_user = ApplicationContext.getInstance().getUser_authentified().getEmail();
        this.current_receiver = DatabaseHandler.getInstance().getDbManager().getEntity(User.class, "example@example.com").getEmail();

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
        this.contacts = ChatModel.getContacts(current_user);
        this.chats = ChatModel.getChats(current_user, current_receiver);

        this.contact_list.getChildren().clear();
        this.receiver_messages.getChildren().clear();
        this.sender_messages.getChildren().clear();
        this.message.setText("");

        for(User user : this.contacts){
            Button button = makeContactButton(user);
            this.contact_list.getChildren().add(button);
        }

        for(Chat chat: this.chats){
            Pane pane = makeMessagePane(chat);
            if(chat.getSender().getEmail().equals(current_user)){
                this.receiver_messages.getChildren().add(new Pane());
            } else {
                this.receiver_messages.getChildren().add(pane);
            }
        }
    }

    public Button makeContactButton(User user){
        Button button = new Button();
        button.getStyleClass().add("contact-button");
        button.setPrefWidth(150);
        button.setPrefHeight(50);
        button.setText(user.getEmail());
        button.setOnAction((event) -> {
            this.current_receiver = user.getEmail();
            refresh();
        });
        return button;
    }

    public HBox makeMessagePane(Chat conversation){
        Label messageLabel = new Label(conversation.getMessage());
        Label dateLabel = new Label(conversation.getDate().toString());

        HBox hbox = new HBox();
        hbox.getChildren().add(messageLabel);
        hbox.getChildren().add(dateLabel);
        return hbox;
    }

    @Override
    public void update() {}

    @FXML
    public void sendMessage(ActionEvent actionEvent) {
        String message = this.message.getText();

        User sender = DatabaseHandler.getInstance().getDbManager().getEntity(User.class, current_user);
        User receiver_user = DatabaseHandler.getInstance().getDbManager().getEntity(User.class, current_receiver);

        Chat chat = new Chat(sender, receiver_user, message, createDate(Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE)));
        DatabaseHandler.getInstance().getDbManager().saveEntity(chat);

        refresh();
    }
}
