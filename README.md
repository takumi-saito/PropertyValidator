# PropertyValidator

`PropertyValidator` は、Kotlin Symbol Processing (KSP) を使用してデータモデルに付与されたアノテーションを基に、バリデーションコードを自動生成するライブラリです。
このライブラリを使用することで、手動でバリデーションロジックを記述する手間を削減し、コードの可読性と保守性を向上させます。

- **自動コード生成**: データモデルにアノテーションを付与するだけで、バリデーションコードが生成されます。
- **軽量**: KSP に基づいているため、ビルドパフォーマンスに優れています。

## [サンプルアプリ](https://github.com/takumi-saito/property-validator/tree/main/app)
<img src="https://github.com/user-attachments/assets/86e17cd5-12f8-46d4-b685-29366c353e53" width="40%">

---

## インストール

### 1. プロジェクトにリポジトリを追加

プロジェクトの `build.gradle.kts` に以下を追加します：

```kotlin
repositories {
    maven {
        url = uri("https://maven.pkg.github.com/<GitHubユーザ名>/<リポジトリ名>")
        credentials {
            username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_ACTOR")
            password = project.findProperty("gpr.token") as String? ?: System.getenv("GITHUB_TOKEN")
        }
    }
}
```

---

### 2. 依存関係を追加

`build.gradle.kts` に以下を追加してください：

```kotlin
plugins {
    id("com.google.devtools.ksp") version "x.x.x" // 適切なKSPバージョンを指定
}

dependencies {
    implementation("t.saito.com.lib.property-validator:1.0.0")
    ksp("t.saito.com.lib.property-validator:1.0.0")
}
```

---

### 3. ローカルでの認証情報管理（推奨）

`local.properties` を使用して GitHub Packages の認証情報を管理する方法を推奨します。

1. プロジェクトルートにある `local.properties` に以下を追加してください：

```properties
gpr.user=<GitHubユーザ名>
gpr.token=<GitHub Personal Access Token>
```

2. `build.gradle.kts` 内で認証情報を参照します：

```kotlin
val localProperties = Properties().apply {
    val file = rootProject.file("local.properties")
    if (file.exists()) {
        load(file.inputStream())
    }
}

repositories {
    maven {
        url = uri("https://maven.pkg.github.com/<GitHubユーザ名>/<リポジトリ名>")
        credentials {
            username = localProperties.getProperty("gpr.user")
            password = localProperties.getProperty("gpr.token")
        }
    }
}
```

---

## 使い方

### 1. データモデルにアノテーションを付与する

以下のように、`@MetadataModel` をクラスに、`@Metadata` を各プロパティに付与します。

```kotlin
@MetadataModel
data class User(
    @Metadata(length = 10, required = true) // 名前は必須で最大10文字
    val name: String = "",
    @Metadata(length = 5) // ニックネームは最大5文字
    val nickname: String = ""
)
```

---

### 2. 自動生成されたバリデーションコードを使用

モデルを検証する関数が自動生成されます。以下はその利用例です：

```kotlin
val user = User(name = "LongUserName", nickname = "Nick123")
val errors = UserValidator.validate(user)

if (errors.isNotEmpty()) {
    errors.forEach { println(it) } // エラーメッセージを出力
} else {
    println("Validation passed!")
}
```

---

### 3. Compose UI でのリアルタイム検証

以下は、ユーザー入力フォームでリアルタイムにバリデーションを行う例です：

```kotlin
@Composable
fun UserInputScreen() {
    var name by remember { mutableStateOf("") }
    var nickname by remember { mutableStateOf("") }
    var errors by remember { mutableStateOf(emptyList<String>()) }

    LaunchedEffect(name, nickname) {
        val user = User(name = name, nickname = nickname)
        errors = UserValidator.validate(user)
    }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("名前") }
        )
        TextField(
            value = nickname,
            onValueChange = { nickname = it },
            label = { Text("ニックネーム") }
        )
        if (errors.isNotEmpty()) {
            Text("エラー:", color = Color.Red)
            errors.forEach { error ->
                Text(error, color = Color.Red)
            }
        }
    }
}
```

---

## アノテーションオプション

### `@Metadata`
| パラメータ  | 説明                              | デフォルト値        |
|-------------|-----------------------------------|---------------------|
| `length`    | 最大文字数を指定します            | `Int.MAX_VALUE`     |
| `required`  | 必須フィールドかどうかを指定します | `false`             |

---

## 注意事項

- **KSPの設定が正しく行われていることを確認してください**。
- **`local.properties` の `gpr.user` と `gpr.token` が正しく設定されている必要があります**。

---

## ライセンス

このプロジェクトは [MIT License](https://github.com/takumi-saito/property-validator/blob/main/property_validator/LICENSE) のもとで公開されています。

