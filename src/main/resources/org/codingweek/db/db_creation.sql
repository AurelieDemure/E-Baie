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
                       owner_id INTEGER NOT NULL,
                       price REAL NOT NULL,
                       type_offer BOOLEAN NOT NULL,
                       frequency TEXT,
                       localization TEXT,
                       path_offer TEXT,
                       FOREIGN KEY (owner_id) REFERENCES User(id)
);

CREATE TABLE Query (
                       id INTEGER PRIMARY KEY,
                       offer_id INTEGER NOT NULL,
                       user_id INTEGER NOT NULL,
                       date_query TEXT NOT NULL,
                       accepted BOOLEAN,
                       notation INTEGER NOT NULL,
                       FOREIGN KEY (offer_id) REFERENCES Offer(id),
                       FOREIGN KEY (user_id) REFERENCES User(id)
);

CREATE TABLE Chat (
                      id INTEGER PRIMARY KEY,
                      user_id1 INTEGER NOT NULL,
                      user_id2 INTEGER NOT NULL,
                      message TEXT NOT NULL,
                      date_message TEXT NOT NULL,
                      FOREIGN KEY (user_id1) REFERENCES User(id),
                      FOREIGN KEY (user_id2) REFERENCES User(id)
);

CREATE TABLE Notification (
                              id INTEGER PRIMARY KEY,
                              type_notification TEXT NOT NULL,
                              user_id INTEGER NOT NULL,
                              seen BOOLEAN NOT NULL,
                              frequency TEXT,
                              date_notification TEXT NOT NULL,
                              FOREIGN KEY (user_id) REFERENCES User(id)
);
