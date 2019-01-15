To only build the project
```
mvn clean install
```

To build and run some integration tests
```
mvn clean install -Denv="test" -Ddb="h2"
```

The data layer is compatible with MySQL and Oracle
```
-Denv="prod" (an empty env string is considered as prod)
-Denv="test"
-Ddb="mysql"
-Ddb="oracle"
-Ddb="postgresql"
-Ddb="h2"
```
