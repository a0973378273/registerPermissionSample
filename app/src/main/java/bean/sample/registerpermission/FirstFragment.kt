package bean.sample.registerpermission


import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Intent
import android.content.Intent.ACTION_OPEN_DOCUMENT_TREE
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import bean.sample.registerpermission.databinding.FragmentFirstBinding
import java.security.Permission

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //use SAF in external SDCard
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val data = Uri.parse("content://com.android.externalstorage.documents/document/primary%3AAndroid%2Fdata")
            val obb = Uri.parse("content://com.android.externalstorage.documents/document/primary%3AAndroid%2Fobb")
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {}
                .launch(Intent(ACTION_OPEN_DOCUMENT_TREE).apply {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
                    putExtra(DocumentsContract.EXTRA_INITIAL_URI, obb)
            })
        }
        Permission
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
//                if (Environment.isExternalStorageManager()) {
//                    println("get file permission in android 11 or above")
//                }
//            }.launch(Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION))
//        } else {
//            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
//                if (it) {
//                    println("get WRITE_EXTERNAL_STORAGE permission")
//                }
//            }.launch(WRITE_EXTERNAL_STORAGE)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}