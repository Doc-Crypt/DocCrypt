package com.upt.cti.doccrypt.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.upt.cti.doccrypt.R
import com.upt.cti.doccrypt.entity.Notification
import com.upt.cti.doccrypt.entity.NotificationStatus.*

class NotificationDrawerAdapter (private val notifications: ArrayList<Notification>) : RecyclerView.Adapter<NotificationDrawerAdapter.NotificationHolder>() {

    class NotificationHolder(view: View) : RecyclerView.ViewHolder(view) {
        var notificationText: TextView
        var notificationStatus: ImageView
        var notificationBorderColor : CardView

        init {
            notificationStatus = view.findViewById(R.id.notification_status)
            notificationText = view.findViewById(R.id.notification_card_text)
            notificationBorderColor = view.findViewById(R.id.notification_card)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notification_card, parent, false)
        return NotificationHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationHolder, position: Int) {
        holder.notificationText.text = notifications[position].notificationText
        when(notifications[position].notificationStatus){
            CHECKED -> holder.notificationStatus.setImageResource(R.drawable.checked_green)
            DENIED ->  {
                holder.run {
                    notificationStatus.setImageResource(R.drawable.denied_red)
                    notificationBorderColor.setCardBackgroundColor(Color.RED)
                }
            }
         }
    }

    override fun getItemCount() = notifications.size

}