package persistent;

public class UserEntity {
    public UserEntity(Long userId) {
        this.userId = userId;
    }

    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
