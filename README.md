**Démarrer une base Postgres en local via Docker :**
`docker volume create agoradata`
`docker run --rm --name pg-agora -e POSTGRES_PASSWORD=password -e -d -p 5432:5432 -v agoradata:/var/lib/postgresql/data  postgres`
