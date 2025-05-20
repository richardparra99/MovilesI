package com.example.juegotetris.Observer

interface Observable {
    fun addObserver(observer: Observer)
    fun notifyObservers()
}