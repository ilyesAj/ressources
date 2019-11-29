# install docker (ubuntu)
````
sudo apt update -y && sudo apt install docker -y 
````
# deploying server (only first time)
````
cd ressources/server-deployment
sudo ./deploy.sh 
# expected result 
# ---> f4a3e0a5fa93
# Successfully built f4a3e0a5fa93
# Successfully tagged arlo:server

````
# lanching server
````
sudo ./launch.sh
# verify if the container is lanched
sudo docker ps
# expected result 
# acce8a5cf52a        arlo:server         "java -jar /root/mai…"   10 seconds ago      Up 7 seconds        0.0.0.0:4567->4567/tcp   upbeat_poitras
````
# stop server
````
sudo ./stop.sh
````
# configure server

modify serverport.ini with the port to expose and re-launch server
# troubleshoot
## Port already in use 
try to modify port in serverport.ini and relanch server
