package t.saito.com.propertyvalidatorusageexample

import androidx.compose.foundation.layout.*
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun UserInputScreen() {
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var nickname by remember { mutableStateOf(TextFieldValue("")) }
    var errors by remember { mutableStateOf(emptyList<String>()) }

    // 入力ごとにエラーを検証
    LaunchedEffect(name, nickname) {
        val user = User(name = name.text, nickname = nickname.text)
        errors = UserValidator.validate(user) // UserValidatorでチェック
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "ユーザー入力",
            fontSize = 20.sp,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(text = "必須", color = Color.Red, fontSize = 14.sp)
        // ユーザー名入力
        Text(text = "名前 (${Constants.MAX_NAME_LENGTH}文字まで)", color = Color.Black, fontSize = 16.sp)
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        // ニックネーム入力
        Text(text = "ニックネーム（${Constants.MAX_NICKNAME_LENGTH}文字まで）", color = Color.Black, fontSize = 16.sp)
        OutlinedTextField(
            value = nickname,
            onValueChange = { nickname = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        // エラー表示
        if (errors.isNotEmpty()) {
            Text(
                text = "エラー:",
                fontSize = 16.sp,
                color = Color.Red,
                modifier = Modifier.padding(top = 16.dp)
            )
            errors.forEach { error ->
                Text(
                    text = "- $error",
                    fontSize = 14.sp,
                    color = Color.Red
                )
            }
        }
    }
}
