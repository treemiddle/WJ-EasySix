package com.example.sample.utils.ext

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit

fun AppCompatActivity.addFragment(@IdRes containerId: Int, fragment: Fragment, backStack: Boolean) {
    supportFragmentManager.apply {
        fragmentFactory.instantiate(classLoader, fragment::class.java.simpleName)
        popBackStack(fragment.javaClass.simpleName, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    supportFragmentManager.commit {
        if (backStack) {
            replace(containerId, fragment, fragment.javaClass.simpleName)
                .addToBackStack(fragment.javaClass.simpleName)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .isAddToBackStackAllowed
        } else {
            replace(containerId, fragment)
        }
    }
}