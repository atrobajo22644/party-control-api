create table promoters (id integer not null, is_active bit not null, name varchar(255) not null, primary key (id)) engine=InnoDB;
create table promoters_seq (next_val bigint) engine=InnoDB;
insert into promoters_seq values ( 1 );
