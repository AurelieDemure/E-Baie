CREATE TABLE User (
                      email TEXT PRIMARY KEY,
                      firstName TEXT NOT NULL,
                      lastName TEXT NOT NULL,
                      password TEXT NOT NULL,
                      phone TEXT,
                      address TEXT,
                      lat REAL NOT NULL,
                      lon REAL NOT NULL,
                      description TEXT,
                      balance INTEGER DEFAULT 100
);

CREATE TABLE Offer (
                       id INTEGER PRIMARY KEY,
                       title TEXT NOT NULL,
                       description TEXT,
                       owner TEXT NOT NULL,
                       price REAL NOT NULL,
                       lat REAL NOT NULL,
                       lon REAL NOT NULL,
                       type_offer BOOLEAN NOT NULL,
                       frequency TEXT,
                       localization TEXT,
                       path_offer TEXT,
                       FOREIGN KEY (owner) REFERENCES User(email)
);

CREATE TABLE Query (
                       id INTEGER PRIMARY KEY,
                       offer_id INTEGER NOT NULL,
                       user_mail INTEGER NOT NULL,
                       date_query date,
                       date_begin date,
                       dtae_end date,
                       accepted BOOLEAN,
                       notation INTEGER NOT NULL,
                       FOREIGN KEY (offer_id) REFERENCES Offer(id),
                       FOREIGN KEY (user_mail) REFERENCES User(email)
);

CREATE TABLE Chat (
                      id INTEGER PRIMARY KEY,
                      sender INTEGER NOT NULL,
                      receiver INTEGER NOT NULL,
                      message TEXT NOT NULL,
                      date_message date,
                      FOREIGN KEY (sender) REFERENCES User(email),
                      FOREIGN KEY (receiver) REFERENCES User(email)
);

CREATE TABLE Notification (
                              id INTEGER PRIMARY KEY,
                              type_notification TEXT NOT NULL,
                              user_email INTEGER NOT NULL,
                              seen BOOLEAN NOT NULL,
                              frequency TEXT,
                              date_notification date,
                              FOREIGN KEY (user_email) REFERENCES User(email)
);
