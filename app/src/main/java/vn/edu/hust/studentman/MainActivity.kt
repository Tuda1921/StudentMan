package vn.edu.hust.studentman

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

  private val students = mutableListOf(StudentModel(
    "Nguyễn Văn An", "SV001"),
    StudentModel("Trần Thị Bảo", "SV002"),
    StudentModel("Lê Hoàng Cường", "SV003"),
    StudentModel("Phạm Thị Dung", "SV004"),
    StudentModel("Đỗ Minh Đức", "SV005"),
    StudentModel("Vũ Thị Hoa", "SV006"),
    StudentModel("Hoàng Văn Hải", "SV007"),
    StudentModel("Bùi Thị Hạnh", "SV008"),
    StudentModel("Đinh Văn Hùng", "SV009"),
    StudentModel("Nguyễn Thị Linh", "SV010"),
    StudentModel("Phạm Văn Long", "SV011"),
    StudentModel("Trần Thị Mai", "SV012"),
    StudentModel("Lê Thị Ngọc", "SV013"),
    StudentModel("Vũ Văn Nam", "SV014"),
    StudentModel("Hoàng Thị Phương", "SV015"),
    StudentModel("Đỗ Văn Quân", "SV016"),
    StudentModel("Nguyễn Thị Thu", "SV017"),
    StudentModel("Trần Văn Tài", "SV018"),
    StudentModel("Phạm Thị Tuyết", "SV019"),
    StudentModel("Lê Văn Vũ", "SV020")
  )
  private lateinit var studentAdapter: StudentAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val listView: ListView = findViewById(R.id.list_view_students)
    studentAdapter = StudentAdapter(this, students)
    listView.adapter = studentAdapter

    // Đăng ký ContextMenu
    registerForContextMenu(listView)

    // Sự kiện khi nhấn vào item trong ListView
    listView.onItemClickListener =
      AdapterView.OnItemClickListener { _, _, position, _ ->
        val student = students[position]
        Toast.makeText(this, "Chọn: ${student.studentName}", Toast.LENGTH_SHORT).show()
      }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.main_menu, menu)
    return super.onCreateOptionsMenu(menu)
  }


  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.menu_add_new -> {
        // Mở Activity để thêm sinh viên
        val intent = Intent(this, AddStudentActivity::class.java)
        startActivityForResult(intent, 100) // Gọi Activity và chờ kết quả trả về
        true // Trả về true để thông báo rằng sự kiện đã được xử lý
      }
      else -> super.onOptionsItemSelected(item) // Trả về mặc định nếu không phải menu_add_new
    }
  }


  override fun onCreateContextMenu(
    menu: ContextMenu?,
    v: View?,
    menuInfo: ContextMenu.ContextMenuInfo?
  ) {
    super.onCreateContextMenu(menu, v, menuInfo)
    menuInflater.inflate(R.menu.context_menu, menu)
  }

  override fun onContextItemSelected(item: MenuItem): Boolean {
    val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
    val position = info.position

    return when (item.itemId) {
      R.id.context_edit -> {
        val intent = Intent(this, EditStudentActivity::class.java)
        intent.putExtra("studentName", students[position].studentName)
        intent.putExtra("studentId", students[position].studentId)
        intent.putExtra("position", position)
        startActivityForResult(intent, 100)
        true
      }
      R.id.context_remove -> {
        students.removeAt(position)
        studentAdapter.notifyDataSetChanged()
        Toast.makeText(this, "Xóa thành công!", Toast.LENGTH_SHORT).show()
        true
      }
      else -> super.onContextItemSelected(item)
    }
  }
  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    // Kiểm tra nếu kết quả là OK và dữ liệu không null
    if (resultCode == RESULT_OK && data != null) {
      when (requestCode) {
        100 -> { // Thêm mới sinh viên
          // Lấy giá trị từ Intent một cách an toàn
          val name = data.getStringExtra("studentName")
          val id = data.getStringExtra("studentId")

          // Log cả name và id
          Log.d("onActivityResult", "Name: $name, ID: $id")

          // Kiểm tra xem name và id có null không
          if (name != null && id != null) {
            // Thêm sinh viên vào danh sách
            students.add(StudentModel(name, id))
            studentAdapter.notifyDataSetChanged() // Cập nhật lại RecyclerView
            Log.d("onActivityResult", "Sinh viên đã được thêm: Name = $name, ID = $id")
          } else {
            // Nếu name hoặc id null, log lỗi
            Log.e("onActivityResult", "Dữ liệu sinh viên không hợp lệ (name hoặc id null)")
          }
        }
      }
    } else {
      // Log nếu kết quả không hợp lệ hoặc data null
      Log.e("onActivityResult", "Kết quả không hợp lệ hoặc data null. resultCode = $resultCode, data = $data")
    }
  }
}
