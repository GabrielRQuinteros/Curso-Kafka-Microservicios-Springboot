# Detener contenedores existentes
docker-compose down -v

# Limpiar volúmenes
rm -rf ./volumes/kafka-*-data

# Crear directorios
mkdir -p ./volumes/kafka-1-data
mkdir -p ./volumes/kafka-2-data
mkdir -p ./volumes/kafka-3-data

# Levantar cluster
docker-compose up -d

# Esperar a que inicien
echo "Esperando a que los brokers estén listos..."
sleep 10

# Formatear cada nodo
docker exec kafka-1 kafka-storage format -t CqdSQInQSU-vGH2wdDvRdA -c /etc/kafka/kraft/server.properties
docker exec kafka-2 kafka-storage format -t CqdSQInQSU-vGH2wdDvRdA -c /etc/kafka/kraft/server.properties
docker exec kafka-3 kafka-storage format -t CqdSQInQSU-vGH2wdDvRdA -c /etc/kafka/kraft/server.properties

# Reiniciar para que tomen el formateo
docker-compose restart

echo "Cluster Kafka inicializado correctamente"