# jms-example
https://github.com/niyazkadirov/jms-example
Используется брокер сообщения ActiveMQ, с поддержкой командной строки, JMX MBean(JConsole)

**Шаги по настройке**

#### 1. Клонируйте приложение

> **https://github.com/niyazkadirov/jms-example.git**

#### 2. Создайте jar файл, используя Maven

> mvn install

#### 3. Запуск приложение

> создастся jar файл your\local\path\JMS\target\original-jms-example-SNAPSHOT.jar

> открываем командную строку для Producer и для Consumer

> в командной строке запускаем с помошью команды Producera  
**java -cp your/localpath/target/original-jms-example-SNAPSHOT.jar example.Producer Topic**

> запуск Consumer **java -cp your/local/path/target/original-jms-example-SNAPSHOT.jar example.Consumer Topic**

**example.Producer** - запуск производителя
**example.Consumer** - запуск потребителя
**Topic** - запуск топика так же можете выбрать очередь с помощью команды Queue для запуска очереди
