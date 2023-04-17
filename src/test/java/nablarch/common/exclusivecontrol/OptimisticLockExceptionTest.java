package nablarch.common.exclusivecontrol;

import nablarch.core.message.Message;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class OptimisticLockExceptionTest {
    
    private static class UserPk extends ExclusiveControlContext {

        enum PK {
            ID
        }

        public UserPk(String userId) {
            setTableName("user");
            setVersionColumnName("version");
            setPrimaryKeyColumnNames(PK.values());
            appendCondition(PK.ID, userId);
        }
    }

    private final Message message = mock(Message.class);


    @Test
    public void test() throws Exception {
        final List<Version> versions = Arrays.asList(new Version(new UserPk("1"), "999"));
        final OptimisticLockException exception = new OptimisticLockException(
                versions, message);

        assertThat(exception.getErrorVersions(), sameInstance(versions));

    }
}