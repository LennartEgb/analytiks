package de.lennartegb.analytiks.errors

class AlreadyRegisteredService(
    message: String? = null,
    throwable: Throwable? = null,
) : IllegalArgumentException(message, throwable) {
    override val message: String
        get() = listOfNotNull(
            super.message,
            "Only different services can be registered."
        ).joinToString(" ")
}
