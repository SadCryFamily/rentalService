# RentalService

`RentalService` is a web application that allows users to browse available properties, make reservations for rentals or place offers to purchase properties.

## Requirements

To run the application, you need to install the following programs:

- Java 11
- Docker
- Docker Compose

## Running the Application

To run the application, follow these steps:

1. Clone the repository:

```git
git clone https://github.com/SadCryFamily/rentalService.git
```
2. Navigate to the project directory:

```sh
cd rentalService
```

3. Build the application:

```sh
./mvnw clean package
```
4. Run the application in a multiple Docker containers:

```docker
docker-compose up -d --build
```

The application will be launched in multiple Docker containers: one container for the `Spring Boot` application and another one - for `PostgreSQL` database.

After the application starts, it will be available at http://localhost:8080.

## Usage 

The application allows users to browse available properties, make reservations for rentals, place offers to purchase properties, and view their reservation and offer status. Users can also create and customize their own profiles and manage their orders.

The project also includes user authentication and registration features. Users can register for a new account, log in with their credentials, and receive an access token to make authorized requests to the API.

Please note that at the moment there is no moderation of orders or changes, so users can freely buy or rent properties and customize their profiles and orders without any approval process. The moderation of orders and changes will be added in the future version of the application.

## Technologies

RentalService was created using the following technologies:

- Java
- Spring Boot (Data, JPA, Security)
- Liquibase 
- PostgreSQL
- Docker
- Docker Compose

## Endpoints

The application provides the following endpoints:

### Auth

- `POST /signup` - Register a new customer
- `POST /activate` - Activate new customer
- `POST /signin` - Authenticate a user and generate an access token (Unavailable while do not activate accout)

### Customer

- `POST /profile` - Retrieve a customer profile
- `PUT /profile` - Update existing customer account data
- `DELETE /profile` - Delete customer account

### Rental

- `POST /rental` - Create new rental
- `GET /rentals` - Retrieve set of all available rentals
- `GET /rental/{id}` - Retrieve specified rental by `{id}` param
- `GET /my` - Retrieve set of all customer rentals
- `DELETE /rental/{id}` - Delete rental by `{id}` param (Delete only customer created rentals)
