CREATE TABLE  IF NOT EXISTS colorlog_2024_06 (
    id INT NOT NULL AUTO_INCREMENT,
    text TEXT NULL,
    date INT,
    colorR INT,
    colorG INT,
    colorB INT,      
    CONSTRAINT testTable_PK PRIMARY KEY(id)        
);