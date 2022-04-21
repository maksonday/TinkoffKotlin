CREATE TABLE images
(
    id                   int NOT NULL PRIMARY KEY,
    name                 varchar(255),
    type                 varchar(255),
    disk_size_requirements int,
    ram_requirements      int
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
    id       int NOT NULL PRIMARY KEY,
    type     varchar(255),
    imageId  int,
    configId int,
    osType   varchar(255),
    state    varchar(255),
    status   varchar(255),
    CONSTRAINT FkImage FOREIGN KEY (imageId) REFERENCES images (id),
    CONSTRAINT FkConfig FOREIGN KEY (configId) REFERENCES configs (id)
);

INSERT INTO images VALUES ( 1, 'Ubuntu 20.04', 'Linux', 8, 8 );
INSERT INTO images VALUES ( 2, 'Ubuntu 16.04', 'Linux', 8, 8 );
INSERT INTO images VALUES ( 3, 'Ubuntu 20.04', 'Linux', 8, 8 );
INSERT INTO configs VALUES ( 1, 100, 4, 8 );
INSERT INTO configs VALUES ( 2, 10, 2, 4 );
INSERT INTO configs VALUES ( 3, 250, 4, 16 );
INSERT INTO vm(id, type, imageId, configId, osType, state, status) values (1, 'kvm', 1, 1, 'Linux', 'OFF', 'DISK_DETACHED')