CREATE KEYSPACE bootcamp WITH replication={'class':'SimpleStrategy','replication_factor':1};
CREATE TABLE bootcamp.emp(emp_id int, emp_name text, emp_city text, emp_phone text, PRIMARY KEY (emp_id));
CREATE TABLE bootcamp.emp_skill(emp_id int, java_exp double, spring_exp double, PRIMARY KEY ((emp_id, java_exp, spring_exp))); 