package org.example.eegspring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Map;

/**
 * Rest Controller for handling EEG related requests.
 * This controller handles the mapping of "/user/{username}/electrode/{electrodeNumber}" GET requests.
 */
@RestController
public class EEGController {

    /**
     * Autowired JdbcTemplate for handling JDBC related operations.
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Handles the GET request for retrieving EEG image for a specific user and electrode number.
     * The method executes a SQL query to fetch the image data from the database and returns it as a base64 encoded string embedded in an HTML img tag.
     *
     * @param username the username of the user
     * @param electrodeNumber the electrode number
     * @return a String containing an HTML document with the EEG image embedded as a base64 string
     */
    @GetMapping("/user/{username}/electrode/{electrodeNumber}")
    public String getEEGImage(@PathVariable String username, @PathVariable int electrodeNumber) {
        String sql = "SELECT image FROM user_eeg WHERE username = ? AND electrode_number = ?";
        Map<String, Object> result = jdbcTemplate.queryForMap(sql, username, electrodeNumber);

        String base64Image = (String) result.get("image");
        return "<html><body><h1>User: " + username + "</h1><h2>Electrode: " + electrodeNumber + "</h2>"
                + "<img src='data:image/png;base64, " + base64Image + "'/></body></html>";
    }
}