package com.project.tailor.utils

import java.lang.Exception

class ApiException(message: String, val code: Int) : Exception(message)

class NetworkException(message: String, val code: Int) : Exception(message)