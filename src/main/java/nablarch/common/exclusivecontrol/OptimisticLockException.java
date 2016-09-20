package nablarch.common.exclusivecontrol;

import java.util.ArrayList;
import java.util.List;

import nablarch.core.message.ApplicationException;
import nablarch.core.message.Message;
import nablarch.core.util.annotation.Published;

/**
 * 楽観的ロックでバージョン番号が更新されている場合に発生する例外。
 * @author Kiyohito Itoh
 */
@Published
public class OptimisticLockException extends ApplicationException {
    
    /** 楽観的ロックエラーが発生したバージョン情報 */
    private List<Version> errorVersions = new ArrayList<Version>();
    
    /**
     * インスタンスを生成する。
     *
     * @param errorVersions 楽観的ロックエラーが発生したバージョン情報のリスト
     * @param message 結果メッセージ
     */
    public OptimisticLockException(List<Version> errorVersions, Message message) {
        this.errorVersions = errorVersions;
        if (message != null) {
            addMessages(message);
        }
    }
    
    /**
     * 楽観的ロックエラーが発生したバージョン番号を取得する。
     * @return 楽観的ロックエラーが発生したバージョン番号
     */
    public List<Version> getErrorVersions() {
        return errorVersions;
    }
}
