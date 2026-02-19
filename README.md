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

Просмотр сгенерированного wsdl: http://localhost:33612/FlatProxyService/FlatProxySoapBean?wsdl

Генерация ключа для mule:
```
keytool -genkeypair -alias mule -keyalg RSA -keysize 4096 \
  -validity 3650 -keystore mule.p12 \
  -storetype PKCS12 -storepass changeit -keypass changeit \
  -dname "CN=localhost, OU=Development, O=Company, L=City, ST=State, C=RU"
```

Создание trustore для mule
1. Экспорт сертификата WildFly
```
keytool -export -alias wildfly -keystore wildfly.p12 -storetype PKCS12 -storepass changeit -file wildfly.cer
```

2. Экспорт сертификата Spring
```
keytool -export -alias spring -keystore spring.p12 -storetype PKCS12 -storepass changeit -file spring.cer
```

3. Создаём truststore.p12 и импортируем первый сертификат
```
keytool -import -alias wildfly -file wildfly.cer -keystore truststore.p12 -storetype PKCS12 -storepass changeit -noprompt
```

4. Импортируем второй сертификат
```
keytool -import -alias spring -file spring.cer -keystore truststore.p12 -storetype PKCS12 -storepass changeit -noprompt
```

### Ссылки на репозитории лабораторной

1. Ссылка на основной вызываемый сервис реализованный на Spring Boot - https://github.com/stoneshik/fourth-lab-soa
2. Ссылка на второй вызывающий сервис реализованный на SOAP wildfly - https://github.com/stoneshik/fourth-lab-soa-second
3. Ссылка на фронтенд - https://github.com/stoneshik/fourth-lab-soa-frontend
