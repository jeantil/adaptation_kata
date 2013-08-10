package services;

import static org.fest.assertions.api.Assertions.assertThat;
import org.junit.Test;
import persistent.UserEntity;

public class FakeSessionTest {
    @Test
    public void should_return_current_user() throws Exception {
        // When
        UserEntity user = new FakeSession().getUser();
        // Then
        assertThat(user).isNotNull();
    }
}
