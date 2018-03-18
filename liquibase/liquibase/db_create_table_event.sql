CREATE TABLE Event
(
  id             BIGINT(20) NOT NULL,
  arrival_time   DATETIME(6),
  departure_time DATETIME(6),
  spot_id        BIGINT(20),
  CONSTRAINT Event_pkey PRIMARY KEY (id),
  CONSTRAINT fk_between_event_and_spot FOREIGN KEY (spot_id) REFERENCES spot(id)
);