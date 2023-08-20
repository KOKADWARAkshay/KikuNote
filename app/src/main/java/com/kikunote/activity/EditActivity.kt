package com.kikunote.activity

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.awesomedialog.*
import com.kikunote.R
import com.kikunote.databinding.ActivityEditBinding
import com.kikunote.entity.Note
import com.kikunote.method.DateChange
import com.kikunote.viewModel.NotesViewModel


class EditActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityEditBinding
    val editNoteExtra = "edit_note_extra"
    private lateinit var notesViewModel: NotesViewModel
    private lateinit var note: Note
    private var isUpdate = false
    private val dateChange = DateChange()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initViewModel()
        initListener()

        binding.bold.setOnClickListener(this)
        binding.italic.setOnClickListener(this)
        binding.bullet.setOnClickListener(this)

    }

    private fun initView() {

        if (intent.getParcelableExtra<Note>(editNoteExtra) != null) {

            isUpdate = true
            binding.buttonDelete.visibility = View.VISIBLE

            note = intent.getParcelableExtra(editNoteExtra)!!
            binding.editTextTitle.setText(note.title)
            binding.editTextBody.setText(note.body)
            binding.editTextTitle.setSelection(note.title.length)

            //set spinner position
            val compareValue = note.label
            val adapter = ArrayAdapter.createFromResource(
                this,
                R.array.labels,
                android.R.layout.simple_spinner_item
            )

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            binding.spLabel.adapter = adapter

            val spinnerPosition = adapter.getPosition(compareValue)
            binding.spLabel.setSelection(spinnerPosition)

        }

    }

    private fun initViewModel() {
        notesViewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
    }


    private fun initListener() {
        binding.toolbar.nibBack.setOnClickListener(this)
        binding.toolbar.btnSave.setOnClickListener(this)
        binding.buttonDelete.setOnClickListener(this)
    }

    private fun deleteNote(note: Note) {
        notesViewModel.deleteNote(note)
        Toast.makeText(this@EditActivity, "Note removed", Toast.LENGTH_SHORT).show()
    }

    private fun showDialog() {

        AwesomeDialog.build(this)
            .position(AwesomeDialog.POSITIONS.CENTER)
            .body("The note will be permanently deleted.", color = ContextCompat.getColor(this, R.color.colorTitle))
            .background(R.drawable.background_dialog)
            .icon(R.mipmap.ic_launcher)
            .onPositive(
                "Yes, delete",
                buttonBackgroundColor = android.R.color.transparent,
                textColor = ContextCompat.getColor(this, R.color.colorTitle)
            ) {
                deleteNote(note)
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                finish()
            }
            .onNegative(
                "Cancel",
                buttonBackgroundColor = R.drawable.bg_btn_black,
                textColor = ContextCompat.getColor(this, R.color.background)
            ) {

            }

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.nib_back -> {
                onBackPressed()
            }
            R.id.btn_save -> {
                val title = binding.editTextTitle.text.toString()
                val body = binding.editTextBody.text.toString()
                val label = binding.spLabel.selectedItem.toString()
                val date = dateChange.getToday()
                val time = dateChange.getTime()

                if (title.isEmpty() && body.isEmpty()) {
                    Toast.makeText(this@EditActivity, "Note cannot be empty", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    if (isUpdate) {
                        notesViewModel.updateNote(
                            Note(
                                id = note.id,
                                title = title,
                                label = label,
                                date = note.date,
                                time = note.time,
                                updatedDate = date,
                                updatedTime = time,
                                body = body
                            )
                        )
                    } else {
                        notesViewModel.insertNote(
                            Note(
                                title = title,
                                label = label,
                                date = date,
                                time = time,
                                body = body
                            )
                        )
                    }

                    Toast.makeText(this@EditActivity, "Note saved", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    finish()
                }

            }

            R.id.button_delete -> {
                showDialog()
            }

            R.id.bold -> {
                applyFormattingToSelectedText(Typeface.BOLD)
            }
            R.id.italic-> {
                applyFormattingToSelectedText(Typeface.ITALIC)
            }
            R.id.bullet -> {
                insertBulletPoint()
            }
        }
    }
    private fun applyFormattingToSelectedText(style: Int) {
        val selectedTextRange = binding.editTextBody.selectionStart to binding.editTextBody.selectionEnd
        val spannableString = SpannableString(binding.editTextBody.text)
        spannableString.setSpan(StyleSpan(style), selectedTextRange.first, selectedTextRange.second, Spannable.SPAN_INCLUSIVE_INCLUSIVE)

        val editableText = binding.editTextBody.editableText
        editableText.replace(selectedTextRange.first, selectedTextRange.second, spannableString)
    }


    private fun insertBulletPoint() {
        val cursorPosition = binding.editTextBody.selectionStart
        val currentLineStart = binding.editTextBody.text.lastIndexOf('\n', cursorPosition - 1) + 1
        val bulletPoint = "\u2022 " // Unicode bullet point character
        val indentedText = bulletPoint + binding.editTextBody.text.subSequence(currentLineStart, cursorPosition)
        binding.editTextBody.text.replace(currentLineStart, cursorPosition, indentedText)
    }
}