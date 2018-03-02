package nablarch.common.exclusivecontrol;

import java.util.List;

import nablarch.core.repository.SystemRepository;
import nablarch.core.util.annotation.Published;

/**
 * 排他制御機能のユーティリティクラス。
 * <p/>
 * 排他制御用テーブルの操作は、{@link ExclusiveControlManager}に委譲する。
 * 本クラスで使用する{@link ExclusiveControlManager}のオブジェクトは
 * {@link SystemRepository}から"exclusiveControlManager"という名前で取得する。
 * 
 * {@link nablarch.common.dao.UniversalDao UniversalDao}を使用する場合には、
 * このクラスではなく{@link nablarch.common.dao.UniversalDao UniversalDao}を使用して排他制御を行うこと。
 *
 * @see ExclusiveControlManager
 * @author Kiyohito Itoh
 */
public final class ExclusiveControlUtil {
    
    /** ExclusiveControlManagerのコンポーネント名 */
    private static final String EXCLUSIVE_CONTROL_MANAGER_NAME = "exclusiveControlManager";
    
    /** 隠蔽コンストラクタ。 */
    private ExclusiveControlUtil() {
    }
    
    /**
     * ExclusiveControlManagerを取得する。
     * @return ExclusiveControlManager
     */
    private static ExclusiveControlManager getExclusiveControlManager() {
        return SystemRepository.get(EXCLUSIVE_CONTROL_MANAGER_NAME);
    }
    
    /**
     * バージョン番号を取得する。(楽観的ロック)
     * @param context {@link ExclusiveControlContext}
     * @return バージョン番号。バージョン番号が存在しない場合は{@code null}
     */
    @Published(tag = "architect")
    public static Version getVersion(ExclusiveControlContext context) {
        return getExclusiveControlManager().getVersion(context);
    }
    
    /**
     * バージョン番号が更新されていないかチェックする。(楽観的ロック)
     * @param versions バージョン番号
     * @throws OptimisticLockException バージョン番号が更新されていた場合
     */
    @Published(tag = "architect")
    public static void checkVersions(List<Version> versions) throws OptimisticLockException {
        getExclusiveControlManager().checkVersions(versions);
    }
    
    /**
     * バージョン番号の更新チェックとバージョン番号の更新を行う。(楽観的ロック)
     * @param versions バージョン番号
     * @throws OptimisticLockException バージョン番号が更新されていた場合
     */
    @Published(tag = "architect")
    public static void updateVersionsWithCheck(List<Version> versions) throws OptimisticLockException  {
        getExclusiveControlManager().updateVersionsWithCheck(versions);
    }
    
    /**
     * バージョン番号を更新する。(悲観的ロック)
     * @param context {@link ExclusiveControlContext}
     */
    @Published
    public static void updateVersion(ExclusiveControlContext context) {
        getExclusiveControlManager().updateVersion(context);
    }
    
    /**
     * バージョン番号を追加する。
     * @param context {@link ExclusiveControlContext}
     */
    @Published
    public static void addVersion(ExclusiveControlContext context) {
        getExclusiveControlManager().addVersion(context);
    }

    /**
     * バージョン番号を削除する。
     * @param context {@link ExclusiveControlContext}
     */
    @Published
    public static void removeVersion(ExclusiveControlContext context) {
        getExclusiveControlManager().removeVersion(context);
    }
    
    /**
     * カラム名を名前付き変数名(先頭コロンを除く)に変換する。
     * @param columnName カラム名
     * @return 名前付き変数名(先頭コロンを除く)
     */
    public static String convertToVariableName(Enum<?> columnName) {
        return convertToVariableName(columnName.name());
    }
    
    /**
     * カラム名を名前付き変数名(先頭コロンを除く)に変換する。
     * @param columnName カラム名
     * @return 名前付き変数名(先頭コロンを除く)
     */
    public static String convertToVariableName(String columnName) {
        return columnName.toLowerCase();
    }
}
