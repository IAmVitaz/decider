package com.vitaz.decider

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView.OnItemLongClickListener
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.vitaz.decider.Controller.App
import com.vitaz.decider.Utilities.SharedPrefs
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

//    val list = arrayListOf("Watch TV", "Save the world!", "Make a photo of Spider Man")
    val list: ArrayList<String> = App.sharedPreferences.getList()
    var prevItemDisplayed: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
        listView.adapter = adapter

        diceLable.setOnClickListener {

            deciderResult.text = ". . ."

            val handler = Handler()
            handler.postDelayed(Runnable {

                val random = Random()
                var randomItem = random.nextInt(list.count())

                // prevent from getting same result twice in a row
                while (randomItem == prevItemDisplayed) {
                    randomItem = random.nextInt(list.count())
                }
                prevItemDisplayed = randomItem

                deciderResult.text = list[randomItem]

            }, 500)

        }

        addNewItemButton.setOnClickListener {
            if (addNewItemEditText.text.toString() != "") {
                val newItem = addNewItemEditText.text.toString()
                list.add(newItem)
                App.sharedPreferences.saveList(list)
                addNewItemEditText.text.clear()
                Log.d("TAG", "This is a test log")

                adapter.notifyDataSetChanged()

                //hide soft keyboard
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            }
        }

        viewList.setOnClickListener {
            if (listViewLayout.visibility == View.INVISIBLE) {
                listViewLayout.visibility = View.VISIBLE
                viewList.text = "Hide list"
            }
            else {
                listViewLayout.visibility = View.INVISIBLE
                viewList.text = "View list"
            }
        }


        listView.onItemLongClickListener =
            OnItemLongClickListener { parent, view, position, id ->
                AlertDialog.Builder(this@MainActivity)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Are you sure?")
                    .setMessage("Do you want to delete this item?")
                    .setPositiveButton(
                        "Yes"
                    ) { dialog, which ->
                        list.removeAt(position)
                        App.sharedPreferences.saveList(list)
                        adapter.notifyDataSetChanged()
                    }
                    .setNegativeButton("No", null)
                    .show()
                true
            }

    }
}
