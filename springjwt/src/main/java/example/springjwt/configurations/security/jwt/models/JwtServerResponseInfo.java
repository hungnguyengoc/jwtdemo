package example.springjwt.configurations.security.jwt.models;

public class JwtServerResponseInfo {
    private final String token;

    public JwtServerResponseInfo(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }
}
