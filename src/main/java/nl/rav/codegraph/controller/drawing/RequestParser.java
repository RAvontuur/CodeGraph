package nl.rav.codegraph.controller.drawing;

import org.springframework.web.util.UriTemplate;

import java.util.Map;

/**
 * Created by rene on 30-3-16.
 */
public class RequestParser {

    /**
     * Parse a /sub1/sub2/sub3/viewName
     *
     * @param url full url of the request
     * @param viewName name of view
     * @return the package sub1.sub2.sub3
     */
    public static String parseToPackage(String url, String viewName) {
        UriTemplate template = new UriTemplate("/{value}/" + viewName);
        boolean isTemplateMatched = template.matches(url);
        if (isTemplateMatched) {
            Map<String, String> matchTemplate = template.match(url);
            String value = matchTemplate.get("value");
            return value.replaceAll("/", ".");
        }
        return "";
    }
}
