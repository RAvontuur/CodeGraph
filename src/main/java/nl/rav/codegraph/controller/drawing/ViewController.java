package nl.rav.codegraph.controller.drawing;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * display view html, containing d3.js code which calls the rest-service for data
 */
@Controller
public class ViewController {

    @RequestMapping("**/view")
    public String view(HttpServletRequest request) throws IOException {
        return "view";
    }
}
