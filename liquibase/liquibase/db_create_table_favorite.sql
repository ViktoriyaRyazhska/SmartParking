CREATE TABLE Favorite
(
  id bigint(20) NOT NULL,
  name varchar(255),
  client_id  bigint(20),
  parking_id  bigint(20),
  CONSTRAINT Favorite_pkey PRIMARY KEY (id),
  constraint fk_between_favorite_and_client foreign key (client_id) references client(id),
  constraint fk_between_favorite_and_parking foreign key (parking_id) references parking(id)
);