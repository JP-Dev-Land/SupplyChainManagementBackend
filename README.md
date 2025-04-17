# SupplyChainManagementBackend

A Spring Boot backend for managing a comprehensive supply chain system, featuring JWT-based authentication and PostgreSQL integration.

---

## 🚀 Getting Started

### 🔧 Clone the Repository

```bash
git clone https://github.com/JP-Dev-Land/SupplyChainManagementBackend.git
cd SupplyChainManagementBackend
```

---

## 🐘 PostgreSQL Setup

Follow the instructions in [`DB_Config.md`](./[DB_Config.md](./backend/DB_Config.md)) to install and configure PostgreSQL on your machine.

---

## 📦 Build and Run the Application

Ensure you have **Java 21** and **Maven** installed.

```bash
mvn clean install
mvn spring-boot:run
```

The application will start on:  
`http://localhost:8080`

---

## 🔐 Authentication & Authorization

The application uses **JWT** for authentication and supports **role-based access control**.

### 🛠️ API Endpoints

You can access the API documentation via Swagger UI at:  
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

> **Note:** Include the JWT in the `Authorization` header as a Bearer token for protected endpoints.

---

## 🌿 Git Branching Workflow

To maintain a clean and organized codebase, follow this branching strategy:

### ➕ Create and Switch to a New Branch

```bash
git checkout -b feature/<your-feature-name>
```

Replace `<your-feature-name>` with a descriptive name for your feature.

### ✅ Commit Your Changes

```bash
git add .
git commit -m "Add <your-feature-name>"
```

### ⬆️ Push Branch to Remote

```bash
git push origin feature/<your-feature-name>
```

> **Note:** Only push your feature branch to remote. Avoid pushing directly to the `main` branch.

---

## 📂 Project Structure

```
SupplyChainManagementBackend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── jpdevland/
│   │   │           └── supply_chain_mgmt/
│   │   │               └── backend/
│   │   │                   ├── config/
│   │   │                   ├── controller/
│   │   │                   ├── dto/
│   │   │                   ├── exception/
│   │   │                   ├── filter/
│   │   │                   ├── mapper/
│   │   │                   ├── model/
│   │   │                   ├── repo/
│   │   │                   ├── service/
│   │   │                   ├── utils/
│   │   │                   └── SupplyChainManagementApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── ...
├── DB_Config.md
├── pom.xml
└── README.md
```

---

## 👨‍💻 Contribution Guidelines

1. Fork the repository.
2. Create a new branch:  
   ```bash
   git checkout -b feature/<your-feature-name>
   ```
3. Make your changes and commit them:  
   ```bash
   git commit -m "Add <your-feature-name>"
   ```
4. Push to your branch:  
   ```bash
   git push origin feature/<your-feature-name>
   ```
5. Open a Pull Request.

---

## 📬 Questions?

For any queries or issues, please open an [issue](https://github.com/JP-Dev-Land/SupplyChainManagementBackend/issues) in the repository or contact [JP-Dev-Land](https://github.com/JP-Dev-Land).
