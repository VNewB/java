#!/usr/bin/env bash

set -e

PROJECT_NAME="JavaDatabase1"

echo "Creating project: $PROJECT_NAME"

mkdir -p "$PROJECT_NAME"/{database,podman,src/main/java}

touch "$PROJECT_NAME/pom.xml"
touch "$PROJECT_NAME/database/database.sql"
touch "$PROJECT_NAME/podman/compose.yaml"
touch "$PROJECT_NAME/src/main/java/Main.java"

cat > "$PROJECT_NAME/podman/compose.yaml" <<'EOF'
services:
  sqlserver:
    image: mcr.microsoft.com/mssql/server:2025-latest
    container_name: my-sqlserver

    environment:
      ACCEPT_EULA: "Y"
      MSSQL_SA_PASSWORD: "YourStrongPassword123!"

    ports:
      - "1433:1433"

    volumes:
      - sqlserver-data:/var/opt/mssql

volumes:
  sqlserver-data:
EOF

cat > "$PROJECT_NAME/pom.xml" <<'EOF'
<?xml version="1.0" encoding="UTF-8"?>

<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="
            http://maven.apache.org/POM/4.0.0
            https://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <groupId>com.example</groupId>
    <artifactId>JavaDatabase1</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.release>25</maven.compiler.release>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>

        <dependency>
            <groupId>com.microsoft.sqlserver</groupId>
            <artifactId>mssql-jdbc</artifactId>
            <version>13.6.0.jre11</version>
        </dependency>

    </dependencies>

    <build>
        <plugins>

            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.14.0</version>
            </plugin>

            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>exec-maven-plugin</artifactId>
                <version>3.5.0</version>

                <configuration>
                    <mainClass>Main</mainClass>
                </configuration>
            </plugin>

        </plugins>
    </build>

</project>
EOF

cat > "$PROJECT_NAME/database/database.sql" <<'EOF'
CREATE DATABASE JavaDatabase;
GO

USE JavaDatabase;
GO

CREATE TABLE users
(
    id INT IDENTITY(1,1) PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100)
);
GO

INSERT INTO users (username, email)
VALUES
    ('admin', 'admin@example.com'),
    ('student', 'student@example.com');
GO
EOF

cat > "$PROJECT_NAME/src/main/java/Main.java" <<'EOF'
import java.sql.*;

public class Main {

    public static void main(String[] args) {

        String url =
            "jdbc:sqlserver://localhost:1433;" +
            "databaseName=JavaDatabase;" +
            "encrypt=true;" +
            "trustServerCertificate=true;";

        String username = "sa";
        String password = "YourStrongPassword123!";

        try (
            Connection connection =
                DriverManager.getConnection(url, username, password)
        ) {

            System.out.println("Connected to SQL Server!");

            String sql = "SELECT id, username, email FROM users";

            try (
                PreparedStatement statement =
                    connection.prepareStatement(sql);

                ResultSet result =
                    statement.executeQuery()
            ) {

                while (result.next()) {

                    int id = result.getInt("id");
                    String user = result.getString("username");
                    String email = result.getString("email");

                    System.out.println(
                        id + " | " + user + " | " + email
                    );
                }
            }

        } catch (SQLException e) {

            System.err.println("Database error:");
            e.printStackTrace();
        }
    }
}
EOF

cat > "$PROJECT_NAME/.gitignore" <<'EOF'
target/
.idea/
.vscode/
*.class
EOF

echo
echo "Project created successfully!"
echo

find "$PROJECT_NAME" -type f | sort

echo
echo "Next:"
echo "  cd $PROJECT_NAME/podman"
echo "  podman compose up -d"
echo
echo "Then:"
echo "  cd .."
echo "  mvn compile"
echo "  mvn exec:java"

