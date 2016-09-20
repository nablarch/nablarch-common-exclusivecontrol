package nablarch.common.exclusivecontrol;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import nablarch.core.util.annotation.Published;

/**
 * 排他制御用テーブルのバージョン番号を保持するクラス。
 * @author Kiyohito Itoh
 */
@Published(tag = "architect")
public class Version {
    
    /** 排他制御用テーブルのテーブル名 */
    private final String tableName;
    
    /** バージョン番号カラム名 */
    private final String versionColumnName;
    
    /** バージョン番号 */
    private final String version;
    
    /** 主キーの条件 */
    private final Map<String, Object> primaryKeyCondition;
    
    /**
     * コンストラクタ。
     * @param condition 主キーの条件
     * @param version バージョン番号
     */
    public Version(ExclusiveControlContext condition, String version) {
        this(condition.getTableName(), condition.getVersionColumnName(), version, condition.getCondition());
    }
    
    /**
     * コンストラクタ。
     * @param tableName 排他制御用テーブルのテーブル名
     * @param versionColumnName バージョン番号カラム名
     * @param version バージョン番号
     * @param primaryKeyCondition 主キーの条件
     */
    public Version(String tableName, String versionColumnName, String version, Map<String, Object> primaryKeyCondition) {
        this.tableName = tableName;
        this.versionColumnName = versionColumnName;
        this.version = version;
        this.primaryKeyCondition = Collections.unmodifiableMap(primaryKeyCondition);
    }
    
    /**
     * 排他制御用テーブルのテーブル名を取得する。
     * @return 排他制御用テーブルのテーブル名
     */
    public String getTableName() {
        return tableName;
    }
    
    /**
     * バージョン番号カラム名を取得する。
     * @return バージョン番号カラム名
     */
    public String getVersionColumnName() {
        return versionColumnName;
    }
    
    /**
     * バージョン番号を取得する。
     * @return バージョン番号
     */
    public String getVersion() {
        return version;
    }
    
    /**
     * 主キーの条件を取得する。
     * @return 主キーの条件
     */
    public Map<String, Object> getPrimaryKeyCondition() {
        return new HashMap<String, Object>(primaryKeyCondition);
    }
    
    /** {@inheritDoc} */
    public String toString() {
        return String.format("tableName = [%s], version = [%s], primaryKeyCondition = [%s]",
                              tableName, version, primaryKeyCondition);
    }
}
