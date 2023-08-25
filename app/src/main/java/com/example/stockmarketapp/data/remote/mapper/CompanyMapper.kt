package com.example.stockmarketapp.data.remote.mapper

import com.example.stockmarketapp.data.local.dbmodel.CompanyListingDbModel
import com.example.stockmarketapp.domain.model.CompanyListing

fun CompanyListingDbModel.toCompanyListing(): CompanyListing{
    return CompanyListing(
        symbol = this.symbol,
        name = this.name,
        exchange = this.exchange
    )
}

fun CompanyListing.toCompanyListingDbModel(): CompanyListingDbModel{
    return CompanyListingDbModel(
        symbol = this.symbol,
        name = this.name,
        exchange = this.exchange
    )
}