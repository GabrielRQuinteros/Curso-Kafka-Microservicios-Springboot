

##########  BASE DE DATOS  ############
## Descargar Imagen de Docker
# Comando:
docker pull postgres:18.1-alpine

#### Levantar el contenedor ####
### COMO SE LEE ( El siguiente comando esta identado para ver las configuraciones. No copiar asi, copiar el de1 sola linea. )
docker run -d \
  --name kafka_project_db \
  -e POSTGRES_DB=kafka_project_db \
  -e POSTGRES_USER=gabriel_admin \
  -e POSTGRES_PASSWORD=gabriel_12345 \
  -p 5432:5432 \
  postgres:18.1-alpine

### COMANDO A EJECUTAR #####
docker run -d --name kafka_project_db -e POSTGRES_DB=kafka_project_db -e POSTGRES_USER=gabriel_admin -e POSTGRES_PASSWORD=gabriel_12345 -p 5432:5432 postgres:18.1-alpine

