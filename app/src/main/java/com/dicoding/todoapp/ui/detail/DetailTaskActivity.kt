package com.dicoding.todoapp.ui.detail

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.todoapp.R
import com.dicoding.todoapp.data.Task
import com.dicoding.todoapp.ui.ViewModelFactory
import com.dicoding.todoapp.utils.DateConverter
import com.dicoding.todoapp.utils.TASK_ID
import com.google.android.material.textfield.TextInputEditText

class DetailTaskActivity : AppCompatActivity() {
    private var taskID: Int = 0
    private lateinit var detailTaskViewModel: DetailTaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        //TODO 11 : Show detail task and implement delete action
        val factory = ViewModelFactory.getInstance(this)
        detailTaskViewModel = ViewModelProvider(this, factory).get(DetailTaskViewModel::class.java)

        detailTaskViewModel.task.observe(this) {
            if (it != null) setData(it) else finish()
        }
        taskID = intent.getIntExtra(TASK_ID, 0)
        detailTaskViewModel.setTaskId(taskID)


        val btDelete: Button = findViewById(R.id.btn_delete_task)
        btDelete.setOnClickListener {
            showConfirmDialog()
        }

    }

    private fun setData(task: Task) {
        val tvTitle: TextInputEditText = findViewById(R.id.detail_ed_title)
        val tvDes: TextInputEditText = findViewById(R.id.detail_ed_description)
        val tvDueDate: TextInputEditText = findViewById(R.id.detail_ed_due_date)

        tvTitle.setText(task.title)
        tvDes.setText(task.description)
        tvDueDate.setText(DateConverter.convertMillisToString(task.dueDateMillis))
    }

    private fun showConfirmDialog() {
        val builder = AlertDialog.Builder(this)
        val mConfirmDialog = builder.create()
        builder.setTitle(getString(R.string.delete_task))
        builder.setMessage(getString(R.string.sure_delete_task))
        builder.create()

        builder.setPositiveButton(getString(R.string.yes)) { _, _ ->
            detailTaskViewModel.deleteTask()
        }

        builder.setNegativeButton(getString(R.string.no)) { _, _ ->
            mConfirmDialog.cancel()
        }
        builder.show()
    }
}