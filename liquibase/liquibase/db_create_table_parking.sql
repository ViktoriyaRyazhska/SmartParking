CREATE TABLE Parking
(
  id bigint(20) NOT NULL,
  latitude double,
  longitude double,
  price decimal(19,2),
  token varchar(255),
  address_id  bigint(20),
  provider_id  bigint(20),
  CONSTRAINT Parking_pkey PRIMARY KEY (id),
  constraint fk_between_parking_and_address foreign key (address_id) references address(id),
  constraint fk_between_parking_and_provider foreign key (provider_id) references provider(id)
);