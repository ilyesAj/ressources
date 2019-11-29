docker ps -a | grep arlo:server | cut -d' ' -f1 | xargs docker stop 
docker ps -a | grep arlo:server | cut -d' ' -f1 | xargs docker rm > /dev/null