CREATE TABLE etudiants (
    idEtudiant INT AUTO_INCREMENT PRIMARY KEY,
    nomEtudiant VARCHAR(50) NOT NULL,
    moyenneSansCoeff DECIMAL(5,2),
    moyenneAvecCoeff DECIMAL(5,2),
    estAdmis TINYINT(1) DEFAULT 0
);

-- Pour l'étudiant Jean Dupont (ID_ETUDIANT = 1) pour l'année 2020-2021 (ID_ANNEE = 1)
INSERT INTO ECOLAGE (ID_ETUDIANT, MONTANT, ID_ANNEE)
VALUES (1, 500000, 1);

-- Pour l'étudiant Jean Dupont (ID_ETUDIANT = 1) pour l'année 2021-2022 (ID_ANNEE = 2)
INSERT INTO ECOLAGE (ID_ETUDIANT, MONTANT, ID_ANNEE)
VALUES (1, 2000000, 2);

-- Pour l'étudiant Claire Martin (ID_ETUDIANT = 2) pour l'année 2022-2023 (ID_ANNEE = 3)
INSERT INTO ECOLAGE (ID_ETUDIANT, MONTANT, ID_ANNEE)
VALUES (2, 100000, 3);

-- Pour l'étudiant Claire Martin (ID_ETUDIANT = 2) pour l'année 2023-2024 (ID_ANNEE = 4)
INSERT INTO ECOLAGE (ID_ETUDIANT, MONTANT, ID_ANNEE)
VALUES (2, 2500000, 4);