package cs246.scripturememorization;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ChapterAdapterViewHolder> {

    private List<Chapters> chaptersList;

    private final ChapterAdapterOnClickHandler clickHandler;

    private Context context;

    public interface ChapterAdapterOnClickHandler {
        void onClick(String String chapterId, String chapterKey);
    }

    public ChapterAdapter(Context context, ChapterAdapterOnClickHandler clickHandler) {
        this.clickHandler = clickHandler;
        this.context = context;
    }

    public class ChapterAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView chapterTitleTextView;

        public ChapterAdapterViewHolder(View view) {
            super(view);
            chapterTitleTextView = view.findViewById(R.id.chapter_title);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            String chapterTitle = chaptersList.get(adapterPosition).getChapterTitle();
            String chapterId = chaptersList.get(adapterPosition).getChapterId();
            clickHandler.onClick(chapterTitle, chapterId);
        }
    }

    @NonNull
    @Override
    public ChapterAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();

        int layoutIdForListItem = R.layout.chapter_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        return new ChapterAdapterViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ChapterAdapterViewHolder holder, int position) {
        Chapters currentChapter = chaptersList.get(position);
        holder.chapterTitleTextView.setText(currentChapter.getChapterTitle());
    }

    @Override
    public int getItemCount() {
        if(chaptersList == null) {
            return 0;
        }
        return chaptersList.size();
    }

    public void setChaptersList(List<Chapters> chapters) {
        chaptersList = chapters;
        notifyDataSetChanged();
    }
}
