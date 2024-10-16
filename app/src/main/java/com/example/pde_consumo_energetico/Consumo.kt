package com.example.pde_consumo_energetico

data class Consumo(val semana: Int, val consumo: Int){
    constructor() : this(0, 0)
}
