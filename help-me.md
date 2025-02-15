# Help Me - Backend Test

## 📌 Introduction
This project is a REST service that allows initiating asynchronous searches and retrieving results later.

## 🛠️ Setting Up the Environment

### 1️⃣ **Prerequisites**
Before starting, make sure you have the following installed:
- **Java 17**
- **Maven 3.6.3+**
- **Docker** (if you want to run via container)

### 2️⃣ **Run Locally**
If you want to run locally without Docker:
```sh
mvn clean verify exec:java
```
The service will be available at **http://localhost:4567**.

### 3️⃣ **Run with Docker**
If you prefer to use Docker, run the following commands:
```sh
docker build -t backend-test .
docker run --rm -p 4567:4567 backend-test
```

## 📡 REST Endpoints
The API exposes two main endpoints:

### **🔍 Start a Search**
- **Method:** `POST`
- **Endpoint:** `/crawl`
- **Request:** JSON containing a `keyword` for the search.

#### 📥 **Example Request:**
```sh
curl -X POST http://localhost:4567/crawl -H "Content-Type: application/json" -d '{"keyword": "security"}'
```

#### 📤 **Example Response (200 OK):**
```json
{
  "id": "30vbllyb"
}
```

#### ⚠ **Possible Errors:**
| Code | Reason |
|------|----------------------------------|
| 400  | Invalid or empty keyword |

---

### **📄 Retrieve Search Results**
- **Method:** `GET`
- **Endpoint:** `/crawl/{searchId}`

#### 📥 **Example Request:**
```sh
curl -X GET http://localhost:4567/crawl/30vbllyb
```

#### 📤 **Example Response (200 OK):**
```json
{
  "id": "30vbllyb",
  "status": "active",
  "urls": [
    "https://ibm.com/index2.html",
    "https://ibm.com/htmlman1/chcon.1.html"
  ]
}
```

#### ⚠ **Possible Errors:**
| Code | Reason |
|------|----------------------------------|
| 404  | Search ID not found |

---

## 🏗️ Project Structure
- **`controller/`** → Contains REST controllers
- **`service/`** → Asynchronous search logic
- **`dto/`** → Data transfer objects
- **`util/`** → Utility methods
- **`exception/`** → Exception methods
- **`enums/`** → Enum methods

## 🔄 Environment Variables
The application requires the `BASE_URL` variable to be set:
```sh
export BASE_URL="https://ibm.com"
```
If using Docker, define it in the execution command:
```sh
docker run --rm -p 4567:4567 -e BASE_URL=https://ibm.com backend-test
```

## 🚀 Final Considerations
Now you are ready to use the service! For more details, check the source code or technical documentation.
