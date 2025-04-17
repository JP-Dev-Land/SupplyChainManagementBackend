### 1. PostgreSQL Setup

1. **Install & Start**

    ```bash
    sudo apt update
    sudo apt install postgresql postgresql-contrib
    sudo systemctl enable postgresql
    sudo systemctl start postgresql
    
    ```

2. **Create Database & User**

    ```sql
    -- Drop the database and role if they already exist
    DROP DATABASE IF EXISTS supply_chain_mgmt;
    DROP ROLE IF EXISTS supply_chain_user;
    
    -- Create the new user with a password
    CREATE USER supply_chain_user WITH PASSWORD 'password';
    
    -- Create the new database and assign ownership to the new user
    CREATE DATABASE supply_chain_mgmt OWNER supply_chain_user;
    
    -- Connect to the new database
    \c supply_chain_mgmt
    
    -- Grant usage and create privileges on the public schema
    GRANT USAGE, CREATE ON SCHEMA public TO supply_chain_user;
    
    -- Grant privileges on all existing tables, sequences, and functions in the schema
    GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO supply_chain_user;
    GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO supply_chain_user;
    GRANT ALL PRIVILEGES ON ALL FUNCTIONS IN SCHEMA public TO supply_chain_user;
    
    -- Grant privileges on future tables, sequences, and functions created in the schema
    ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL PRIVILEGES ON TABLES TO supply_chain_user;
    ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL PRIVILEGES ON SEQUENCES TO supply_chain_user;
    ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL PRIVILEGES ON FUNCTIONS TO supply_chain_user;
    
    -- Exit the session
    \q
    ```


### 2. Type‑Safe Configuration with `@ConfigurationProperties`

Spring’s `@ConfigurationProperties` binds your YAML/ENV vars into **strongly‑typed** Java beans at startup—catching typos and missing keys at compile time

1. **Add to `pom.xml`**

    ```xml
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-configuration-processor</artifactId>
      <optional>true</optional>
    </dependency>
    
    ```

2. **Define `AppProperties`**

    ```java
    @ConfigurationProperties(prefix = "app")
    @Data
    public class AppProperties {
      private String jwtSecret;
      private final Database db = new Database();
      @Data
      public static class Database {
        private String url;
        private String username;
        private String password;
      }
    }
    
    ```

3. **Enable in Main**

    ```java
    @SpringBootApplication
    @EnableConfigurationProperties(AppProperties.class)
    public class SupplyChainMgmtHubApplication { … }
    
    ```

4.  **Ensure [`application.properties`](http://application.properties) has below props**

    ```jsx
    spring.application.name=SupplyChainMgmtHub
    
    spring.datasource.url=${DATABASE_URL:jdbc:postgresql://localhost:5432/supply_chain_mgmt}
    spring.datasource.username=${DATABASE_USERNAME:supply_chain_user}
    spring.datasource.password=${DATABASE_PASSWORD:password}
    
    # JWT settings under the `app` prefix
    app.jwt-secret=${JWT_SECRET:GqHHQD1hoGSuGSs4DGsWnw4KJuQOX0njvxwLXaHkAX0=}
    # 8 hours exp time
    app.jwt-expiration=28800000
    
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
    spring.jpa.properties.hibernate.format_sql=true
    # Log the parameter values bound to the SQL statements (DEBUG level)
    logging.level.org.hibernate.SQL=DEBUG
    # Log the JDBC parameters type binding (TRACE level for maximum detail)
    logging.level.org.hibernate.type.descriptor.sql=TRAC
    ```


### 3. JPA Entities & Repositories

Use Lombok + JPA for concise, type‑safe models:

```java
package com.jpdevland.SupplyChainMgmtHub.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false) private String name;
    @Column(unique = true, nullable = false) private String username;
    @Column(nullable = false) private String password;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;}

```

```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);
}
```

### 4. DTOs & MapStruct

Generate compile‑time mappers for Entities ↔ DTOs:

1. **Define DTO**

    ```java
    package com.jpdevland.SupplyChainMgmtHub.backend.dto;
    
    import lombok.Data;
    
    import javax.management.relation.Role;
    
    @Data
    public class UserDTO {
        private Long id;
        private String name;
        private String username;
        private Role role;
    }
    ```

2. **Create Mapper**

    ```java
    package com.jpdevland.SupplyChainMgmtHub.backend.mapper;
    
    import com.jpdevland.SupplyChainMgmtHub.backend.dto.UserDTO;
    import com.jpdevland.SupplyChainMgmtHub.backend.model.User;
    import org.mapstruct.Mapper;
    
    @Mapper(componentModel = "spring")
    public interface UserMapper {
        UserDTO toDto(User user);
        User toEntity(UserDTO dto);
    }
    ```