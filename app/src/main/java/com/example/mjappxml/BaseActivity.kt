package com.example.mjappxml

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<B : ViewDataBinding>(
    @LayoutRes private val layoutId : Int
) : AppCompatActivity() {

    protected lateinit var binding: B
//    protected lateinit var dialog : LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)

        with(binding) {
            lifecycleOwner = this@BaseActivity
        }
    }

    open fun onBackClick(view: View){
        finish()
    }

    protected fun showDialog() {
//        dialog = LoadingDialog().also {
//            it.show(supportFragmentManager, null)
//        }
    }

    protected fun dismissDialog(){
//        if (this::dialog.isInitialized) {
//            dialog.dismissDialog()
//        }
    }

}