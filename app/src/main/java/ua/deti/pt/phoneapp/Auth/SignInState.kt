package ua.deti.pt.phoneapp.Auth

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)