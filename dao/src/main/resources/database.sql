USE training_csv;

CREATE TABLE IF NOT EXISTS person_details (
	pd_id INT NOT NULL AUTO_INCREMENT,
	login VARCHAR(50) NOT NULL UNIQUE,
	email VARCHAR(50) NOT NULL,
	phoneNumber VARCHAR(50),
	PRIMARY KEY (pd_id)
) ENGINE=InnoDB  
DEFAULT CHARACTER SET = utf8
DEFAULT COLLATE = utf8_general_ci;

CREATE TABLE IF NOT EXISTS person (
	p_id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL,
	surname VARCHAR(50) NOT NULL,
	PRIMARY KEY (p_id),
	foreign key (p_id) references person_details (pd_id)
) ENGINE=InnoDB  
DEFAULT CHARACTER SET = utf8
DEFAULT COLLATE = utf8_general_ci;