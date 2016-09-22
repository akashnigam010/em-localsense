var db = connect('127.0.0.1:27017/sense-local-db');
/*--------------------------------------------- CREATE SCRIPTS -----------------------------------------*/
db.createCollection("bill");
db.createCollection("category");
db.createCollection("sub_category");
db.createCollection("item");
db.createCollection("contact_support");
db.createCollection("personnel");
db.createCollection("message");
db.createCollection("role");
db.createCollection("sequence");
db.createCollection("table");
db.createCollection("table_seating_area");
db.createCollection("tax");
db.createCollection("kot");
db.createCollection("email_template");