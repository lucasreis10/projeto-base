buildscript {
	dependencies {
		classpath("org.flywaydb:flyway-mysql:8.5.10")
	}
}

plugins {
	java
	id("org.springframework.boot") version "3.0.1"
	id("io.spring.dependency-management") version "1.1.0"
	id("org.graalvm.buildtools.native") version "0.9.18"
	id("org.flywaydb.flyway") version "8.5.10"
}

group = "com.example.base"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("mysql:mysql-connector-java:8.0.15")

	implementation("org.springframework.security:spring-security-web:6.0.0")
	implementation("org.springframework.security:spring-security-core:6.0.0")
	implementation("org.springframework.boot:spring-boot-starter-security:3.0.1")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

	implementation("jakarta.validation:jakarta.validation-api:3.0.2")
	implementation("org.hibernate.validator:hibernate-validator:8.0.0.Final")

	implementation("org.springdoc:springdoc-openapi-webmvc-core:1.6.11")
	implementation("org.springdoc:springdoc-openapi-ui:1.6.11")
	implementation("org.springframework.boot:spring-boot-starter-actuator:3.0.1")

	testImplementation("org.flywaydb:flyway-core")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.mockito:mockito-junit-jupiter:4.8.0")
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")

	testRuntimeOnly("com.h2database:h2")
}

flyway {
	url = System.getenv("FLYWAY_DB") ?: "jdbc:mysql://localhost:3306/projeto_base"
	user = System.getenv("FLYWAY_USER") ?: "root"
	password = System.getenv("FLYWAY_PASS") ?: "123456"
}

tasks.withType<Test> {
	useJUnitPlatform()
}
