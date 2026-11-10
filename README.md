# Курсова робота з КПП (Варіант 13)
**Тема:** Інформаційна система технічних заявок готельного комплексу
**Студент:** Лихопій Ярослав (група 202-ТН)

## Як запустити проєкт
1. Встановіть Java 21, Maven, MySQL (XAMPP), Apache Tomcat 9.x.
2. Виконайте скрипт `database.sql` у phpMyAdmin для створення бази даних.
3. У папці `server` виконайте `mvn clean package`, візьміть файл `hotel-tickets-server.war` з папки `target` та покладіть його у `webapps` Tomcat.
4. Запустіть Tomcat та перевірте роботу сервера за адресою: `http://localhost:8080/hotel-tickets-server/rooms`
5. У папці `client` запустіть клієнт командою: `mvn javafx:run`

## Використані технології
- Java 21
- JavaFX 21
- Maven
- Apache Tomcat 9.x, Servlet API
- MySQL, JDBC
- Jackson (JSON)
