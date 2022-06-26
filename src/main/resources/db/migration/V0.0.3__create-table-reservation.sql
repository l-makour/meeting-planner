create table if not exists reservation (
    id integer primary key GENERATED ALWAYS AS IDENTITY,
    start_date timestamp,
    end_date timestamp,
    effective_end_date timestamp,
    meeting_type varchar(6),
    nb_external_webcam integer,
    nb_external_tableau integer,
    nb_external_pieuvre integer,
    nb_external_ecran integer,
    id_room integer,
    CONSTRAINT fk_room FOREIGN KEY(id_room) REFERENCES room(id)
);