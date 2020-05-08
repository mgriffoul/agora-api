**Démarrer une base Postgres en local via Docker :**
`docker volume create agoradata`
`docker run --name pg-agora -e POSTGRES_PASSWORD=password -d -p 5432:5432 -v agoradata:/var/lib/postgresql/data  postgres`
