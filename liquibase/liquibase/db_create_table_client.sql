CREATE TABLE Client
(
  id bigint(20) NOT NULL,
  email varchar(255),
  first_name varchar(255),
  last_name varchar(255),
  password varchar(255),
  role int(11),
  provider_id  bigint(20),
  CONSTRAINT Client_pkey PRIMARY KEY (id),
  constraint fk_between_client_and_provider foreign key (provider_id) references provider(id)

);