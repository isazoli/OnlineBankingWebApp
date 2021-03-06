-- Drop User table
ALTER TABLE BANKACCOUNT DROP CONSTRAINT FK_A5ABB7YLUIRC4I1DK21AXMDUN;
COMMIT;
DROP TABLE IF EXISTS USERMASTER;
COMMIT;
-- Create User table
CREATE TABLE USERMASTER(ID BIGINT NOT NULL,EMAIL VARCHAR(64) NOT NULL UNIQUE,PASSWORD VARCHAR(128) NOT NULL, ROLE VARCHAR(128) DEFAULT 'ROLE_USER',PRIMARY KEY (ID));
COMMIT;
-- Insert default users
INSERT INTO USERMASTER VALUES(1,'tom','fa8d685d3b2e00cff043680c8a2debd57a70d5611b6b9f551640cedbe9451c49ae4d3725064c5b69','ROLE_USER');
INSERT INTO USERMASTER VALUES(2,'jerry','ffc5b84443c59b2e19e6b7fa7f32a33e3d1ec3aa59aa3bf3a330a67c59a19a400be90750a143dcc3','ROLE_USER');
INSERT INTO USERMASTER VALUES(3,'spike','baf621b28c778c93acee002fc6815045458b9656145a8b589b992c9220596bb0cd7839eebd52cbad','ROLE_USER');
INSERT INTO USERMASTER VALUES(4,'tyke','baefb420b48e578a2093a610bd0f1fbd40b480d6a7ef0ea101069931b25057fe7045a2ae037fabeb','ROLE_USER');
COMMIT;
-- Drop Bank Account table
DROP TABLE IF EXISTS BANKACCOUNT CASCADE;
COMMIT;
-- Create Bank Account table
CREATE TABLE BANKACCOUNT(ID BIGINT NOT NULL,ACCOUNTNUMBER VARCHAR(64) NOT NULL UNIQUE,CURRENCY VARCHAR(4) NOT NULL,BALANCE BIGINT NOT NULL,USER_ID BIGINT NOT NULL,PRIMARY KEY (ID));
COMMIT;
-- Add Constraint
ALTER TABLE BANKACCOUNT ADD FOREIGN KEY (USER_ID) REFERENCES USERMASTER (ID);
COMMIT;
--Insert test Bank Accounts
---Tom's
INSERT INTO BANKACCOUNT VALUES(1, 'T-1111-1111-1111-1111','USD',1000,1);
INSERT INTO BANKACCOUNT VALUES(2, 'T-1111-1111-1111-2222','EUR',2000,1);
INSERT INTO BANKACCOUNT VALUES(3, 'T-1111-1111-1111-3333','GBP',3000,1);
INSERT INTO BANKACCOUNT VALUES(4, 'T-1111-1111-1111-4444','AUD',4000,1);
---Jerry's
INSERT INTO BANKACCOUNT VALUES(5, 'J-2222-1111-1111-1111','USD',1111,2);
INSERT INTO BANKACCOUNT VALUES(6, 'J-2222-1111-1111-2222','RUB',2222,2);
INSERT INTO BANKACCOUNT VALUES(7, 'J-2222-1111-1111-3333','GBP',3333,2);
---Spike's
INSERT INTO BANKACCOUNT VALUES(8, 'S-3333-1111-1111-1111','CAD',6666,3);
INSERT INTO BANKACCOUNT VALUES(9, 'S-3333-1111-1111-2222','RUB',9999,3);
INSERT INTO BANKACCOUNT VALUES(10, 'S-3333-1111-1111-3333','GBP',8888,3);
COMMIT;
-- Drop transaction log table
DROP TABLE IF EXISTS MONEYTRANSFERLOG CASCADE;
COMMIT;
--Create transaction log table
CREATE TABLE MONEYTRANSFERLOG(ID BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),SOURCEACCOUNTID BIGINT NOT NULL, TARGETACCOUNTNAME VARCHAR(64) NOT NULL,CURRENCY VARCHAR(4) NOT NULL,AMOUNT BIGINT NOT NULL,OPERATION VARCHAR(32) NOT NULL,PRIMARY KEY (ID));
COMMIT;
