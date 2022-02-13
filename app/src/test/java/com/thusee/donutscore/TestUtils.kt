package com.thusee.donutscore

import java.lang.reflect.Field
import java.lang.reflect.Modifier

object TestUtils {


    fun setProperty(instance: Any, name: String, param: Any?) {

        val field = instance.javaClass.getDeclaredField(name)
        field.isAccessible = true
        field.set(instance, param)
    }

    fun<T> getProperty(instance: Any, name: String): T {

        val field = instance.javaClass.getDeclaredField(name)
        field.isAccessible = true
        return field.get(instance) as T
    }

    fun invokeMethod(instance: Any, methodName: String, vararg arguments: Any): Any? {

        val clazz = arrayOfNulls<Class<*>>(arguments.size)
        arguments.forEachIndexed { index, it ->
            when (it) {
                is Integer -> clazz[index] = Int::class.java
                else -> clazz[index] = it::class.java
            }
        }

        val method = instance.javaClass.getDeclaredMethod(methodName, *clazz)
        method.isAccessible = true
        return method.invoke(instance, *arguments)
    }

    fun invokeMethod(instance: Any, methodName: String, classes: List<Class<*>>, args: List<Any?>): Any? {
        val clazz = classes.toTypedArray()
        val arguments = args.toTypedArray()

        val method = instance.javaClass.getDeclaredMethod(methodName, *clazz)
        method.isAccessible = true
        return method.invoke(instance, *arguments)
    }



    private fun makeFieldVeryAccessible(field: Field) {
        field.isAccessible = true

        try {
            val modifiersField = Field::class.java.getDeclaredField("modifiers")
            modifiersField.isAccessible = true
            try {
                modifiersField.setInt(field, field.modifiers and Modifier.FINAL.inv())
            } catch (e: IllegalAccessException) {
                throw RuntimeException(e)
            }

        } catch (e: NoSuchFieldException) {
            // ignore missing fields
        }

    }
}

