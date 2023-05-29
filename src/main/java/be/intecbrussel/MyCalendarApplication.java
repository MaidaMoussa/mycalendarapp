package be.intecbrussel;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class of app
 * !!! IF PUT IN SEPARATE PACKAGE ONLY CLASSES IN THAT PACKAGE ARE KNOWN TO SPRING BOOT
 */
@OpenAPIDefinition(info = @Info(title = "Tasks manipulation API",
        description = "API to create, manipulate and delete tasks",
        version = "1.0",
        contact = @Contact(
                name = "Kadir, Moussa",
                email = "kadir_moussa@gmail.com",
                url = "https://github.com/MaidaMoussa/mycalendarapp"
        ),
        license = @License(
                name = "MIT Licence",
                url = "https://opensource.org/licenses/mit-license.php"
        )
))
@SpringBootApplication
public class MyCalendarApplication {
    // Swagger-ui url : http://localhost:8888/swagger-ui/index.html
    //           json : http://localhost:8888/v3/api-docs0

    public static void main(String[] args) {
        SpringApplication.run(MyCalendarApplication.class, args);
    }

}
