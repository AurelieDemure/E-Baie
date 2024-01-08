CREATE TABLE User (
                      id INTEGER PRIMARY KEY,
                      firstName TEXT NOT NULL,
                      lastName TEXT NOT NULL,
                      password TEXT NOT NULL,
                      email TEXT NOT NULL UNIQUE,
                      balance INTEGER DEFAULT 100
);

CREATE TABLE Offer (
                       id INTEGER PRIMARY KEY,
                       title TEXT NOT NULL,
                       description TEXT,
                       ownerId INTEGER NOT NULL,
                       price REAL NOT NULL,
                       type_offer BOOLEAN NOT NULL,
                       frequency TEXT,
                       localization TEXT,
                       FOREIGN KEY (ownerId) REFERENCES User(id)
);

CREATE TABLE Query (
                       id INTEGER PRIMARY KEY,
                       offerId INTEGER NOT NULL,
                       userId INTEGER NOT NULL,
                       date_query TEXT NOT NULL,
                       accepted BOOLEAN,
                       FOREIGN KEY (offerId) REFERENCES Offer(id),
                       FOREIGN KEY (userId) REFERENCES User(id)
);

CREATE TABLE Chat (
                      id INTEGER PRIMARY KEY,
                      userId1 INTEGER NOT NULL,
                      userId2 INTEGER NOT NULL,
                      message TEXT NOT NULL,
                      date_message TEXT NOT NULL,
                      FOREIGN KEY (userId1) REFERENCES User(id),
                      FOREIGN KEY (userId2) REFERENCES User(id)
);

CREATE TABLE Notification (
                              id INTEGER PRIMARY KEY,
                              type_notification TEXT NOT NULL,
                              userId INTEGER NOT NULL,
                              seen BOOLEAN NOT NULL,
                              frequency TEXT,
                              date_notification TEXT NOT NULL,
                              FOREIGN KEY (userId) REFERENCES User(id)
);
