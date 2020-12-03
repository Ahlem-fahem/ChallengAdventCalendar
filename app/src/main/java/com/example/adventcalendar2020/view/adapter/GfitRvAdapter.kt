package com.example.adventcalendar2020.view.adapter


import android.animation.Animator
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.adventcalendar2020.R
import com.example.adventcalendar2020.data.AppDatabase
import com.example.adventcalendar2020.data.Day
import kotlinx.android.synthetic.main.rv_gift_item.view.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors
import kotlin.collections.ArrayList


class GiftRvAdapter : RecyclerView.Adapter<GiftRvAdapter.SingleViewHolder>() {

    private var dayList =  ArrayList<Day>()
    fun setList(giftList: List<Day>){
        dayList = giftList as ArrayList<Day>
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): SingleViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.rv_gift_item, viewGroup, false)
        return SingleViewHolder(view)
    }

    override fun onBindViewHolder(singleViewHolder: SingleViewHolder, position: Int) {
        singleViewHolder.bind(dayList[position])
    }

    override fun getItemCount(): Int {
        return dayList.size
    }


    inner class SingleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(day : Day) {
            itemView.apply {
                val dateSubstring = day.date.substring(0,5)
                dateText.text = dateSubstring
                if (day.isOpened){
                    giftIcon.visibility = View.VISIBLE
                    lottie_animation.visibility = View.INVISIBLE
                    giftIcon.setImageResource(day.giftDrawable)
                } else {
                    giftIcon.visibility = View.GONE
                    lottie_animation.visibility = View.VISIBLE
                }


            /*    if (notAvailableDay(day)){
                    cardView.setBackgroundColor(ContextCompat.getColor(context,R.color.light_grey))
                    cardView.setOnClickListener {
                       Toast.makeText(context,"rendez-vous le ${dateSubstring} pour découvrir votre cadeau !",Toast.LENGTH_LONG).show()
                    }
                }else {*/
                    cardView.setBackgroundColor(ContextCompat.getColor(context,android.R.color.white))
                    cardView.setOnClickListener {
                        showDialog(context,day,adapterPosition)

                    }
                //}
            }

        }


    }


     fun notAvailableDay(day: Day) : Boolean{
         val sdf = SimpleDateFormat("dd-MM-yyyy")
         val strDate = sdf.parse(day.date)
         return !Date().after(strDate)
     }

    fun showDialog(context: Context,day: Day,position: Int){
        val dialogBuilder = AlertDialog.Builder(context)
        val dialogCostumeView = View.inflate(context, R.layout.gift_dialog_custom_view, null)
        dialogBuilder.setView(dialogCostumeView)

        val alertDialog = dialogBuilder.create()
        dialogCostumeView.lottie_animation.addAnimatorListener(object :
            Animator.AnimatorListener {
            override fun onAnimationEnd(animation: Animator) {
            }

            override fun onAnimationCancel(animation: Animator) {
            }

            override fun onAnimationStart(animation: Animator?) {

            }

            override fun onAnimationRepeat(animation: Animator) {
                day.isOpened = true
                Executors.newSingleThreadExecutor().execute {
                    AppDatabase.getInstance(context).let {
                        it.dayDao().insertOrUpdate(day)
                    }
                }
                notifyItemChanged(position)
                alertDialog.dismiss()

            }
        })

        alertDialog.show()
    }
}