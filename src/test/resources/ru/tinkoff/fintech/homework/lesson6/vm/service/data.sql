INSERT INTO images VALUES ( 1, 'Ubuntu 20.04', 'Linux', 8, 8 );
INSERT INTO images VALUES ( 2, 'Ubuntu 16.04', 'Linux', 8, 8 );
INSERT INTO images VALUES ( 3, 'Ubuntu 20.04', 'Linux', 8, 8 );
INSERT INTO configs VALUES ( 1, 100, 4, 8 );
INSERT INTO configs VALUES ( 2, 10, 2, 4 );
INSERT INTO configs VALUES ( 3, 250, 4, 16 );
INSERT INTO vm(type, imageId, configId, osType, state, status) values ('kvm', 1, 1, 'Linux', 'OFF', 'DISK_DETACHED')