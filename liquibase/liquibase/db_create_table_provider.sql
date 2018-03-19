CREATE TABLE Provider
(
  id bigint(20) NOT NULL,
  active bit(1),
  name varchar(255),
  state varchar(255),
  legalAddress_id  bigint(255),
  CONSTRAINT Provider_pkey PRIMARY KEY (id),
  constraint fk_between_provider_and_address foreign key (legalAddress_id) references address(id)

);