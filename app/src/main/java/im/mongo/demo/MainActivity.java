package im.mongo.demo;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import net.datafans.android.common.widget.controller.FragmentController;

import im.mongo.MongoIM;
import im.mongo.ui.view.conversation.ConversationListFragment;


public class MainActivity extends FragmentController {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Fragment getRootFragment() {
        return new ConversationListFragment();
    }

    @Override
    protected String getNavTitle() {
        return "消息";
    }

    @Override
    protected boolean enableReturnButton() {
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add) {

            final EditText editText = new EditText(this);
            editText.setTextColor(Color.BLACK);
            AlertDialog.Builder dialog = new AlertDialog.Builder(this).setTitle("输入用户ID").setView(
                    editText).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (editText.getText().toString().isEmpty()) return;
                    MongoIM.sharedInstance().startPrivateConversation(MainActivity.this, editText.getText().toString());
                }
            }).setNegativeButton("取消", null);

            dialog.show();


            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
