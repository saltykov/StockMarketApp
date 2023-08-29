package com.example.stockmarketapp.data.remote.mapper

import com.example.stockmarketapp.data.local.dbmodel.CompanyDbModel
import com.example.stockmarketapp.data.remote.dto.CompanyInfoDto
import com.example.stockmarketapp.domain.model.Company
import com.example.stockmarketapp.domain.model.CompanyInfo

fun CompanyDbModel.toCompany(): Company {
    return Company(
        symbol = this.symbol,
        name = this.name,
        exchange = this.exchange
    )
}

fun Company.toCompanyDbModel(): CompanyDbModel {
    return CompanyDbModel(
        symbol = this.symbol,
        name = this.name,
        exchange = this.exchange
    )
}

fun CompanyInfoDto.toCompanyInfo(): CompanyInfo {
    return CompanyInfo(
        //elvis для случаев, когда количество обращений по бесплатному ключу API заканчиваются, и поля не находятся.
        name = this.name ?: "",
        description = this.description ?: "",
        symbol = this.symbol ?: "",
        country = this.country ?: "",
        industry = this.industry ?: ""
    )
}