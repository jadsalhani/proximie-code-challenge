package com.jadsalhani.proximiedatafeed.features.details

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.text.Html.FROM_HTML_MODE_COMPACT
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.jadsalhani.proximiedatafeed.R
import com.jadsalhani.proximiedatafeed.domain.volume.Volume
import com.jadsalhani.proximiedatafeed.network.volume.VolumeAPIImpl
import com.squareup.picasso.Picasso
import java.io.File
import java.io.File.separator
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions

@RuntimePermissions
class DetailsActivity : AppCompatActivity() {

    private val apiImpl: VolumeAPIImpl =
        VolumeAPIImpl()
    lateinit var volumeID: String
    var volume: Volume? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        intent.getStringExtra("volumeID")?.also { id ->
            volumeID = id
        }

        GlobalScope.launch(Dispatchers.Main) {

            details_progress_bar.visibility = View.VISIBLE

            volume = getVolumeDetails(volumeID)

            if (volume != null) {
                details_progress_bar.visibility = View.GONE
                setupView()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupView() {
        volume_title_text.text = volume?.volumeInfo?.title

        volume?.volumeInfo?.imageLinks?.small?.let {
            Picasso.get().load(it).into(volume_thumbnail_image)
        }

        val publishingInfo =
            volume?.volumeInfo?.publisher + " - " + volume?.volumeInfo?.publishedDate
        volume_publisher_text.text = publishingInfo
        volume_average_rating_text.text = volume?.volumeInfo?.averageRating.toString()

        // fromHTML needed since description returns in HTML format to be displayed as is

        if (!volume?.volumeInfo?.description.isNullOrEmpty()) {
            volume_description_text.text =
                Html.fromHtml(volume?.volumeInfo?.description, FROM_HTML_MODE_COMPACT)
        }

        when (volume?.accessInfo?.pdf?.isAvailable) {
            true -> {
                val downloadURL = volume?.accessInfo?.pdf?.downloadLink
                if (!downloadURL.isNullOrEmpty()) {
                    volume_pdf_download_btn.visibility = View.VISIBLE

                    volume_pdf_download_btn.setOnClickListener { view ->
                        downloadVolumePDF(downloadURL)
                    }
                }
            }
            else -> {
            }
        }

        if (volume?.volumeInfo?.authors != null) {
            volume_authors_text.text = volume?.volumeInfo?.authors?.joinToString()
        }
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun downloadVolumePDF(pdfURL: String) {
        Log.d("Download PDF", pdfURL)
        GlobalScope.launch {
            downloadPDFVersion(pdfURL)
        }
    }

    private suspend fun getVolumeDetails(volumeID: String): Volume? {
        try {
            val result = apiImpl.getVolumeByIDAsync(volumeID).await()
            return result
        } catch (e: Throwable) {
            Log.e("API ERROR", e.localizedMessage!!)
        }
        return null
    }

    private suspend fun downloadPDFVersion(downloadURL: String) {
        val response = apiImpl.downloadVolumePDFAsync(downloadURL).await()
        saveVolumePDFToStorage(response)
    }

    private fun saveVolumePDFToStorage(body: ResponseBody): Boolean {
        try {
            // todo change the file location/name according to your needs
            val fileStorageDirectory = getExternalFilesDir(null)?.path + separator + "${volume?.volumeInfo?.title}.pdf"
            val volumePDF = File(fileStorageDirectory)

            Log.d("DetailsActivity", "Downloading file to: $fileStorageDirectory")

            var inputStream: InputStream? = null
            var outputStream: OutputStream? = null

            try {
                val fileReader = ByteArray(4096)

                val fileSize = body.contentLength()
                var fileSizeDownloaded: Long = 0

                inputStream = body.byteStream()
                outputStream = FileOutputStream(volumePDF)

                while (true) {
                    val read = inputStream.read(fileReader)

                    if (read == -1) {
                        break
                    }

                    outputStream.write(fileReader, 0, read)

                    fileSizeDownloaded += read.toLong()

                    Log.d("DetailsActivity", "file download: $fileSizeDownloaded of $fileSize")
                }

                outputStream.flush()

                // Open file after saving is complete
                val intent = Intent(Intent.ACTION_VIEW)
                // FileProvider is needed to move the file to external for the application to read it
                intent.setDataAndType(FileProvider.getUriForFile(applicationContext, "${applicationContext.packageName}.provider", volumePDF), "application/pdf")
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                startActivity(intent)

                return true
            } catch (e: IOException) {
                return false
            } finally {
                inputStream?.close()
                outputStream?.close()
            }
        } catch (e: IOException) {
            return false
        }
    }
}
