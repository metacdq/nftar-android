package com.knear.android.provider.response.genesisconfig

data class DeployContractCost(
    val execution: Long,
    val send_not_sir: Long,
    val send_sir: Long
)
