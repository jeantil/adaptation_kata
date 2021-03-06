package builders.persistent;

import org.apache.commons.lang.math.RandomUtils;
import persistent.UserEntity;

public class UserEntityTestBuilder {
    private Long userId;

    public UserEntity build() {
        return new UserEntity(userId);
    }

    public UserEntityTestBuilder userId(Long userId) {
        this.userId = userId;

        return this;  //To change body of created methods use File | Settings | File Templates.
    }

    public static UserEntityTestBuilder userWithRandomId() {
        return new UserEntityTestBuilder().userId(RandomUtils.nextLong());
    }

}
