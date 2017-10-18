CREATE TABLE Drinkki (
id integer PRIMARY KEY,
nimi varchar(100));


CREATE TABLE RaakaAine(
id integer PRIMARY KEY,
nimi varchar(50));

CREATE TABLE DrinkkiRaakaAine (
drinkki_id integer,
raakaaine_id integer,
jarjestys integer,
maara real,
ohje varchar(200),
FOREIGN KEY (drinkki_id) REFERENCES Drinkki(id),
FOREIGN KEY (raakaaine_id) REFERENCES RaakaAine(id));

