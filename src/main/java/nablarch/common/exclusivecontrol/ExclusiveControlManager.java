package nablarch.common.exclusivecontrol;

import java.util.List;

import nablarch.core.util.annotation.Published;

/**
 * 排他制御(悲観的ロック、楽観的ロック)を管理するインタフェース。
 * <p/>
 * 排他制御用テーブルを使用した排他制御(悲観的ロック、楽観的ロック)を行うための機能、
 * 及び排他制御用テーブルに対する行データの追加と削除を行う機能を提供する。
 * <p/>
 * 排他制御用テーブルは、排他制御の対象に含めるテーブルの範囲を決めた上で、
 * 親となるテーブルを決定し排他制御用テーブルとする。
 * 排他制御用テーブルには、バージョン番号をカラムを定義する。
 * <p/>
 * 悲観的ロックは、{@link #updateVersion(ExclusiveControlContext)}メソッドにより、
 * 処理の開始時にバージョン番号を更新することでロックを実現する。
 * トランザクションがコミット又はロールバックされるまで、ロックが維持される。
 * <p/>
 * 楽観的ロックは、{@link #getVersion(ExclusiveControlContext)}メソッドでバージョン番号を取得しておき、
 * {@link #checkVersions(List)}、{@link #updateVersionsWithCheck(List)}メソッドにより、
 * 取得したバージョン番号が更新されていないかをチェックすることで実現する。
 * 
 * @author Kiyohito Itoh
 */
@Published(tag = "architect")
public interface ExclusiveControlManager {
    
    /**
     * バージョン番号を取得する。(楽観的ロック)
     * @param context 排他制御コンテキスト
     * @return バージョン番号。バージョン番号が存在しない場合はnull
     */
    Version getVersion(ExclusiveControlContext context);
    
    /**
     * バージョン番号が更新されていないかチェックする。(楽観的ロック)
     * @param versions バージョン番号
     * @throws OptimisticLockException バージョン番号が更新されていた場合
     */
    void checkVersions(List<Version> versions) throws OptimisticLockException;
    
    /**
     * バージョン番号の更新チェックとバージョン番号の更新を行う。(楽観的ロック)
     * @param versions バージョン番号
     * @throws OptimisticLockException バージョン番号が更新されていた場合
     */
    void updateVersionsWithCheck(List<Version> versions) throws OptimisticLockException;
    
    /**
     * バージョン番号を更新する。(悲観的ロック)
     * @param context 排他制御コンテキスト
     */
    void updateVersion(ExclusiveControlContext context);
    
    /**
     * バージョン番号を追加する。
     * @param context 排他制御コンテキスト
     */
    void addVersion(ExclusiveControlContext context);

    /**
     * バージョン番号を削除する。
     * @param context 排他制御コンテキスト
     */
    void removeVersion(ExclusiveControlContext context);
}
