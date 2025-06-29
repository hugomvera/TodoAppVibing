package com.example.todoappvibing
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
//import com.example.todoapp.ui.theme.TodoAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
          //  TodoAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ) {
                    TaskListScreen()
                //}
            }
        }
    }
}

// Data class
data class Task(
    val id: Int,
    val text: String,
    val isDone: Boolean
)

@Composable
fun TaskRow(task: Task, onCheckedChange: (Boolean) -> Unit, onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = task.isDone,
            onCheckedChange = onCheckedChange
        )
        Text(
            text = task.text,
            modifier = Modifier.weight(1f).padding(start = 8.dp),
            style = TextStyle(
                color = Color.White,
                fontSize = 18.sp,
                textDecoration = if (task.isDone) TextDecoration.LineThrough else TextDecoration.None
            )
        )
        IconButton(onClick = onDelete) {
            Icon(Icons.Default.Close, contentDescription = "Delete", tint = Color.Red)
        }
    }
}

@Composable
fun TaskListScreen() {
    var title by remember { mutableStateOf("Laundry") }
    var tasks by remember {
        mutableStateOf(
            listOf(
                Task(1, "take out trash", false),
                Task(2, "make food", true),
                Task(3, "garbage", false)
            )
        )
    }
    var newTaskId by remember { mutableIntStateOf(4) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title", color = Color.White) },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedTextColor = Color.White,
                    unfocusedBorderColor = Color.White,
                    focusedTextColor = Color.White,
                    focusedBorderColor = Color.Cyan
                ),
                textStyle = TextStyle(color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                tasks = tasks + Task(newTaskId++, "New Task", false)
            }) {
                Text("+")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(tasks) { task ->
                TaskRow(
                    task = task,
                    onCheckedChange = { isChecked ->
                        tasks = tasks.map {
                            if (it.id == task.id) it.copy(isDone = isChecked) else it
                        }
                    },
                    onDelete = {
                        tasks = tasks.filterNot { it.id == task.id }
                    }
                )
            }
        }
    }
}
