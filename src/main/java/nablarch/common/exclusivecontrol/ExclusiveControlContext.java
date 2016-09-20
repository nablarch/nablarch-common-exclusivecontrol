package nablarch.common.exclusivecontrol;

import java.util.HashMap;
import java.util.Map;

import nablarch.core.util.annotation.Published;

/**
 * 排他制御の実行に必要な情報を保持するクラス。
 * <p/>
 * 排他制御用テーブルのスキーマ情報と排他制御対象のデータを指定する主キー条件を保持する。
 * 
 * @author Kiyohito Itoh
 */
@Published
public class ExclusiveControlContext {
    
    /** 排他制御用テーブルのテーブル名 */
    private String tableName;
    
    /** バージョン番号カラム名 */
    private String versionColumnName;
    
    /** 主キーのカラム名 */
    private Enum<?>[] primaryKeyColumnNames;
    
    /** 排他制御対象の行データを指定する条件 */
    private Map<String, Object> condition = new HashMap<String, Object>();

    /**
     * 排他制御用テーブルのテーブル名を取得する。
     * @return 排他制御用テーブルのテーブル名
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * 排他制御用テーブルのテーブル名を設定する。
     * @param tableName 排他制御用テーブルのテーブル名
     */
    protected void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * バージョン番号カラム名を取得する。
     * @return バージョン番号カラム名
     */
    public String getVersionColumnName() {
        return versionColumnName;
    }

    /**
     * バージョン番号カラム名を設定する。
     * @param versionColumnName バージョン番号カラム名
     */
    protected void setVersionColumnName(String versionColumnName) {
        this.versionColumnName = versionColumnName;
    }
    
    /**
     * 主キーのカラム名を取得する。
     * @return 主キーのカラム名
     */
    public Enum<?>[] getPrimaryKeyColumnNames() {
        return primaryKeyColumnNames;
    }

    /**
     * 主キーのカラム名を設定する。
     * @param primaryKeyColumnNames 主キーのカラム名
     */
    protected void setPrimaryKeyColumnNames(Enum<?>... primaryKeyColumnNames) {
        this.primaryKeyColumnNames = primaryKeyColumnNames;
    }

    /**
     * 排他制御対象の行データを指定する条件を取得する。
     * @return 排他制御対象の行データを指定する条件
     */
    public Map<String, Object> getCondition() {
        return condition;
    }
    
    /**
     * 排他制御対象の行データを指定する条件を追加する。
     * @param columnName 主キーのカラム名
     * @param value 検索する値
     * @return 本オブジェクト
     */
    public ExclusiveControlContext appendCondition(Enum<?> columnName, Object value) {
        condition.put(ExclusiveControlUtil.convertToVariableName(columnName), value);
        return this;
    }
}
