package bean.sample.registerpermission

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.DocumentsContract
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity


@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun AppCompatActivity.accessAndroidDataFilePermission(listener: (ActivityResult) -> Unit) {
    val data =
        Uri.parse("content://com.android.externalstorage.documents/document/primary%3AAndroid%2Fdata")
    registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        //save permission
        contentResolver.takePersistableUriPermission(
            it.data?.data!!,
            Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        )
        listener.invoke(it)
    }.launch(Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            putExtra(DocumentsContract.EXTRA_INITIAL_URI, data)
            flags =
                Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION or Intent.FLAG_GRANT_PREFIX_URI_PERMISSION
        }
    })
}

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
private fun AppCompatActivity.accessAndroidObbFilePermission(listener: (ActivityResult) -> Unit) {
    val obb =
        Uri.parse("content://com.android.externalstorage.documents/document/primary%3AAndroid%2Fobb")
    registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        //save permission
        contentResolver.takePersistableUriPermission(
            it.data?.data!!,
            Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        )
        listener.invoke(it)
    }.launch(Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            putExtra(DocumentsContract.EXTRA_INITIAL_URI, data)
            flags =
                Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION or Intent.FLAG_GRANT_PREFIX_URI_PERMISSION
        }
    })
}

/**
 * return null if no folder permission
 */
private fun AppCompatActivity.getSAFList(uri: Uri): List<String> {
    val fileNameList = ArrayList<String>()
    runCatching {
        val newUri = DocumentsContract.buildChildDocumentsUriUsingTree(
            uri,
            DocumentsContract.getDocumentId(uri)
        )
        val cursor = contentResolver.query(
            newUri,
            arrayOf(DocumentsContract.Document.COLUMN_DOCUMENT_ID),
            null,
            null,
            null
        )
        cursor?.let {
            while (cursor.moveToNext()) {
                fileNameList.add(it.getString(0))
            }
        }
    }
    return fileNameList
}