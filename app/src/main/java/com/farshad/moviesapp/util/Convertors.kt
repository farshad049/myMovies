package com.farshad.moviesapp.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.ByteArrayOutputStream
import java.io.File

open class Convertors {


    fun convertBitmapTOBase64(bitmap: Bitmap):String{
        val stream= ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream)
        val image= stream.toByteArray()
        return Base64.encodeToString(image, Base64.DEFAULT)
    }


    fun convertUriToBitmap(context : Context, uri: Uri): Bitmap {
        return if (Build.VERSION.SDK_INT < 28) {
            MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        } else {
            val source= ImageDecoder.createSource(context.contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        }
    }


    // convert uri to file request body
    fun convertImagePathToRequestBody(path:String , fieldName:String): MultipartBody.Part? {

        var imageRequestBody: MultipartBody.Part?= null

        if (!path.equals("", ignoreCase = true)) {
            val file = File(path)
            val reqFile = file.asRequestBody("image/*".toMediaType())
            imageRequestBody = MultipartBody.Part.createFormData(fieldName, file.name, reqFile)
        }
        return imageRequestBody
    }

}