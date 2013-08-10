package services;

import org.springframework.stereotype.Component;
import persistent.UserEntity;

public interface Session {
    UserEntity getUser();
}

@Component
class FakeSession implements Session{

    @Override
    public UserEntity getUser() {
        return new UserEntity(1l);
    }
}