# SEHM-Server

SEHM - or Stress Eating Health Management - is a project aimed at reducing stress eating habits. 

This server provides a secure restful API for the client data as well as classifies stress and non stress data using an FFT.

The full writeup on the project can be found in my dissertation [here](https://github.com/gabrielstellini/Thesis-Android-App/blob/master/Thesis%20writeup%20-%20final.pdf)

## Getting started

### Prerequisites
The server connects to a mySQL instance. The credentials should be updated in the 
`[Thesis-Server](https://github.com/gabrielstellini/Thesis-Server/tree/9b24c52597053efc745437191d5edfba0e7904d1)/[src](https://github.com/gabrielstellini/Thesis-Server/tree/9b24c52597053efc745437191d5edfba0e7904d1/src)/[main](https://github.com/gabrielstellini/Thesis-Server/tree/9b24c52597053efc745437191d5edfba0e7904d1/src/main)/[resources](https://github.com/gabrielstellini/Thesis-Server/tree/9b24c52597053efc745437191d5edfba0e7904d1/src/main/resources)/**application.yml**

This project also makes use of an OAuth solution provided by [Auth0](https://auth0.com/).  In order to authenticate the client, you need to update the server and client keys.

This server is the backend to the [sehm-client](https://github.com/gabrielstellini/Thesis-Android-App)

### Installing

 1. Clone the repo
 2. Update the credentials and auth0 api keys
 3. Build the app with maven
 4. Run the server

### License
This project is Licensed under the MIT License.
