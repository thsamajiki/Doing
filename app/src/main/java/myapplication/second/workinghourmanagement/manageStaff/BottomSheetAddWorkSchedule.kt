package myapplication.second.workinghourmanagement.manageStaff

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import myapplication.second.workinghourmanagement.RetrofitManager
import myapplication.second.workinghourmanagement.databinding.BottomSheetAddWorkScheduleBinding
import myapplication.second.workinghourmanagement.dto.ResultResponse
import myapplication.second.workinghourmanagement.dto.workTime.WorkAddRequest
import myapplication.second.workinghourmanagement.retrofit.WorkTimeService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BottomSheetAddWorkSchedule(
    context: Context,
    private val employmentId: Int,
    private val staffId: Int
) :
    BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetAddWorkScheduleBinding
    private val mContext: Context = context
    private lateinit var service: WorkTimeService
    private lateinit var start: String
    private lateinit var finish: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BottomSheetAddWorkScheduleBinding.inflate(layoutInflater)
        service = RetrofitManager.retrofit.create(WorkTimeService::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.startTime.setOnClickListener {
            binding.startTimePicker.visibility = View.VISIBLE
            binding.finishTimePicker.visibility = View.GONE
        }
        binding.finishTime.setOnClickListener {
            binding.startTimePicker.visibility = View.GONE
            binding.finishTimePicker.visibility = View.VISIBLE
        }

        binding.startTimePicker.setOnTimeChangedListener { timePicker, hourOfDay, minute ->
            var hour12 = hourOfDay
            val isPm = hourOfDay >= 12
            if (hourOfDay > 12) hour12 -= 12
            else if (hourOfDay == 0) hour12 = 12
            binding.startTime.text =
                String.format("%s %02d:%02d", if (isPm) "오후" else "오전", hour12, minute)
            start = String.format("%02d:%02d:00", hourOfDay, minute)
        }
        binding.finishTimePicker.setOnTimeChangedListener { timePicker, hourOfDay, minute ->
            var hour12 = hourOfDay
            val isPm = hourOfDay >= 12
            if (hourOfDay > 12) hour12 -= 12
            else if (hourOfDay == 0) hour12 = 12
            binding.finishTime.text =
                String.format("%s %02d:%02d", if (isPm) "오후" else "오전", hour12, minute)
            finish = String.format("%02d:%02d:00", hourOfDay, minute)
        }

        binding.btnOk.setOnClickListener {
            val checkedDay = mutableListOf<String>()
            val checkboxIds = arrayOf(
                binding.checkboxMon,
                binding.checkboxTue,
                binding.checkboxWed,
                binding.checkboxThu,
                binding.checkboxFri,
                binding.checkboxSat,
                binding.checkboxSun
            )
            for(checkboxId in checkboxIds){
                if(checkboxId.isChecked){
                    when(checkboxId.text){
                        "월" -> checkedDay.add("MONDAY")
                        "화" -> checkedDay.add("TUESDAY")
                        "수" -> checkedDay.add("WEDNESDAY")
                        "목" -> checkedDay.add("THURSDAY")
                        "금" -> checkedDay.add("FRIDAY")
                        "토" -> checkedDay.add("SATURDAY")
                        "일" -> checkedDay.add("SUNDAY")
                    }
                }
            }

            val data = WorkAddRequest(checkedDay, finish, staffId, start)
            service.addWorkTime(employmentId, data).enqueue(object : Callback<ResultResponse>{
                override fun onResponse(
                    call: Call<ResultResponse>,
                    response: Response<ResultResponse>
                ) {
                }

                override fun onFailure(call: Call<ResultResponse>, t: Throwable) {
                    Log.e("addWorkTime fail", t.message.orEmpty())
                }
            })
            dismiss()
        }
        binding.title.btnBottomSheetClose.setOnClickListener {
            Toast.makeText(mContext, "닫기", Toast.LENGTH_SHORT).show()
            dismiss()
        }
        return binding.root
    }
}