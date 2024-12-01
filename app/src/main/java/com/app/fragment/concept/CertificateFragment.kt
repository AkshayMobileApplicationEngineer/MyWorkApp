import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.app.MainActivity
import com.teampanlogic.R

class CertificateFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = inflater.inflate(R.layout.fragment_certificate, container, false)

        val recipientNameEditText = binding.findViewById<EditText>(R.id.recipientName)
        val emailEditText = binding.findViewById<EditText>(R.id.emailField)
        val registrationDateEditText = binding.findViewById<EditText>(R.id.registrationDate)
        val courseNameEditText = binding.findViewById<EditText>(R.id.courseName)
        val submitButton = binding.findViewById<Button>(R.id.submitButton)

        submitButton.setOnClickListener {
            val recipientName = recipientNameEditText.text.toString()
            val email = emailEditText.text.toString()
            val registrationDate = registrationDateEditText.text.toString()
            val courseName = courseNameEditText.text.toString()

            if (recipientName.isEmpty() || email.isEmpty() || registrationDate.isEmpty() || courseName.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(requireContext(), MainActivity::class.java)
                intent.putExtra("Display", "shareCertificate")
                intent.putExtra("recipientName", recipientName)
                intent.putExtra("email", email)
                intent.putExtra("registrationDate", registrationDate)
                intent.putExtra("courseName", courseName)
                startActivity(intent)
                Toast.makeText(requireContext(), "Certificate data passed successfully", Toast.LENGTH_LONG).show()
            }
        }

        return binding
    }
}

