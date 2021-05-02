CREATE TABLE address(
	id integer NOT NULL auto_increment
   ,state                 VARCHAR(2) NOT NULL
  ,city                  VARCHAR(255) NOT NULL
  ,neighborhood          VARCHAR(255) NOT NULL
  ,zipCode               VARCHAR(255) NOT NULL
  ,street                VARCHAR(255) NOT NULL
  ,number                INTEGER  NOT NULL
  ,additionalInformation VARCHAR(255)  NOT NULL
  ,customerId integer NOT NULL
  ,main                  BOOLEAN NOT NULL,
	PRIMARY KEY (`id`)
);