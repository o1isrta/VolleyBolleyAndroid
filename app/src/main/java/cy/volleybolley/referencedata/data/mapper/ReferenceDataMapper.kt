package cy.volleybolley.referencedata.data.mapper

import cy.volleybolley.referencedata.data.dto.CityDto
import cy.volleybolley.referencedata.data.dto.CountryDto
import cy.volleybolley.referencedata.data.dto.CurrencyDto
import cy.volleybolley.referencedata.data.dto.FaqDto
import cy.volleybolley.referencedata.data.localdto.CityLocalDto
import cy.volleybolley.referencedata.data.localdto.CountryLocalDto
import cy.volleybolley.referencedata.data.localdto.CurrencyLocalDto
import cy.volleybolley.referencedata.data.localdto.FaqLocalDto
import cy.volleybolley.referencedata.domain.model.City
import cy.volleybolley.referencedata.domain.model.Country
import cy.volleybolley.referencedata.domain.model.Currency
import cy.volleybolley.referencedata.domain.model.Faq

// Remote Dto mappers

fun CityDto.mapToDomain(): City {
    return City(
        id = id,
        name = name
    )
}

fun CountryDto.mapToDomain(): Country {
    return Country(
        id = id,
        name = name,
        cities = cities?.map { it.mapToDomain() }
    )
}

fun List<CountryDto>.mapToDomain(): List<Country> {
    return this.map { it.mapToDomain() }
}

fun CurrencyDto.mapToDomain(): Currency {
    return Currency(
        id = id,
        type = type,
        name = name,
        country = country.mapToDomain()
    )
}

fun List<CurrencyDto>.mapToDomain(): List<Currency> {
    return this.map { it.mapToDomain() }
}

fun FaqDto.mapToDomain(): Faq {
    return Faq(
        faq = faq
    )
}

// Local Dto mappers

fun CityLocalDto.mapToDomain(): City {
    return City(
        id = id,
        name = name
    )
}

fun City.mapToLocalDto(): CityLocalDto {
    return CityLocalDto(
        id = id,
        name = name
    )
}

fun CountryLocalDto.mapToDomain(): Country {
    return Country(
        id = id,
        name = name,
        cities = cities?.map { it.mapToDomain() }
    )
}

fun Country.mapToLocalDto(): CountryLocalDto {
    return CountryLocalDto(
        id = id,
        name = name,
        cities = cities?.map { it.mapToLocalDto() }
    )
}

fun List<CountryLocalDto>.mapToDomain(): List<Country> {
    return this.map { it.mapToDomain() }
}

fun List<Country>.mapToLocalDto(): List<CountryLocalDto> {
    return this.map { it.mapToLocalDto() }
}

fun CurrencyLocalDto.mapToDomain(): Currency {
    return Currency(
        id = id,
        type = type,
        name = name,
        country = country.mapToDomain()
    )
}

fun Currency.mapToLocalDto(): CurrencyLocalDto {
    return CurrencyLocalDto(
        id = id,
        type = type,
        name = name,
        country = country.mapToLocalDto()
    )
}

fun List<CurrencyLocalDto>.mapToDomain(): List<Currency> {
    return this.map { it.mapToDomain() }
}

fun List<Currency>.mapToLocalDto(): List<CurrencyLocalDto> {
    return this.map { it.mapToLocalDto() }
}

fun FaqLocalDto.mapToDomain(): Faq {
    return Faq(
        faq = faq
    )
}

fun Faq.mapToLocalDto(): FaqLocalDto {
    return FaqLocalDto(
        faq = faq
    )
}
