package de.lennartegb.analytiks.errors

internal class AlreadyRegisteredService(
    override val message: String = "Only different services can be registered."
) : RegisteringFailed(message)
