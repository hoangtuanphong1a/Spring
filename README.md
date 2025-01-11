<div align="center">

# 🚀 The Ultimate Spring Boot Guide
### Your Complete Reference for Spring Boot Development

*A comprehensive guide to help you master Spring Boot development*

</div>

---

## 📋 Table of Contents
- [🎯 Getting Started](#-getting-started)
- [📝 Basic Configuration](#-basic-configuration)
- [🏗️ Project Structure](#-project-structure)
- [🔧 Core Components](#-core-components)
- [📊 Database Integration](#-database-integration)
- [🔐 Security](#-security)
- [🧪 Testing](#-testing)
- [🚀 Deployment](#-deployment)

---

## 🎯 Getting Started

### Project Setup
```xml
<!-- Maven configuration (pom.xml) -->
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.2.0</version>
</parent>

<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>
```

### Basic Application
```java
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

---

## 📝 Basic Configuration

### Application Properties
```properties
# application.properties
server.port=8080
spring.application.name=my-app

# Database
spring.datasource.url=jdbc:mysql://localhost:3306/mydb
spring.datasource.username=root
spring.datasource.password=password

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### YAML Configuration
```yaml
# application.yml
spring:
  application:
    name: my-app
  datasource:
    url: jdbc:mysql://localhost:3306/mydb
    username: root
    password: password
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

---

## 🏗️ Project Structure

```
src
├── main
│   ├── java
│   │   └── com.example.demo
│   │       ├── Application.java
│   │       ├── controllers
│   │       ├── services
│   │       ├── repositories
│   │       └── models
│   └── resources
│       └── application.properties
└── test
    └── java
```

---

## 🔧 Core Components

### REST Controllers
```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }
    
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.save(user));
    }
}
```

### Services
```java
@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    public User findById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
    
    public User save(User user) {
        return userRepository.save(user);
    }
}
```

### JPA Repositories
```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByLastName(String lastName);
}
```

---

## 📊 Database Integration

### Entity Example
```java
@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String firstName;
    
    @Column(nullable = false)
    private String lastName;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    // Getters, setters, constructors
}
```

### JPA Configuration
```java
@Configuration
@EnableJpaRepositories(basePackages = "com.example.demo.repositories")
@EntityScan(basePackages = "com.example.demo.models")
public class JpaConfig {
    // Additional JPA configuration if needed
}
```

---

## 🔐 Security

### Basic Security Configuration
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/public/**").permitAll()
                .requestMatchers("/api/**").authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .permitAll()
            )
            .csrf(csrf -> csrf.disable());
            
        return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

---

## 🧪 Testing

### Unit Testing
```java
@SpringBootTest
class UserServiceTest {
    
    @MockBean
    private UserRepository userRepository;
    
    @Autowired
    private UserService userService;
    
    @Test
    void whenFindById_thenReturnUser() {
        User user = new User("John", "Doe", "john@example.com");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        
        User found = userService.findById(1L);
        assertThat(found.getEmail()).isEqualTo(user.getEmail());
    }
}
```

### Integration Testing
```java
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void whenGetUser_thenStatus200() throws Exception {
        mockMvc.perform(get("/api/users/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email").exists());
    }
}
```

---

## 🚀 Deployment

### JAR Packaging
```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>
```

### Docker Configuration
```dockerfile
FROM openjdk:17-jdk-slim
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

### Common Commands
```bash
# Build the application
./mvnw clean package

# Run the application
java -jar target/myapp-0.0.1-SNAPSHOT.jar

# Build Docker image
docker build -t myapp .

# Run Docker container
docker run -p 8080:8080 myapp
```

---

## 🎓 Best Practices

### Code Organization
- Follow the layered architecture (Controller -> Service -> Repository)
- Use DTOs for data transfer between layers
- Keep business logic in service layer
- Use interfaces for better abstraction

### Configuration Tips
- Externalize configuration using properties files
- Use profiles for different environments
- Keep sensitive data in environment variables
- Use actuator for monitoring and metrics

### Security Guidelines
- Always encrypt sensitive data
- Use HTTPS in production
- Implement proper authentication and authorization
- Regular security updates

---

<div align="center">

## 🌟 Additional Resources

| Resource | Link |
|----------|------|
| Spring Documentation | [Official Spring Docs](https://docs.spring.io/spring-boot/docs/current/reference/html/) |
| Spring Guides | [Spring.io Guides](https://spring.io/guides) |
| Spring Blog | [Spring Blog](https://spring.io/blog) |

### Happy Coding! 🎉


</div>

<div align="center">

# 🚀 Hướng Dẫn Spring Boot Toàn Tập Nâng Cao
### Cẩm Nang Toàn Diện Cho Phát Triển Spring Boot

*Tài liệu hướng dẫn chi tiết giúp bạn làm chủ Spring Boot*

</div>

---

## 📋 Mục Lục
- [🎯 Bắt Đầu](#-bắt-đầu)
- [📝 Cấu Hình Cơ Bản](#-cấu-hình-cơ-bản)
- [🏗️ Cấu Trúc Project](#-cấu-trúc-project)
- [🔧 Components Chính](#-components-chính)
- [📊 Tích Hợp Database](#-tích-hợp-database)
- [🔐 Bảo Mật](#-bảo-mật)
- [🧪 Testing](#-testing)
- [🚀 Triển Khai](#-triển-khai)

---

## 🎯 Bắt Đầu

### Thiết Lập Project với Spring Initializr

```xml
<!-- File pom.xml với các dependencies phổ biến -->
<dependencies>
    <!-- Core Spring Boot -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    
    <!-- Database -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
    </dependency>
    
    <!-- Security -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    
    <!-- Validation -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
    
    <!-- Lombok để giảm boilerplate code -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
</dependencies>
```

### Ứng Dụng Spring Boot Cơ Bản với Logger

```java
@SpringBootApplication
@Slf4j
public class Application {
    public static void main(String[] args) {
        log.info("Khởi động ứng dụng Spring Boot...");
        SpringApplication app = new SpringApplication(Application.class);
        
        // Thêm các tùy chỉnh
        app.setBannerMode(Banner.Mode.OFF);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8081"));
        
        app.run(args);
        log.info("Ứng dụng đã khởi động thành công!");
    }
    
    @Bean
    public CommandLineRunner init(UserService userService) {
        return args -> {
            // Khởi tạo dữ liệu mẫu
            log.info("Đang tạo dữ liệu mẫu...");
            userService.createSampleUsers();
        };
    }
}
```

---

## 📝 Cấu Hình Nâng Cao

### Cấu Hình Theo Môi Trường
```yaml
# application-dev.yml
spring:
  profiles: dev
  datasource:
    url: jdbc:mysql://localhost:3306/devdb
    username: devuser
    password: devpass
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
  logging:
    level:
      org.springframework: DEBUG
      com.example.demo: DEBUG

---
# application-prod.yml
spring:
  profiles: prod
  datasource:
    url: jdbc:mysql://${DB_HOST}:${DB_PORT}/${DB_NAME}
    username: ${DB_USER}
    password: ${DB_PASS}
  jpa:
    hibernate:
      ddl-auto: none
    show-sql: false
  logging:
    level:
      root: ERROR
      com.example.demo: INFO
```

### Cấu Hình Custom Properties
```java
@Configuration
@ConfigurationProperties(prefix = "app")
@Validated
@Data
public class AppProperties {
    @NotNull
    private String apiVersion;
    
    private Security security = new Security();
    
    @Data
    public static class Security {
        private String tokenSecret;
        private long tokenExpirationMsec;
        private String[] allowedOrigins;
    }
}
```

---

## 🏗️ Cấu Trúc Project Mở Rộng

```
src
├── main
│   ├── java
│   │   └── com.example.demo
│   │       ├── Application.java
│   │       ├── config
│   │       │   ├── SecurityConfig.java
│   │       │   ├── SwaggerConfig.java
│   │       │   └── WebMvcConfig.java
│   │       ├── controllers
│   │       │   ├── AuthController.java
│   │       │   └── UserController.java
│   │       ├── dto
│   │       │   ├── UserDTO.java
│   │       │   └── AuthResponse.java
│   │       ├── exceptions
│   │       │   ├── GlobalExceptionHandler.java
│   │       │   └── CustomException.java
│   │       ├── models
│   │       │   ├── User.java
│   │       │   └── Role.java
│   │       ├── repositories
│   │       │   └── UserRepository.java
│   │       ├── services
│   │       │   ├── UserService.java
│   │       │   └── AuthService.java
│   │       └── utils
│   │           ├── Constants.java
│   │           └── DateUtils.java
│   └── resources
│       ├── application.yml
│       ├── application-dev.yml
│       └── application-prod.yml
└── test
    └── java
        └── com.example.demo
            ├── controllers
            ├── services
            └── repositories
```

---

## 🔧 Components Chính với Ví Dụ Thực Tế

### Controller Đầy Đủ với Validation và Error Handling
```java
@RestController
@RequestMapping("/api/v1/users")
@Slf4j
@Validated
public class UserController {
    
    private final UserService userService;
    
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping
    public ResponseEntity<Page<UserDTO>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        log.debug("Lấy danh sách người dùng - page: {}, size: {}", page, size);
        return ResponseEntity.ok(userService.getAllUsers(page, size, sortBy));
    }
    
    @PostMapping
    public ResponseEntity<UserDTO> createUser(
            @Valid @RequestBody UserDTO userDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException("Dữ liệu không hợp lệ");
        }
        
        log.info("Tạo người dùng mới: {}", userDTO.getEmail());
        UserDTO createdUser = userService.createUser(userDTO);
        return ResponseEntity
            .created(URI.create("/api/v1/users/" + createdUser.getId()))
            .body(createdUser);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserDTO userDTO) {
        log.info("Cập nhật người dùng ID: {}", id);
        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.info("Xóa người dùng ID: {}", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
```

### Service Layer với Business Logic
```java
@Service
@Transactional
@Slf4j
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    
    @Autowired
    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }
    
    public Page<UserDTO> getAllUsers(int page, int size, String sortBy) {
        log.debug("Lấy danh sách người dùng với phân trang");
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        
        return userRepository.findAll(pageable)
            .map(user -> modelMapper.map(user, UserDTO.class));
    }
    
    public UserDTO createUser(UserDTO userDTO) {
        log.info("Tạo người dùng mới với email: {}", userDTO.getEmail());
        
        // Kiểm tra email đã tồn tại
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new DuplicateEmailException("Email đã tồn tại");
        }
        
        // Chuyển đổi DTO thành entity
        User user = modelMapper.map(userDTO, User.class);
        
        // Mã hóa mật khẩu
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        
        // Lưu vào database
        User savedUser = userRepository.save(user);
        
        return modelMapper.map(savedUser, UserDTO.class);
    }
    
    // Các phương thức khác...
}
```

---

## 📊 Tích Hợp Database Nâng Cao

### Entity với Relationships
```java
@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Auditable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String firstName;
    
    @Column(nullable = false)
    private String lastName;
    
    @Column(unique = true, nullable = false)
    @Email
    private String email;
    
    @Column(nullable = false)
    @JsonIgnore
    private String password;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();
    
    @CreatedDate
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    private LocalDateTime updatedAt;
    
    // Custom methods
    public void addRole(Role role) {
        roles.add(role);
        role.getUsers().add(this);
    }
    
    public void removeRole(Role role) {
        roles.remove(role);
        role.getUsers().remove(this);
    }
}
```

---

## 🔐 Bảo Mật Nâng Cao

### JWT Authentication
```java
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
    securedEnabled = true,
    jsr250Enabled = true,
    prePostEnabled = true
)
public class SecurityConfig {
    
    @Autowired
    private CustomUserDetailsService userDetailsService;
    
    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors()
                .and()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
            .csrf()
                .disable()
            .formLogin()
                .disable()
            .httpBasic()
                .disable()
            .authorizeRequests()
                .antMatchers("/auth/**")
                    .permitAll()
                .antMatchers("/api/public/**")
                    .permitAll()
                .antMatchers("/api/admin/**")
                    .hasRole("ADMIN")
                .anyRequest()
                    .authenticated();
        
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    // Các beans khác...
}
```

---

[Previous content remains the same...]

## 🧪 Testing Nâng Cao

### Unit Testing với Mockito và JUnit 5
```java
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private PasswordEncoder passwordEncoder;
    
    @InjectMocks
    private UserService userService;
    
    @Test
    @DisplayName("Nên tạo user thành công khi dữ liệu hợp lệ")
    void shouldCreateUserSuccessfully() {
        // Arrange
        UserDTO userDTO = UserDTO.builder()
            .email("test@example.com")
            .firstName("Test")
            .lastName("User")
            .password("password123")
            .build();
            
        User user = User.builder()
            .email(userDTO.getEmail())
            .firstName(userDTO.getFirstName())
            .lastName(userDTO.getLastName())
            .build();
            
        User savedUser = User.builder()
            .id(1L)
            .email(user.getEmail())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .build();
            
        when(userRepository.existsByEmail(userDTO.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(userDTO.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        
        // Act
        UserDTO result = userService.createUser(userDTO);
        
        // Assert
        assertNotNull(result);
        assertEquals(userDTO.getEmail(), result.getEmail());
        assertEquals(userDTO.getFirstName(), result.getFirstName());
        
        verify(userRepository).existsByEmail(userDTO.getEmail());
        verify(passwordEncoder).encode(userDTO.getPassword());
        verify(userRepository).save(any(User.class));
    }
    
    @Test
    @DisplayName("Nên ném ngoại lệ khi email đã tồn tại")
    void shouldThrowExceptionWhenEmailExists() {
        // Arrange
        UserDTO userDTO = UserDTO.builder()
            .email("existing@example.com")
            .build();
            
        when(userRepository.existsByEmail(userDTO.getEmail())).thenReturn(true);
        
        // Act & Assert
        assertThrows(DuplicateEmailException.class, () -> {
            userService.createUser(userDTO);
        });
        
        verify(userRepository).existsByEmail(userDTO.getEmail());
        verifyNoMoreInteractions(userRepository);
    }
}
```

### Integration Testing với TestContainers
```java
@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class UserControllerIntegrationTest {
    
    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0.26")
        .withDatabaseName("testdb")
        .withUsername("test")
        .withPassword("test");
        
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private UserRepository userRepository;
    
    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }
    
    @Test
    @DisplayName("Nên tạo user và trả về status 201")
    void shouldCreateUserAndReturn201() throws Exception {
        // Arrange
        UserDTO userDTO = UserDTO.builder()
            .email("test@example.com")
            .firstName("Test")
            .lastName("User")
            .password("password123")
            .build();
            
        // Act & Assert
        mockMvc.perform(post("/api/v1/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userDTO)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.email").value(userDTO.getEmail()))
            .andExpect(jsonPath("$.firstName").value(userDTO.getFirstName()))
            .andExpect(jsonPath("$.lastName").value(userDTO.getLastName()))
            .andDo(print());
    }
}
```

### Performance Testing với JMeter
```xml
<?xml version="1.0" encoding="UTF-8"?>
<jmeterTestPlan version="1.2" properties="5.0">
  <hashTree>
    <TestPlan guiclass="TestPlanGui" testclass="TestPlan" testname="User API Test Plan">
      <elementProp name="TestPlan.user_defined_variables" elementType="Arguments">
        <collectionProp name="Arguments.arguments"/>
      </elementProp>
      <stringProp name="TestPlan.comments"></stringProp>
      <boolProp name="TestPlan.functional_mode">false</boolProp>
      <boolProp name="TestPlan.serialize_threadgroups">false</boolProp>
      <stringProp name="TestPlan.user_define_classpath"></stringProp>
    </TestPlan>
    <hashTree>
      <ThreadGroup guiclass="ThreadGroupGui" testclass="ThreadGroup" testname="User API Load Test">
        <elementProp name="ThreadGroup.main_controller" elementType="LoopController">
          <boolProp name="LoopController.continue_forever">false</boolProp>
          <stringProp name="LoopController.loops">100</stringProp>
        </elementProp>
        <stringProp name="ThreadGroup.num_threads">50</stringProp>
        <stringProp name="ThreadGroup.ramp_time">10</stringProp>
        <longProp name="ThreadGroup.start_time">1373789594000</longProp>
        <longProp name="ThreadGroup.end_time">1373789594000</longProp>
        <boolProp name="ThreadGroup.scheduler">false</boolProp>
        <stringProp name="ThreadGroup.duration"></stringProp>
        <stringProp name="ThreadGroup.delay"></stringProp>
      </ThreadGroup>
    </hashTree>
  </hashTree>
</jmeterTestPlan>
```

## 🚀 Triển Khai Chi Tiết

### Docker Compose cho Môi Trường Development
```yaml
version: '3.8'

services:
  app:
    build: .
    ports:
      - "8080:8080"
    environment:
      - SPRING_PROFILES_ACTIVE=dev
      - SPRING_DATASOURCE_URL=jdbc:mysql://db:3306/myapp
      - SPRING_DATASOURCE_USERNAME=root
      - SPRING_DATASOURCE_PASSWORD=root
    depends_on:
      - db
    networks:
      - backend
      
  db:
    image: mysql:8.0
    ports:
      - "3306:3306"
    environment:
      - MYSQL_DATABASE=myapp
      - MYSQL_ROOT_PASSWORD=root
    volumes:
      - mysql-data:/var/lib/mysql
    networks:
      - backend
      
networks:
  backend:
    driver: bridge
    
volumes:
  mysql-data:
```

### Jenkins Pipeline cho CI/CD
```groovy
pipeline {
    agent any
    
    environment {
        DOCKER_IMAGE = 'myapp'
        DOCKER_TAG = "${env.BUILD_NUMBER}"
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Build') {
            steps {
                sh './mvnw clean package -DskipTests'
            }
        }
        
        stage('Test') {
            steps {
                sh './mvnw test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/TEST-*.xml'
                }
            }
        }
        
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh './mvnw sonar:sonar'
                }
            }
        }
        
        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("${DOCKER_IMAGE}:${DOCKER_TAG}")
                }
            }
        }
        
        stage('Deploy to Staging') {
            when {
                branch 'develop'
            }
            steps {
                sh """
                    kubectl apply -f k8s/staging/
                    kubectl set image deployment/myapp myapp=${DOCKER_IMAGE}:${DOCKER_TAG}
                """
            }
        }
        
        stage('Deploy to Production') {
            when {
                branch 'main'
            }
            steps {
                input 'Deploy to Production?'
                sh """
                    kubectl apply -f k8s/production/
                    kubectl set image deployment/myapp myapp=${DOCKER_IMAGE}:${DOCKER_TAG}
                """
            }
        }
    }
    
    post {
        success {
            slackSend channel: '#deployments',
                      color: 'good',
                      message: "Deployment successful: ${env.JOB_NAME} ${env.BUILD_NUMBER}"
        }
        failure {
            slackSend channel: '#deployments',
                      color: 'danger',
                      message: "Deployment failed: ${env.JOB_NAME} ${env.BUILD_NUMBER}"
        }
    }
}
```

### Kubernetes Deployment
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: myapp
spec:
  replicas: 3
  selector:
    matchLabels:
      app: myapp
  template:
    metadata:
      labels:
        app: myapp
    spec:
      containers:
      - name: myapp
        image: myapp:latest
        ports:
        - containerPort: 8080
        env:
        - name: SPRING_PROFILES_ACTIVE
          value: "prod"
        - name: SPRING_DATASOURCE_URL
          valueFrom:
            configMapKeyRef:
              name: myapp-config
              key: database-url
        - name: SPRING_DATASOURCE_USERNAME
          valueFrom:
            secretKeyRef:
              name: myapp-secrets
              key: database-username
        - name: SPRING_DATASOURCE_PASSWORD
          valueFrom:
            secretKeyRef:
              name: myapp-secrets
              key: database-password
        readinessProbe:
          httpGet:
            path: /actuator/health
            port: 8080
          initialDelaySeconds: 30
          periodSeconds: 10
        livenessProbe:
          httpGet:
            path: /actuator/health
            port: 8080
          initialDelaySeconds: 60
          periodSeconds: 15
        resources:
          requests:
            memory: "512Mi"
            cpu: "500m"
          limits:
            memory: "1Gi"
            cpu: "1000m"
```

### Monitoring với Prometheus và Grafana
```yaml
management:
  endpoints:
    web:
      exposure:
        include: "*"
  metrics:
    export:
      prometheus:
        enabled: true
  endpoint:
    health:
      show-details: always
    metrics:
      enabled: true
    prometheus:
      enabled: true
```

---

## 📚 Tài Liệu Tham Khảo và Công Cụ Hữu Ích

1. **Spring Boot Documentation**
   - Official Reference: https://docs.spring.io/spring-boot/docs/current/reference/html/
   - API Documentation: https://docs.spring.io/spring-boot/docs/current/api/
   
2. **Công Cụ Phát Triển**
   - Spring Boot DevTools
   - Lombok
   - MapStruct
   - Spring Configuration Processor
   
3. **Testing Tools**
   - JUnit 5
   - Mockito
   - TestContainers
   - REST Assured
   
4. **Monitoring & Logging**
   - Spring Boot Actuator
   - Prometheus
   - Grafana
   - ELK Stack

---

<div align="center">

## 🌟 Kết Luận

Spring Boot là một framework mạnh mẽ cho phát triển ứng dụng Java. Với hướng dẫn này, bạn đã có một nền tảng vững chắc để bắt đầu xây dựng các ứng dụng Spring Boot chuyên nghiệp.

### Chúc Bạn Code Vui! 🎉


</div>

<div align="center">

# 🚀 The Ultimate Git Guide
### Your Complete Reference for Git Version Control

![Git Logo](https://git-scm.com/images/logos/downloads/Git-Icon-1788C.png)

*A comprehensive guide to help you master Git commands and workflows*

</div>

---

## 📋 Table of Contents
- [🎯 Getting Started](#-getting-started)
- [📝 Basic Commands](#-basic-commands)
- [🌿 Branch Management](#-branch-management)
- [🤝 Collaboration](#-collaboration)
- [🔄 Advanced Operations](#-advanced-operations)
- [⚠️ Troubleshooting](#️-troubleshooting)
- [🎓 Best Practices](#-best-practices)

---

## 🎯 Getting Started

### Repository Initialization
```bash
# Create a new repository
git init

# Clone an existing repository
git clone <repository-url>
```

### First-Time Setup
```bash
# Configure your identity
git config --global user.name "Your Name"
git config --global user.email "your.email@example.com"
```

---

## 📝 Basic Commands

### Checking Status and Changes
```bash
# View repository status
git status

# View detailed changes
git diff

# View staged changes
git diff --staged
```

### Managing Changes
```bash
# Stage specific files
git add <file-name>

# Stage all changes
git add .

# Stage changes interactively
git add -p

# Commit changes
git commit -m "Your descriptive message"

# Commit with detailed message
git commit -v
```

---

## 🌿 Branch Management

### Working with Branches
```bash
# Create a new branch
git branch <branch-name>

# Switch to a branch
git checkout <branch-name>

# Create and switch (shorthand)
git checkout -b <branch-name>

# List all branches
git branch --all

# Delete a branch
git branch -d <branch-name>
```

### Merging
```bash
# Merge a branch into current branch
git merge <branch-name>

# Merge with no fast-forward
git merge --no-ff <branch-name>
```

---

## 🤝 Collaboration

### Remote Operations
```bash
# Add remote repository
git remote add origin <repository-url>

# View remote repositories
git remote -v

# Fetch updates
git fetch origin

# Pull changes
git pull origin <branch-name>

# Push changes
git push origin <branch-name>
```

### Syncing Forks
```bash
# Add upstream remote
git remote add upstream <original-repository-url>

# Sync with upstream
git fetch upstream
git merge upstream/main
```

---

## 🔄 Advanced Operations

### Stashing Changes
```bash
# Stash current changes
git stash save "Your stash message"

# List stashes
git stash list

# Apply stash
git stash apply stash@{0}

# Drop stash
git stash drop stash@{0}
```

### History Management
```bash
# View commit history
git log

# View pretty commit graph
git log --graph --oneline --decorate

# View changes in commit
git show <commit-hash>
```

---

## ⚠️ Troubleshooting

### Fixing Mistakes
```bash
# Undo last commit (keep changes)
git reset --soft HEAD~1

# Undo last commit (discard changes)
git reset --hard HEAD~1

# Undo changes in file
git checkout -- <file-name>

# Fix last commit message
git commit --amend
```

### Resolving Conflicts

1. **When a conflict occurs:**
   ```bash
   # Identify conflicting files
   git status
   ```

2. **Open conflicting files and look for:**
   ```
   <<<<<<< HEAD
   Your changes
   =======
   Their changes
   >>>>>>> branch-name
   ```

3. **After resolving:**
   ```bash
   git add <resolved-files>
   git commit -m "Resolve merge conflicts"
   ```

---

## 🎓 Best Practices

### Commit Guidelines
- Write clear, concise commit messages
- Use present tense ("Add feature" not "Added feature")
- Keep commits focused and atomic
- Reference issue numbers when applicable

### Workflow Tips
- Pull before pushing to avoid conflicts
- Create feature branches for new work
- Regularly backup your work
- Keep your repository clean

### Security
- Never commit sensitive data
- Use `.gitignore` for private files
- Regularly audit access permissions
- Use SSH keys for authentication

---

<div align="center">

## 🌟 Additional Resources

| Resource | Link |
|----------|------|
| Git Documentation | [Official Git Docs](https://git-scm.com/doc) |
| Git Cheat Sheet | [GitHub Cheat Sheet](https://training.github.com) |
| Interactive Tutorial | [Learn Git Branching](https://learngitbranching.js.org/) |

### Happy Coding! 🎉


</div>
