import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.food.foodporterapplication.R
import com.food.foodporterapplication.customer.activity.addcategoryitemdeatils.OnAddOnItemListener
import com.food.foodporterapplication.customer.model.CustomizationItem

class AddonItemAdapter(
    private val items: List<CustomizationItem>,
    private val listener: OnAddOnItemListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_TITLE = 0
        const val VIEW_TYPE_ITEM = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is CustomizationItem.SectionHeader -> VIEW_TYPE_TITLE
            is CustomizationItem.OptionItem -> VIEW_TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_TITLE) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_addon_section, parent, false)
            TitleViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.add_on_item_layout, parent, false)
            OptionViewHolder(view)
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TitleViewHolder -> holder.bind(items[position] as CustomizationItem.SectionHeader)
            is OptionViewHolder -> holder.bind(items[position] as CustomizationItem.OptionItem)
        }
    }

    inner class TitleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: CustomizationItem.SectionHeader) {
            itemView.findViewById<TextView>(R.id.sectionTitle).text = item.title
            itemView.findViewById<TextView>(R.id.sectionSubtitle).text =
                "Select up to ${item.maxSelect} options"
        }
    }

    inner class OptionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: CustomizationItem.OptionItem) {
            val checkBox = itemView.findViewById<CheckBox>(R.id.cbItem)
            val label = itemView.findViewById<TextView>(R.id.tvItemName)
            val price = itemView.findViewById<TextView>(R.id.tvItemPrice)

            label.text = item.name
            price.text = "Rs.${item.price}"
            checkBox.isChecked = item.isSelected

            checkBox.setOnCheckedChangeListener(null)
            checkBox.isChecked = item.isSelected
                checkBox.setOnCheckedChangeListener { _, isChecked ->
                    item.isSelected = isChecked
                    listener.onItemCheckedChanged(item.price,isChecked)
                }
            }
        }
    }


