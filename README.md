## SpringDataJpaRepo
Example app with Spring Jpa

## Заметки

http://localhost:8080/paging?pageNumber=0&pageSize=2&sortBy=name

### Пошагово

Шаг 1: Создайте новый проект Spring.

Используйте вашу среду разработки или сборщик проектов (например, Maven или Gradle) для создания нового проекта Spring.
Убедитесь, что в проекте есть зависимости для Spring, Spring Data JPA, Jackson и Mysql.

Шаг 2: Определите сущность Employee.

Создайте новый класс Employee с аннотацией @Entity.
Определите поля id, name, position и phone и аннотируйте их соответствующими аннотациями JPA, такими как @Id, @GeneratedValue и @Column.

```
@Entity
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
  
    private String name;

    private String position;
 
    private String phone;
```

Шаг 3: Создайте интерфейс репозитория EmployeeRepo.

Создайте новый интерфейс EmployeeRepository, который расширяет JpaRepository<Employee, Integer>.
Благодаря наследованию от CrudRepository, вам не нужно объявлять стандартные методы, так как они уже доступны.

```
public interface EmployeeRepo extends JpaRepository<Employee,Integer> {
}
```

Шаг 4: Настройте подключение к базе данных с помощью Java-конфигурации.

Создайте класс конфигурации, который будет использоваться для настройки подключения к базе данных и Hibernate.
Аннотируйте класс конфигурации аннотацией @Configuration.
В классе конфигурации определите бины для настройки EntityManagerFactory и TransactionManager.

```
@Configuration
@ComponentScan(basePackages = "com.example.springdatajpa")
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
@EnableWebMvc
@EnableJpaRepositories("com.example.springdatajpa.repo")
public class SpringConfig {
    /*
    Интерфейс Environment определяет контракт для доступа к свойствам окружения
    и другим характеристикам среды выполнения.
    Это позволяет разработчикам получать информацию о текущем окружении и
    использовать ее для конфигурации приложения.
     */
    private final Environment env;

    @Autowired
    public SpringConfig(Environment env) {
             this.env = env;
    }

    /*
    Интерфейс DataSource является частью Java-платформы и предоставляет абстракцию
    для получения подключений к базам данных.
    Он определяет методы, которые позволяют приложению устанавливать соединение с базой данных,
    получать объекты Connection и управлять ресурсами базы данных.
     */
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(env.getRequiredProperty("spring.datasource.driver-class-name"));
        dataSource.setUrl(env.getRequiredProperty("spring.datasource.url"));
        dataSource.setUsername(env.getRequiredProperty("spring.datasource.username"));
        dataSource.setPassword(env.getRequiredProperty("spring.datasource.password"));
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setPackagesToScan("com.example.springdatajpa.model"); // Пакет, содержащий ваши сущности
        factoryBean.setJpaVendorAdapter(vendorAdapter);
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("spring.jpa.dialect", "org.hibernate.dialect.MySQLDialect");
        hibernateProperties.setProperty("spring.jpa.show-sql", "true");
        factoryBean.setJpaProperties(hibernateProperties);

        return factoryBean;
    }

    /*
    PlatformTransactionManager предоставляет абстракцию для управления началом,
    фиксацией и откатом транзакций, а также для установки свойств транзакции,
    таких как изоляция и поведение при ошибке.
    */
    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }
```

Шаг 5: Используйте репозиторий в вашем коде.

В классе или сервисе, где вы хотите использовать репозиторий, добавьте поле типа EmployeeRepo и аннотацию @Autowired.
Используйте методы репозитория, такие как save, findById, findAll, delete и другие, в своем коде.

```
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepo employeeRepo;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    public List<Employee> findAll() {
        return employeeRepo.findAll();
    }

    public Page<Employee> getEmployees(int pageNumber, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return employeeRepo.findAll(pageable);
    }
}
```
