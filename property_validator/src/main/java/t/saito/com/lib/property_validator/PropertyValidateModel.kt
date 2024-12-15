package t.saito.com.lib.property_validator

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
annotation class PropertyValidate(
    val length: Int = Int.MAX_VALUE,
    val required: Boolean = false
)

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class PropertyValidateModel
