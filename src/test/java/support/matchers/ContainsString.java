package support.matchers;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public class ContainsString extends BaseMatcher<String> {

    private final String expected;

    private ContainsString(String match) {
        this.expected = match;
    }

    @Override
    public boolean matches(Object o) {
        return o.toString().contains(expected);
    }

    @Override
    public void describeTo(Description description) {
    }
    public static ContainsString containsString(String expected){
        return new ContainsString(expected);
    }
}
