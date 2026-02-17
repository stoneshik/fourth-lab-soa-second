# Сервис-ориентированная архитектура

## Лабораторная работа № 4

### Вызывающий сервис, написанный на SOAP wildfly

Wildfly располагаем в корневую директорию проекта

`/flat-ejb` - логика сервиса, которая вызывается через remote интерфейс

`/flat-web` - основное приложение

Для сборки:
```
mvn clean package
```

Эта команда упаковывает `/flat-web` в `war` и `/flat-ejb` в `jar`, копирует `war` и `jar` в `wildfly/standalone/deployments`

Запуск wildfly:<br>
```
./wildfly/bin/standalone.sh -c standalone-node1.xml
```

Генерация ключа:
```
keytool -genkeypair -alias wildfly -keyalg RSA -keysize 4096 \
  -validity 3650 -keystore wildfly.p12 \
  -storetype PKCS12 -storepass changeit -keypass changeit \
  -dname "CN=localhost, OU=Development, O=Company, L=City, ST=State, C=RU"
```

### Ссылки на репозитории лабораторной

1. Ссылка на основной вызываемый сервис реализованный на Spring Boot - https://github.com/stoneshik/fourth-lab-soa
2. Ссылка на второй вызывающий сервис реализованный на SOAP wildfly - https://github.com/stoneshik/fourth-lab-soa-second
3. Ссылка на фронтенд - https://github.com/stoneshik/fourth-lab-soa-frontend
