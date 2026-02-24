# Detener todo
docker-compose down -v

# Borrar volúmenes
Remove-Item -Path "./volumes" -Recurse -Force -ErrorAction SilentlyContinue

# Crear directorios vacíos
New-Item -Path "./volumes/kafka-1-data" -ItemType Directory -Force
New-Item -Path "./volumes/kafka-2-data" -ItemType Directory -Force
New-Item -Path "./volumes/kafka-3-data" -ItemType Directory -Force

# Levantar cluster (ahora se formateará automáticamente)
docker-compose -f "./docker-compose.yml" up -d

# Esperar 30 segundos
Start-Sleep -Seconds 30

# Verificar que estén funcionando
docker-compose ps
docker logs kafka-1 --tail 50