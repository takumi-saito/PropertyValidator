package t.saito.com.lib.property_validator

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.*
import java.io.OutputStream

class PropertyValidateProcessor(
    private val codeGenerator: CodeGenerator, // コード生成を管理するオブジェクト
    private val logger: KSPLogger // デバッグや情報出力のためのロガー
) : SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {
        logger.info("Starting process method.") // 処理開始ログ
        // `@PropertyValidateModel` アノテーションを持つシンボルを取得
        val symbols = resolver.getSymbolsWithAnnotation(PropertyValidateModel::class.qualifiedName!!)
        logger.info("Found ${symbols.count()} symbols with @PropertyValidateModel annotation.") // 検出したシンボル数をログ出力

        // 検出されたシンボルをクラス宣言にフィルタリングして処理
        symbols.filterIsInstance<KSClassDeclaration>().forEach { processClass(it) }

        logger.info("Completed processing of symbols.") // 処理完了ログ
        return emptyList() // 処理済みシンボルを返す（この場合は何もしない）
    }

    private fun processClass(classDeclaration: KSClassDeclaration) {
        // クラス名とパッケージ名を取得
        val className = classDeclaration.simpleName.asString()
        val packageName = classDeclaration.packageName.asString()
        logger.info("Processing class: $packageName.$className") // 処理中のクラス名をログ

        // 生成するファイル名を決定
        val fileName = "${className}Validator"
        // コード生成用のファイルを作成
        val file = codeGenerator.createNewFile(
            Dependencies(false, classDeclaration.containingFile!!),
            packageName,
            fileName
        )
        logger.info("Created new file for $fileName in package $packageName.") // ファイル生成完了をログ

        // ファイルへの書き込み処理
        file.use { stream ->
            stream.writeText("package $packageName\n\n") // パッケージ宣言
            stream.writeText("object $fileName {\n") // クラス名をオブジェクトとして定義
            stream.writeText("    fun validate(model: $className): List<String> {\n") // validate関数の定義
            stream.writeText("        val errors = mutableListOf<String>()\n") // エラーメッセージを格納するリストを作成

            // クラス内の全プロパティを取得して処理
            classDeclaration.getAllProperties().forEach { property ->
                val propertyName = property.simpleName.asString()
                logger.info("Processing property: $propertyName") // 処理中のプロパティ名をログ

                // プロパティに付与されたアノテーションを処理
                property.annotations.forEach { annotation ->
                    if (annotation.shortName.asString() == "PropertyValidate") {
                        // アノテーションの引数（length と required）を取得
                        val length = annotation.arguments.find { it.name?.asString() == "length" }?.value as? Int
                        val required = annotation.arguments.find { it.name?.asString() == "required" }?.value as? Boolean
                        logger.info("Found @PropertyValidate annotation on $propertyName with length=$length, required=$required")

                        // 必須の場合のエラーチェックコードを生成
                        if (required == true) {
                            stream.writeText("        if (model.$propertyName.isEmpty()) errors.add(\"$propertyName is required.\")\n")
                        }
                        // 長さ制限がある場合のエラーチェックコードを生成
                        if (length != null && length < Int.MAX_VALUE) {
                            stream.writeText("        if (model.$propertyName.length > $length) errors.add(\"$propertyName must be less than $length characters.($propertyName=\${model.$propertyName}, length=\${model.$propertyName.length})\")\n")
                        }
                    }
                }
            }

            stream.writeText("        return errors\n") // エラーメッセージを返すコード
            stream.writeText("    }\n}\n") // オブジェクト定義を終了
        }
        logger.info("Finished writing to $fileName.") // ファイル書き込み完了をログ
    }

    // OutputStream に文字列を書き込む拡張関数
    private fun OutputStream.writeText(text: String) {
        this.write(text.toByteArray()) // 文字列をバイト配列に変換して書き込み
    }
}
