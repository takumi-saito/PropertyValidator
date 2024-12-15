package t.saito.com.lib.property_validator

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider

class PropertyValidateProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return PropertyValidateProcessor(
            codeGenerator = environment.codeGenerator,
            logger = environment.logger
        )
    }
}
