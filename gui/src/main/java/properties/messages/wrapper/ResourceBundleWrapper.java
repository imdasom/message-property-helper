package properties.messages.wrapper;

import com.konai.common.util.FileUtils;
import com.konai.common.vo.Key;
import com.konai.common.vo.MessageProperty;
import com.konai.common.vo.Value;
import com.konai.search.util.MessageTokenizer;
import com.konai.search.vo.Message;

import java.io.IOException;
import java.util.*;

public class ResourceBundleWrapper {

    private Map<Key, Message> resourceMap;

    public ResourceBundleWrapper() {
        resourceMap = new HashMap<>();
    }

    public void add(String location, String bundleName, Locale locale) throws IOException {
        ResourceBundle bundle = FileUtils.getResourceBundle(location, bundleName, locale);
        MessageTokenizer tokenizer = new MessageTokenizer();
        Map<String, String> messagePropertyMap = tokenizer.getMapFromResource(bundle);
        Map<Key, Message> resourceTokenList = tokenizer.getTokenListFromMap(messagePropertyMap);
        resourceMap.putAll(resourceTokenList);
    }

    public Map<Key, Message> getResourceMap() {
        return resourceMap;
    }

    public List<MessageProperty> getResourceMapToList() {
        List<MessageProperty> messageProperties = new ArrayList<>();
        for(Map.Entry e : resourceMap.entrySet()) {
            Key key = new Key(String.valueOf(e.getKey()));
            Value value = new Value(String.valueOf(e.getValue()));
            messageProperties.add(new MessageProperty(key, value));
        }
        return messageProperties;
    }
}
