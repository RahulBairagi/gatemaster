package datamodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) // This will ignore any extra fields that are not defined in your model
public class TokenRefreshModel {
    private String statusCode;
    private String title;
    private String message;

    public String getStatusCode() {
        return statusCode;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public ResponseData getResponseData() {
        return responseData;
    }

    private ResponseData responseData;

    // Getters and setters

    public class ResponseData {
        private String token_type;

        public String getToken_type() {
            return token_type;
        }

        public int getExpires_in() {
            return expires_in;
        }

        public String getAccess_token() {
            return access_token;
        }

        private int expires_in;
        private String access_token;

        // Getters and setters
    }
}
