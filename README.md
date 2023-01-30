# Rental Service :office:

Rental Service - service that allow customers to take a rent any available apartaments in their city.

## How to run? :rotating_light:

**To run application locally** - download the source code using this [link](https://github.com/SadCryFamily/rentalService.git) and paste it into Get From VCS at your IDE. After code checking and building - run it manually with `main(String[] args)` method in `RentalApplication`.

**To run application in Docker** - do the same according to run application locally until run the code by RentalApplication and follow next tips:

1) Create an executable .jar file using next maven command `mvn clean package -DskipTests`.

2) Open a command line and paste there a your path to root of project. For example, `cd /Desktop/renralService`.

3) Open Docker Desktop on your local machine or if its missing - download from [official site](https://desktop.docker.com/win/main/amd64/Docker%20Desktop%20Installer.exe?utm_source=docker&utm_medium=webreferral&utm_campaign=dd-smartbutton&utm_location=module).

4. After Docker Desktop started - in root of project directory execute next commands:

```cmd
docker-compose up -d --build
```

``docker-compose up -d --build`` - allows to containerize project data and build container.

#### If you wish to check manually that any of given containters started successfully using next command:

```cmd
docker ps
docker logs <CONTAINER_ID> [--follow]
```

``docker ps`` - required for checking all active containers in you local machine. Can be really useful when any of given containers wont be started to stop them and re-run.

``docker logs <CONTAINER_ID> [--follow]`` allows to check logs as a trace or in real-time (using **-follow** key). Can provide helpful information when any of components wont be working fine.

#### If you need to stop all active containers activated either:

```cmd
docker-compose down
```

## How to use? :recycle:

For usage, rentalService provides a several APIs described down here:

``POST localhost:8080/signup`` - register a new customer.

``POST localhost:8080/activate`` - active a recently registered customer.

``POST localhost:8080/signin`` - log in activated customer.

``POST localhost:8080/rental`` - create new rental.

``GET localhost:8080/rental`` - get all created customer rentals.

``DELETE localhost:8080/rental?id=?`` - delete an customer rental by given id.

``PUT localhost:8080/profile`` - update a customer profile info.

``DELETE localhost:8080/profile`` -  block customer.
