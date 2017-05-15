package com.shipin.player.gui.video;

import android.os.Message;
import android.util.Log;

import com.shipin.player.MediaLibrary;
import com.shipin.player.interfaces.IVideoBrowser;
import com.shipin.player.util.WeakHandler;

public class VideoListHandler extends WeakHandler<IVideoBrowser> {
    public static final int UPDATE_ITEM = 0;
    public static final int MEDIA_ITEMS_UPDATED = 100;

    public VideoListHandler(IVideoBrowser owner) {
        super(owner);
    }

    @Override
    public void handleMessage(Message msg) {
        IVideoBrowser owner = getOwner();
        if(owner == null) return;

        switch (msg.what) {
            case UPDATE_ITEM:
                owner.updateItem();
                break;
            case MediaLibrary.MEDIA_ITEMS_UPDATED:
                Log.d("load", "MEDIA_ITEMS_UPDATED");
                owner.updateList();
                break;
        }
    }
};