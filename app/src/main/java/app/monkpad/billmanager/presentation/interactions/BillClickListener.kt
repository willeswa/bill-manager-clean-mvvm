package app.monkpad.billmanager.presentation.interactions

import app.monkpad.billmanager.framework.models.BillDTO

class BillClickListener(val clickListener: (bill: BillDTO) -> Unit) {
    fun onBillClicked(bill: BillDTO) = clickListener(bill)
}