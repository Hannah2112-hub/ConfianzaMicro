package com.example.confianzamicro.auth

import com.example.confianzamicro.domain.AdvisorEntity

object SessionManager {

    private var advisor: AdvisorEntity? = null

    fun login(a: AdvisorEntity) {
        advisor = a
    }

    fun current(): AdvisorEntity? = advisor

    fun isLogged(): Boolean = advisor != null

    fun logout() {
        advisor = null
    }
}
