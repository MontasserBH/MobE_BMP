# Mini-Projet Android
Sujet: L’idée générale est de développer une application multi-service pour les
étudiants de l’Université Paul Sabatier
Le principal scénario d’utilisation est le suivant :
• Un étudiant veut aller déjeuner, il regarde l’occupation des RU et choisi le
RU2
• Il ne sait pas trop où se trouve ce RU et utilise une carte embarquée pour y
aller
• Il sait qu’il a le temps car il a vérifé rapidement son emploi du temps
avant
Activités
De manière plus formelle on peut avoir les activités suivantes :
1. Géolocalisation des batiments/amphis : Utilisation FireBase pour stocker les
noms (au moins U1 à U4), OpenStreetMap pour la partie carte (ou google
maps si trop compliqué). On pourra rechercher un lieu et marquer un point
d’intéret.
2. Interrogation des EdT de l’Université : Interaction HTTP + Interface
Graphique
3. Information : Utilisation d’un QR Code qui afche une page web ou des
données d’une base FireBase (soit texte, index frebase, soit url et dans ce
cas n’importe quel site).
4. Détection d’anomalie : On prend une photo qui est géolocalisée et orientée
avec un niveau de criticité (Confort, Problème, Danger). L’utilisateur peut
tracer une boite pour préciser l’endroit sur l’image. Le tout est envoyé sur
une base FireBase en basse résolution.
5. Confguration : Si vous devez mettre des informations
(login/mdp/formation/url de l’edt/...)
6. Information : Vos photos, noms, et le logo du Master DL, le niveau sonore
et la luminosité ambiante
7. On pourra partager un point d’intérêt entre deux personnes par bluetooth
(les motivés pourront tenter de rajouter du LiFi ou du sub-sonique), à
rajouter à l’activité 1.
Ces services doivent apparaître sur un menu avec deux niveaux
- Ma fac : 1+4
- Mes cours : 2+3
- Confguration : 5+6
Travail demandé
Le travail demandé est le suivant :
1. Un code source Android sous licence GPL (la qualité du code sera évaluée)
avec des images de votre création ou du domaine public.
2. Un document utilisateur (2 pages maximum) expliquant l'utilisation de
l'application
3. Un document de conception (4 pages max) explicitant tous les choix de
conception, les limites de l'application, les capacités, la structure de classe
ainsi qu'une série d'écrans (une demi page maximum) montrant un cas
d'utilisation. Vous y préciserez particulièrement les fonctionnalités
présentes et absentes.
Remarque : On sera attentif à ce que l'application utilise la caméra, le GPS,
l'écran tactile, le réseau, la boussole, le bluetooth, et aie une IHM cohérente avec
l'objectif de l'application.
Notation
• Etape 1 (notée sur 12) : Activités 1,3,5
• Etape 2 (notée sur 16) : Idem Etape 1 plus activités 2 et 6
• Etape 3 (notée sur 20) : Idem Etape 2 plus activités 4 et 7