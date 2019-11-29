FILE="serverport.ini"
if [ ! -f "$FILE" ] ; then
    echo "PORT=4567" > $FILE
    PORT=4567
else
	PORT=`cat serverport.ini | cut -d'=' -f2`
fi

docker run -d -p $PORT:4567 arlo:server