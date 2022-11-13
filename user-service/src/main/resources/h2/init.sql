create table users (
   id bigint auto_increment,
   name varchar(50),
   balance int,
   primary key (id)
);

create table user_transactions (
   id bigint auto_increment,
   user_id bigint,
   amount int,
   transaction_date timestamp,
   foreign key (user_id) references users(id) on delete cascade
);

insert into users (name, balance) VALUES
                  ('Sashank', 1000),
                  ('Aparna', 2000),
                  ('Monalisa', 3000),
                  ('Nalini', 4000),
                  ('Susant', 5000),
                  ('Anirudh', 6000),
                  ('Anurag', 7000);

