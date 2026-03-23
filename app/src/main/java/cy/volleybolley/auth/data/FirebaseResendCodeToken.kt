package cy.volleybolley.auth.data

import com.google.firebase.auth.PhoneAuthProvider
import cy.volleybolley.auth.phone.domain.ResendCodeToken

class FirebaseResendCodeToken(val token: PhoneAuthProvider.ForceResendingToken) : ResendCodeToken
