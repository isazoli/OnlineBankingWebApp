-- Drop User Account
DROP TABLE IF EXISTS USERMASTER;
-- Create User USER
CREATE TABLE USERMASTER(ID BIGINT NOT NULL,EMAIL VARCHAR(64) NOT NULL UNIQUE,PASSWORD VARCHAR(128) NOT NULL, ROLE VARCHAR(128) DEFAULT 'ROLE_USER',PRIMARY KEY (ID));
COMMIT;
-- Insert default users
INSERT INTO USERMASTER VALUES(1,'tom','fa8d685d3b2e00cff043680c8a2debd57a70d5611b6b9f551640cedbe9451c49ae4d3725064c5b69','ROLE_USER');
INSERT INTO USERMASTER VALUES(2,'jerry','ffc5b84443c59b2e19e6b7fa7f32a33e3d1ec3aa59aa3bf3a330a67c59a19a400be90750a143dcc3','ROLE_USER');
INSERT INTO USERMASTER VALUES(3,'spike','baf621b28c778c93acee002fc6815045458b9656145a8b589b992c9220596bb0cd7839eebd52cbad','ROLE_USER');
INSERT INTO USERMASTER VALUES(4,'tyke','baefb420b48e578a2093a610bd0f1fbd40b480d6a7ef0ea101069931b25057fe7045a2ae037fabeb','ROLE_USER');
COMMIT;