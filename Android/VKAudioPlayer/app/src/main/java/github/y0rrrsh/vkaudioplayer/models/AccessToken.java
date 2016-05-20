package github.y0rrrsh.vkaudioplayer.models;

import android.support.annotation.NonNull;

/**
 * @author Artur Yorsh
 */
public class AccessToken {

    private String token;
    private long expiresIn;
    private long userId;

    private static AccessToken sInstance;

    private AccessToken(String token, String expiresIn, String userId) {
        this.token = token;
        this.expiresIn = Long.valueOf(expiresIn);
        this.userId = Long.valueOf(userId);
    }

    public static AccessToken init(@NonNull String token, String expiresIn, @NonNull String userId) {
        sInstance = new AccessToken(token, expiresIn, userId);
        return sInstance;
    }

    public static AccessToken getInstance() {
        assert sInstance != null;
        return sInstance;
    }

    public String getToken() {
        return token;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public long getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "AccessToken{" +
                "token='" + token + '\'' +
                ", expiresIn=" + expiresIn +
                ", userId=" + userId +
                '}';
    }
}
