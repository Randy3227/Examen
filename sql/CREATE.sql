create database examenS4;
CREATE TABLE User(
    id SERIAL PRIMARY KEY,
    name VARCHAR(20),
    password VARCHAR(30)
);
CREATE TABLE User(
    id integer primary key auto_increment,
    name VARCHAR(20),
    password VARCHAR(30)
);
CREATE TABLE Prevision(
    id integer primary key auto_increment,
    libelle VARCHAR(50),
    montant INTEGER,
    idUser INTEGER references User(id)
);
CREATE TABLE Depense(
    id integer primary key auto_increment,
    libelle integer references Prevision(id),
    montant INTEGER,
    idUser INTEGER references User(id)
);