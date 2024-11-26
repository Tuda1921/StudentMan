package vn.edu.hust.studentman

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class EditStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_student)

        val editName: EditText = findViewById(R.id.editTextName)
        val editId: EditText = findViewById(R.id.editTextStudentId)
        val buttonUpdate: Button = findViewById(R.id.buttonUpdate)

        val name = intent.getStringExtra("studentName")
        val id = intent.getStringExtra("studentId")
        val position = intent.getIntExtra("position", -1)

        editName.setText(name)
        editId.setText(id)

        buttonUpdate.setOnClickListener {
            val updatedName = editName.text.toString()
            val updatedId = editId.text.toString()
            if (updatedName.isNotEmpty() && updatedId.isNotEmpty()) {
                val intent = intent
                intent.putExtra("studentName", updatedName)
                intent.putExtra("studentId", updatedId)
                intent.putExtra("position", position)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
    }
}
