# Mediscreen

**Mediscreen** est une _application Web_ dont le but est de déterminer les patients les plus à
risque au niveau diabète de type 2.

## Stack technique

<img src="https://img.shields.io/badge/-JAVA%2017-00A7BB?style=for-the-badge&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/-SPRING%20BOOT%202.7.4-6eb442?style=for-the-badge&logo=spring&logoColor=white">
<img src="https://img.shields.io/badge/-SPRING%20WEB-397200?style=for-the-badge&logo=spring&logoColor=white">
<img src="https://img.shields.io/badge/-SPRING%20DATA%20JPA-8db411?style=for-the-badge&logo=spring&logoColor=white">
<img src="https://img.shields.io/badge/-SPRING%20DATA%20MONGODB-8db411?style=for-the-badge&logo=spring&logoColor=white">
<br><img src="https://img.shields.io/badge/-MYSQL-006189?style=for-the-badge&logo=mysql&logoColor=white"> 
<img src="https://img.shields.io/badge/-MONGODB-6eb442?style=for-the-badge&logo=mongodb&logoColor=white">
<br><img src="https://img.shields.io/badge/-MAVEN-black?style=for-the-badge&logo=apachemaven&logoColor=white">
<img src="https://img.shields.io/badge/-JACOCO-810a00?style=for-the-badge">
<br><img src="https://img.shields.io/badge/-THYMELEAF-005f0f?style=for-the-badge&logo=thymeleaf&logoColor=white"> 
<br><img src="https://img.shields.io/badge/-DOCKER-2496ed?style=for-the-badge&logo=docker&logoColor=white">

## Microservices

Le back-end est développé avec _Spring Boot_ est divisé en 4 _microservices_ :

### Port: 9090

- [Clientui](clientui) : l'UI développé avec Thymeleaf

### Port: 9091

- [Patient](patient) : gère les données sur l'identité des patients (création, mise à jour, suppression ...).

### Port: 9092

- [Note](note) : gère la traçabilité des consultations des patients (Notes des médecins)

### Port: 9093

- [Assessment](assessment) : détermine le niveau de risque (diabète) d'un patient .

## Architecture Logiciel

![Diagramme d'Architecture](ArchitectureLogiciel/DiagrammeArchitecture.PNG)

## Installation

- Cloner ce repository : git
  clone [https://github.com/Nicolas-Jc/P9_MOREAU_Nicolas_Mediscreen.git](https://github.com/Nicolas-Jc/P9_MOREAU_Nicolas_Mediscreen.git)


- Créer une base de données MySQL et adapter le fichier de configuration :
    - [application.properties](patient/src/main/resources/application.properties) (patient-microservice)


- Créer une base de données MongoDB et adapter le fichiers de configuration :
    - [application.properties](note/src/main/resources/application.properties) (note-microservice)

## Docker compose

Pour lancer le projet avec Docker compose:

A la racine du projet, lancer les 2 commandes suivantes séquentiellement :

- mvn clean install -DskipTests
- docker-compose up

## Rapports de Test

### Patient

![Jacaco Patient](.Jacoco-reports/Patient.png)

### Note

![Jacoco Note](.Jacoco-reports/Note.png)

### Assessment

![Jacoco Assessment](.Jacoco-reports/Assessment.png)

### ClientUI

![Jacoco clientUI](.Jacoco-reports/ClientUI.png)

## API Endpoints Documentation

Swagger link for Patient-microservice : http://localhost:9011/swagger-ui.html#/

Swagger link for PatientHistorical-microservice : http://localhost:9012/swagger-ui.html#/

Swagger link for PatientDiabetesRisk-microservice : http://localhost:9013/swagger-ui.html#/