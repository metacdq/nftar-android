package com.cindaku.nftar.modules.repository

import com.cindaku.nftar.db.dao.AccountDao
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl constructor(
    private val accountDao: AccountDao,
): UserRepository {

}