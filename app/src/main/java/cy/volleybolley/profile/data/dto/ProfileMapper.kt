package cy.volleybolley.profile.data.dto

import cy.volleybolley.profile.domain.model.Payment
import cy.volleybolley.profile.domain.model.PaymentType
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
        type = PaymentType.findByName(type),
        account = account,
        isPreferred = isPreferred,
    )
}

fun Payment.toDto(): PaymentDto {
    return PaymentDto(
        type = type.nameValue,
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

fun PersonalData.getActualUpdateBody(oldPersonalData: PersonalData?): PersonalDataUpdateBody {
    return oldPersonalData?.let {
        PersonalDataUpdateBody(
            firstName = it.firstName.checkSameStringField(firstName),
            lastName = it.lastName.checkSameStringField(lastName),
            birthDate = it.birthDate.checkSameStringField(birthDate),
            countryId = it.countryId.checkSameIntField(countryId),
            cityId = it.cityId.checkSameIntField(cityId)
        )
    } ?: PersonalDataUpdateBody(
        firstName = firstName,
        lastName = lastName,
        birthDate = birthDate,
        countryId = countryId,
        cityId = cityId
    )
}

private fun String.checkSameStringField(newString: String): String? = if (this == newString) null else newString
private fun Int.checkSameIntField(newInt: Int): Int? = if (this == newInt) null else newInt
