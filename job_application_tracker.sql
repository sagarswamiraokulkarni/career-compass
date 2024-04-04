USE sql3695075;

SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS job_tags;
DROP TABLE IF EXISTS job_applications;
DROP TABLE IF EXISTS job_application_job_tags;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE IF NOT EXISTS users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR(50) NOT NULL UNIQUE,
  last_name VARCHAR(50) NOT NULL UNIQUE,
  email VARCHAR(100) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  verify_hash VARCHAR(255) NOT NULL,
  is_verified BOOLEAN DEFAULT FALSE,
  phone_number VARCHAR(20),
  role ENUM('ADMIN','USER') NOT NULL
);

CREATE TABLE IF NOT EXISTS job_tags (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  name VARCHAR(50) NOT NULL,
  UNIQUE (user_id, name),
  FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS job_applications (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  company VARCHAR(100) NOT NULL,
  position VARCHAR(100) NOT NULL,
  status ENUM('Yet to Apply', 'Applied', 'Waiting to Hear Back', 'Need To Follow Up', 'Accepted', 'Rejected') NOT NULL,
  application_date DATE NOT NULL,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  company_url VARCHAR(255),
  is_deleted BOOLEAN,
  starred BOOLEAN DEFAULT FALSE,
  notes TEXT,
  created_at TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(id)
);

# TODO: QUERY
# SELECT * FROM job_applications WHERE user_id=:user_id AND is_deleted=FALSE

-- CREATE TABLE IF NOT EXISTS job_application_job_tags (
--   job_application_id INT NOT NULL,
--   job_tag_id INT NOT NULL,
--   PRIMARY KEY (job_application_id, job_tag_id),
--   FOREIGN KEY (job_application_id) REFERENCES job_applications(id),
--   FOREIGN KEY (job_tag_id) REFERENCES job_tags(id)
-- );

CREATE TABLE IF NOT EXISTS job_application_job_tags (
  job_app_tag_id INT,
  job_application_id INT NOT NULL,
  job_tag_id INT NOT NULL,
  UNIQUE(job_application_id, job_tag_id ),
  PRIMARY KEY (job_app_tag_id ),
  FOREIGN KEY (job_application_id) REFERENCES job_applications(id),
  FOREIGN KEY (job_tag_id) REFERENCES job_tags(id)
);

INSERT INTO `sql3695075`.`job_applications`
(`user_id`,
 `company`,
 `position`,
 `status`,
 `application_date`,
 `updated_at`,
 `company_url`,
 `is_deleted`,
 `starred`,
 `notes`,
 `created_at`)
VALUES
    (3,"AWS","SDE","YET TO APPLY", curdate(), curdate(), "http://localhost:8080/swagger-ui/index.html#/public-controller/getNumberOfUsers",false,false,"NOTES",now()),
    (3,"Amazon","SDE","APPLIED", curdate(), curdate(), "http://localhost:8080/swagger-ui/index.html#/public-controller/getNumberOfUsers",false,false,"NOTES",now());

