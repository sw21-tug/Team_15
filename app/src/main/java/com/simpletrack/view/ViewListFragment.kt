package com.simpletrack.view

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.simpletrack.MainActivity
import com.simpletrack.R
import com.simpletrack.model.ViewListViewModel

class ViewListFragment : Fragment() {

    companion object {
        fun newInstance() = ViewListFragment()
    }

    private lateinit var viewModel: ViewListViewModel

    private lateinit var listView: ListView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.view_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listView = view.findViewById(R.id.listView)

        val listAdapter = ListAdapter(this.activity as Activity, MainActivity.taskList)
        listView.adapter = listAdapter

        listView.setOnItemClickListener { parent, view, position, id ->
            val builder: AlertDialog.Builder = android.app.AlertDialog.Builder(this.context)
            builder.setTitle(R.string.Title)

            val input = EditText(this.context)
            input.setHint(R.string.EnterText)
            input.inputType = InputType.TYPE_CLASS_TEXT
            input.setText(MainActivity.taskList[position].name)
            input.setSelectAllOnFocus(true)
            builder.setView(input)

            var m_Text: String = ""
            builder.setPositiveButton(
                R.string.OK,
                DialogInterface.OnClickListener { dialog, which ->
                    m_Text = input.text.toString()
                    MainActivity.taskList[position].setNewName(m_Text)
                    MainActivity.storage.storeData(MainActivity.taskList)
                }
            )
            builder.setNegativeButton(R.string.Cancel, DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
            builder.show()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ViewListViewModel::class.java)
        // TODO: Use the ViewModel
    }
}
