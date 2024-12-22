CREATE SEQUENCE seq_matieres START WITH 1 INCREMENT BY 1;

CREATE TABLE Matieres (
    idMatiere NUMBER PRIMARY KEY,
    nomMatiere VARCHAR2(50) NOT NULL,
    credits NUMBER NOT NULL CHECK (credits > 0),
    idConfig NUMBER(1) DEFAULT 0
);

CREATE OR REPLACE TRIGGER trg_matieres_id
BEFORE INSERT ON Matieres
FOR EACH ROW
WHEN (NEW.idMatiere IS NULL)
BEGIN
    SELECT seq_matieres.NEXTVAL INTO :NEW.idMatiere FROM dual;
END;
/

CREATE SEQUENCE seq_notes START WITH 1 INCREMENT BY 1;

CREATE TABLE Notes (
    idNote NUMBER PRIMARY KEY,
    idEtudiant NUMBER NOT NULL,
    idMatiere NUMBER NOT NULL,
    noteValeur NUMBER CHECK (noteValeur BETWEEN 0 AND 20),
    FOREIGN KEY (idEtudiant) REFERENCES Etudiants(idEtudiant),
    FOREIGN KEY (idMatiere) REFERENCES Matieres(idMatiere)
);

CREATE OR REPLACE TRIGGER trg_notes_id
BEFORE INSERT ON Notes
FOR EACH ROW
WHEN (NEW.idNote IS NULL)
BEGIN
    SELECT seq_notes.NEXTVAL INTO :NEW.idNote FROM dual;
END;
/