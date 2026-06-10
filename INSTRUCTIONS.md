# Instrucciones de ejecución

Prerequisitos
- Java 17 (JDK)
- Maven 3.6+
- Node.js 18+ y npm
- (Opcional) Docker y Docker Compose

Backend (Spring Boot)
1. Abrir una terminal en la carpeta `backend`:

```bash
cd backend
```

2. Compilar y ejecutar (modo desarrollo):

```bash
mvn clean package
mvn spring-boot:run
```

3. La API estará disponible en `http://localhost:8080`.

Notas útiles:
- Endpoints principales: `http://localhost:8080/api/users` y `http://localhost:8080/api/users/{id}`
- Consola H2 (si está habilitada): `http://localhost:8080/h2-console`

Frontend (React + Vite)
1. Abrir una terminal en la carpeta `frontend`:

```bash
cd frontend
```

2. Instalar dependencias:

```bash
npm install
```

Si hay conflictos de dependencias, intente:

```bash
npm install --legacy-peer-deps
```

3. Iniciar el servidor de desarrollo:

```bash
npm run dev
```

4. Abrir `http://localhost:5173` en el navegador.

Configuración y CORS
- Durante desarrollo el frontend usa `http://localhost:5173` y el backend `http://localhost:8080`.
- El backend permite CORS desde `http://localhost:5173` para facilitar desarrollo local.

API / Documentación OpenAPI
- El archivo `openapi.yaml` con el diseño de la API está en la raíz del repositorio.

Uso rápido (ejemplos)

Listar usuarios:

```bash
curl http://localhost:8080/api/users
```

Crear usuario (ejemplo):

```bash
curl -X POST http://localhost:8080/api/users \
	-H "Content-Type: application/json" \
	-d '{"nombres":"Juan","apellidos":"Perez","rut":12345678,"dv":"5","fechaNacimiento":"1990-01-01","correoElectronico":"juan@example.com","contrasena":"secret"}'
```

Ejecución con Docker (opcional)
- Ejemplos rápidos (no se incluyen Dockerfiles en el repo por defecto):

Backend:
```bash
cd backend
mvn clean package
docker build -t mantenedor-backend .
docker run -p 8080:8080 mantenedor-backend
```

Frontend:
```bash
cd frontend
npm install
npm run build
docker build -t mantenedor-frontend .
docker run -p 5173:5173 mantenedor-frontend
```

Soporte
- Ejecutar `mvn test` en `backend` para correr los tests de unidad.
- Para pruebas manuales de la API usar `curl` o Postman.
