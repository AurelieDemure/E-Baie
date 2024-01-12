package org.codingweek.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.codingweek.ApplicationContext;
import org.codingweek.db.entity.Chat;
import org.codingweek.db.entity.Notification;
import org.codingweek.db.entity.User;
import org.codingweek.model.ChatModel;
import org.codingweek.model.DatabaseHandler;

import java.net.URL;
import java.util.*;

import java.util.Calendar;
import java.util.Date;

public class TchatController extends Controller implements Observeur {
    @FXML
    public VBox contact_list;
    @FXML
    public GridPane conversation;

    @FXML
    public TextArea message;

    @FXML
    public Label receiver;

    public List<User> contacts = new ArrayList<User>();

    public List<Chat> chats = new ArrayList<Chat>();

    public String current_user;
    public String current_receiver;

    @FXML
    public ScrollPane scrollPaneConversation;

    @FXML
    public Button send;

    public Boolean isFirstContact = false;

    public Label email;
    public Label tel;
    public Label address;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.contacts.clear();
        this.chats.clear();
        this.current_user = ApplicationContext.getInstance().getUser_authentified().getEmail();
        scrollPaneConversation.vvalueProperty().bind(conversation.heightProperty());

        if(ApplicationContext.getInstance().getContactUser() != null){
            this.current_receiver = ApplicationContext.getInstance().getContactUser().getEmail();
            this.isFirstContact = true;
            ApplicationContext.getInstance().setContactUser(null);
            this.send.setDisable(false);
            this.message.setDisable(false);
            refresh();
        } else if(ChatModel.getContacts(current_user).size() > 0 && ChatModel.getContacts(current_user) != null){
            this.current_receiver = ChatModel.getContacts(current_user).get(0).getEmail();
            this.send.setDisable(false);
            this.message.setDisable(false);
            refresh();
        }else{
            this.send.setDisable(true);
            this.message.setDisable(true);
        }
    }

    public Date createDate(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    @Override
    public void refresh() {
        int messageCounter = 0;

        this.contacts = ChatModel.getContacts(current_user);
        this.chats = ChatModel.getChats(current_user, current_receiver);

        this.contact_list.getChildren().clear();
        this.conversation.getChildren().clear();

        this.message.setText("");

        /* receiver information (right panel) */
        if(current_receiver != null){
            User user = DatabaseHandler.getInstance().getDbManager().getEntity(User.class, current_receiver);
            this.receiver.setText(user.getFirstName() + " " + user.getLastName());
            this.email.setText(user.getEmail());
            if (user.getPhone() == null) {
                this.tel.setText("Pas de numero disponible");
            } else {this.tel.setText(user.getPhone());}
            if (user.getAddress() == null) {
                this.tel.setText("Pas d'adresse disponible");
            } else {this.address.setText(user.getAddress());}
        } else {
            this.receiver.setText("No receiver selected");
        }

        /* add first contact */
        if (this.isFirstContact) {
            User user = DatabaseHandler.getInstance().getDbManager().getEntity(User.class, current_receiver);
            Button button = makeContactButton(user);
            button.getStyleClass().add("contact-button");
            this.contact_list.getChildren().add(button);
        }

        /* contact list (left panel) */
        for(User user : this.contacts){
            /* if it is the first contact, he is already displayed */
            if(this.isFirstContact && user.getEmail().equals(current_receiver)){
                continue;
            }
            Button button = makeContactButton(user);
            button.getStyleClass().add("contact-button");
            this.contact_list.getChildren().add(button);
        }

        /* conversation */
        for(Chat chat: this.chats){
            Label label = new Label(chat.getMessage());

            if (chat.getSender().getEmail().equals(current_user)) {
                label.getStyleClass().add("user-message");
            } else {
                label.getStyleClass().add("other-message");
            }

            int column = chat.getSender().getEmail().equals(current_user) ? 1 : 0;
            conversation.add(label, column, messageCounter);

            messageCounter++;
        }
        this.isFirstContact = false;
    }

    public Button makeContactButton(User user){
        Button button = new Button();
        button.getStyleClass().add("contact-button");
        button.setPrefWidth(200);
        button.setPrefHeight(50);
        button.setText(user.getFirstName() + " " + user.getLastName());
        if(this.current_receiver != null && this.current_receiver.equals(user.getEmail())){
            button.getStyleClass().add("contact-button-selected");
        }
        button.setOnAction((event) -> {
            this.current_receiver = user.getEmail();
            refresh();
        });
        return button;
    }

    @Override
    public void update() {
        //refresh();
    }

    @FXML
    public void sendMessage(ActionEvent actionEvent) {
        if(this.current_receiver != null){
            String message = this.message.getText();
            if (message.isEmpty()) {
                return;
            }

            User sender = DatabaseHandler.getInstance().getDbManager().getEntity(User.class, current_user);
            User receiver_user = DatabaseHandler.getInstance().getDbManager().getEntity(User.class, current_receiver);

            Chat chat = new Chat(sender, receiver_user, message, createDate(Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE)));
            DatabaseHandler.getInstance().getDbManager().saveEntity(chat);

            Notification notification = new Notification("Message de " + sender.getFirstName() + " " + sender.getLastName(),receiver_user, false, "unique", new Date());
            DatabaseHandler.getInstance().getDbManager().saveEntity(notification);

            refresh();
        }
    }
}
