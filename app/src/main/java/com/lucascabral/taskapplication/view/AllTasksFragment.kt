package com.lucascabral.taskapplication.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lucascabral.taskapplication.service.listener.TaskListener
import com.lucascabral.taskapplication.R
import com.lucascabral.taskapplication.service.constants.TaskConstants
import com.lucascabral.taskapplication.view.adapter.TaskAdapter
import com.lucascabral.taskapplication.viewmodel.AllTasksViewModel

class AllTasksFragment : Fragment() {

    private lateinit var mViewModel: AllTasksViewModel
    private lateinit var mListener: TaskListener
    private val mAdapter = TaskAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View {
        mViewModel = ViewModelProvider(this).get(AllTasksViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_all_tasks, container, false)

        val recycler = root.findViewById<RecyclerView>(R.id.recycler_all_tasks)
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = mAdapter

        // Eventos disparados ao clicar nas linhas da RecyclerView
        mListener = object : TaskListener {
            override fun onListClick(id: Int) {
                val intent = Intent(context, TaskFormActivity::class.java)
                val bundle = Bundle()
                bundle.putInt(TaskConstants.BUNDLE.TASKID, id)
                intent.putExtras(bundle)
                startActivity(intent)
            }

            override fun onDeleteClick(id: Int) {
            }

            override fun onCompleteClick(id: Int) {
            }

            override fun onUndoClick(id: Int) {
            }
        }

        // Cria os observadores
        observe()

        // Retorna view
        return root
    }

    override fun onResume() {
        super.onResume()
        mAdapter.attachListener(mListener)
    }

    private fun observe() {}

}
