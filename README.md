# Library Catalog Project
- Class: CIIC4020 (Data Structures)
- Institution: UPRM (University of Puerto Rico Mayaguez)
- Professor: Gretchen Bonilla 
- Student: Diego Quinones 
- Student Email: diego.quinones8@upr.edu
- Student #: 802-22-4049
# Instructions
In order to run the gui utilized the built in dockerfile if you have your own catalog either move it to the data directory or add a volume in your run command  
<Code>
```
git clone https://github.com/RUM-CIIC4020-DS/librarycatalog-fall2023project-diegoq48.git
```
```
cd into cloned dir 
```
```
sudo docker build -t librarycatalog ./
```
```
sudo docker run --rm test_docker --network=host -e DISPLAY=$DISPLAY -v /tmp/.X11-unix/:/tmp/.X11-unix test_docker
```
</Code>

# Project Description
This project is a simple library catalog built in java it can be installed both locally and with docker the code is not meant to be used in a real environement as the code is amnesic in other words data isnt saved from run to run future implementations without the class constraints should aim to do so future tasks

# Future Tasks
- [] Create a GUI webserver
- [] add permanence to the data
- [] add a database to the project
- [] add a login system
- [] add a trash system