CREATE TABLE IF NOT EXISTS USERS (
  userid INT PRIMARY KEY auto_increment,
  username VARCHAR(20) UNIQUE,
  salt VARCHAR,
  password VARCHAR,
  firstname VARCHAR(20),
  lastname VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS NOTES (
    noteid INT PRIMARY KEY auto_increment,
    notetitle VARCHAR(20),
    notedescription VARCHAR (1000),
    userid INT,
    foreign key (userid) references USERS(userid)
);

CREATE TABLE IF NOT EXISTS FILES (
    fileId INT PRIMARY KEY auto_increment,
    filename VARCHAR,
    contenttype VARCHAR,
    filesize VARCHAR,
    userid INT,
    filedata BLOB,
    foreign key (userid) references USERS(userid)
);

CREATE TABLE IF NOT EXISTS CREDENTIALS (
    credentialid INT PRIMARY KEY auto_increment,
    url VARCHAR(100),
    username VARCHAR (30),
    key VARCHAR,
    password VARCHAR,
    userid INT,
    foreign key (userid) references USERS(userid)
);

INSERT INTO USERS (username, salt, password, firstname, lastname)
VALUES('thanh123', 'uYjVewOcg7Cf0U7UXoDy1g==', 'ZXP/n+tSg+gFnlXBe03ydSKe47NpokhHx4ErCOGAxuSnbZmxmBtenMmQiA6QVOHfmxpgcdqB+ZASYkTxxNgNqtP3UaTCVOY4UESTDBBWHnikIJ0m1FrpMNKUhqfibaVFvTnzGtAf2e+C1eBj1UkHVD0yKyxJboQQzZlFlHwVCcXbcDOq80bDBlngbGz702AazIL4Od+s8Nzo2ERHa2tlXUlq12MZWOO9Vp/dqN1k4w1OZMzTM6FcbmpWj176xA924VmzCB56I6gRmuMHeZUtaCz0EQj9jxwS7lP5f/NVML0QIMVLsB0hmRhaseR7l806rnXqKcKg9xWbcW0RRJq+Bg==', 'Thanh', 'Tran'),
('thanh456', 'uYjVewOcg7Cf0U7UXoDy1g==', 'ZXP/n+tSg+gFnlXBe03ydSKe47NpokhHx4ErCOGAxuSnbZmxmBtenMmQiA6QVOHfmxpgcdqB+ZASYkTxxNgNqtP3UaTCVOY4UESTDBBWHnikIJ0m1FrpMNKUhqfibaVFvTnzGtAf2e+C1eBj1UkHVD0yKyxJboQQzZlFlHwVCcXbcDOq80bDBlngbGz702AazIL4Od+s8Nzo2ERHa2tlXUlq12MZWOO9Vp/dqN1k4w1OZMzTM6FcbmpWj176xA924VmzCB56I6gRmuMHeZUtaCz0EQj9jxwS7lP5f/NVML0QIMVLsB0hmRhaseR7l806rnXqKcKg9xWbcW0RRJq+Bg==', 'ThanhTLN', 'Tran Le');
