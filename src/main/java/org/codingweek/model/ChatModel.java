package org.codingweek.model;

import org.codingweek.db.entity.Chat;
import org.codingweek.db.entity.Notification;
import org.codingweek.db.entity.Offer;
import org.codingweek.db.entity.User;
import org.codingweek.model.filter.Frequency;
import org.codingweek.model.filter.OfferType;
import org.codingweek.model.filter.Price;
import org.codingweek.model.filter.SortOffer;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ChatModel {

    private static List<User> users;
    private static List<String> senders_messages;
    private static List<String> receivers_messages;

    /** Return all the users that already interacted with the user */
    public static List<User> getContacts(String email) {
        List<Chat> chats = DatabaseHandler.getInstance().getDbManager().getAllEntity(Chat.class);

        users = chats.stream()
                .filter(chat -> chat.getReceiver().getEmail().equals(email) || chat.getSender().getEmail().equals(email))
                .map(chat -> chat.getReceiver().getEmail().equals(email) ? chat.getSender() : chat.getReceiver())
                .distinct()
                .collect(Collectors.toList());

        return users;
    }

    /** Return all the messages between the user and the contact sorted by date */
    public static List<String> getSenderMessages(String email, String contact) {
        List<Chat> chats = DatabaseHandler.getInstance().getDbManager().getAllEntity(Chat.class);

        senders_messages = chats.stream()
                .filter(chat -> chat.getReceiver().getEmail().equals(email) && chat.getSender().getEmail().equals(contact))
                .map(chat -> chat.getMessage())
                .collect(Collectors.toList());
        return senders_messages;
    }

    /** Return all the messages between the user and the contact sorted by date */
    public static List<String> getReceiverMessages(String email, String contact) {
        List<Chat> chats = DatabaseHandler.getInstance().getDbManager().getAllEntity(Chat.class);

        receivers_messages = chats.stream()
                .filter(chat -> chat.getReceiver().getEmail().equals(contact) && chat.getSender().getEmail().equals(email))
                .map(chat -> chat.getMessage())
                .collect(Collectors.toList());
        return receivers_messages;
    }

    public static List<Chat> getChats(String currentUser, String currentReceiver) {
        List<Chat> chats = DatabaseHandler.getInstance().getDbManager().getAllEntity(Chat.class);

        return chats.stream()
                .filter(chat -> chat.getReceiver().getEmail().equals(currentReceiver) && chat.getSender().getEmail().equals(currentUser) ||
                        chat.getReceiver().getEmail().equals(currentUser) && chat.getSender().getEmail().equals(currentReceiver))
                .sorted(Comparator.comparing(Chat::getDate))
                .collect(Collectors.toList());
    }
}
