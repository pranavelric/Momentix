package com.alarm.momentix.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alarm.momentix.BR
import com.alarm.momentix.R
import com.alarm.momentix.data.model.Alarm
import com.alarm.momentix.databinding.ItemAlarmBinding
import com.alarm.momentix.utils.TimePickerUtil

class AlarmRcAdapter() :
    ListAdapter<Alarm, AlarmRcAdapter.MyAlarmViewHolder>(AlarmItemDiffCallback()) {
    inner class MyAlarmViewHolder(private val binding: ItemAlarmBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(alarm: Alarm, position: Int) {
            binding.setVariable(BR.myAlarm, alarm)
            binding.executePendingBindings()
            binding.itemAlarmTime.text = TimePickerUtil.getFormattedTime(alarm.hour, alarm.minute)
            binding.itemAlarmStarted.setOnCheckedChangeListener { buttonView, isChecked ->
                onAlarmCancelClickListener?.let { click ->
                    click(alarm, isChecked)
                }
            }
            binding.itemAlarmRecurringDelete.setOnClickListener {
                onAlarmDeleteClickListener?.let { click ->
                    click(alarm,position)
                }
            }

            binding.alarmCard.setOnClickListener {

                onItemClickListener?.let { click ->
                    click(alarm)
                }
            }

        }

    }

    class AlarmItemDiffCallback : DiffUtil.ItemCallback<Alarm>() {
        override fun areItemsTheSame(oldItem: Alarm, newItem: Alarm): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Alarm, newItem: Alarm): Boolean {
            return oldItem.alarmId == newItem.alarmId
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAlarmViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemAlarmBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_alarm, parent, false)
        return MyAlarmViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyAlarmViewHolder, position: Int) {
        holder.bind(getItem(position),position)
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    private var onItemClickListener: ((Alarm) -> Unit)? = null
    fun setOnItemClickListener(listener: (Alarm) -> Unit) {
        onItemClickListener = listener
    }

    private var onAlarmCancelClickListener: ((Alarm, Boolean) -> Unit)? = null
    fun setOnAlarmCancelClickListener(listener: (Alarm, Boolean) -> Unit) {
        onAlarmCancelClickListener = listener
    }

    private var onAlarmDeleteClickListener: ((Alarm,Int) -> Unit)? = null
    fun setAlarmDeleteClickListener(listener: (Alarm,Int) -> Unit) {
        onAlarmDeleteClickListener = listener
    }


}