package cs246.scripturememorization;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class VerseAdapter extends RecyclerView.Adapter<VerseAdapter.VerseAdapterViewHolder> {

    private List<Verses> versesList;

    private final VerseAdapterOnClickHandler clickHandler;

    private Context context;

    public interface VerseAdapterOnClickHandler {
        void onClick(String verseId, String verseKey);
    }

    public VerseAdapter(Context context, VerseAdapterOnClickHandler clickHandler) {
        this.clickHandler = clickHandler;
        this.context = context;
    }

    public class VerseAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView verseTitleTextView;

        public VerseAdapterViewHolder(View view) {
            super(view);
            verseTitleTextView = view.findViewById(R.id.verse_title);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            String verseTitle = versesList.get(adapterPosition).getVerseTitle();
            String verseId = versesList.get(adapterPosition).getVerseId();
            clickHandler.onClick(verseTitle, verseId);
        }
    }

    @NonNull
    @Override
    public VerseAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();

        int layoutIdForListItem = R.layout.verse_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        return new VerseAdapterViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull VerseAdapterViewHolder holder, int position) {
        Verses currentVerse = versesList.get(position);
        holder.verseTitleTextView.setText(currentVerse.getVerseTitle());
    }

    @Override
    public int getItemCount() {
        if(versesList == null) {
            return 0;
        }
        return versesList.size();
    }

    public void setVersesList(List<Verses> verses) {
        versesList = verses;
        notifyDataSetChanged();
    }
}
