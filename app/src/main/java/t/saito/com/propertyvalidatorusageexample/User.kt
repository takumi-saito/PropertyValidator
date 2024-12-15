package t.saito.com.propertyvalidatorusageexample

import t.saito.com.lib.property_validator.PropertyValidateModel
import t.saito.com.lib.property_validator.PropertyValidate

@PropertyValidateModel
data class User(
    @PropertyValidate(length = Constants.MAX_NAME_LENGTH, required = true)
    val name: String,
    @PropertyValidate(length = Constants.MAX_NICKNAME_LENGTH)
    val nickname: String
)
