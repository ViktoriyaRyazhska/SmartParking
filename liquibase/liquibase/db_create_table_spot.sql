CREATE TABLE Spot
(
  id bigint(20) NOT NULL,
  parking_id  bigint(20),
  CONSTRAINT Spot_pkey PRIMARY KEY (id),
  constraint fk_between_Spot_and_parking foreign key (parking_id) references parking(id)
);