CREATE TABLE images
(
    id                   int NOT NULL PRIMARY KEY,
    name                 varchar(255),
    type                 varchar(255),
    diskSizeRequirements int,
    ramRequirements      int
);

CREATE TABLE configs
(
    id       int NOT NULL PRIMARY KEY,
    diskSize int,
    cores    int,
    sizeRam  int
);

CREATE TABLE vm
(
    id       SERIAL PRIMARY KEY,
    type     varchar(255),
    imageId  int,
    configId int,
    osType   varchar(255),
    state    varchar(255),
    status   varchar(255),
    CONSTRAINT FkImage FOREIGN KEY (imageId) REFERENCES images (id),
    CONSTRAINT FkConfig FOREIGN KEY (configId) REFERENCES configs (id)
);