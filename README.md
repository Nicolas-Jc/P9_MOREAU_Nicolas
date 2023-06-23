# Mediscreen

**Mediscreen** est une _application Web_ dont le but est de détecter, au sein de la base de données, les patients à
risque les plus exposés au diabète de type 2.

## Stack technique

<img src="https://img.shields.io/badge/-JAVA%2017-00A7BB?style=for-the-badge&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/-SPRING%20BOOT%202.7.4-6eb442?style=for-the-badge&logo=spring&logoColor=white">
<img src="https://img.shields.io/badge/-SPRING%20WEB-397200?style=for-the-badge&logo=spring&logoColor=white">
<br> <img src="https://img.shields.io/badge/-SPRING%20DATA%20JPA-8db411?style=for-the-badge&logo=spring&logoColor=white">
<img src="https://img.shields.io/badge/-SPRING%20DATA%20MONGODB-8db411?style=for-the-badge&logo=spring&logoColor=white">
<br><img src="https://img.shields.io/badge/-MYSQL-006189?style=for-the-badge&logo=mysql&logoColor=white"> 
<img src="https://img.shields.io/badge/-MONGODB-6eb442?style=for-the-badge&logo=mongodb&logoColor=white">
<br><img src="https://img.shields.io/badge/-MAVEN-black?style=for-the-badge&logo=apachemaven&logoColor=white">
<img src="https://img.shields.io/badge/-JACOCO-810a00?style=for-the-badge">
<br><img src="https://img.shields.io/badge/-THYMELEAF-c41829?style=for-the-badge&logo=thymeleaf&logoColor=white"> 
<br><img src="https://img.shields.io/badge/-DOCKER-2496ed?style=for-the-badge&logo=docker&logoColor=white">

## Microservices

Le back office, créé avec _Spring Boot_ est divisée en plusieurs _microservices_ :

- [Patient](patient) : gère les données qui concernent l'identité des patients.
- [Note](Notes) : gère l'historique des consultations des patients
- [Assessment](Assessment) : calcule le taux de risque de diabète chez les patients .
- [Clientui](ui%2Fmediscreen-ui) : l'UI en Angular

## Installation

- Cloner ce repository : git
  clone [https://github.com/Nicolas-Jc/P9_MOREAU_Nicolas_Mediscreen.git](https://github.com/Nicolas-Jc/P9_MOREAU_Nicolas_Mediscreen.git)


- Créer une base de données MySQL et adapter le fichier de configuration :
    - [application.properties](mAuthentication%2Fsrc%2Fmain%2Fresources%2Fapplication.properties) (mAuthentication)


- Créer unhe base de données MongoDB et adapter les fichiers de configuration :
    - [application.properties](mNotes%2Fsrc%2Fmain%2Fresources%2Fapplication.properties) (mNotes)

## Docker compose

Pour éxecuter le projet avec Docker compose:

- A la racine du projet, lancer la commande :
    - $ mvn clean install
- Egalement à la racine du projet, lancer la commande
    - $ docker-compose up
- (!) L'exécution par Docker ne nécessite pas d'effectuer les étapes de la partie **Installation** autre que le clonage
  du repository.



