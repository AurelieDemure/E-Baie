package org.codingweek.model;

import org.codingweek.db.entity.Chat;
import org.codingweek.db.entity.User;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ChatModel {

    /** Return all the users that already interacted with the user */
    public static List<User> getContacts(String email) {
        List<Chat> chats = DatabaseHandler.getInstance().getDbManager().getAllEntity(Chat.class);

        if (chats == null) {
            return Collections.emptyList();
        }

        List<User> users = chats.stream()
                .filter(chat -> {
                    User receiver = chat.getReceiver();
                    User sender = chat.getSender();
                    return receiver != null && sender != null &&
                            (receiver.getEmail().equals(email) || sender.getEmail().equals(email));
                })
                .map(chat -> {
                    User receiver = chat.getReceiver();
                    User sender = chat.getSender();
                    return receiver.getEmail().equals(email) ? sender : receiver;
                })
                .distinct()
                .collect(Collectors.toList());

        return users;
    }

    /** Return all the messages between the user and the contact sorted by date */
    public static List<Chat> getChats(String currentUser, String currentReceiver) {
        List<Chat> chats = DatabaseHandler.getInstance().getDbManager().getAllEntity(Chat.class);

        if (chats == null) {
            return Collections.emptyList();
        }

        List<Chat> filteredChats = chats.stream()
                .filter(chat -> {
                    User receiver = chat.getReceiver();
                    User sender = chat.getSender();
                    return receiver != null && sender != null &&
                            ((receiver.getEmail().equals(currentReceiver) && sender.getEmail().equals(currentUser)) ||
                                    (receiver.getEmail().equals(currentUser) && sender.getEmail().equals(currentReceiver)));
                })
                .sorted(Comparator.comparing(Chat::getDate))
                .collect(Collectors.toList());

        return filteredChats;
    }
}