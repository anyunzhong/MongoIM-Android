package im.mongo.ui.view.body;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import net.datafans.android.common.helper.LogHelper;
import net.datafans.android.common.widget.table.PlainTableView;
import net.datafans.android.common.widget.table.TableView;
import net.datafans.android.common.widget.table.TableViewCell;
import net.datafans.android.common.widget.table.TableViewDataSource;
import net.datafans.android.common.widget.table.TableViewDelegate;
import net.datafans.android.common.widget.table.refresh.RefreshControlType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import im.mongo.MongoIM;
import im.mongo.core.model.Message;
import im.mongo.ui.view.template.BaseMessageTemplate;
import im.mongo.ui.view.template.MessageTemplateManager;


public class MessageTableViewFragment extends Fragment implements
        TableViewDataSource<Message>, TableViewDelegate {

    private static final int TIME_SHOW_INTERVAL = 3 * 60 * 1000;

    private TableView<Message> tableView;


    private List<Message> messages = new ArrayList<>();

    private Map<Integer, Message> messageMap = new HashMap<>();


    private Delegate delegate;


    public MessageTableViewFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (tableView == null) {

            PlainTableView.Builder<Message> builder = new PlainTableView.Builder<>();
            builder.setContext(getActivity());
            builder.setRefreshType(RefreshControlType.SwipeRefresh);
            builder.setDataSource(this).setDelegate(this);
            tableView = builder.build();
            tableView.getView().setBackgroundColor(Color.rgb(235, 235, 235));
            tableView.hideDivider();
            tableView.getAdapter().getListView().setStackFromBottom(true);
            tableView.getAdapter().getListView().setSelector(new ColorDrawable(Color.TRANSPARENT));
        }

        return tableView.getView();
    }

    @Override
    public int getRows(int section) {
        return messages.size();
    }

    @SuppressWarnings("unchecked")
    @Override
    public TableViewCell<Message> getTableViewCell(int section, int row) {
        Message message = messages.get(row);
        MessageTemplateManager manager = MessageTemplateManager.sharedInstance();
        BaseMessageTemplate template = (BaseMessageTemplate) manager.create(message.getContentType());
        template.setCurrentActivity(getActivity());
        return template;
    }

    @Override
    public Message getEntity(int section, int row) {
        return messages.get(row);
    }


    @Override
    public int getItemViewType(int section, int row) {

        Message message = getEntity(section, row);
        MessageTemplateManager manager = MessageTemplateManager.sharedInstance();
        return manager.getTypeIndex(message.getContentType());
    }

    @Override
    public int getItemViewTypeCount() {
        return MessageTemplateManager.sharedInstance().getCellTypeCount();
    }


    @Override
    public void onClickRow(int section, int row) {

    }

    @Override
    public void onRefresh() {

        //加载历史消息

        if (delegate != null) {
            if (!messages.isEmpty()) {
                int messageId = messages.get(0).getMessageId();
                delegate.onLoadHistoryMessageMore(messageId);
            }
        }

        tableView.endRefresh();
    }

    @Override
    public void onLoadMore() {

    }

    public void refresh() {
        tableView.reloadData();
    }


    public void changeMessageStatus(int messageId, Message.SendStatus sendStatus) {

        Message message = getMessage(messageId);

        if (message == null) return;

        message.setSendStatus(sendStatus);

        tableView.reloadData();
    }


    public void addMessage(Message message) {

        if (!message.getReceiveStatus().isRead()) {
            MongoIM.sharedInstance().getMessageHandler().setMessageReceiveStatus(message.getMessageId(), new Message.ReceiveStatus(Message.ReceiveStatus.READ));
        }

        long lastTs = 0;
        if (!messages.isEmpty()) {
            Message lastMessage = messages.get(messages.size() - 1);
            lastTs = lastMessage.getReceivedTime();
        }

        messages.add(message);
        messageMap.put(message.getMessageId(), message);

        if ((message.getReceivedTime() - lastTs) >= TIME_SHOW_INTERVAL) {
            message.setShowTime(true);
        }

        tableView.reloadData();
        scrollToBottom();
    }

    public void addMessages(List<Message> messages) {
        for (Message message : messages) {
            this.messages.add(0, message);
            messageMap.put(message.getMessageId(), message);
        }

        long lastTs = 0;
        if (!messages.isEmpty()) {
            for (Message message : this.messages) {
                if ((message.getReceivedTime() - lastTs) >= TIME_SHOW_INTERVAL) {
                    message.setShowTime(true);
                }
                lastTs = message.getReceivedTime();
            }
        }

        tableView.reloadData();
    }

    public Message getMessage(int messageId) {
        return messageMap.get(messageId);
    }

    public void deleteMessage(int messageId) {
        Message message = getMessage(messageId);
        messages.remove(message);
        tableView.reloadData();
    }


    public void scrollToBottom() {

        ListView listView = tableView.getAdapter().getListView();
        listView.setSelection(listView.getBottom());
        LogHelper.debug("滑动到底部");
    }

    public void setDelegate(Delegate delegate) {
        this.delegate = delegate;
    }


    public static interface Delegate {

        void onLoadHistoryMessageMore(int messageId);
    }
}
