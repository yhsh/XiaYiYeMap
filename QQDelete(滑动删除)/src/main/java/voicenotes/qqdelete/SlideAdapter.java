package voicenotes.qqdelete;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

/**
 * @author ：下一页5（轻飞扬）
 *         创建时间：2018/6/27.10:10
 */
public class SlideAdapter extends BaseAdapter implements View.OnClickListener {

    private List<String> dataList;
    private Context context;
    private LayoutInflater inflater;

    public SlideAdapter(Context context, List<String> dataList) {
        this.context = context;
        this.dataList = dataList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            View content = inflater.inflate(R.layout.item_cardlist, null);
            View menu = inflater.inflate(R.layout.adapter_item_menu, null);
            holder = new ViewHolder(content, menu);
            SlideItem slideItem = new SlideItem(context);
            slideItem.setContentView(content, menu);
            convertView = slideItem;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.itemTvDelete.setOnClickListener(this);
        holder.tv_content.setText(dataList.get(position));
        return convertView;
    }

    class ViewHolder {
        TextView tv_content;
        TextView itemTvDelete;

        public ViewHolder(View center, View menu) {
            this.itemTvDelete = (TextView) menu.findViewById(R.id.item_delete);
            this.tv_content = (TextView) center.findViewById(R.id.reapal_tv_bindCard_list_item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_delete:
                Toast.makeText(context, "删除啦", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
