
docker stop arloserver 2> /dev/null
docker rm arloserver  2> /dev/null

docker build . -t arlo:server --no-cache

