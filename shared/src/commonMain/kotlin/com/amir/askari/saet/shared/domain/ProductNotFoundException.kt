package com.amir.askari.saet.shared.domain

class ProductNotFoundException(val id: Long) : Exception("Product $id not found")
