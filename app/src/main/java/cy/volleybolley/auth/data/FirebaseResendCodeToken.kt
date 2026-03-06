package cy.volleybolley.auth.data

import com.google.firebase.auth.PhoneAuthProvider
import cy.volleybolley.phone.domain.ResendCodeToken

class FirebaseResendCodeToken(val token: PhoneAuthProvider.ForceResendingToken) : ResendCodeToken
