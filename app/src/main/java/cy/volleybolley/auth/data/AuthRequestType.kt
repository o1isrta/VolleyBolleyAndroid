package cy.volleybolley.auth.data

sealed class AuthRequestType {
    data class Google(val request: GoogleAuthRequest) : AuthRequestType()
    //data class Phone(val request: PhoneAuthRequest) : AuthRequestType()
}
