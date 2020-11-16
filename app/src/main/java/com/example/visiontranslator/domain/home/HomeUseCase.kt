package com.example.visiontranslator.domain.home

import com.example.visiontranslator.infra.model.translation.Translation

/**
 * ホーム画面(翻訳済みのデータをリスト表示する画面)で使用するmodel, api操作のメソッド定義
 */
interface HomeUseCase {
    /**
     * 一覧表示するためにTranslationModelのデータをすべてリストとして取得
     */
    suspend fun getAllTranslations(): List<Translation>

    /**
     * 一覧からキーワード検索を行い結果をリストとして取得
     */
    suspend fun findTranslationByQueryWord(queryWord:String):List<Translation>
}