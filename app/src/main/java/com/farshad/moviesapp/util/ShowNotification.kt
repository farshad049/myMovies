package com.farshad.moviesapp.util

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavDeepLinkBuilder
import com.farshad.moviesapp.R
import com.farshad.moviesapp.ui.MainActivity
import javax.inject.Inject

class ShowNotification @Inject constructor(val activity : Activity,val context : Context ) {

     fun showNotification(title: String ,channelDescription: String , navigationActionName: String , movieId : Int = 0 ){
        //I can use it if i want to sent the notification to main activity

//        val intent = Intent(requireContext() , MainActivity::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        }
//       val pendingIntent = PendingIntent.getActivity(requireContext() , 0 , intent , 0)

        //put movie id like safe args, so the destination fragment will receive the movie id, remember the key "movieId" should be the same in nav_graph
        val movieIdInNotification = Bundle()
        movieIdInNotification.putInt(navigationActionName, movieId)
        //make an intent to sent to destination fragment
        val pendingIntent = NavDeepLinkBuilder(context)
            .setComponentName(MainActivity::class.java)
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.moviesDetailFragment)
            .setArguments(movieIdInNotification)
            .createPendingIntent()




        val builder = NotificationCompat.Builder(context , Constants.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_round_local_movies_24)
            .setContentTitle(title)
            .setContentText(channelDescription)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channelName = title
            val channelDescriptionB = channelDescription
            val channelPriority = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(Constants.CHANNEL_ID , channelName , channelPriority ).apply {
                description = channelDescriptionB
            }

            val notificationManager = activity.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }

        with(NotificationManagerCompat.from(context)){
            notify(Constants.NOTIFICATION_ID, builder.build())
        }
    }







     fun showBigNotification( title: String ,channelDescription: String , navigationActionName: String , movieId : Int = 0 , imageUri : Uri?){
//        val intent = Intent(requireContext() , MainActivity::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        }
//        val pendingIntent = PendingIntent.getActivity(requireContext() , 0 , intent , 0)

        val movieIdInNotification = Bundle()
        movieIdInNotification.putInt(navigationActionName, movieId)

        val pendingIntent = NavDeepLinkBuilder(context)
            .setComponentName(MainActivity::class.java)
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.moviesDetailFragment)
            .setArguments(movieIdInNotification)
            .createPendingIntent()

        val movieImage = NotificationCompat.BigPictureStyle()
            .bigPicture(imageUri?.let { convertUriToBitmap(it) })
            .setBigContentTitle(title)

        val builder = NotificationCompat.Builder(context , Constants.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_round_local_movies_24)
            .setContentTitle(title)
            .setContentText(channelDescription)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setStyle(movieImage)
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channelName = title
            val channelDescriptionB = channelDescription
            val channelPriority = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(Constants.CHANNEL_ID, channelName , channelPriority ).apply {
                description = channelDescriptionB
            }

            val notificationManager = activity.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }

        with(NotificationManagerCompat.from(context)){
            notify(Constants.NOTIFICATION_ID_BIG_STYLE, builder.build())
        }
    }





    private fun convertUriToBitmap(uri: Uri): Bitmap {
        return if (Build.VERSION.SDK_INT < 28) {
            MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        } else {
            val source= ImageDecoder.createSource(context.contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        }
    }






}