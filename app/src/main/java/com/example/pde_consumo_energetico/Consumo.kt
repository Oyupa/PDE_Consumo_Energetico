package com.example.pde_consumo_energetico

data class Consumo(val semana: Int, val consumo: Double){
    constructor() : this(0, 0.0)
}
