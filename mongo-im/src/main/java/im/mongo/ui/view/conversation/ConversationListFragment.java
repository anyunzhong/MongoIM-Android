package im.mongo.ui.view.conversation;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.datafans.android.common.event.BaseEvent;
import net.datafans.android.common.widget.table.PlainTableView;
import net.datafans.android.common.widget.table.TableView;
import net.datafans.android.common.widget.table.TableViewCell;
import net.datafans.android.common.widget.table.TableViewDataSource;
import net.datafans.android.common.widget.table.TableViewDelegate;
import net.datafans.android.common.widget.table.refresh.RefreshControlType;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import im.mongo.MongoIM;
import im.mongo.R;
import im.mongo.core.model.Conversation;
import im.mongo.core.storage.ConversationStorageService;
import im.mongo.ui.event.ConnectSuccessEvent;
import im.mongo.ui.event.MessageReceiveEvent;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConversationListFragment extends Fragment implements
        TableViewDataSource<Conversation>, TableViewDelegate {


    private TableView<Conversation> tableView;

    private List<Conversation> conversations;

    public ConversationListFragment() {
        conversations = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        if (tableView == null) {
            PlainTableView.Builder<Conversation> builder = new PlainTableView.Builder<>();

            builder.setContext(getActivity());
            builder.setRefreshType(RefreshControlType.None);
            builder.setDataSource(this).setDelegate(this);
            tableView = builder.build();
            tableView.getView().setBackgroundColor(Color.WHITE);
        }

        return tableView.getView();
    }

    @Override
    public void onResume() {
        super.onResume();

        refresh();
    }

    @Override
    public int getRows(int section) {
        return conversations.size();
    }


    @Override
    public int getItemViewType(int i, int i1) {
        return 0;
    }

    @Override
    public int getItemViewTypeCount() {
        return 1;
    }


    @Override
    public TableViewCell<Conversation> getTableViewCell(int section, int row) {
        return new ConversationCell(R.layout.conversation_cell, getActivity());
    }

    @Override
    public Conversation getEntity(int section, int row) {
        return conversations.get(row);
    }


    @Override
    public void onClickRow(int section, int row) {
        MongoIM.sharedInstance().startPrivateConversation(getActivity(), getEntity(section,row).getTargetId());
    }


    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }


    public void onEvent(BaseEvent event){
        if (event instanceof ConnectSuccessEvent) {
           refresh();
        }
    }

    public void onEventMainThread(BaseEvent event){
        if (event instanceof MessageReceiveEvent) {
           refresh();
        }
    }

    private void refresh(){
        conversations = MongoIM.sharedInstance().getMessageHandler().getConversations();
        tableView.reloadData();
    }


}
