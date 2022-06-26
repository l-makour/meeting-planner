create table if not exists room (
     id integer primary key,
     name varchar(32),
     nb_place integer,
     nb_webcam integer,
     nb_tableau integer,
     nb_pieuvre integer,
     nb_ecran integer
);