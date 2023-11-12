package com.example.definitionsdefectsbuildingstructures.ui.screens.authorization


fun isValidEmail(email: String): Boolean {
    val emailPattern =
        """(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])""".toRegex()
    return emailPattern.matches(email)
}

fun isValidPassword(password: String): Boolean {
    val passwordPattern = """(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{6,}""".toRegex()
    return passwordPattern.matches(password)
}

fun isValidRepeatPassword(password: String, repeatPassword: String): Boolean =
    password == repeatPassword

fun isValidPhoneNum(phoneNum: String): Boolean {
    val phoneNumberPattern = """^8[0-9]{10}$""".toRegex()
    return phoneNumberPattern.matches(phoneNum)
}

fun isValidFullName(name: String): Boolean {
    val pattern = Regex("^[А-Я][а-я]*$")
    return pattern.matches(name)
}

fun isValidProjectOrDrawingName(name: String): Boolean = name.isNotEmpty()