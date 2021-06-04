package com.example.library.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.library.R
import com.example.library.fragments.EditBooksFragment
import com.example.library.fragments.SpecificBookFragment
import com.example.library.interfaces.FragmentCommunication
import com.example.library.models.BookElement

class MainActivity : AppCompatActivity(),FragmentCommunication {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addEditBooksFragment()
    }

    private fun addEditBooksFragment(){
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        val tag = EditBooksFragment::class.java.name
        val addTransaction = transaction.add(
            R.id.frame_layout, EditBooksFragment.newInstance("",""), tag
        )
        addTransaction.commit()
    }

    override fun openSpecificBookFragment(bookElement: BookElement?) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        val tag = SpecificBookFragment::class.java.name
        val replaceTransaction = transaction.replace(
            R.id.frame_layout, SpecificBookFragment.newInstance("","",bookElement), tag
        )
        replaceTransaction.addToBackStack(tag)
        replaceTransaction.commit()
    }

}