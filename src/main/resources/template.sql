CREATE TABLE IF NOT EXISTS DOCUMENTS(
    ID IDENTITY NOT NULL PRIMARY KEY ,
    DOC_UUID UUID NOT NULL,
    TITLE VARCHAR(144) NOT NULL,
    PATH_TO_TEXT VARCHAR(256) NOT NULL, 
    CREATION_TIME TIMESTAMP NOT NULL,
);
/*Table PHOTOS describe how database store photos that were attached to documetns
*/
CREATE TABLE IN NOT EXISTS IMAGES(
    ID  IDENTITY NOT NULL PRIMARY KEY,
    DOC_ID INTEGER ,
    PATH_TO_IMG VARCHAR(256) NOT NULL,
    FOREIGN KEY (DOC_ID) REFERENCES DOCUMENTS(ID)
);
/*Table SAVES store data about saves of documents to common formats as PDF,WORD and etc.
  FIELD DOC_ID - its id of document
  PATH_TO_SAVE - field that show where was document file was saved
  DOC_TYPE - says what type was used for saving (PDF,WORD,ODT and etc)
 */
CREATE TABLE IF NOT EXISTS SAVES(
    ID  IDENTITY NOT NULL PRIMARY KEY,
    DOC_ID INTEGER,
    PATH_TO_SAVE VARCHAR(256) NOT NULL,
    DOC_TYPE VARCHAR(16) NOT NULL,
    FOREIGN KEY (DOC_ID) REFERENCES DOCUMENTS(ID)
);