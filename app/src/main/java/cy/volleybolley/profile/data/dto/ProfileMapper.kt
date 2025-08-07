package cy.volleybolley.profile.data.dto

import cy.volleybolley.profile.domain.model.Payment
import cy.volleybolley.profile.domain.model.PersonalData

fun List<PaymentDto>.toDomain(): List<Payment> = this.map { it.toDomain() }

fun List<Payment>.toUpdateBody(): PaymentsUpdateBodyDto {
    return PaymentsUpdateBodyDto(
        payments = this.toDto()
    )
}

fun List<Payment>.toDto(): List<PaymentDto> = this.map { it.toDto() }

fun PaymentDto.toDomain(): Payment {
    return Payment(
        type = type,
        account = account,
        isPreferred = isPreferred,
    )
}

fun Payment.toDto(): PaymentDto {
    return PaymentDto(
        type = type,
        account = account,
        isPreferred = isPreferred
    )
}

fun PersonalDataDto.toDomain(): PersonalData {
    return PersonalData(
        firstName = firstName,
        lastName = lastName,
        gender = gender,
        birthDate = birthDate,
        level = level,
        countryId = countryId,
        cityId = cityId,
        avatar = avatar,
    )
}

fun PersonalData.toUpdateBody(): PersonalDataUpdateBody {
    return PersonalDataUpdateBody(
        firstName = checkStringDataField(firstName),
        lastName = checkStringDataField(lastName),
        gender = checkStringDataField(gender),
        birthDate = checkStringDataField(birthDate),
        level = checkStringDataField(level),
        countryId = checkIntDataField(countryId),
        cityId = checkIntDataField(cityId)
    )
}

private fun checkStringDataField(field: String): String? = if (field.isEmpty()) null else field
private fun checkIntDataField(field: Int): Int? = if (field == -1) null else field
