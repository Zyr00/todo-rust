CREATE TABLE IF NOT EXISTS todo (
  id SERIAL PRIMARY KEY,
  title VARCHAR NOT NULL,
  vdesc VARCHAR NOT NULL,
  done BOOLEAN NOT NULL
);
