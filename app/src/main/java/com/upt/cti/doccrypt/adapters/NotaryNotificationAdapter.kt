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

class NotaryNotificationAdapter (private val notifications: ArrayList<Notification>) : RecyclerView.Adapter<NotaryNotificationAdapter.NotaryNotificationHolder>() {

    class NotaryNotificationHolder(view: View) : RecyclerView.ViewHolder(view) {
        var notificationText: TextView
        var notificationStatus: ImageView
        var notificationBorderColor : CardView

        init {
            notificationStatus = view.findViewById(R.id.notification_status)
            notificationText = view.findViewById(R.id.notification_card_text)
            notificationBorderColor = view.findViewById(R.id.notification_card)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotaryNotificationHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notification_card, parent, false)
        return NotaryNotificationHolder(view)
    }

    override fun onBindViewHolder(holder: NotaryNotificationHolder, position: Int) {
        when (notifications[position].notificationStatus) {
            PENDING -> holder.run {
                notificationText.text = notifications[position].notificationText
                notificationStatus.setImageResource(R.drawable.pending_blue)
                notificationBorderColor.setCardBackgroundColor(Color.CYAN)
            }
            else -> {}
        }
    }

    override fun getItemCount() = notifications.size

}