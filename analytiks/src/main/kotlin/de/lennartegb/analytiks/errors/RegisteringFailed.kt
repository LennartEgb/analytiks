package de.lennartegb.analytiks.errors

internal open class RegisteringFailed(message: String? = null) : Throwable(message) {
    override val message: String
        get() = listOfNotNull(super.message, "Failure while trying to register.").joinToString(" ")
}