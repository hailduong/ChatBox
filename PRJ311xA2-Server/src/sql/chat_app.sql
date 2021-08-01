# Create MessageDetail table
create table MessageDetail
(
    messageId   int auto_increment,
    fromUser    nvarchar(255)  null,
    toUser      nvarchar(255)  null,
    dateCreated datetime       not null,
    content     nvarchar(4000) null,
    messageType nvarchar(255)  not null,
    constraint MessageDetail_pk
        primary key (messageId)
);

# Create User table
create table User
(
    userName    varchar(255) charset utf8 not null
        primary key,
    displayName varchar(255) charset utf8 not null
);