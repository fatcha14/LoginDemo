CREATE TABLE IF NOT EXISTS `USER` (
  `ID` INTEGER (32) NOT NULL AUTO_INCREMENT,
  `USERNAME` VARCHAR (255) DEFAULT NULL,
  `PASSWORD` VARCHAR (255) DEFAULT NULL,
  `CUSTOMERNAME` VARCHAR (255) DEFAULT NULL,
  `CUSTOMEREMAIL` VARCHAR (255) DEFAULT NULL,
  `CUSTOMERDATEOFBIRTH` VARCHAR (255) DEFAULT NULL,
  `CUSTOMERADDRESS` VARCHAR (255) DEFAULT NULL,
  `FAILEDATTEMPT` INTEGER (32) DEFAULT NULL,
  PRIMARY KEY (`ID`)
);