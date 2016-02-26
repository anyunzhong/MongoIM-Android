package im.mongo.ui.emotion;

import android.content.Intent;
import android.util.ArrayMap;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhonganyun on 16/2/11.
 */
public class EmotionManager {

    private final static EmotionManager manager = new EmotionManager();
    private List<BaseEmotion> emotions = new ArrayList<>();
    private Map<Integer, BaseEmotion> emotionMap = new HashMap<>();
    private int pageSize;
    private Map<Integer, Integer> emotionIndexMap = new HashMap<>();

    private EmotionManager() {

    }

    public static EmotionManager sharedInstance() {
        return manager;
    }


    public void addEmotion(BaseEmotion emotion) {
        emotions.add(emotion);

        //通过全局page_index查找emotion
        for (int i = pageSize; i < pageSize + emotion.getPageCount(); i++) {
            emotionMap.put(i, emotion);
            emotionIndexMap.put(i, emotions.size() - 1);
        }
        //当前emotion在全局emotions中的起点 这样可以将全局page_index换算成局部的page_index
        emotion.setPageOffset(pageSize);
        pageSize += emotion.getPageCount();


    }

    public List<BaseEmotion> getEmotions() {
        return emotions;
    }

    public int getPageSize() {
        return pageSize;
    }

    public View getView(int index) {
        BaseEmotion emotion = emotionMap.get(index);
        return emotion.getPageView(index - emotion.getPageOffset());
    }

    //通过全局的page_index获得改emotion在所有emotions数组的位置
    public int getEmotionIndex(int index) {
        return emotionIndexMap.get(index);
    }

    public BaseEmotion getEmotion(int index) {
        return emotionMap.get(index);
    }


}
