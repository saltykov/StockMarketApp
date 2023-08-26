package com.example.stockmarketapp.data.remote.mapper

import com.example.stockmarketapp.data.local.dbmodel.CompanyDbModel
import com.example.stockmarketapp.domain.model.Company

fun CompanyDbModel.toCompany(): Company{
    return Company(
        symbol = this.symbol,
        name = this.name,
        exchange = this.exchange
    )
}

fun Company.toCompanyDbModel(): CompanyDbModel{
    return CompanyDbModel(
        symbol = this.symbol,
        name = this.name,
        exchange = this.exchange
    )
}