# Examen BackEnd Cash

## Instalación y ejecución
### Docker
Se debe tener instalado docker en la máquina y ejecutar el siguiente comando desde la raíz del proyecto:

```bash
docker compose up
```
La aplicación inicia en http://localhost:6868

### Ambiente local
Se debe tener instalado MySQL 8 y se debe ejecutar el siguiente script:
```sql
CREATE DATABASE IF NOT EXISTS `loans_db` /*!40100 DEFAULT CHARACTER SET utf8mb4  COLLATE utf8mb4_0900_ai_ci */;
USE `loans_db`;
CREATE USER 'loans_user'@'localhost' IDENTIFIED BY 'loans123';
GRANT ALL PRIVILEGES ON `loans_db`.* TO 'loans_user'@'localhost';

```
Para cargar los datos se debe ejecutar el script `loans_db.sql` ubicado en la raíz del repositorio

Para compilar y ejecutar los tests se debe correr el siguiente comando desde la raíz del proyecto:
```bash
mvn clean install
```
Para iniciar la aplicación debe ejecutar el siguiente comando:
```bash
mvn clean spring-boot:run
```
Para correr solo los tests se debe ejecutar el siguiente comando:
```bash
mvn test
```
La aplicación inicia por defecto en http://localhost:8081


## API DOC
https://j-tamayo.github.io/loans-challenge/index.html