-- Insert Person
INSERT INTO Person VALUES
                          (1,'Lionel','Andres','Messi','1987-06-24','Argentina'),
                          (2,'Cristiano',' ','Ronaldo','1985-02-05','Portugal'),
                          (3,'Neymar',' ','Jr.','1992-02-05','Brazil'),
                          (4,'Robert',' ','Lewandowski','1988-08-21','Poland'),
                          (5,'Zinedine',' ','Zidane','1972-06-23','France');

-- Insert Team
INSERT INTO Team VALUES
                        (1,'FC Barcelona','Barcelona','Blue and Red',1899),
                        (2,'Real Madrid','Madrid','White',1902),
                        (3,'Paris Saint-Germain','Paris','Blue and Red',1970),
                        (4,'Bayern Munich','Munich','Red and White',1900),
                        (5,'Juventus','Turin','Black and White',1897);

-- Insert Sponsor
INSERT INTO Sponsor VALUES
                           (1,'Nike','Sportswear',1964),
                           (2,'Adidas','Sportswear',1949),
                           (3,'Puma','Sportswear',1948),
                           (4,'Under Armour','Sportswear',1996),
                           (5,'New Balance','Sportswear',1906);

-- Insert Player
INSERT INTO Player VALUES
                          (1,true,700),
                          (2,true,800),
                          (3,true,600),
                          (4,true,650),
                          (5,false,100);

-- Insert CoachingStaff
INSERT INTO CoachingStaff VALUES
       (5,'Manager');

-- Insert Manager
INSERT INTO Manager VALUES
       (5,20);

-- Insert Contract
INSERT INTO Contract VALUES
                            (1,1,1,'2020-08-25','2023-06-30',71000000,10,'Forward'),
                            (2,2,2,'2018-07-10','2022-06-30',31000000,7,'Forward'),
                            (3,3,3,'2017-08-03','2022-06-30',36000000,10,'Forward'),
                            (4,4,4,'2014-07-09','2023-06-30',23000000,9,'Forward'),
                            (5,5,2,'2016-01-04','2022-06-30',14000000,null,null);

-- Insert CaptainHistory
INSERT INTO CaptainHistory VALUES
                                  (1,1,'2020-08-25','2021-08-24'),
                                  (2,2,'2018-07-10','2021-07-09'),
                                  (3,3,'2017-08-03','2021-08-02'),
                                  (4,4,'2014-07-09','2021-07-08'),
                                  (5,5,'2016-01-04','2021-01-03');

-- Insert P_Sponsorship
INSERT INTO P_Sponsorship VALUES
                                 (1,1,1,'2020-01-01','2022-12-31','Boot Sponsorship'),
                                 (2,2,2,'2018-01-01','2022-12-31','Boot Sponsorship'),
                                 (3,3,3,'2019-01-01','2023-12-31','Boot Sponsorship'),
                                 (4,4,4,'2016-01-01','2024-12-31','Boot Sponsorship');

-- Insert T_Sponsorship
INSERT INTO T_Sponsorship VALUES
                                 (1,1,1,'2020-01-01','2023-12-31','Kit Sponsorship'),
                                 (2,2,2,'2019-01-01','2023-12-31','Kit Sponsorship'),
                                 (3,3,3,'2018-01-01','2022-12-31','Kit Sponsorship'),
                                 (4,4,4,'2017-01-01','2021-12-31','Kit Sponsorship'),
                                 (5,5,5,'2020-01-01','2024-12-31','Kit Sponsorship');


-- Insert Trains
INSERT INTO Trains VALUES
                          (5,1),
                          (5,2),
                          (5,3),
                          (5,4);

-- Insert Person
INSERT INTO Person VALUES
                       (1,'Lionel','Andres','Messi','1987-06-24','Argentina'),
                       (2,'Cristiano',NULL,'Ronaldo','1985-02-05','Portugal'),
                       (3,'Neymar',NULL,'Jr.','1992-02-05','Brazil'),
                       (4,'Robert',NULL,'Lewandowski','1988-08-21','Poland'),
                       (5,'Zinedine',NULL,'Zidane','1972-06-23','France');

-- Insert Team
INSERT INTO Team VALUES
                     (1,'FC Barcelona','Barcelona',NULL,1899),
                     (2,'Real Madrid','Madrid',NULL,1902),
                     (3,'Paris Saint-Germain','Paris',NULL,1970),
                     (4,'Bayern Munich','Munich',NULL,1900),
                     (5,'Juventus','Turin',NULL,1897);

-- Insert Sponsor
INSERT INTO Sponsor VALUES
                        (1,'Nike','Sportswear',1964),
                        (2,'Adidas','Sportswear',1949),
                        (3,'Puma','Sportswear',1948),
                        (4,'Under Armour','Sportswear',1996),
                        (5,'New Balance','Sportswear',1906);

-- Insert Player
INSERT INTO Player VALUES
                       (1,true,700,NULL),
                       (2,true,800,NULL),
                       (3,true,600,NULL),
                       (4,true,650,NULL),
                       (5,false,100,NULL);

-- Insert CoachingStaff
INSERT INTO CoachingStaff VALUES
    (5,'Manager');

-- Insert Manager
INSERT INTO Manager VALUES
    (5,20);

-- Insert CoachingStaffContract
INSERT INTO CoachingStaffContract VALUES
    (1,5,2,'2016-01-04','2022-06-30',14000000);

-- Insert PlayerContract
INSERT INTO PlayerContract VALUES
                               (1,1,1,'2020-08-25','2023-06-30',71000000,10,'Forward'),
                               (2,2,2,'2018-07-10','2022-06-30',31000000,7,'Forward'),
                               (3,3,3,'2017-08-03','2022-06-30',36000000,10,'Forward'),
                               (4,4,4,'2014-07-09','2023-06-30',23000000,9,'Forward');

-- Insert CaptainHistory
INSERT INTO CaptainHistory VALUES
                               (1,'2020-08-25','2021-08-24',1),
                               (2,'2018-07-10','2021-07-09',2),
                               (3,'2017-08-03','2021-08-02',3),
                               (4,'2014-07-09','2021-07-08',4);

-- Insert P_Sponsorship
INSERT INTO P_Sponsorship VALUES
                              (1,1,1,'2020-01-01','2022-12-31','Boot Sponsorship'),
                              (2,2,2,'2018-01-01','2022-12-31','Boot Sponsorship'),
                              (3,3,3,'2019-01-01','2023-12-31','Boot Sponsorship'),
                              (4,4,4,'2016-01-01','2024
