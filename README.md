# 🌤️ Weather Info API

A Spring Boot-based REST API to fetch and cache **weather information** by **Indian pincode** and **date** using OpenWeather APIs.

---

## 🧾 Requirements & Assumptions

- Input is always a valid **Indian 6-digit pincode**
- The `for_date` input must be:
  - a valid ISO date (`YYYY-MM-DD`)
  - **not in the future**
- On first call:
  - Pincode is geocoded to `lat/lon` via **OpenWeather Geocoding API**
  - Then used to fetch weather data via **Current Weather API**
- Weather info is **stored in DB**, so repeated calls for same `pincode + date` return from DB
- Assumes PostgreSQL is running locally

---

## 🧑‍💻 System Requirements

| Component         | Version            |
|------------------|--------------------|
| Java             | 17+                |
| Maven            | 3.8+               |
| PostgreSQL       | 13+                |
| Spring Boot      | 3.x                |
| OpenWeather API  | Free API Key       |
| Swagger (Springdoc OpenAPI) | 2.8.x   |

---

## 🚀 Project Setup (Local)

### 1. Clone the Repo

```bash
git clone https://github.com/your-username/weather-api.git
cd weather-api
```

### 2. Configure PostgreSQL

>> Create a database:
```bash
CREATE DATABASE weather_db;
```

>> Update src/main/resources/application.properties:
```bash
spring.datasource.url=jdbc:postgresql://localhost:5432/weather_db
spring.datasource.username=postgres
spring.datasource.password=yourpassword

openweather.api.key=your_openweather_api_key
```

### 3. Run the Application

```bash
mvn spring-boot:run
```

### 4. Access Swagger UI

```bash
http://localhost:8080/swagger-ui.html
```

## Documentation:
```bash
https://docs.google.com/document/d/1Gk_gDywkUxTTwbTv_5IDWSPcTPGecauXxouTKKbAR9o/edit?usp=sharing
```