CREATE TABLE address(
   state                 VARCHAR(2) NOT NULL PRIMARY KEY
  ,city                  VARCHAR(255) NOT NULL
  ,neighborhood          VARCHAR(255) NOT NULL
  ,zipCode               VARCHAR(255) NOT NULL
  ,street                VARCHAR(255) NOT NULL
  ,number                INTEGER  NOT NULL
  ,additionalInformation VARCHAR(255)  NOT NULL
  ,main                  BOOLEAN NOT NULL
);