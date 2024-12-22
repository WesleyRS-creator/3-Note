INSERT INTO MODE_PAIEMENT (NOM_MODE, DIVISEUR_MOIS)
VALUES 
    ('Mensuel', 1),       -- NULL pour le mode mensuel (1 mois à gérer en Java)
    ('Trimestriel', 3),      -- Divise le total des mois par 3
    ('Semestriel', 6),       -- Divise le total des mois par 2
    ('Annuel', 12);           -- Tout l'année payée, pas de division nécessaire

    -- Insertion de 7 données d'écolages pour quelques étudiants
INSERT INTO ECOLAGE (ID_ETUDIANT, ID_MODE_PAIEMENT, MONTANT, SEMESTRE, ID_ANNEE) VALUES
(1, 1, 1000000, 1, 1), -- Jean Dupont, paiement en mode 1, montant 1,000,000, premier semestre, année 2020-2021
(1, 2, 2000000, 2, 2), -- Jean Dupont, paiement en mode 2, montant 2,000,000, deuxième semestre, année 2021-2022
(2, 1, 1500000, 1, 3), -- Alice Martin, paiement en mode 1, montant 1,500,000, premier semestre, année 2022-2023
(2, 2, 2500000, 2, 3), -- Alice Martin, paiement en mode 2, montant 2,500,000, deuxième semestre, année 2022-2023
(3, 3, 1000000, 1, 4), -- Marc Durand, paiement en mode 3, montant 1,000,000, premier semestre, année 2023-2024
(4, 1, 1200000, 1, 4), -- Sophie Bernard, paiement en mode 1, montant 1,200,000, premier semestre, année 2023-2024
(5, 2, 1800000, 2, 5); -- Thomas Leroy, paiement en mode 2, montant 1,800,000, deuxième semestre, année 2024-2025

