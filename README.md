# Mediscreen

**Mediscreen** est une _application Web_ dont le but est de détecter, au sein de la base de données, les patients à
risque les plus exposés au diabète de type 2.

## Stack technique

<img src="https://img.shields.io/badge/-JAVA%2017-00A7BB?style=for-the-badge&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/-SPRING%20BOOT%203.0.5-6eb442?style=for-the-badge&logo=spring&logoColor=white">
<img src="https://img.shields.io/badge/-SPRING%20WEB-397200?style=for-the-badge&logo=spring&logoColor=white">
<br> <img src="https://img.shields.io/badge/-SPRING%20DATA%20JPA-8db411?style=for-the-badge&logo=spring&logoColor=white">
<img src="https://img.shields.io/badge/-SPRING%20DATA%20MONGODB-8db411?style=for-the-badge&logo=spring&logoColor=white">
<br><img src="https://img.shields.io/badge/-SPRING%20CLOUD%20GATEWAY-8db411?style=for-the-badge&logo=spring&logoColor=white">
<img src="https://img.shields.io/badge/-SPRING%20SECURITY-1a5900?style=for-the-badge&logo=spring&logoColor=white">
<img src="https://img.shields.io/badge/-NETFLIX%20EUREKA-e71e2f?style=for-the-badge&logo=spring&logoColor=white">
<br><img src="https://img.shields.io/badge/-MYSQL-006189?style=for-the-badge&logo=mysql&logoColor=white"> 
<img src="https://img.shields.io/badge/-MONGODB-6eb442?style=for-the-badge&logo=mongodb&logoColor=white">
<br><img src="https://img.shields.io/badge/-MAVEN-black?style=for-the-badge&logo=apachemaven&logoColor=white">
<img src="https://img.shields.io/badge/-JACOCO-810a00?style=for-the-badge">
<br><img src="https://img.shields.io/badge/-ANGULAR-c41829?style=for-the-badge&logo=angular&logoColor=white"> 
<br><img src="https://img.shields.io/badge/-DOCKER-2496ed?style=for-the-badge&logo=docker&logoColor=white">

## Microservices

Le back office, créé avec _Spring Boot_ est divisée en plusieurs _microservices_ :

- [mPatient](mPatient) : gère les données qui concernent l'identité des patients.
- [mNotes](mNotes) : gère l'historique des consultations des patients
- [mAssessment](mAssessment) : calcule le taux de risque de diabète chez les patients .
- [mAuthentication](mAuthentication) : gère l'identification des utilisateurs de l'application
- [mGateway](mGateway) : permet le routage API pour tous les microservice de l'application
- [mServer](mServer) : enregistre les instances des microservices
- [mediscreen-ui](ui%2Fmediscreen-ui) : l'UI en Angular

## Installation

- Cloner ce repository : git clone https://github.com/franzk/mediscreen.git


- Créer une base de données MySQL et adapter les fichiers de configuration :
    - [application-dev.properties](mPatient%2Fsrc%2Fmain%2Fresources%2Fapplication-dev.properties) (mPatient)
    - [application-test.properties](mPatient%2Fsrc%2Fmain%2Fresources%2Fapplication-test.properties) (mPatient)
    - [application.properties](mAuthentication%2Fsrc%2Fmain%2Fresources%2Fapplication.properties) (mAuthentication)


- Créer unhe base de données MongoDB et adapter les fichiers de configuration :
    - [application.properties](mNotes%2Fsrc%2Fmain%2Fresources%2Fapplication.properties) (mNotes)
    - [application.properties](mAssessment%2Fsrc%2Fmain%2Fresources%2Fapplication.properties) (mAssessment)

## Docker compose

Pour éxecuter le projet avec Docker compose:

- A la racine du projet, lancer la commande :
    - $ mvn clean install
- Egalement à la racine du projet, lancer la commande
    - $ docker-compose up
- (!) L'exécution par Docker ne nécessite pas d'effectuer les étapes de la partie **Installation** autre que le clonage
  du repository.

### /!\ Could Gateway

Lorsqu'un service est lancé ultérieurement au service mGateway, il faudra rafraichir les routes du gateway manuellement
grâce à la commande :  
curl --location --request POST 'http://localhost:8080/actuator/gateway/refresh'
