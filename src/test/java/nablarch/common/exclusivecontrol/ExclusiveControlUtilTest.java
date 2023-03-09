package nablarch.common.exclusivecontrol;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.collection.IsMapContaining;

import nablarch.core.repository.ObjectLoader;
import nablarch.core.repository.SystemRepository;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;

/**
 * {@link ExclusiveControlUtil}テスト。
 *
 * @author Kiyohito Itoh
 */
public class ExclusiveControlUtilTest {

    @Mocked
    private ExclusiveControlManager mockExclusiveControlManager;


    @Before
    public void setUp() {
        SystemRepository.load(new ObjectLoader() {
            @Override
            public Map<String, Object> load() {
                final Map<String, Object> objects = new HashMap<String, Object>();
                objects.put("exclusiveControlManager", mockExclusiveControlManager);
                return objects;
            }
        });
    }

    private static class ExUserMstPk extends ExclusiveControlContext {

        enum PK {
            USER_ID,
            PK2,
            PK3
        }

        public ExUserMstPk(String userId, String pk2, String pk3) {
            setTableName("EXCLUSIVE_USER_MST");
            setVersionColumnName("VERSION");
            setPrimaryKeyColumnNames(PK.values());
            appendCondition(PK.USER_ID, userId);
            appendCondition(PK.PK2, pk2);
            appendCondition(PK.PK3, pk3);
        }
    }

    @Test
    @Ignore("jacoco と jmockit が競合してエラーになるため")
    public void testGetVersion() throws Exception {
        final ExUserMstPk pk = new ExUserMstPk("a", "1", "2");
        new Expectations() {{
            mockExclusiveControlManager.getVersion(pk);
            result = new Version(pk, "999");
        }};

        final Version version = ExclusiveControlUtil.getVersion(pk);
        assertThat(version.getTableName(), is("EXCLUSIVE_USER_MST"));
        assertThat(version.getVersionColumnName(), is("VERSION"));
        assertThat(version.getVersion(), is("999"));
        assertThat(version.getPrimaryKeyCondition(), is(allOf(
                IsMapContaining.<String, Object>hasEntry("user_id", "a"),
                IsMapContaining.<String, Object>hasEntry("pk2", "1"),
                IsMapContaining.<String, Object>hasEntry("pk3", "2")
        )));
    }

    @Test
    public void testAddVersion() throws Exception {
        ExclusiveControlUtil.addVersion(new ExUserMstPk("id", "id2", "id3"));

        new Verifications() {{
            ExclusiveControlContext context;
            mockExclusiveControlManager.addVersion(context = withCapture());
            times = 1;

            assertThat(context.getCondition(), allOf(
                    IsMapContaining.<String, Object>hasEntry("user_id", "id"),
                    IsMapContaining.<String, Object>hasEntry("pk2", "id2"),
                    IsMapContaining.<String, Object>hasEntry("pk3", "id3")
            ));

            assertThat(context.getTableName(), is("EXCLUSIVE_USER_MST"));
            assertThat(context.getVersionColumnName(), is("VERSION"));
            assertThat((ExUserMstPk.PK[]) context.getPrimaryKeyColumnNames(), is(ExUserMstPk.PK.values()));
        }};
    }

    @Test
    public void testUpdateVersion() throws Exception {
        ExclusiveControlUtil.updateVersion(new ExUserMstPk("1", "2", "3"));
        
        new Verifications() {{
            ExclusiveControlContext context;
            mockExclusiveControlManager.updateVersion(context = withCapture());
            times = 1;

            assertThat(context.getCondition(), allOf(
                    IsMapContaining.<String, Object>hasEntry("user_id", "1"),
                    IsMapContaining.<String, Object>hasEntry("pk2", "2"),
                    IsMapContaining.<String, Object>hasEntry("pk3", "3")
            ));
        }};
    }

    @Test
    public void removeVersion() throws Exception {
        ExclusiveControlUtil.removeVersion(new ExUserMstPk("a", "b", "c"));
        
        new Verifications() {{
            ExclusiveControlContext context;
            mockExclusiveControlManager.removeVersion(context = withCapture());
            times = 1;

            assertThat(context.getCondition(), allOf(
                    IsMapContaining.<String, Object>hasEntry("user_id", "a"),
                    IsMapContaining.<String, Object>hasEntry("pk2", "b"),
                    IsMapContaining.<String, Object>hasEntry("pk3", "c")
            ));
        }};
        
    }

    @Test
    public void testCheckVersion() throws Exception {
        ExclusiveControlUtil.checkVersions(
                Collections.singletonList(new Version(new ExUserMstPk("a", "b", "c"), "987")));

        new Verifications() {{
            List<Version> versions;
            mockExclusiveControlManager.checkVersions(versions = withCapture());
            times = 1;

            assertThat(versions, hasSize(1));
            final Version version = versions.get(0);
            assertThat(version.getVersion(), is("987"));
        }};
    }

    @Test
    public void testUpdateVersionsWithCheck() throws Exception {
        ExclusiveControlUtil.updateVersionsWithCheck(
                Collections.singletonList(new Version(new ExUserMstPk("a", "b", "c"), "987")));
        
        new Verifications() {{
            List<Version> versions;
            mockExclusiveControlManager.updateVersionsWithCheck(versions = withCapture());
            times = 1;

            assertThat(versions, hasSize(1));
            final Version version = versions.get(0);
            assertThat(version.getVersion(), is("987"));
        }};
    }
}
